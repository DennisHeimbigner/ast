/*
 * Copyright (c) 1998 - 2010. University Corporation for Atmospheric Research/Unidata
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */

/*
Mimicking protoc, this code
generates a single file for the top-level package.
Thus, if a user has imports, then those import files
must be separately compiled.
*/

package unidata.protobuf.compiler;

import gnu.getopt.Getopt;

import java.util.*;
import java.io.*;

public class JavaGenerator extends Generator
{
    //////////////////////////////////////////////////

    static final String DFALTDIR = ".";

    static final String DIGITCHARS = "0123456789";
    static final String IDCHARS =
	  "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	+ "abcdefghijklmnopqrstuvwxyz"
	+ DIGITCHARS
	+ "_$";

    static final short IDMAX = 0x7f;
    static final short IDMIN = ' ';

    //////////////////////////////////////////////////
    // Define the per-node extra info; grouped here into a single class.

    static public class Annotation
    {
	String packagename = null;
	String refpath = null;
	String outerclass = null;
    }

    //////////////////////////////////////////////////
    // Instance variables

    String outputdir = DFALTDIR;

    String outerclass = null; // used also for the output file name

    //////////////////////////////////////////////////
    // Constructor

    public JavaGenerator()
    {
    }	 

    //////////////////////////////////////////////////
    // Command line processing

    List<String>
    processcommandline(String[] argv)
    {
	int c;
	List<String> arglist = new ArrayList<String>();
	Getopt g = new Getopt("JavaGenerator",argv,"-:o",null);
	while ((c = g.getopt()) != -1) {
	    switch (c) {
	    case 1: // intermixed non-option
		arglist.add(g.getOptarg());
		break;		
	    case ':':
		System.err.println("Command line option requires argument "+g.getOptopt());
		System.exit(1);
	    case '?':
		System.err.println("Illegal cmd line option: "+g.getOptopt());
		System.exit(1);
	    // True options start here
	    case 'o':
		String dir = g.getOptarg();
		if(dir != null && dir.length() > 0) outputdir = dir;
		break;	
	    default:
		System.err.println("Unexpected getopt tag: "+c);
		System.exit(1);
	    }
	}
	return arglist;
    }
    //////////////////////////////////////////////////

/*
- compute the Java package names for all the packages
- compute the filename for the top package
- compute the reference path for each message and enum
- for each enum in top package
   - generate the top level enum definitions
   - generate the top level message classes
     - for each message, generate the ED functions
*/

    public boolean generate(String[] argv, AST.Root root) throws Exception
    {
	List<String> arglist = processcommandline(argv);

	// Assign annotation objects 
	for(AST ast: root.getNodeSet()) {
	    switch (ast.getSort()) {
	    case PACKAGE: case MESSAGE: case ENUM:
		Annotation a = new Annotation();
		ast.setAnnotation(a);
		break;
	    default: break;
	    }
	}

	// Compute the Java package names for all the packages
	for(AST.Package p: root.getPackageSet()) {
	    Annotation a = (Annotation)p.getAnnotation();
	    a.packagename = p.optionLookup("java_package");
	    if(a.packagename == null) a.packagename = p.getName();
	}

	// compute the outer class / filename for the all packages
	for(AST.Package p: root.getPackageSet()) {
	    Annotation a = (Annotation)p.getAnnotation();
            a.outerclass = computeouterclassname(p);
	}

	// and save that of the top package and truncate the corresponding file
	AST.Package toppackage = root.getTopPackage();
        Annotation a = (Annotation)toppackage.getAnnotation();
	outerclass = a.outerclass;
        // Now, truncate
	FileWriter filewriter = null;
	String filename = outputdir + "/" + this.outerclass+".j";
	try {
	    filewriter = new FileWriter(filename);
	} catch (Exception e) {
	    System.err.println("Cannot access file: "+filename);
	    return false;
	}
	// close the file
	try {filewriter.close();} catch (Exception e) {};

	// Compute the java reference path for each message and enum
        List<AST> path = new ArrayList<AST>();
	for(AST ast: root.getNodeSet()) {
	    a = (Annotation)ast.getAnnotation();
	    switch (ast.getSort()) {
	    case MESSAGE: case ENUM:
		collectpath(ast,path,false);
		a.refpath = computejavapath(path,outerclass);
		break;
	    default: break;
	    }
	}

	// Generate the top package's content
        Printer printer = null;
	// Open the .java file for this package
	try {
	    printer = new Printer(filename);
	    generate_header(toppackage,printer);
	    generate_content(toppackage,printer);
	    generate_trailer(toppackage,printer);
	    printer.close();
	} catch (Exception e) {
	    System.err.println("Generation Failure: "+filename+":"+e);
	    return false;
	}
	return true;
    } // generate()

