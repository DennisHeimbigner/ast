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

static public String qualify(String name, AST enclosure)
{
    if(name.startsWith(".")) return name; // already fully qualified
    return enclosure.getQualifiedName()+name;
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


static List<AST> concat(List<AST> list1, List<AST> list2)
{
    List<AST> catlist = new ArrayList<AST>();
    if(list1 != null) for(AST e1: list1) catlist.add(e1);
    if(list2 != null) for(AST e2: list2) catlist.add(e2);
    return catlist;
}


static String computequalifiedname(AST node)
{
    if(node.getQualifiedName() != null)
        return node.getQualifiedName();
    AST.Package pack = node.getPackage();
    assert( pack != null): "node not in package:"+node.getName();
    AST container = node.getContainer();
    switch (node.getClassEnum()) {
    case FILE:
    case PACKAGE:
	node.setQualifiedName(node.getName());
	break;
    case MESSAGE:
    case SERVICE:
    case ENUM:
    case EXTEND:
    case FIELD:
    case ENUMFIELD:
    case EXTENSIONRANGE:
    case OPTION:
    case RPC:
    case PRIMITIVETYPE:
	node.setQualifiedName(computequalifiedname(container)
			      +"."+node.getName());
	break;	
    }

    return node.getQualifiedName();    
}


} // class Util

