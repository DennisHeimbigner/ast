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

import java.util.*;
import java.io.*;

public class AuxFcns
{

static int uid = 0; // for generating unique ids.

static int nextuid() {return ++uid;}

static String qualify(String relpath, AST node)
{
    if(relpath.startsWith(".")) return relpath;
    // prepend the qualified name of the node
    String qname = node.getQualifiedName() + "." + relpath;
    return qname;
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
    // For some nodes, we need to modify the qualified name
    switch (node.getSort()) {
    case EXTEND:
        qualname = qualname + "." +nextuid();
	break;
    case EXTENSIONS:
        qualname = qualname + "." + nextuid();
	break;
/*IGNORE
    case OPTION:
        qualname = qualname + ".$option." + nextuid();
        break;
*/
    default: break;
    }

    return qualname;
}

static public String locatefile(String suffix, List<String> includepaths)
{
    suffix = suffix.trim();
    if(suffix.charAt(0) == '/') return suffix;
    if(includepaths != null)
        for(int i=0;i<includepaths.size();i++) {
            String path = includepaths.get(i)+"/"+suffix;
            File f = new File(path);
            if(f.canRead()) return path;
        }
    // Try raw suffix as last resort
    File f = new File(suffix);
    if(f.canRead()) return suffix;
    return null;
}

// Replace '.' characters with '_'
static public String escapedname(String name)
{
    return name.replace('.','_');
}

// Given a reference to a type name in the context of a specified node,
// Try to locate all nodes that might match using scope rules:
// 1. Check for primitive type names or absolute path typename
// 2. Walk up the parent chain until the root is reached.
// 3. At each parent, try to match the typename against all possible
//    paths starting at that parent and ending at a type node

static List<AST.Type> findtypebyname(String typename, AST node, AST.Root root)
{
    List<AST.Type> typematches = new ArrayList<AST.Type>();
    // First, see is this a primitive type name
    List<AST.PrimitiveType> primitives = root.getPrimitiveTypes();
    for(AST.PrimitiveType pt: primitives) 
        if(typename.equals(pt.getName())) {typematches.add(pt); return typematches;}

    // If the typename is absolute, then find it
    if(typename.charAt(0) == '.') {
	// find exact match(es)
	for(AST ast : root.getNodeSet()) {
	    if(ast instanceof AST.Type && ast.getQualifiedName().equals(typename))
		typematches.add((AST.Type)ast);
	}
        return typematches;
    }

    // Walk up the scopenode chain to the specified root looking for matches
    // Start by finding the innermost enclosing type
    AST scopenode;
    List<AST> matches = new ArrayList<AST>();
    List<String> path = parsepath(typename);
    for(scopenode = node; scopenode != root; scopenode = scopenode.getParent()) {
	    // See if any of the children of this node will match
	    if(AuxFcns.isscope(scopenode) && scopenode.getChildSet() != null) {
            for(AST ast : scopenode.getChildSet())
		        matchpath(path,ast,matches);
            if(matches.size() > 0) break;
        }
    }
    // Search in other packages in two ways
    if(matches.size() == 0) {
	// 1. test for path against the package.
	for(AST.Package p: root.getPackageSet()) {
	    if(p.getName().equals(path.get(0))) 
	        matchpath(path,p,matches);
	    if(matches.size() > 0) break; // stop when something is found
	}
    }
    // 2. test for path.subList(1,path.size()) against
    //    the children of the package
    //    This, of course, is inherently ambiguous because
    //    There is actually no defined order for the packages
    //    (although the original c++ parser probably has one).
    if(matches.size() == 0) {
	for(AST.Package p: root.getPackageSet()) {
	    for(AST ast: p.getChildSet())
                matchpath(path,ast,matches);
	    if(matches.size() > 0) break; // stop when something is found
	}
    }
    // Transfer all type instances
    for(AST ast: matches) {
	if(ast instanceof AST.Type)
	    typematches.add((AST.Type)ast);
    }
    return typematches;
}    

static List<String> parsepath(String name)
{
    String[] segments = name.split("[.]");
    List<String> slist = new ArrayList<String>();
    for(String s: segments)  slist.add(s);
    return slist;
}

/*
Given a node, see if the given path can match starting at that node
*/
static boolean matchpath(List<String> path, AST node, List<AST> matches)
{
    assert(path.size() > 0);
    // match the first element against this node
    if(!node.getName().equals(path.get(0))) return false; // cannot possibly match
    // First path element matches; are we done?
    if(path.size() == 1) {
	matches.add(node);
	return true;
    }
    // Try the rest recursively
    if(node.getChildSet() != null) {
       for(AST ast : node.getChildSet()) { 
	    matchpath(path.subList(1,path.size()),ast,matches); // recurse
        }
    }
    return (matches.size() > 0);
}

// The scope definers are messages and packages
static boolean isscope(AST node)
{
    return node.getSort() == AST.Sort.MESSAGE || node.getSort() == AST.Sort.PACKAGE;
}


/* IGNORE
static protected boolean searchpackage(String typename, AST parent,
                                    List<AST.Type> matches)
{
    if(parent.getChildSet() != null) {
        for(AST ast : parent.getChildSet()) {
	    if(ast instanceof AST.Type) {
	        if(issuffix(ast.getQualifiedName(),typename))
                    matches.add((AST.Type)ast);
	    }
	    if(matches.size() > 0) return true;
	    if(searchpackage(typename,ast,matches)) return true; // recurse
	}
    }
    return false;
}


static protected boolean issuffix(String qualname, String suffix)
{
    // Test suffix against the segments of the qualname
    String[] qualsegments = qualname.split("[.]");
    String[] suffixsegments = suffix.split("[.]");
    if(qualsegments.length < suffixsegments.length) return false;
    int qlen = qualsegments.length;
    int slen = suffixsegments.length;
    for(int i=1;i<=slen;i++) {
        if(!qualsegments[qlen-i].equals(suffixsegments[slen-i]))
            return false;
    }
    return true;
}

static protected boolean isprefix(String packname, String prefix)
{
    // Test prefix against the segments of the packname
    String[] packsegments = packname.split("[.]");
    String[] prefixsegments = prefix.split("[.]");
    if(packsegments.length > prefixsegments.length) return false;
    int plen = packsegments.length;
    int prelen = prefixsegments.length;
    for(int i=0;i<plen;i++) {
        if(!packsegments[i].equals(prefixsegments[i]))
            return false;
    }
    return true;
}
*/

// Collect path of parent nodes upto
// and (optionally) including the package
static void
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



} // class AuxFcns

