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

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class Semantics
{

static public boolean debug = false;

//////////////////////////////////////////////////
// Constructor
public Semantics() {}

//////////////////////////////////////////////////

public boolean process(AST.Root root)
{
    PrintWriter w = new PrintWriter(System.err);
    Debug.printprops.qualified = true;
    Debug.printprops.useuid = true;
    if(!collectnodes(root,root)) return false;
    if(debug) {
        w.println("\ncollectnodes:");
        Debug.printTreeNodes(root,w);        
    }
    if(!setfilelink(root,null)) return false;
    if(debug) {
        w.println("\nsetcontainers:");
	Debug.printTreeNodes(root,w);
    }
    if(!setpackagelink(root,null)) return false;
    if(debug) {
        w.println("\nsetcontainers:");
	Debug.printTreeNodes(root,w);
    }
    if(!groupbypackage(root)) return false;
    if(debug) {
        w.println("\ngroupbypackages:");
	Debug.printTreeNodes(root,w);
    }
    if(!collectpackagenodesets(root)) return false;
    if(debug) {
        w.println("\ncollectpackagenodesets:");
	Debug.printTreeNodes(root,w);
    }
    if(!setnodegroups(root)) return false;
    if(debug) {
        w.println("\nsetnodegroups:");
	Debug.printTreeNodes(root,w);
    }
    if(!qualifynames(root)) return false;
    Debug.printprops.useuid = false;
    if(debug) {
        w.println("\nqualifynames:");
	Debug.printTreeNodes(root,w);
    }
    if(!dereference(root)) return false;
    if(debug) {
        w.println("\ndereference:");
	Debug.printTreeNodes(root,w);
    }
    if(!checkduplicates(root.getNodeSet())) return false;
    Debug.resetprintprops();
    
    if(debug) {
        // Print two ways
        System.err.println("-------------------------");
        System.err.println("Tree Format:");
	Debug.printTree(root,w);
        w.flush();
        System.err.println("-------------------------");
        System.err.println("Proto Format:");
        Debug.print(root,w);
        w.flush();
    }

    return true;
}

/*IGNORE
void verify(AST.Root root)
{
    // verify that everynode is in allnodes
    for(AST ast : root.getNodeSet()) ast.visited = true;
    System.err.println("begin missing");
    for(AST ast : root.nodeset) {
	if(ast.visited) continue;
        if(ast instanceof AST.PrimitiveType) continue;
	// We have an uncaptured node
	System.err.printf("%s: %s\n",ast.getName(),ast.getSort().getName());
    }
    System.err.println("end missing");
}
*/

/**
 * Pass does the following:
 * - Collect all nodes
 * - Collect all package nodes and all file nodes
 * - link all nodes to the root
 * - link children to parent
 *
 * @param node The current node being walked
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/

boolean
collectnodes(AST node, AST.Root root)
{
    if(node == null) return true;
    root.getNodeSet().add(node);
    node.setRoot(root);
    // Collect selected sets of nodes
    switch (node.getSort()) {
    case FILE:
	root.getFileSet().add((AST.File)node);
	break;
    case PACKAGE:
	root.getPackageSet().add((AST.Package)node);
	// Make sure package node set is defined
	((AST.Package)node).setNodeSet(new ArrayList<AST>());
	break;
    default: break;
    }
    // recurse on child set
    if(node.getChildSet() != null) {
        for(AST subnode: node.getChildSet()) {
            subnode.setParent(node);
            if(!collectnodes(subnode,root)) return false;
        }
    }
    return true;
}

/**
 * Pass does the following:
 * - Link subnodes to src file
 * - Verify that each file has at most one package 
 *
 * @param node The current node being walked
 * @param currentfile The currently enclosing file
 * @return true if the processing succeeded.
*/

boolean
setfilelink(AST node, AST.File currentfile)
{
    node.setSrcFile(currentfile);
    switch (node.getSort()) {
    case FILE:
	AST.File f = (AST.File)node;
	// Check the child set for multiple packages
	if(f.getChildSet() != null) {
	    AST p = null;
	    for(AST ast: f.getChildSet()) {
		if(ast.getSort() == AST.Sort.PACKAGE) {
		    if(p != null) {
                        semerror(f,"File: "+f.getName()+"; multiple package declarations");
		    }
		    p = ast;
		}
	    }
	}
	f.setSrcFile(currentfile); 
        currentfile = f; // Make this the current file
	break;
    default: break;
    }

    // Recurse on child set 
    if(node.getChildSet() != null) {
        // Recurse on child set
        for(AST subnode: node.getChildSet()) {
            if(!setfilelink(subnode,currentfile)) return false;
        }
    }
    return true;
}

/**
 * Pass does the following:
 * - Link subnodes to package
 * - Link package and file
 *
 * @param node The current node being walked
 * @param currentpackage The currently enclosing package
 * @return true if the processing succeeded.
*/

boolean
setpackagelink(AST node, AST.Package currentpackage)
{
    AST.Root root = node.getRoot();
    node.setPackage(currentpackage);
    if(node.getSort() == AST.Sort.FILE) {
	AST.File f = (AST.File)node;
	AST.Package p = f.getFilePackage();
	// Set the linkages properly for the file and its package
	if(p != null) {
	    p.setPackage(currentpackage);
            currentpackage = p; // Make this the current package
	    // Cross link file and the package
	    p.setPackageFile(f);
	}
    }
    // Recurse on child set
    if(node.getChildSet() != null) {
        // Recurse on child set
        for(AST subnode: node.getChildSet()) {
            if(!setpackagelink(subnode,currentpackage)) return false;
        }
    }
    return true;
}

/**
 * Pass does the following:
 * - move packages to be the children of the root
 * - move file children to be children of the file's package
 *
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/

boolean
groupbypackage(AST.Root root)
{
    // Root has no containing file or package
    root.setSrcFile(null);
    root.setPackage(null);
    root.setRoot(root); // true for all nodes

    // Make root's children become the set of packages    
    root.getChildSet().clear();
    for(AST.Package p: root.getPackageSet()) {
	root.getChildSet().add(p);
	p.setParent(root);
    }

    // Make each file's children to be children of the containing package
    for(AST.File file: root.getFileSet()) {
	AST.Package p = file.getPackage();
        if(p == null) continue;
	for(AST fnode: file.getChildSet()) {
	    if(p.getChildSet() == null) p.setChildSet(new ArrayList<AST>());
	    p.getChildSet().add(fnode);
	    fnode.setParent(p);
	}
    }
    return true;
}

/**
 * Pass does the following:
 * - collect the nodeset for each package in no specified order
 *
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/
boolean
collectpackagenodesets(AST.Root root)
{
    for(AST node: root.getNodeSet()) {
	AST.Package p = node.getPackage();
	if(p == null) continue;
	if(p.getNodeSet() == null) p.setNodeSet(new ArrayList<AST>());
	p.getNodeSet().add(node);
    }
    return true;
}

/**
 * Pass does the following:
 * - Fill in various fields for each node type
 *
 * @param root The ast tree root
 * @return true if the processing succeeded.
*/

boolean
setnodegroups(AST.Root root)
{
    List<AST> allnodes = root.getNodeSet();
    // Divide children lists
    for(AST node: allnodes) {
        switch (node.getSort()) {
        case PACKAGE:
            AST.Package astpackage = (AST.Package)node;
            astpackage.messages = new ArrayList<AST.Message>();
            astpackage.extenders = new ArrayList<AST.Extend>();
            astpackage.enums = new ArrayList<AST.Enum>();
            astpackage.options = new ArrayList<AST.Option>();
            astpackage.services = new ArrayList<AST.Service>();
            for(AST ast: astpackage.getChildSet()) {
                switch(ast.sort) {
                case ENUM:
                    astpackage.enums.add((AST.Enum)ast);
                    break;
                case EXTEND:
                    astpackage.extenders.add((AST.Extend)ast);
                    break;
                case MESSAGE:
                    astpackage.messages.add((AST.Message)ast);
                    break;
                case OPTION:
                    astpackage.options.add((AST.Option)ast);
                    break;
                case SERVICE:
                    astpackage.services.add((AST.Service)ast);
                    break;
                default: /*ignore*/ break;
                }
            }
            break;
        case MESSAGE:
            // Group the message elements
            AST.Message msg = (AST.Message)node;
            msg.fields = new ArrayList<AST.Field>();
            msg.enums = new ArrayList<AST.Enum>();
            msg.messages = new ArrayList<AST.Message>();
            msg.extenders = new ArrayList<AST.Extend>();
            msg.extensions = new ArrayList<AST.Extensions>();
            msg.options = new ArrayList<AST.Option>();
            for(AST ast: msg.getChildSet()) {
                switch(ast.getSort()) {
                case FIELD: msg.fields.add((AST.Field)ast); break;
                case ENUM: msg.enums.add((AST.Enum)ast); break;
                case MESSAGE: msg.messages.add((AST.Message)ast); break;
                case EXTEND: msg.extenders.add((AST.Extend)ast); break;
                case EXTENSIONS: msg.extensions.add((AST.Extensions)ast); break;
                case OPTION: msg.options.add((AST.Option)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        case SERVICE:
            AST.Service svc = (AST.Service)node;
            svc.options = new ArrayList<AST.Option>();
            svc.rpcs = new ArrayList<AST.Rpc>();
            for(AST ast: svc.getChildSet()) {
                switch(ast.getSort()) {
                case OPTION: svc.options.add((AST.Option)ast); break;
                case RPC: svc.rpcs.add((AST.Rpc)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;          
        case ENUM:
            AST.Enum astenum = (AST.Enum)node;
            astenum.setEnumFields(new ArrayList<AST.EnumField>());
            for(AST ast: astenum.getChildSet()) {
                switch(ast.getSort()) {
                case ENUMFIELD: astenum.getEnumFields().add((AST.EnumField)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            
            }
            break;
        case EXTEND:
            AST.Extend astextend = (AST.Extend)node;
            astextend.setFields(new ArrayList<AST.Field>());
            for(AST ast: astextend.getChildSet()) {
                switch(ast.getSort()) {
                case FIELD: astextend.getFields().add((AST.Field)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        case FIELD:
            AST.Field astfield = (AST.Field)node;
            astfield.setOptions(new ArrayList<AST.Option>());
            for(AST ast: astfield.getChildSet()) {
                switch(ast.getSort()) {
                case OPTION: astfield.getOptions().add((AST.Option)ast); break;
                case PACKAGE: astfield.setPackage((AST.Package)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        // Cases where no extra action is required in pass
        case ROOT:
        case FILE:
        case ENUMFIELD:
        case EXTENSIONS:
        case OPTION:
        case RPC:
        case PRIMITIVETYPE:
    	    break;
        default: {// should not happen
    	    assert(false) : "Illegal astcase";
        } break;
        }
    }
    return true;
}

/**
 * Pass does the following:
 * - assign qualified names
 * - check for duplicate qualified names
 *
 * @param root of the tree
 * @return true if the processing succeeded.
*/

boolean
qualifynames(AST.Root root)
{
    // Assign first the name for root, then all files, then all packages
    root.setQualifiedName("");    
    for(AST ast1 : root.getFileSet()) {
	ast1.setQualifiedName("."+ast1.getName());
    }    
    for(AST ast1 : root.getPackageSet()) {
	ast1.setQualifiedName("."+ast1.getName());
    }    
    List<AST> allnodes = root.getNodeSet();
    // Assign all other qualified names (assumes allnodes in preorder)
    for(AST ast1 : allnodes) {
	switch (ast1.getSort()) {
	case ROOT: case PACKAGE: break;
	default:
            String qualname = Util.computequalifiedname(ast1);
            ast1.setQualifiedName(qualname);
            if(ast1.getQualifiedName() == null) continue;
            for(AST ast2 : allnodes) {
                if(ast2 == ast1 || ast2.getQualifiedName() == null) continue;
		if(ast2.getQualifiedName().equals(ast1.getQualifiedName())) {
                    semerror(ast1,"Duplicate qualified names: '"
			 +ast1.getQualifiedName()+"'"
			 + String.format(" [%d,%d]",ast1.index,ast2.index)
			 );
                    return false;
                }
            }
	}
    }
    return true;
}

/**
 * Pass does the following:
 * - Deref all references to other objects
 *
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/

boolean
dereference(AST.Root root)
{
    boolean found;
    String qualname;
    List<AST> matches;
    List<AST> allnodes = root.getNodeSet();
    for(AST node: allnodes) {
	switch (node.getSort()) {
	case EXTEND:
	    // deref the msg name
	    AST.Extend extender = (AST.Extend)node;
	    String msgname = (String)extender.getAnnotation();
	    extender.setAnnotation(null);
	    matches = Util.findbyname(msgname,allnodes);
	    found = false;
	    for(AST ast : matches) {
		if(ast instanceof AST.Message) {
		    extender.message = (AST.Message)ast;
		    found = true;
		    break;
		}
	    }
	    if(!found) 
	        return semerror(node,"Extend refers to undefined message: "
				     + msgname);
	    break;

	case FIELD:
	    // deref the field type name
	    AST.Field field = (AST.Field)node;
	    String fieldtypename = (String)field.getAnnotation();
	    field.setAnnotation(null);
            // Compute absolute name relative to the parent message
	    List<AST.Type> typematches = Util.findtypebyname(fieldtypename,root);
	    if(typematches.size() == 0) {
	        return semerror(node,"Field refers to undefined type: "+fieldtypename);
	    } else if(typematches.size() > 1) {
		return semerror(typematches.get(0),"Duplicate type names:"+typematches.get(0).getName());
	    } else { // typematches.size() == 1
   	        field.fieldtype = typematches.get(0);
	    }
	    break;

	case RPC:
	    // deref the argtype name and the returntype name
	    AST.Rpc rpc = (AST.Rpc)node;
	    String[] names = (String[])rpc.getAnnotation();
	    rpc.setAnnotation(null);
	    typematches = Util.findtypebyname(names[0],root);
	    if(typematches.size() == 0) {
	        return semerror(node,"RPC returntype refers to undefined type: "+names[0]);
	    } else if(typematches.size() > 1) {
		return semerror(typematches.get(0),"Duplicate type names:"+typematches.get(0).getName());
	    } else {// typematches.size() == 1
   	        rpc.argtype = typematches.get(0);
	    }
	    typematches = Util.findtypebyname(names[1],root);
	    if(typematches.size() == 0) {
	        return semerror(node,"RPC returntype refers to undefined type: "+names[1]);
	    } else if(typematches.size() > 1) {
		return semerror(typematches.get(0),"Duplicate type names:"+typematches.get(0).getName());
	    } else {// typematches.size() == 1
   	        rpc.returntype = typematches.get(0);
	    }
	    break;

	default: break; // ignore
	}
    }
    return true;
}

/**
 * Pass does the following:
 * - Check that all msg ids appear legal and are not duplicates
 * - Check for duplicate enum field values 
 *
 * @param allnodes The set of all collected nodes
 * @return true if the processing succeeded.
*/

boolean
checkduplicates(List<AST> allnodes)
{
    for(AST node: allnodes) {
        switch (node.getSort()) {
        case ENUM:
            // check for duplicates
            for(AST.EnumField field1: ((AST.Enum)node).getEnumFields()) {
                for(AST.EnumField field2: ((AST.Enum)node).getEnumFields()) {
                    if(field1 == field2) continue;
                    if(field1.value == field2.value) {
                        semerror(node,"Duplicate enum field numbers: "+field1.value);
                        break;
                    }
                }
            }
            break;
        case MESSAGE:
            // check for duplicates
           for(AST.Field field1: ((AST.Message)node).fields) {
               for(AST.Field field2: ((AST.Message)node).fields) {
                    if(field1 == field2) continue;
                    if(field1.id == field2.id) {
                        semerror(node,"Duplicate message field numbers: "+field1.id);
                        break;
                    }
                }
            }
            break;

        case FIELD:
            // Check legality of field number
            AST.Field field = (AST.Field)node;
            if(field.id < 0 || field.id >= AST.MAXFIELDID)
                semerror(node,"Illegal message field id"+field.id);
            break;
        default:
	    break;
        }
    }
    return true;
}


/**
 * Pass does the following:
 * - Rebuild allnodes to be preorder
 *
 * @param node The AST tree node
 * @param newallnodes The new list of all nodes in preorder
 * @return true if the processing succeeded.
*/
/*IGNORE
boolean
rebuild(AST node, List<AST> newallnodes)
{
    newallnodes.add(node);
    switch(node.getSort()) {
    case ROOT: {
	AST.Root r = (AST.Root)node;
	for(AST.Package p: r.getPackageSet()) pass8(p,newallnodes);	    
	} break;
    case PACKAGE: {
	AST.Package p = (AST.Package)node;
	pass8(p.getSrcFile(),newallnodes);
	if(p.getOptions() != null)
	    for(AST.Option o: p.getOptions()) pass8(o,newallnodes);	    
	if(p.getEnums() != null)
	    for(AST.Enum en: p.getEnums()) pass8(en,newallnodes);	    
	if(p.getMessages() != null)
	    for(AST.Message m: p.getMessages()) pass8(m,newallnodes);
	if(p.getExtenders() != null)
	    for(AST.Extend ex: p.getExtenders()) pass8(ex,newallnodes);
	if(p.getServices() != null)
	    for(AST.Service s: p.getServices()) pass8(s,newallnodes);
	} break;
    case ENUM: {
	AST.Enum en = (AST.Enum)node;
	if(en.getEnumFields() != null)
	    for(AST.EnumField ef: en.getEnumFields()) pass8(ef,newallnodes);
	} break;
    case EXTEND: {
	AST.Extend ex = (AST.Extend)node;
	if(ex.getFields() != null)
	    for(AST.Field f: ex.getFields()) pass8(f,newallnodes);
	} break;
    case FIELD: {
	AST.Field f = (AST.Field)node;
	if(f.getOptions() != null)
	    for(AST.Option o: f.getOptions()) pass8(o,newallnodes);	    
	} break;
    case MESSAGE: {
	AST.Message m = (AST.Message)node;
	if(m.getOptions() != null)
	    for(AST.Option o: m.getOptions()) pass8(o,newallnodes);	    
	if(m.getEnums() != null)
	    for(AST.Enum e: m.getEnums()) pass8(e,newallnodes);	    
	if(m.getMessages() != null)
	    for(AST.Message m2: m.getMessages()) pass8(m2,newallnodes);
	if(m.getFields() != null)
	    for(AST.Field f: m.getFields()) pass8(f,newallnodes);
	if(m.getExtenders() != null)
	    for(AST.Extend ex: m.getExtenders()) pass8(ex,newallnodes);
	} break;
    case SERVICE: {
	AST.Service s = (AST.Service)node;
	if(s.getOptions() != null)
	    for(AST.Option o: s.getOptions()) pass8(o,newallnodes);	    
	if(s.getRpcs() != null)
	    for(AST.Rpc r: s.getRpcs()) pass8(r,newallnodes);	    
	} break;
    case ENUMFIELD:
    case EXTENSIONS:
    case FILE:
    case OPTION:
    case PRIMITIVETYPE:
    case RPC:
	break;
    }
    return true;
}
*/

boolean
semerror(AST node, String msg)
{
    if(node != null && node.position != null) {
	System.err.println(String.format("Semantic error: %s ; line %d\n",
			   msg, node.position.lineno));
    } else {
	System.err.println(String.format("Semantic error: %s\n",msg));
    }
    return false;
}


} // class Semantics

