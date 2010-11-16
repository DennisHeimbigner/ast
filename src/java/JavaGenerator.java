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
	String classname = null; // also used as file name
	String refpath = null;
	Printer printer = null;
    }

    //////////////////////////////////////////////////
    // Instance variables

    String outputdir = DFALTDIR;

    String outerclass = null; // Name of the outer class

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
    //////////////////////////////////////////////////

/*
- compute the filename for the top level class
- compute the Java package names for all the packages
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

	// Compute the output filename
	String file = root.getPackage().optionLookup("java_outer_classname");
        if(file == null) file = makeouterclassname(root.getRootFile());

	// Compute the Java package names for all the packages
	for(AST.Package p: root.getPackageSet()) {
	    Annotation a = p.getAnnotation();
	    a.packagename = p.optionLookup("java_package");
	    if(a.packagename == null) a.packagename = p.getName();
	}
	// compute the filenames for all the top-level message types
	for(AST ast: root.getNodeSet()) {
	    if(ast.getParent().getSort() != AST.Sort.Package)
		continue; // not top level
	    switch (ast.getSort()) {
	    case MESSAGE:
		AST.Message msg = (AST.Message)ast;
		Annotation a = msg.getAnnotation();
		a.classname = msg.getName();
		break;
	    default: break;
	    }
	}
	// Compute the reference path for each message and enum
	for(AST ast: root.getNodeSet()) {
	    Annotation a = ast.getAnnotation();
	    switch (ast.getSort()) {
	    case MESSAGE: CASE ENUM:
		a.refpath = ast.getQualifiedName();
		break;
	    default: break;
	    }
	}
	// Truncate the .java enum files and message files
	for(AST ast: root.getNodeSet()) {
	    FileWriter filewriter = null;
	    if(ast.getParent().getSort() != AST.Sort.Package)
		continue; // not top level
	    Annotation a = msg.getAnnotation();
	    switch (ast.getSort()) {
	    case MESSAGE: case ENUM:
		String filename = outputdir + "/" + a.classname+".java";
		try {
		    filewriter = new FileWriter(filename);
		} catch (Exception e) {
		    System.err.println("Cannot access file: "+filename);
		    return false;
		}
		try {a.filewriter.close();} catch (Exception e) {};
		break;
	    default: break;
	    }
	}
	// Generate the top level enum definitions
	for(AST ast: root.getNodeSet()) {
	    if(ast.getParent().getSort() != AST.Sort.Package) continue;
	    Annotation a = msg.getAnnotation();
	    switch (ast.getSort()) {
	    case ENUM:
		// Open the .java file for this enum
		AST.Enum e = (AST.Enum)ast;
		String filename = outputdir + "/" + a.classname+".java";
		try {
		    a.printer = new printer(filename);
		    if(!generate_enum(e,a.printer)) return false;
		} catch (Exception e) {
		    System.err.println("IO Failure: "+filename+":"+e);
		    return false;
		}
	    default: break;
	    }
	}
	// Generate the top level message definitions
	for(AST ast: root.getNodeSet()) {
	    if(ast.getParent().getSort() != AST.Sort.Package) continue;
	    Annotation a = msg.getAnnotation();
	    switch (ast.getSort()) {
	    case MESSAGE:
		// Open the .java file for this message
		AST.Message e = (AST.Message)ast;
		String filename = outputdir + "/" + a.classname+".java";
		try {
		    a.printer = new Printer(filename);
		    if(!generate_message(e,a.printer)) return false;
		} catch (Exception e) {
		    System.err.println("IO Failure: "+filename+":"+e);
		    return false;
		}
	    default: break;
	    }
	}
	return true;
    }
} // generate()


boolean generate_enum(AST.Enum node, Printer printer) throws Exception
{
// Dump the package decl
assert(node.getParent() instanceof AST.Package);
Annotation pa = node.getParent().getAnnotation();
Printer.println("package " + pa.packagename() + ";")
Printer.println("");


}



} // JavaGenerator

