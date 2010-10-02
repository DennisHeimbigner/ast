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

package unidata.protobuf.ast.compiler;

import java.util.*;
import java.io.*;

public class Util
{

static String qualify(String relpath, AST node)
{
    if(relpath.startsWith(".")) return relpath;
    AST.Package p = node.getPackage();
    return p.getName() + "." + relpath;
}

static public List<AST> findbyname(String qualname, List<AST> allnodes)
{
    List<AST> matches = new ArrayList<AST>();
    if(allnodes != null)
        for(AST ast : allnodes) {
	    if(ast.qualifiedname != null && qualname.equals(ast.qualifiedname))
	        matches.add(ast);
        }
    return matches;
}

static List<AST.Type> findtypebyname(String typename, AST node)
{
    List<AST.Type> matches = new ArrayList<AST.Type>();
    // First, see is this a primitive type name
    List<AST.PrimitiveType> primitives = node.getRoot().getPrimitiveTypes();
    for(AST.PrimitiveType pt: primitives) 
        if(typename.equals(pt.getName())) {matches.add(pt); return matches;}
    // Otherwise, qualify the name and search for it
    String qualname = Util.qualify(typename,node);
    // Locate the single matching type name
    for(AST ast : node.getRoot().getAllNodes()) {
        if(ast.qualifiedname != null && qualname.equals(ast.qualifiedname)
	   && ast instanceof AST.Type) {
	    matches.add((AST.Type)ast);
        }
    }
    return matches;
}    

static List<AST> concat(List<AST> list1, List<AST> list2)
{
    List<AST> catlist = new ArrayList<AST>();
    if(list1 != null) for(AST e1: list1) catlist.add(e1);
    if(list2 != null) for(AST e2: list2) catlist.add(e2);
    return catlist;
}


// Compute path from root to this node.
// Root is 0 entry, package is 1 entry  and the node is last entry
static List<AST> computepath(AST node)
{
    List<AST> path = new ArrayList<AST>();
    while(node != null) {
        path.add(0,node);
        node = node.getParent();
    }
    return path;
}

static String computequalifiedname(AST node)
{
    List<AST> path = computepath(node);
    String qualname = node.getRoot().getQualifiedName();
    for(int i=1;i<path.size();i++)  // do not include root
        qualname = qualname + "." + path.get(i).getName();
    return qualname;
}

} // class Util
