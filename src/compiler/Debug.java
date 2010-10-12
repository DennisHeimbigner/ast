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

package unidata.protobuf.compiler;

import java.io.*;
import java.util.*;

public class Debug
{
    static int debuglevel = 0;
    static void setLevel(int level) {Debug.debuglevel = level;}
    static int getLevel() {return Debug.debuglevel;}
    
    static int stdindent = 2;


/**
 * Print the AST optionally using qualified names
 * Assumes the AST has been semantically processed.
 * Result is not quite same as original imput.
 */


static public void print(AST.Root root, PrintWriter writer)
{
    print(root,writer,false);
}

static public void print(AST.Root root, PrintWriter writer, boolean qualified)
{
    for(AST.File f: root.getAllFiles()) {
        printR(f,0,writer,qualified);
    }
    for(AST.Package p: root.getAllPackages()) {
        printR(p,0,writer,qualified);
    }
}

static void printR(AST node, int depth,
			 PrintWriter writer,
			 boolean qualified)
{
    String typename;
    String name;

    name = namefor(node,qualified);
    switch (node.sort) {
    case FILE:
	AST.File astfile = (AST.File)node;
	if(astfile != astfile.getRoot().getRootFile()) {
	    writer.printf("%simport %s;\n",indent(depth),astfile.getName());
	}
	break;
    case PACKAGE:
	AST.Package astpackage = (AST.Package)node;
	writer.printf("%spackage %s;\n",indent(depth),name);
	for(AST subnode : astpackage.getChildren()) 
	    printR(subnode,depth,writer,qualified);
	break;
    case MESSAGE:
	AST.Message astmessage = (AST.Message)node;
	writer.printf("%smessage %s {\n",indent(depth),name);
	for(AST subnode : astmessage.children)
	    printR(subnode,depth+1,writer,qualified);
	writer.printf("%s}\n",indent(depth));
	break;
    case SERVICE:
	AST.Service astservice = (AST.Service)node;
	writer.printf("%sservice %s {\n",indent(depth),name);
	for(AST subnode : astservice.children) 
	    printR(subnode,depth+1,writer,qualified);
	writer.printf("%s}\n",indent(depth));
	break;
    case ENUM:
	AST.Enum astenum = (AST.Enum)node;
	writer.printf("%senum %s {\n",indent(depth),name);
	for(int i=0;i<astenum.enumfields.size();i++)
	    printR(astenum.enumfields.get(i),depth+1,writer,qualified);
	writer.printf("%s}\n",indent(depth));
	break;
    case ENUMFIELD:
	AST.EnumField astenumfield = (AST.EnumField)node;
	writer.printf("%s%s = %d;\n",indent(depth),name,astenumfield.value);
	break;
    case EXTEND:
	AST.Extend astextend = (AST.Extend)node;
	writer.printf("%sextend %s {\n",indent(depth),name);
	for(AST subnode : astextend.fields) 
	    printR(subnode,depth+1,writer,qualified);
	writer.printf("%s}\n",indent(depth));
	break;
    case EXTENSIONS:
	AST.Extensions astextensions = (AST.Extensions)node;
	writer.printf("%sextensions %s {\n",indent(depth),name);
	for(AST.Range range : astextensions.getRanges()) {
	    writer.printf("%sextensionrange %d to %d\n",indent(depth),
		  	  range.start,range.stop);
	}
	writer.printf("%s}\n",indent(depth));
	break;
    case FIELD:
	AST.Field astfield = (AST.Field)node;
	typename = namefor(astfield.fieldtype,qualified);
	writer.printf("%s%s %s %s = %d",indent(depth),
		      astfield.cardinality.getName(),typename,name,astfield.id);
	// Special case handling
	if(astfield.options.size() > 0) {
            writer.print(" [");
	    boolean first = true;
	    for(AST.Option opt : astfield.options) {
	        if(!first) writer.print(", ");
		printSimpleOption(opt.name,opt,writer);
		first = false;
	    }
            writer.print("]\n");
	}
	writer.println(";");
	break;
    case OPTION: // Option statement
	AST.Option astoption = (AST.Option)node;
        writer.printf("%soption ",indent(depth));
	printSimpleOption(name,astoption,writer);
	writer.println(";");
	break;
    case RPC:
	AST.Rpc astrpc = (AST.Rpc)node;
	String argtype = namefor(astrpc.argtype,qualified);
	String returntype = namefor(astrpc.returntype,qualified);
	writer.printf("%srpc %s (%s) returns (%s);\n",indent(depth),name,argtype,returntype);
	break;
    default: // ignore
	break;
    }
}

private static void printSimpleOption(String name, AST.Option option, PrintWriter writer)
{
    if(option.getUserDefined()) {
        writer.printf("(%s) = %s",name,option.value);
    } else if(name.equals(AST.DEFAULTNAME))
        writer.printf("DEFAULT = %s",option.value);
    else
	writer.printf("%s = %s",name,option.value);
}


private static String namefor(AST node)
{
    return namefor(node,false);
}

private static String namefor(AST node, boolean qualified)
{
    if(node == null) return null;
    if(qualified)
        return node.qualifiedname;
    return node.name;
}    

private static String indent(int depth)
{
    String indentation = "";
    depth *= stdindent;
    while(depth-- > 0) indentation += " ";
    return indentation;
}


// Print an indented tree representation of the AST Tree

static public void printTree(AST.Root root, PrintWriter writer)
{
    printTree(root,writer,false);
}

static public void printTree(AST.Root root, PrintWriter writer, boolean presemantic)
{
    printTreeR(root,0,writer,presemantic);
    writer.flush();
}

static void printTreeR(AST node, int depth,
			 PrintWriter writer,
                         boolean presemantic)
{
    String typename;
    String name;

    writer.printf("[%s][%d] %s%s ",depth,node.index,indent(depth),node.sort.getName());
    name = namefor(node);
    if(name != null) writer.print("name="+name);
    name = namefor(node,true);
    if(name != null) writer.print(" qualifiedname="+name);
    // print container, file, package pointers
    if(node.getParent() != null) {
	name = namefor(node.getParent());
	if(name != null) writer.print(" parent="+name);
    }
    if(node.getSrcFile() != null) {
	name = namefor(node.getSrcFile());
	if(name != null) writer.print(" file="+name);
    }
    if(node.getPackage() != null) {
	name = namefor(node.getPackage());
	if(name != null) writer.print(" package="+name);
    }

    // switch to print any additional parameters
    switch (node.sort) {
    case ROOT:
        AST.Root root = (AST.Root)node;
        writer.print(" rootfile="+root.getRootFile().getName());
        break;
    case FILE:
        AST.File f = (AST.File)node;
        if(f.getFilePackage() != null)
            writer.print(" filepackage="+f.getFilePackage().getName());
        break;
    case FIELD:
	AST.Field astfield = (AST.Field)node;
	if(presemantic)
            typename = (String)astfield.getAnnotation();
        else
            typename = namefor(astfield.fieldtype);
	writer.printf(" cardinality=%s type=%s id=%d",
		      astfield.cardinality.getName(),
		      typename,astfield.id);
	break;
    case EXTENSIONS:
	AST.Extensions astextensions = (AST.Extensions)node;
	for(AST.Range range: astextensions.getRanges()) {
	    writer.printf(" start=%d stop=%d",
			   range.start,range.stop);
	}
	break;
    case ENUMFIELD:
	AST.EnumField astenumfield = (AST.EnumField)node;
	writer.printf(" value=%s",astenumfield.value);
	break;
    case OPTION: // Option statement
	AST.Option astoption = (AST.Option)node;
        writer.printf(" value=%s ",astoption.getValue());
	writer.println(";");
	break;
    case RPC:
	AST.Rpc astrpc = (AST.Rpc)node;
        String argtype;
        String returntype;
        if(presemantic) {
	    argtype = ((String[])astrpc.getAnnotation())[0];
	    returntype = ((String[])astrpc.getAnnotation())[1];
	} else {
            argtype = astrpc.argtype.getQualifiedName();
            returntype = astrpc.returntype.getQualifiedName();
	}
	writer.printf(" argtype=%s returntype=%s", argtype, returntype);
	break;
    default: // ignore
	break;
    }
    writer.println();

    // Recurse to dump children
    if(presemantic) {
        if(node.getChildren() != null) {
	    for(AST subnode : node.getChildren())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
    } else {// Dump under the packages, not the files
    switch (node.sort) {
    case ROOT:
        AST.Root root = (AST.Root)node;
        if(root.getAllPackages() != null) {
            for(AST subnode : root.getAllPackages())
                printTreeR(subnode,depth+1,writer,presemantic);
        } break;
    case PACKAGE:
	AST.Package p = (AST.Package)node;
        if(p.getOptions() != null) {
            for(AST subnode : p.getOptions())
                printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(p.getEnums() != null) {
            for(AST subnode : p.getEnums())
                printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(p.getMessages() != null) {
            for(AST subnode : p.getMessages())
                printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(p.getExtenders() != null) {
            for(AST subnode : p.getExtenders())
                printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(p.getServices() != null) {
            for(AST subnode : p.getServices())
                printTreeR(subnode,depth+1,writer,presemantic);
        }
        break;
    case MESSAGE:
        AST.Message m = (AST.Message)node;
        if(m.getOptions() != null) {
	    for(AST subnode : m.getOptions())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(m.getEnums() != null) {
	    for(AST subnode : m.getEnums())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(m.getFields() != null) {
	    for(AST subnode : m.getFields())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(m.getExtensions() != null) {
	    for(AST subnode : m.getExtensions())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(m.getMessages() != null) {
	    for(AST subnode : m.getMessages())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(m.getExtenders() != null) {
	    for(AST subnode : m.getExtenders())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        break;
    case SERVICE:
        AST.Service svc = (AST.Service)node;
        if(svc.getOptions() != null) {
	    for(AST subnode : svc.getOptions())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        if(svc.getRpcs() != null) {
	    for(AST subnode : svc.getRpcs())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        break;
    case ENUM:
        AST.Enum e = (AST.Enum)node;
          if(e.getEnumFields() != null) {
	    for(AST subnode : e.getEnumFields())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        break;
    case EXTEND:
        AST.Extend extender = (AST.Extend)node;
          if(extender.getFields() != null) {
	    for(AST subnode : extender.getFields())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        break;
    case FIELD:
          AST.Field field = (AST.Field)node;
          if(field.getOptions() != null) {
	    for(AST subnode : field.getOptions())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
        break;
    case RPC:
    // No children
    case FILE:
    case ENUMFIELD:
    case OPTION:
    case PRIMITIVETYPE:
        break;
    }

    }

}



} // class Debug

