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

// For code taken from google protobuf src:
//
// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// http://code.google.com/p/protobuf/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

/*
This generator generates two files per package:
1. <package>.h and
2. <package>.c
Thus, if a user has imports, then those import files
must be separately compiled.
*/

package unidata.protobuf.compiler;

import gnu.getopt.Getopt;

import java.util.*;
import java.io.*;

public class CGenerator extends Generator
{
    //////////////////////////////////////////////////

    static final String LANGUAGE = "C";

    static final String FCNPREFIX = "ast_";

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
	String filebase = null;
    }

    //////////////////////////////////////////////////
    // Instance variables

    String outputdir = DFALTDIR;

    String filebase = null; // used also for the output file name

    //////////////////////////////////////////////////
    // Constructor

    public CGenerator()
    {
    }	 

    //////////////////////////////////////////////////
    // Command line processing

    List<String>
    processcommandline(String[] argv)
    {
	int c;
	List<String> arglist = new ArrayList<String>();
	Getopt g = new Getopt(LANGUAGE+"Generator",argv,"-:o",null);
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
- compute the C file names for all the packages
- compute the filename for the top package
- compute the reference path for each message and enum
- for each enum in top package
   - generate the top level enum definitions
   - generate the top level message classes
     - for each message, generate the (de)serialize functions
       and the free function.
*/

    public boolean generate(AST.Root root, String[] argv) throws Exception
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

	// Compute the C file names for all the packages
	for(AST.Package p: root.getPackageSet()) {
	    Annotation a = (Annotation)p.getAnnotation();
	    a.filebase = p.optionLookup("c_file");
	    if(a.filebase == null) a.filebase = p.getName();
	}

	// Save that of the top package and truncate the corresponding file
	AST.Package toppackage = root.getTopPackage();
        Annotation a = (Annotation)toppackage.getAnnotation();
	filebase = a.filebase;
        // Now, truncate both .h and the .c files
	FileWriter filewriterH = null;
	FileWriter filewriterC = null;
	String filename = outputdir + "/" + this.filebase;
	try {
	    filewriterH = new FileWriter(filename+".h");
	    filewriterC = new FileWriter(filename+".c");
	} catch (Exception e) {
	    System.err.println("Cannot access file: "+filename+".[hc]");
	    return false;
	}
	// close the files to truncate
	try {
	    filewriterH.close();
	    filewriterC.close();
	} catch (Exception e) {};


	// Generate the top package's <filebase>.[hc] content
        Printer printer = null;
	FileWriter wfile = null;	
        File file = null;

	try {
	    // Open the .h file for this package
	    file = new File(filename+".h");
	    if(!file.canWrite()) {
	        System.err.println("Cannot access: "+file);
	        return false;
	    }
	    wfile = new FileWriter(file);
	    printer = new Printer(wfile);
	    generate_h(toppackage,printer);
	    printer.close(); wfile.close();
	} catch (Exception e) {
	    System.err.println("Generation Failure: "+file+":"+e);
	    e.printStackTrace();
	    return false;
	}
	try {
	    // Open the .c file for this package
	    file = new File(filename+".c");
	    if(!file.canWrite()) {
	        System.err.println("Cannot access: "+file);
	        return false;
	    }
	    wfile = new FileWriter(file);
	    printer = new Printer(wfile);
	    generate_c(toppackage,printer);
	    printer.close(); wfile.close();
	} catch (Exception e) {
	    System.err.println("Generation Failure: "+file+":"+e);
	    e.printStackTrace();
	    return false;
	}
	return true;
    } // generate()

    void generate_h(AST.Package p, Printer printer) throws Exception
    {
	Annotation a = (Annotation)p.getAnnotation();
	printer.printf("#ifndef %s_H\n",p.getName());
	printer.printf("#define %s_H\n",p.getName());
	printer.blankline();

	// Generate the enum definitions
	for(AST ast: p.getChildSet()) {
	    if(ast.getSort() != AST.Sort.ENUM) continue;
	    generate_enum((AST.Enum)ast,printer);
	}

	// Generate the message structures
	for(AST ast: p.getChildSet()) {
	    if(ast.getSort() != AST.Sort.MESSAGE) continue;
	    generate_messagestruct((AST.Message)ast,printer);
	}

	printer.blankline();
	printer.printf("#endif /*%s_H*/\n",p.getName());
    }

    void generate_enum(AST.Enum e, Printer printer) throws Exception
    {
	printer.blankline();
	Annotation a = (Annotation)e.getAnnotation();
	printer.blankline();
	printer.printf("typedef enum %s {\n",e.getName());
	printer.indent();
        List<AST.EnumValue> values = e.getEnumValues();
        int nvalues = values.size();
        for(int i=0;i<nvalues;i++) {
            AST.EnumValue eval = values.get(i);
            printer.printf("%s=%d%s\n",
                eval.getName(),
                eval.getValue(),
                (i == (nvalues - 1)?";":","));
        }
	printer.outdent();
	printer.printf("} %s;\n",e.getName());
    }

    void generate_messagestruct(AST.Message msg, Printer printer) throws Exception
    {
        Annotation a = (Annotation)msg.getAnnotation();
	printer.blankline();
	printer.printf("typedef struct %s {\n",msg.getName());
	printer.indent();
	// Generate the fields
	for(AST.Field field: msg.getFields()) {
	    if(field.getCardinality() == AST.Cardinality.REQUIRED) {
	        printer.printf("%s %s;\n",
				ctypefor(field.getType()),
				field.getName());
	    } else if(field.getCardinality() == AST.Cardinality.OPTIONAL) {
	        printer.printf("struct {int exists; %s value;} %s;\n",
			ctypefor(field.getType()),
			cfieldvar(field));
	    } else { // field.getCardinality() == AST.Cardinality.REPEATED
	        printer.printf("struct {int count; %s* values;} %s;\n",
			ctypefor(field.getType()),
			cfieldvar(field));
	    }
 	}
	printer.outdent();
	printer.printf("} %s;\n",msg.getName());

	// Generate the per-message-type function prototypes
	printer.blankline();
	printer.printf("extern int %s_write(Runtime*,%s*);\n",
			cfcnname(msg),
			msg.getName());
	printer.printf("extern int %s_read(Runtime*,%s**);\n",
			cfcnname(msg),
			msg.getName());
	printer.printf("extern int %s_reclaim(Runtime*,%s*);\n",
			cfcnname(msg),
			msg.getName());
	printer.printf("extern int %s_default(Runtime*,%s**);\n",
			cfcnname(msg),
			msg.getName());
	printer.printf("extern long %s_size(Runtime*,%s**);\n",
			cfcnname(msg),
			msg.getName());
    }


    void generate_c(AST.Package p, Printer printer) throws Exception
    {
	Annotation a = (Annotation)p.getAnnotation();
	
	// Add includes
	printer.blankline();
	printer.printf("#include <stdlib.h>\n");
	printer.printf("#include <stdio.h>\n");
	printer.blankline();

        printer.printf("#include \"%s.h\"\n",a.filebase);
	for(AST.Package p1: p.getRoot().getPackageSet()) {
	    if(p1 == p) continue;
	    a = (Annotation)p1.getAnnotation();
	    printer.printf("#include \"%s.h\"\n",a.filebase);
	}
	printer.blankline();

	// Generate the per-message functions
	for(AST ast: p.getChildSet()) {
	    if(ast.getSort() != AST.Sort.MESSAGE) continue;
	    generate_messagefunctions((AST.Message)ast,printer);
	}
    }

    void generate_messagefunctions(AST.Message msg, Printer printer)
	throws Exception
    {
	generate_writefunction(msg,printer);
	printer.blankline();
	generate_readfunction(msg,printer);
	printer.blankline();
	generate_reclaimfunction(msg,printer);
	printer.blankline();
	generate_sizefunction(msg,printer);
    }

    void generate_writefunction(AST.Message msg, Printer printer)
	throws Exception
    {
	printer.printf("static int\n%s_write(Runtime* rt, %s* %s)\n",
			cfcnname(msg),
			ctypefor(msg), cmsgvar(msg));
	printer.println("{");
	printer.indent();
	printer.println("int status = NOERR;");
	printer.println("int i = 0;");
	printer.blankline();

	// Generate the fields serializations
	for(AST.Field field: msg.getFields()) {
	    if(field.getCardinality() == AST.Cardinality.REQUIRED) {
	        printer.printf("status = %s_write(rt,%s->%s);\n",
				cfcnname(field.getType()),
				msg.getName(),field.getName());
		printer.println("if(!status) {goto done;}");
	    } else if(field.getCardinality() == AST.Cardinality.OPTIONAL) {
	        printer.printf("if(%s->%s.exists) {\n",
				msg.getName(),field.getName());
		printer.indent();
	        printer.printf("status = %s_write(rt,%s->%s.value);\n",
				cfcnname(field.getType()),
				msg.getName(),field.getName(),field.getName());
		printer.println("if(!status) {goto done;}");
		printer.outdent();
	        printer.printf("}\n");
	    } else { // field.getCardinality() == AST.Cardinality.REPEATED
	        printer.printf("for(i=0;i<%s->%s.count;i++) {\n",
				msg.getName(),field.getName());
		printer.indent();
	        printer.printf("status = %s_write(rt,%s->%s.values[i]);\n",
				cfcnname(field.getType()),
				msg.getName(),field.getName(),field.getName());
		printer.println("if(!status) {goto done;}");
		printer.outdent();
	        printer.printf("}\n");
	    }
 	}
	printer.outdent();
	printer.blankline();
	printer.println("done:");
	printer.indent();
	printer.println("return status;");
	printer.outdent();
	printer.blankline();
	printer.printf("} /*%s_write*/\n",msg.getName());
    }

    void generate_readfunction(AST.Message msg, Printer printer)
	throws Exception
    {
	printer.printf("static int\n%s_read(Runtime* rt, %s* %s)\n",
			cfcnname(msg),
			ctypefor(msg), cmsgvar(msg));
	printer.println("{");
	printer.indent();
	printer.println("int status = NOERR;");
	printer.println("int i = 0;");
	printer.blankline();

	// Generate the fields serializations
	for(AST.Field field: msg.getFields()) {
	    if(field.getCardinality() == AST.Cardinality.REQUIRED) {
	        printer.printf("status = %s_write(rt,%s->%s);\n",
				cfcnname(field.getType()),
				msg.getName(),field.getName());
		printer.println("if(!status) {goto done;}");
	    } else if(field.getCardinality() == AST.Cardinality.OPTIONAL) {
	        printer.printf("if(%s->%s.exists) {\n",
				msg.getName(),field.getName());
		printer.indent();
	        printer.printf("status = %s_write(rt,%s->%s.value);\n",
				cfcnname(field.getType()),
				msg.getName(),field.getName(),field.getName());
		printer.println("if(!status) {goto done;}");
		printer.outdent();
	        printer.printf("}\n");
	    } else { // field.getCardinality() == AST.Cardinality.REPEATED
	        printer.printf("for(i=0;i<%s->%s.count;i++) {\n",
				msg.getName(),field.getName());
		printer.indent();
	        printer.printf("status = %s_write(rt,%s->%s.values[i]);\n",
				cfcnname(field.getType()),
				msg.getName(),field.getName(),field.getName());
		printer.println("if(!status) {goto done;}");
		printer.outdent();
	        printer.printf("}\n");
	    }
 	}
	printer.outdent();
	printer.blankline();
	printer.println("done:");
	printer.indent();
	printer.println("return status;");
	printer.outdent();
	printer.blankline();
	printer.printf("} /*%s_read*/\n",msg.getName());
    }

    void generate_reclaimfunction(AST.Message msg, Printer printer)
	throws Exception
    {
	printer.printf("static int\n%s_reclaim(Runtime* rt, %s* %s)\n",
			cfcnname(msg),
			msg.getName(), msg.getName());
	printer.println("{");
	printer.indent();
	printer.println("int status = NOERR;");
	printer.println("int i = 0;");
	printer.blankline();

	// Generate the fields serializations
	for(AST.Field field: msg.getFields()) {
	    // Only need to reclaim fields whose type is a message pointer
            // or a string or a byte string.
	    switch (field.getType().getSort()) {
	    case MESSAGE: break;
	    case PRIMITIVETYPE:
		switch (((AST.PrimitiveType)(field.getType())).getPrimitiveSort()) {
		case STRING: break;
		case BYTES: break;
		default: continue;
		}
		break;
	    default: continue;
	    }

	    if(field.getCardinality() == AST.Cardinality.REQUIRED) {
	        printer.printf("status = %s_free(rt,%s->%s);\n",
				cfcnname(field.getType()),
				msg.getName(),field.getName());
		printer.println("if(!status) {goto done;}");
	    } else if(field.getCardinality() == AST.Cardinality.OPTIONAL) {
	        printer.printf("if(%s->%s.exists) {\n",
				msg.getName(),field.getName());
		printer.indent();
	        printer.printf("status = %s_free(rt,%s->%s.value);\n",
				cfcnname(field.getType()),
				msg.getName(),field.getName(),field.getName());
		printer.println("if(!status) {goto done;}");
		printer.outdent();
	        printer.printf("}\n");
	    } else { // field.getCardinality() == AST.Cardinality.REPEATED
	        printer.printf("for(i=0;i<%s->%s.count;i++) {\n",
				msg.getName(),field.getName());
		printer.indent();
	        printer.printf("status = %s_free(rt,%s->%s.values[i]);\n",
				cfcnname(field.getType()),
				msg.getName(),field.getName());
		printer.println("if(!status) {goto done;}");
		printer.outdent();
	        printer.printf("}\n");
		printer.printf("free(%s->%s.values)\n",
				msg.getName(),field.getName());
	    }
 	}
	printer.outdent();
	printer.blankline();
	printer.println("done:");
	printer.indent();
	printer.println("return status;");
	printer.outdent();
	printer.blankline();
	printer.printf("} /*%s_free*/\n",msg.getName());
    }


    void generate_sizefunction(AST.Message msg, Printer printer)
	throws Exception
    {
	printer.printf("static long\n%s_size(Runtime* rt, %s* %s)\n",
			cfcnname(msg),
			ctypefor(msg), msg.getName());
	printer.println("{");
	printer.indent();
	printer.println("int status = NOERR;");
	printer.println("int i = 0;");
	printer.println("long totalsize = 0;");
	printer.blankline();

	// sum the field sizes
	for(AST.Field field: msg.getFields()) {
	    switch (field.getType().getSort()) {
	    case MESSAGE: break;
	    case ENUM: break;
	    case PRIMITIVETYPE: break;
	    default: continue;
	    }

	    if(field.getCardinality() == AST.Cardinality.REQUIRED) {
	        printer.printf("totalsize += %s_write_size(rt,%d,&%s->%s);\n",
				cfcnname(field.getType()),
				field.getId(),
				msg.getName(),cfieldvar(field));
	    } else if(field.getCardinality() == AST.Cardinality.OPTIONAL) {
	        printer.printf("if(%s->%s.exists) {\n",
				msg.getName(),field.getName());
		printer.indent();
	        printer.printf("totalsize += %s_write_size(rt,%d,&%s->%s);\n",
				cfcnname(field.getType()),
				field.getId(),
				msg.getName(),cfieldvar(field));
		printer.outdent();
	        printer.printf("}\n");
	    } else { // field.getCardinality() == AST.Cardinality.REPEATED
	        printer.printf("for(i=0;i<%s->%s.count;i++) {\n",
				msg.getName(),field.getName());
		printer.indent();
	        printer.printf("totalsize += %s_write_size(rt,%d,&%s->%s);\n",
				cfcnname(field.getType()),
				field.getId(),
				msg.getName(),cfieldvar(field));
		printer.outdent();
	        printer.printf("}\n");
	    }
 	}
	printer.println("return status;");
	printer.outdent();
	printer.blankline();
	printer.printf("} /*%s_write_size*/\n",msg.getName());
    }


    //////////////////////////////////////////////////

    // Convert a msg name to an acceptable C variable name
    String
    cmsgvar(AST.Message msg)
    {
	String cname = msg.getName().toLowerCase();
	return cname;
    }

    // Convert a field name to an acceptable C variable name
    String
    cfieldvar(AST.Field field)
    {
	String cname = field.getName().toLowerCase();
	return cname;
    }


    String
    cfcnname(AST.Type asttype)
    {
        String typename = null;
	if(asttype.getSort() == AST.Sort.PRIMITIVETYPE) {
	    typename = ((AST.PrimitiveType)asttype).getPrimitiveSort().getName();
	    typename = FCNPREFIX + typename;
	} else if(asttype.getSort() == AST.Sort.ENUM
	          || asttype.getSort() == AST.Sort.MESSAGE) {
	    typename = asttype.getName();
	} else { // Illegal
	    System.err.println("Illegal type: "+asttype.getName());
	
        }
	return typename;
    }

    String
    ctypefor(AST.Type asttype)
    {
	String typ = null;
	
	if(asttype.getSort() == AST.Sort.PRIMITIVETYPE) {
            switch (((AST.PrimitiveType)asttype).getPrimitiveSort()) {
	    case SINT32:
	    case SFIXED32:
	    case UINT32:
	    case FIXED32:
	    case INT32:	  typ = "int"; break;

	    case SINT64:
	    case SFIXED64:
	    case UINT64:
	    case FIXED64:
	    case INT64:   typ = "longlong"; break;

	    case FLOAT:	  typ = "float"; break;
	    case DOUBLE:  typ = "double"; break;

	    case BOOL:	  typ = "int"; break;
	    case STRING:  typ = "char*"; break;

	    case BYTES:	  typ = "Bytestring"; break;
	    // No default because we want the compiler to complain if any new
	    // types are added.
	    }
	} else if(asttype.getSort() == AST.Sort.ENUM
	          || asttype.getSort() == AST.Sort.MESSAGE) {
	    typ = asttype.getName();
	} else { // Illegal
	    System.err.println("Cannot translate type to C Type: "+asttype.getName());
	
        }
	return typ;
    }

    String
    defaultfor(AST.Field field)
    {
	AST.Type fieldtype = field.getType();
        if(field.getCardinality() == AST.Cardinality.REPEATED) {
	    // repeated default is always a list of the type of the field
	    return String.format("new ArrayList<%s>()",ctypefor(fieldtype));
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
					fieldtype.getName());
	    }
	}
	return "null";	
    } 


} // CGenerator
    
    
    