    void generate_header(AST.Package p, Printer printer) throws Exception
    {
	Annotation a = (Annotation)p.getAnnotation();
	printer.println("package " + p.getName() + ";");
	
	// Add imports
	printer.blankline();
	printer.println("import java.io.*;");
	printer.println("import java.util.*;");

	printer.blankline();
	printer.println("import Unidata.protobuf.*;");

	printer.blankline();
	for(AST.Package p1: p.getRoot().getPackageSet()) {
	    if(p1 == p) continue;
	    a = (Annotation)p1.getAnnotation();
	    printer.printf("import %s.*;\n",a.packagename);
	}

        // Open the outerclass
	printer.blankline();
	printer.println("public class "+outerclass);
	printer.println("{");
    }

    void generate_trailer(AST.Package p, Printer printer) throws Exception
    {
	Annotation a = (Annotation)p.getAnnotation();
	// Close the outer class
	printer.println("} //"+outerclass);
    }

    void generate_content(AST.Package p, Printer printer) throws Exception
    {
	// Generate the enum definitions
	for(AST ast: p.getChildSet()) {
	    if(ast.getSort() != AST.Sort.ENUM) continue;
	    generate_enum((AST.Enum)ast,printer);
	}

	// Generate the message definitions (including recursive contents)
	for(AST ast: p.getChildSet()) {
	    if(ast.getSort() != AST.Sort.MESSAGE) continue;
	    generate_message((AST.Message)ast,printer);
	}
    }


    void generate_enum(AST.Enum e, Printer printer) throws Exception
    {
	printer.blankline();
	Annotation a = (Annotation)e.getAnnotation();
	printer.blankline();
	printer.println("public enum");
	printer.println("{");
            List<AST.EnumValue> values = e.getEnumValues();
            int nvalues = values.size();
            for(int i=0;i<nvalues;i++) {
                AST.EnumValue eval = values.get(i);
                printer.printf("%s(%d)%s\n",
                    eval.getName(),
                    eval.getValue(),
                    (i == (nvalues - 1)?";":","));
            }
            // Dump the value accessors
            printer.println("private final int value;");
            printer.printf("%s(int value) {this.value = value;}",e.getName());
            printer.println("public String getValue() {return value;}");
	    printer.printf("public %s getEnum(int value)\n",e.getName());
	    printer.println("{");
	    printer.println("switch (value) {");
		for(AST.EnumValue ev: e.getEnumValues()) {
		    printer.printf("case %d: return %s;\n",ev.getValue(),ev.getName());
		}
		printer.println("default:");
		printer.println("throw Unidata.protobuf.Runtime.Error(\"Illegal Enumeration value\");");
	    printer.println("} // switch");
	    printer.println("} // getEnum");
	printer.println("} //"+e.getName());
    }

