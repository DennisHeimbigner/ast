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
    static class PrintProps {
        int stdindent = 2;
        boolean presemantic = false;
        boolean qualified = true;
	boolean useuid = false;
    };

    static class ParseProps {
        boolean trace = false;
    }

    static PrintProps printprops = new PrintProps();

    static void resetprintprops() {setprintprops(null);}

    static void setprintprops(PrintProps newprops)
    {
	Debug.printprops = (newprops == null? new PrintProps() : newprops);
    }

    // Non print properties
    static int debuglevel = 0;
    static public void setLevel(int level) {Debug.debuglevel = level;}
    static public int getLevel() {return Debug.debuglevel;}

    static HashSet<String> tags = new HashSet<String>();
    static public void setTag(String tag)
        {tags.add(tag);}
    static public void setTag(String... taglist)
        {for(String tag: taglist) tags.add(tag);}
    static public boolean enabled(String tag)
    {
        for(String s: tags) {
            if(tag.equals(s)) return true;
        };
        return false;
    }
    


/**
 * Print the AST optionally using qualified names
 * Assumes the AST has been semantically processed.
 * Result is not quite same as original imput.
 */

static public void print(AST.Root root, PrintWriter writer)
{
    for(AST.File f: root.getFileSet()) {
        printR(f,0,writer);
    }
    for(AST.Package p: root.getPackageSet()) {
        printR(p,0,writer);
    }
}

static void printR(AST node, int depth, PrintWriter writer)
{
    String typename;
    String name;

    name = qualnamefor(node);
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
	for(AST subnode : astpackage.getChildSet()) 
	    printR(subnode,depth,writer);
	break;
    case MESSAGE:
	AST.Message astmessage = (AST.Message)node;
	writer.printf("%smessage %s {\n",indent(depth),name);
	for(AST subnode : astmessage.getChildSet())
	    printR(subnode,depth+1,writer);
	writer.printf("%s}\n",indent(depth));
	break;
    case SERVICE:
	AST.Service astservice = (AST.Service)node;
	writer.printf("%sservice %s {\n",indent(depth),name);
	for(AST subnode : astservice.getChildSet()) 
	    printR(subnode,depth+1,writer);
	writer.printf("%s}\n",indent(depth));
	break;
    case ENUM:
	AST.Enum astenum = (AST.Enum)node;
	writer.printf("%senum %s {\n",indent(depth),name);
	for(int i=0;i<astenum.enumfields.size();i++)
	    printR(astenum.enumfields.get(i),depth+1,writer);
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
	    printR(subnode,depth+1,writer);
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
	typename = qualnamefor(astfield.fieldtype);
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
	String argtype = qualnamefor(astrpc.argtype);
	String returntype = qualnamefor(astrpc.returntype);
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


// Print an indented tree representation of the AST Tree.
// 2 methods.

// Method 1: print all nodes non-recursively
static public void printTreeNodes(AST.Root root, PrintWriter writer)
{
    for(AST ast: root.getNodeSet())  {
	printTreeNode(ast,-1,writer); // use same level for all
        writer.flush();
    }
    writer.flush();
}

// Method 2: print all nodes recursively
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
    printTreeNode(node,depth,writer);
    // Recurse to dump children
    if(presemantic) {
        if(node.getChildSet() != null) {
	    for(AST subnode : node.getChildSet())
	        printTreeR(subnode,depth+1,writer,presemantic);
        }
    } else {// Dump per-package
        switch (node.sort) {
        case ROOT:
            AST.Root root = (AST.Root)node;
            if(root.getPackageSet() != null) {
                for(AST subnode : root.getPackageSet())
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
    
// Print a single tree node
static void printTreeNode(AST node, int depth,PrintWriter writer)
{
    String typename;
    String name;
    String qname;

    if(depth >= 0) writer.printf("[%s]",depth);
    writer.printf("[%d] %s%s",node.index,indent(depth),node.sort.getName());
    qname = qualnamefor(node);
    name = namefor(node);
    writer.printf(" name=|%s|",name);
    writer.printf(" qualifiedname=|%s|",qname);
    // print container, file, package pointers
    if(node.getParent() != null) {
	qname = qualnamefor(node.getParent());
	writer.printf(" parent=|%s|",qname);
    }
    if(node.getSrcFile() != null) {
	qname = qualnamefor(node.getSrcFile());
	writer.printf(" file=|%s|",qname);
    }
    if(node.getPackage() != null) {
	qname = qualnamefor(node.getPackage());
	writer.printf(" package=|%s|",qname);
    }

    // switch to print any additional parameters
    switch (node.sort) {
    case ROOT:
        AST.Root root = (AST.Root)node;
        writer.printf(" rootfile=|%s|",root.getRootFile().getName());
        break;
    case FILE:
        AST.File f = (AST.File)node;
        if(f.getFilePackage() != null)
            writer.printf(" filepackage=|%s|",f.getFilePackage().getName());
        break;
    case FIELD:
	AST.Field astfield = (AST.Field)node;
	if(astfield.getAnnotation() != null)
            typename = (String)astfield.getAnnotation();
        else
            typename = qualnamefor(astfield.fieldtype);
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
        writer.printf(" value=|%s| ",astoption.getValue());
	writer.println(";");
	break;
    case RPC:
	AST.Rpc astrpc = (AST.Rpc)node;
        String argtype;
        String returntype;
        if(astrpc.getAnnotation() != null) {
	    argtype = ((String[])astrpc.getAnnotation())[0];
	    returntype = ((String[])astrpc.getAnnotation())[1];
	} else {
            argtype = astrpc.argtype.getQualifiedName();
            returntype = astrpc.returntype.getQualifiedName();
	}
	writer.printf(" argtype=|%s| returntype=|%s|", argtype, returntype);
	break;
    default: // ignore
	break;
    }
    writer.println();
}
    

private static String indent(int depth)
{
    String indentation = "";
    depth *= printprops.stdindent;
    while(depth-- > 0) indentation += " ";
    return indentation;
}

private static String namefor(AST node)
{
    if(node == null) return null;
    if(printprops.useuid)
	return String.format("[%d]",node.getId());
    if(node.name != null)
        return node.name;
    return String.format("[?%d]",node.getId());
}    

private static String qualnamefor(AST node)
{
    if(node == null) return null;
    if(!printprops.useuid
       && printprops.qualified
       && node.qualifiedname != null)
        return node.qualifiedname;
    return namefor(node);
}    


static void printChildSets(AST.Root root, PrintStream w)
	{printChildSets(root,new PrintWriter(w));}

static void printChildSets(AST.Root root, PrintWriter w)
{
    for(AST ast: root.getNodeSet()) {
	if(ast.getName() == null || ast.getName().length() == 0)
            w.printf("[%d](%s): children=",ast.getId(),ast.getSort().getName());
        else
            w.print(ast.getName()+": children=");
	w.print("{");
	if(ast.getChildSet() != null) {
	    for(AST sub: ast.getChildSet()) {
		w.print(" "+sub.getName());
	    }
	}
	w.print("}");
	w.println();	
    }
    w.flush();
}



} // class Debug
    
    
