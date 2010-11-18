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
The code generated by protoc for java is a bit odd.
In order to generate only a single output file,
protoc forces the existence of a single outer class
into which everything else in the .proto file is defined.
The name of the single outer class is taken either from the
option "java_outer_classname" or from the name of the file
with all non-alphnumeric characters deleted and
the remainer converted to upper case.
(i.e. t_x.proto is used to make class TX).

This code is intended to be non-interpretive,
so it must produce an output file for each
package decl=> using the import file name
and assuming that each file contains as most
one package decl.
*/

package unidata.protobuf.compiler;

import gnu.getopt.Getopt;

import java.util.*;
import java.io.*;

public class JavaGenerator implements Generator
{
    //////////////////////////////////////////////////

    static final String DFALTDIR = ".";

    //////////////////////////////////////////////////
    // Define the per-node extra info; grouped here into a single class.

    static public class Annotation
    {
	String packagename = null;
	String outerclass = null; // also used as file name
	String refpath = null;
    }

    //////////////////////////////////////////////////
    // Instance variables

    String outputdir = DFALTDIR;

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
    }
    //////////////////////////////////////////////////

/*
- compute the Java package names for all the packages
- compute the filenames for each package
- compute the reference path for each message and enum
- for each enum in package p
   - generate the top level enum definitions
   - generate the top level message classes
     - for each message, generate the ED functions
*/

    public boolean generate(String[] argv, AST root) throws Exception
    {
	List<String> arglist = processcommandline();

	// Assign annotation objects 
	for(AST ast: root.getNodeSet()) {
	    switch (ast.getSort()) {
	    case PACKAGE: MESSAGE: case ENUM:
		Annotation a = new Annotation();
		ast.setAnnotation(a);
		break;
	    default: break;
	    }
	}

	// Compute the Java package names for all the packages
	for(AST.Package p: root.getPackageSet()) {
	    Annotation a = p.getAnnotation();
	    a.packagename = p.optionLookup("java_package");
	    if(a.packagename == null) a.packagename = p.getName();
	}

	// compute the outer class / filename for all the packages
	// Truncate each such filename
	for(AST p0: root.getPackageSet()) {
	    String base = computeouterclassname(p0);
	    // Make sure name is unique
	    int counter = 1;
	    String outerclass = base;
	    for(boolean ok=false;ok;) {
		ok = true;
		for(AST p1: root.getPackageSet()) {
		    Annotation a = (Annotation)p1.getAnnotation();
		    if(outerclass.equals(a.outerclass)) {
			outerclass = base + counter++;
		        ok = false;
		    }
		}
	    }
	    Annotation a = (Annotation)p0.getAnnotation();
	    a.outerclass = outerclass;
	    // Now, truncate
	    FileWriter filewriter = null;
	    String filename = outputdir + "/" + a.outerclass+".java";
	    try {
		filewriter = new FileWriter(filename);
	    } catch (Exception e) {
		System.err.println("Cannot access file: "+filename);
		return false;
	    }
	    try {filewriter.close();} catch (Exception e) {};
	}

	// Compute the java reference path for each message and enum
        List<AST> path = new ArrayList<AST>();
	for(AST ast: root.getNodeSet()) {
	    Annotation a = ast.getAnnotation();
	    switch (ast.getSort()) {
	    case MESSAGE: CASE ENUM:
		collectpath(ast,path,false)
		a.refpath = computejavapath(path);
		break;
	    default: break;
	    }
	}

	// Generate each package's content
	for(AST.Package p0: root.getPackageSet()) {
	    Annotation a = p0.getAnnotation();
	    Printer printer = null;
	    // Open the .java file for this package
   	    String filename = outputdir + "/" + a.outerclass+".java";
  	    try {
		printer = new Printer(filename);
		generate_header(po,printern);
		generate_content(po,printer);
		generate_trailer(po,printer);
		printer.close();
	    } catch (Exception e) {
		System.err.println("Generation Failure: "+filename+":"+e);
		return false;
	    }
	}
	return true;
    } // generate()

    void generate_header(AST.Package p, Printer printer) throws Exception
    {
	Annotation a = p.getAnnotation();
	printer.println("package " + p.packagename() + ";")
	printer.println("");
	
        // Open the outerclass
	printer.println("public class "+a.outerclass);
	printer.println("{");
    }

    void generate_trailer(AST.Package p, Printer printer) throws Exception
    {
	Annotation a = p.getAnnotation();
	// Close the outer class
	printer.println("} //"+a.outerclass);
    }

    void generate_content(AST.Package p, Printer printer) throws Exception
    {
	// Generate the enum definitions
	for(AST ast: p.getChildSet()) {
	    if(ast.getSort() != AST.Sort.ENUM) continue;
	    generate_enum((AST.Enum)ast,pw);
	}

	// Generate the message definitions (including recursive contents)
	for(AST ast: p.getChildSet()) {
	    if(ast.getSort() != AST.Sort.MESSAGE) continue;
	    generate_message((AST.Message)ast,pw);
	}
    }


    void generate_enum(AST.Enum e, Printer printer) throws Exception
    {
	Annotation a = e.getAnnotation();
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
	    printer.println("public %s getEnum(int value)",e.getName());
	    printer.println("{");
	    printer.println("switch (value) {");
		for(AST.EnumValue ev: e.getEnumValues()) {
		    printer.println("case %d: return %s;",e.getValue(),e.getName());
		}
		printer.println("default:")
		printer.println("throw Unidata.protobuf.Error("Illegal Enumeration value"););
	    printer.println("} // switch");
	    printer.println("} // getEnum");
	printer.println("} //"+e.getName())"
    }

    void generate_message(AST.Message msg, Printer printer) throws Exception
    {
        Annotation a = msg.getAnnotation();
	// Generate the class header
	printer.printf("static public class %s");
	printer.println("{");

	// Generate the class fields
	for(AST.Field field: msg.getFields()) {
	    printer.printf("%s %s = %s;\n",
		javatypefor(field.getType()),
		field.getName(),
		defaultfor(field));
 	}

	// Generate get instance function
	printer.printf("public %s getInstance()\n",msg.getName());
	printer.println("{");
	    printer.printf("return new %s();\n",msg.getName());
	printer.println("} // getInstance");

	// Generate the serialize/deserialize functions

	// Generate the serialize function
	printer.println("public boolean writeTo(EDF edf) throws Unidata.protobuf.Error");
	printer.println("{");
	for(AST.Field field: msg.getFields()) {
	    AST.Type fieldtype = field.getType();
	    if(fieldtype.getSort() == AST.Sort.PRIMITIVETYPE) {
	        printer.printf("Unidata.protobuf.Runtime.%s_writeTo(edf,%s);\n",
			field.getType().getName(),field.getName());
	    } else if(fieldtype.getSort() == AST.Sort.ENUM) {
	        printer.printf("Unidata.protobuf.Runtime.int_writeTo(edf,%s);\n",
			       field.getName());
	    } else { /User defined type
	        printer.printf("%s.writeTo(edf);\n",field.getName());
	    }
 	}
	printer.println("} // writeTo"); // serialize function trailer

	// Generate the deserialize function
	printer.println("public boolean readFrom(EDF edf)");
	printer.println("{");
	printer.println("int key;");
	printer.println("while((key = edf.readc() >= 0) {");
        printer.println("int wiretype = (key|0x7);");
        printer.println("int tag = (key >> 3);");
        printer.println("switch (tag) {");
	for(AST.Field field: msg.getFields()) {
	    if(!generate_deserialize_field(field,printer)) return false;
	    printer.println("} break;");
	}
        // add default
        printer.println("default:");
        printer->println("Unidata.protobuf.Runtime.skip_field(edf);");

        printer.println("};\n"); // switch
        printer.println("};\n"); // while
	printer.println("} // readFrom"); // deserialize function trailer

	// generate class trailer
	printer.println("} //"+msg.getName())"
    }


    void 
    fieldwriteto(AST.field field)
    {
        AST.Type fieldtype = field.getType();
	if(fieldtype.getSort() == AST.Sort.PRIMITIVETYPE) {
	    printer.printf("Unidata.protobuf.Runtime.%s_readFrom(edf,%s);\n",
			   field.getType().getName(),field.getName());
        } else if(fieldtype.getSort() == AST.Sort.ENUM) {// User defined enum
	    printer.printf("Unidata.protobuf.Runtime.int_readFrom(edf,%s.getValue());\n",
			   field.getName());
        } else { //User defined msg type
	    printer.printf("%s.readFrom(edf);\n",field.getName());
        }
    }

    boolean
    generate_deserialize_field(AST.Field field, Printer printer)
    {
	
	printer.println("case %d: {",field.getId());
	switch (field.getCardinality()) {	
        case REQUIRED:
	    fieldreadfrom(field);
	    break;
        case OPTIONAL:
	    // Assume that every field has been initialized with some default value
	    fieldreadfrom(field);
	    break;
        case REPEATED:
	    // The assumption here is that the field is a list<T> with the default = empty list
	    // Would prefer to use T[], but we do not know the size in advance
	    fieldreadfromrepeated(field);
	    break;
	}
	return true;
    }

    void 
    fieldreadfrom(AST.field field)
    {
        AST.Type fieldtype = field.getType();
	if(fieldtype.getSort() == AST.Sort.PRIMITIVETYPE) {
	    printer.printf("Unidata.protobuf.Runtime.%s_readFrom(edf,%s);\n",
			   field.getType().getName(),field.getName());
        } else if(fieldtype.getSort() == AST.Sort.ENUM) {// User defined enum
	    printer.printf("{int enumid = -1";");
	    printer.printf("enumid = Unidata.protobuf.Runtime.int_readFrom(edf);\n",
	    printer.printf("%s = %s.getEnum(enumid);",field.getName(),fieldtype.getName());
        } else { //User defined msg type
	    printer.printf("%s.readFrom(edf);\n",field.getName());
        }
    }

    void 
    fieldreadfromrepeated(AST.field field)
    {
	if(fieldtype == FieldDescriptor::TYPE_MESSAGE) {
	    printer->Print(vars,"$fieldtype$* tmp;\n");
	} else {
	    printer->Print(vars,"$cfieldtype$ tmp;\n");
        }
        printer->Print(vars,"err = edf_$edffcnname$_decode(edf,&tmp);\n");
	printer->Print(vars,"if(err) break;\n");
	printer->Print(vars,"err = edf_append(edf,(void*)&$msgidname$->$fieldname$,sizeof(tmp),(void*)&tmp);\n");

        AST.Type fieldtype = field.getType();
	if(fieldtype.getSort() == AST.Sort.PRIMITIVETYPE) {
	    printer.printf("Unidata.protobuf.Runtime.%s_readFromRepeated(edf,%s);\n",
			   field.getType().getName(),field.getName());
        } else if(fieldtype.getSort() == AST.Sort.ENUM) {// User defined enum
	    printer.printf("{");
	    printer.printf("int enumid = Unidata.protobuf.Runtime.int_readFrom(edf);\n",
	    printer.printf("%s eval = %s.getEnum(enumid);",
				field.getName(),
				getrefname(fieldtype),
				getrefname(fieldtype));
        } else { //User defined msg type
	    printer.println("{");
	    printer.printf(%s value = %s.readFrom(edf);\n",
				getrefname(fieldtype),
				field.getName());
	    printer.printf("%s.add(value);\n",field.getName())
        }
    }

    String
    computeouterclassname(AST.Package p)
    {
        AST.File file = p.getPackageFile();
        String filename = file.getName();
        String outername = "";
        for(int i=0;i<filename.length();i++) {
            char c = filename.charAt(i).toUpperCase();
            if("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".indexOf(char) >= 0)
                outername += c;
        }
        return outername
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

} // JavaGenerator