    void generate_message(AST.Message msg, Printer printer) throws Exception
    {
        Annotation a = (Annotation)msg.getAnnotation();
	// Generate the class header
	printer.blankline();
	printer.printf("static public class %s\n",msg.getName());
	printer.println("{");

	// Generate the class fields
	for(AST.Field field: msg.getFields()) {
	    if(field.getCardinality() == AST.Cardinality.REPEATED) {
	        printer.printf("List<%s> %s = %s;\n",
				javatypefor(field.getType()),
				field.getName(),
				defaultfor(field));
	    } else {
	        printer.printf("%s %s = %s;\n",
				javatypefor(field.getType()),
				field.getName(),
				defaultfor(field));
	    }
 	}

	// Generate get instance function
	printer.blankline();
	printer.printf("static %s defaultInstance = new %s();\n",msg.getName(),msg.getName());
	printer.printf("static public %s getDefaultInstance() {return defaultInstance;}\n",
			msg.getName());

	// Generate the serialize/deserialize functions

	// Generate the serialize function
	printer.blankline();
	printer.println("public boolean writeTo(EDF edf) throws Unidata.protobuf.Runtime.Error");
	printer.println("{");
	for(AST.Field field: msg.getFields()) {
	    AST.Type fieldtype = field.getType();
	    if(fieldtype.getSort() == AST.Sort.PRIMITIVETYPE) {
	        printer.printf("edf.%s_writeTo(%s);\n",
			field.getType().getName(),field.getName());
	    } else if(fieldtype.getSort() == AST.Sort.ENUM) {
	        printer.printf("edf.int_writeTo(%s);\n",
			       field.getName());
	    } else { // User defined type
	        printer.printf("%s.writeTo(edf);\n",field.getName());
	    }
 	}
	printer.println("} // writeTo"); // serialize function trailer

	// Generate the deserialize function
	printer.blankline();
	printer.printf("public %s readFrom(EDF edf) throws Unidata.protobuf.Runtime.Error\n",
			msg.getName());
	printer.println("{");
	printer.println("int key;");
	printer.println("while((key = edf.peekc()) >= 0) {");
        printer.println("int wiretype = (key|0x7);");
        printer.println("int tag = (key >> 3);");
        printer.println("switch (tag) {");
	for(AST.Field field: msg.getFields()) {
	    generate_deserialize_field(field,printer);
	    printer.println("} break;");
	}
        // add default
        printer.println("default:");
        printer.println("edf.skip_field();");

        printer.println("};\n"); // switch
        printer.println("};\n"); // while
	printer.println("} // readFrom"); // deserialize function trailer

	// generate class trailer
	printer.println("} //"+msg.getName());
    }


    void 
    fieldwriteto(AST.Field field, Printer printer) throws IOException
    {
        AST.Type fieldtype = field.getType();
	if(fieldtype.getSort() == AST.Sort.PRIMITIVETYPE) {
	    printer.printf("edf.%s_readFrom(%s);\n",
			   field.getType().getName(),field.getName());
        } else if(fieldtype.getSort() == AST.Sort.ENUM) {// User defined enum
	    printer.printf("edf.int_readFrom(%s.getValue());\n",
			   field.getName());
        } else { //User defined msg type
	    printer.printf("%s.readFrom(edf);\n",field.getName());
        }
    }

    boolean
    generate_deserialize_field(AST.Field field, Printer printer) throws IOException
    {
	
	printer.printf("case %d: {\n",field.getId());
	switch (field.getCardinality()) {	
        case REQUIRED:
	    fieldreadfrom(field,printer);
	    break;
        case OPTIONAL:
	    // Assume that every field has been initialized with some default value
	    fieldreadfrom(field,printer);
	    break;
        case REPEATED:
	    // The assumption here is that the field is a list<T> with
            // the default = new ArrayList<T>.
	    // Would prefer to use T[], but we do not know the size in advance
	    fieldreadfromrepeated(field,printer);
	    break;
	}
	return true;
    }

    void 
    fieldreadfrom(AST.Field field, Printer printer) throws IOException
    {
        AST.Type fieldtype = field.getType();
	if(fieldtype.getSort() == AST.Sort.PRIMITIVETYPE) {
	    printer.printf("%s = edf.%s_readFrom();\n",
			   field.getName(),field.getType().getName());
        } else if(fieldtype.getSort() == AST.Sort.ENUM) {// User defined enum
	    printer.printf("int enumid = -1;");
	    printer.println("enumid = edf.int_readFrom();");
	    printer.printf("%s = %s.getEnum(enumid);",
			   field.getName(),fieldtype.getName());
        } else { //User defined msg type
	    printer.printf("%s = %s.readFrom(edf);\n",
			   field.getName(),field.getType().getName());
        }
    }

    void 
    fieldreadfromrepeated(AST.Field field, Printer printer) throws IOException
    {
        AST.Type fieldtype = field.getType();
	String typename = getrefname(fieldtype);
	String javatypename = javatypefor(fieldtype);
	if(fieldtype.getSort() == AST.Sort.PRIMITIVETYPE) {
	    printer.printf("%s value = edf.%s_readFrom();\n",
			   javatypename,typename);
        } else if(fieldtype.getSort() == AST.Sort.ENUM) {// User defined enum
	    printer.println("int enumid = edf.int_readFrom();");
	    printer.printf("%s value = %s.getEnum(enumid);",
				typename,typename);
        } else { //User defined msg type
	    printer.printf("%s value = %s.readFrom(edf);\n",
				typename,typename);
        }
        printer.printf("%s.add(value);\n",field.getName());
    }

    /*
     * Compute the outer class name.  This is done
     * in a different way than is used by protoc --java_out.
     * The rule here is as follows:
     * 1. Start with the base of the file name (i.e. minus any .proto suffix)
     * 2. Replace all non-java characters with underscore
     * 3. If the initial character is a digit, then prefix with an underscore
     * 3. Capitolize the initial character if it is alphabetic
    */
    String
    computeouterclassname(AST.Package p)
    {
        AST.File file = p.getPackageFile();
        File f = new File(file.getName());
        StringBuilder outername;

        // remove any trailing ".proto"
	outername = new StringBuilder(f.getName());
	int len = outername.length();
        if(outername.substring(len - 6, len).equals(".proto"))
	    outername.setLength(len-6);
	// Replace non-java id characters
        for(int i=0;i<outername.length();i++) {
            char c = outername.charAt(i);
            if(c < IDMIN || (c < IDMAX && IDCHARS.indexOf(c) < 0))
                outername.setCharAt(i,'_');
        }
	// Make sure name does not start with digit
	if(DIGITCHARS.indexOf(outername.charAt(0)) >= 0)
	    outername.insert(0,'_');
	// Capitolize first character
        outername.setCharAt(0,Character.toUpperCase(outername.charAt(0)));
        return outername.toString();
    }

    // Collect path of parent nodes upto
    // and (optionally) including the package
    void
    collectpath(AST ast, List<AST> path, boolean thrupackage)
    {
        path.clear();
        AST parent = ast;
        while(parent.getSort() != AST.Sort.ROOT) {
            if(parent.getSort() == AST.Sort.PACKAGE && !thrupackage) break;
            path.add(0,parent);
            parent = parent.getParent();
        }
    }

    String
    computejavapath(List<AST> path, String outerclass)
    {
        String spath = outerclass;
        for(int i=0;i<path.size();i++) {
            AST ast = path.get(i);
            spath = spath + "." + ast.getName();
        }
        return spath;
    }

    String
    getrefname(AST ast)
    {
	Annotation a = (Annotation)ast.getAnnotation();
	if(a == null) return ast.getName();
	return a.refpath;
    }


    String
    javatypefor(AST.Type asttype)
    {
	String jtype = null;
	
	if(asttype.getSort() == AST.Sort.PRIMITIVETYPE) {
            switch (((AST.PrimitiveType)asttype).getPrimitiveSort()) {
	    case SINT32:
	    case SFIXED32:
	    case UINT32:
	    case FIXED32:
	    case INT32:	  jtype = "Integer"; break;

	    case SINT64:
	    case SFIXED64:
	    case UINT64:
	    case FIXED64:
	    case INT64:   jtype = "Long"; break;

	    case FLOAT:	  jtype = "Float"; break;
	    case DOUBLE:  jtype = "Double"; break;

	    case BOOL:	  jtype = "Boolean"; break;
	    case STRING:  jtype = "String"; break;

	    case BYTES:	  jtype = "Unidata.protobuf.Runtime.Bytestring"; break;
	    // No default because we want the compiler to complain if any new
	    // types are added.
	    }
	} else if(asttype.getSort() == AST.Sort.ENUM) {
	    jtype = getrefname(asttype);
	} else if(asttype.getSort() == AST.Sort.MESSAGE) {
	    jtype = getrefname(asttype);
	} else { // Illegal
	    System.err.println("Cannot translate type to Java Type: "+asttype.getName());
	
        }
	return jtype;
    }

    String
    defaultfor(AST.Field field)
    {
	AST.Type fieldtype = field.getType();
        if(field.getCardinality() == AST.Cardinality.REPEATED) {
	    // repeated default is always a list of the type of the field
	    return String.format("new ArrayList<%s>()",javatypefor(fieldtype));
	} else {
   	    // See if the field has a defined default
            Object value = field.optionLookup("DEFAULT");
	    if(value == null) return "null";
	    if(fieldtype.getSort() == AST.Sort.PRIMITIVETYPE) {
	        return (String)value;
	    } else if(fieldtype.getSort() == AST.Sort.ENUM) {
	        return (String)value;
	    } else if(fieldtype.getSort() == AST.Sort.MESSAGE) {
	        return String.format("%s.getDefaultInstance()",
					getrefname(fieldtype));
	    }
	}
	return "null";	
    } 


} // JavaGenerator


