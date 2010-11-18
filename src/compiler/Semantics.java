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

import static unidata.protobuf.compiler.AST.*;

public class Semantics
{

//////////////////////////////////////////////////
// Constructor
public Semantics() {}

static boolean ctrl = true;

//////////////////////////////////////////////////

public boolean process(AST.Root root)
{
    PrintWriter w = new PrintWriter(System.err);
    Debug.printprops.qualified = true;
//    Debug.printprops.useuid = true;
    if(!collectnodes(root,root)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\ncollectnodes:");
        Debug.printTreeNodes(root,w);        
    }
    if(!setfilelink(root,null)) return false;
    if(ctrl&& Debug.enabled("trace.semantics.steps")) {
        w.println("\nsetcontainers:");
	Debug.printTreeNodes(root,w);
    }
    if(!setpackagelink(root,null)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\nsetcontainers:");
	Debug.printTreeNodes(root,w);
    }
    if(!groupbypackage(root)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\ngroupbypackages:");
	Debug.printTreeNodes(root,w);
    }
    if(!collectpackagenodesets(root)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\ncollectpackagenodesets:");
	Debug.printTreeNodes(root,w);
    }
    if(!setnodegroups(root)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\nsetnodegroups:");
	Debug.printTreeNodes(root,w);
    }

    if(!qualifynames(root)) return false;
    Debug.printprops.useuid = false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\nqualifynames:");
	Debug.printTreeNodes(root,w);
    }

    if(!dereference(root)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\ndereference:");
	Debug.printTreeNodes(root,w);
    }

    if(!checkduplicates(root.getNodeSet())) return false;
    if(!mapoptions(root)) return false;

    Debug.resetprintprops();
    // Print two ways
    if(ctrl && Debug.enabled("trace.semantics")) {
        System.err.println("-------------------------");
        System.err.println("Tree Format:");
	Debug.printTree(root,w);
        w.flush();
    }
    if(ctrl && Debug.enabled("trace.semantics")) {
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
	AST.Package p = (AST.Package)node;
	boolean match = false;
	// See if this is a duplicate package?
	for(AST.Package p2: root.getPackageSet()) {
	    if(p2.getName().equals(p.getName())) {
		if(Debug.enabled("trace.duplicate.packages"))
		    duperror(p,p2,"Duplicate Packages: "+p.getName());
	        match = true;
		break;
	    }
	}
	if(!match) {
	    root.getPackageSet().add(p);
	    // Make sure package node set is defined
	    p.setNodeSet(new ArrayList<AST>());
	}
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
    // But does have top files
    AST.File f = root.getSrcFile();
    root.setTopFile(f);
    root.setPackage(f.getFilePackage());

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
	AST.Package p = file.getFilePackage();
	for(AST fnode: file.getChildSet()) {
	    if(fnode == p) continue;
	    p.getChildSet().add(fnode);
	    fnode.setParent(p);
            fnode.setPackage(p);
	}
        file.setChildSet(null);
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
            astpackage.setMessages(new ArrayList<AST.Message>());
            astpackage.setExtenders(new ArrayList<AST.Extend>());
            astpackage.setEnums(new ArrayList<AST.Enum>());
            astpackage.setOptions(new ArrayList<AST.Option>());
            astpackage.setServices(new ArrayList<AST.Service>());
            for(AST ast: astpackage.getChildSet()) {
                switch(ast.getSort()) {
                case ENUM:
                    astpackage.getEnums().add((AST.Enum)ast);
                    break;
                case EXTEND:
                    astpackage.getExtenders().add((AST.Extend)ast);
                    break;
                case MESSAGE:
                    astpackage.getMessages().add((AST.Message)ast);
                    break;
                case OPTION:
                    astpackage.getOptions().add((AST.Option)ast);
                    break;
                case SERVICE:
                    astpackage.getServices().add((AST.Service)ast);
                    break;
                default: /*ignore*/ break;
                }
            }
            break;
        case MESSAGE:
            // Group the message elements
            AST.Message msg = (AST.Message)node;
            msg.setFields(new ArrayList<AST.Field>());
            msg.setEnums(new ArrayList<AST.Enum>());
            msg.setMessages(new ArrayList<AST.Message>());
            msg.setExtenders(new ArrayList<AST.Extend>());
            msg.setExtensions(new ArrayList<AST.Extensions>());
            msg.setOptions(new ArrayList<AST.Option>());
            msg.setGroups(new ArrayList<AST.Group>());
            for(AST ast: msg.getChildSet()) {
                switch(ast.getSort()) {
                case FIELD: msg.getFields().add((AST.Field)ast); break;
                case GROUP: msg.getGroups().add((AST.Group)ast); break;
                case ENUM: msg.getEnums().add((AST.Enum)ast); break;
                case MESSAGE: msg.getMessages().add((AST.Message)ast); break;
                case EXTEND: msg.getExtenders().add((AST.Extend)ast); break;
                case EXTENSIONS: msg.getExtensions().add((AST.Extensions)ast); break;
                case OPTION: msg.getOptions().add((AST.Option)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        case SERVICE:
            AST.Service svc = (AST.Service)node;
            svc.setOptions(new ArrayList<AST.Option>());
            svc.setRpcs(new ArrayList<AST.Rpc>());
            for(AST ast: svc.getChildSet()) {
                switch(ast.getSort()) {
                case OPTION: svc.getOptions().add((AST.Option)ast); break;
                case RPC: svc.getRpcs().add((AST.Rpc)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;          
        case ENUM:
            AST.Enum astenum = (AST.Enum)node;
            astenum.setEnumValues(new ArrayList<AST.EnumValue>());
            for(AST ast: astenum.getChildSet()) {
                switch(ast.getSort()) {
                case ENUMVALUE: astenum.getEnumValues().add((AST.EnumValue)ast); break;
                case OPTION: astenum.getOptions().add((AST.Option)ast); break;
                default:
                    assert(false) : "Illegal ast case"; break;
                }
            
            }
            break;
        case EXTEND:
            AST.Extend astextend = (AST.Extend)node;
            astextend.setFields(new ArrayList<AST.Field>());
            astextend.setGroups(new ArrayList<AST.Group>());
            for(AST ast: astextend.getChildSet()) {
                switch(ast.getSort()) {
                case FIELD: astextend.getFields().add((AST.Field)ast); break;
                case GROUP: astextend.getGroups().add((AST.Group)ast); break;
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
        case ENUMVALUE:
            AST.EnumValue astenumvalue = (AST.EnumValue)node;
            astenumvalue.setOptions(new ArrayList<AST.Option>());
            for(AST ast: astenumvalue.getChildSet()) {
                switch(ast.getSort()) {
                case OPTION: astenumvalue.getOptions().add((AST.Option)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        // Cases where no extra action is required in pass
        case GROUP:
        case ROOT:
        case FILE:
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
            String qualname = AuxFcns.computequalifiedname(ast1);
            ast1.setQualifiedName(qualname);
            if(ast1.getQualifiedName() == null) continue;
            for(AST ast2 : allnodes) {
                if(ast2 == ast1 || ast2.getQualifiedName() == null) continue;
		if(ast2.getQualifiedName().equals(ast1.getQualifiedName())) {
		    duperror(ast1,ast2,"Duplicate qualified name: "
			     +ast1.getName());
		    break;
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
    List<AST.Type> matches;
    List<AST> allnodes = root.getNodeSet();
    for(AST node: allnodes) {
	switch (node.getSort()) {
	case EXTEND:
	    // deref the msg name
	    AST.Extend extender = (AST.Extend)node;
	    String msgname = (String)extender.getAnnotation();
	    extender.setAnnotation(null);
	    matches = AuxFcns.findtypebyname(msgname,node,root);
	    found = false;
	    for(AST ast : matches) {
		if(ast instanceof AST.Message) {
		    extender.setMessage((AST.Message)ast);
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
	    String typename = (String)field.getAnnotation();
	    field.setAnnotation(null);
            // Compute absolute name relative to the parent message
	    List<AST.Type> typematches = AuxFcns.findtypebyname(typename,node,root);
	    if(typematches.size() == 0) {
	        return semerror(node,"Field refers to undefined type: "+typename);
	    } else if(typematches.size() > 1) {
		return duperror(typematches.get(0),typematches.get(1),
				"Duplicate qualified type names: "
				+typematches.get(0).getName());
	    } else { // typematches.size() == 1
   	        field.setType(typematches.get(0));
	    }
	    break;

	case RPC:
	    // deref the argtype name and the returntype name
	    AST.Rpc rpc = (AST.Rpc)node;
	    String[] names = (String[])rpc.getAnnotation();
	    rpc.setAnnotation(null);
	    typematches = AuxFcns.findtypebyname(names[0],node,root);
	    if(typematches.size() == 0) {
	        return semerror(node,"RPC returntype refers to undefined type: "+names[0]);
	    } else if(typematches.size() > 1) {
		return duperror(typematches.get(0),typematches.get(1),
				"Duplicate qualified type names: "
				+typematches.get(0).getName());
	    } else {// typematches.size() == 1
   	        rpc.setArgType(typematches.get(0));
	    }
	    typematches = AuxFcns.findtypebyname(names[1],node,root);
	    if(typematches.size() == 0) {
	        return semerror(node,"RPC returntype refers to undefined type: "+names[1]);
	    } else if(typematches.size() > 1) {
		return duperror(typematches.get(0),typematches.get(1),
				"Duplicate qualified type names: "
				+typematches.get(0).getName());
	    } else {// typematches.size() == 1
   	        rpc.setReturnType(typematches.get(0));
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
            for(AST.EnumValue field1: ((AST.Enum)node).getEnumValues()) {
                for(AST.EnumValue field2: ((AST.Enum)node).getEnumValues()) {
                    if(field1 == field2) continue;
                    if(field1.getValue() == field2.getValue()) {
                        duperror(field1,field2,
				 String.format("Duplicate enum field numbers: %s=%s and %s=%s",
				 field1.getName(),field1.getValue(),
				 field2.getName(),field2.getValue()));
                        break;
                    }
                }
            }
            break;
        case MESSAGE:
            // check for duplicates
           for(AST.Field field1: ((AST.Message)node).getFields()) {
               for(AST.Field field2: ((AST.Message)node).getFields()) {
                    if(field1 == field2) continue;
                    if(field1.getId() == field2.getId()) {
                        duperror(field1,field2,
				"Duplicate message field numbers: "+field1.getId());
                        break;
                    }
                }
            }
            break;

        case FIELD:
            // Check legality of field number
            AST.Field field = (AST.Field)node;
            if(field.getId() < 0 || field.getId() >= AST.MAXFIELDID)
                semerror(node,"Illegal message field id"+field.getId());
            break;
        default:
	    break;
        }
    }
    return true;
}


/**
 * Pass does the following:
 * - Copy the raw options for each node into the optionmap for that node
 *
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/
boolean
mapoptions(AST.Root root)
{
    for(AST ast: root.getNodeSet()) {
	if(ast.getOptions() != null)
	    for(AST.Option option: ast.getOptions()) {
		ast.setOptionMap(option.getName(),option.getValue());
	    }
    }
    return true;
}

boolean
semerror(AST node, String msg)
    {return semreport(node,msg,true);}

boolean
semwarning(AST node, String msg)
    {return semreport(node,msg,false);}


boolean
semreport(AST node, String msg, boolean err)
{
    System.err.print(err?"Semantic error ":"Warning ");
    if(node != null && node.getPosition() != null) {
	System.err.print(String.format("%s @ %s\n",
			   msg, node.getPosition()));
    } else {
	System.err.print(String.format("%s\n",msg));
    }
    return !err;
}


boolean
duperror(AST node1, AST node2, String msg)
{
    System.err.print(msg);
    if(node1 != null && node1.getPosition() != null
       && node2 != null && node2.getPosition() != null) {
	System.err.print(String.format(" ; node1 @ %s node2 @ %s",
			   node1.getPosition(),node2.getPosition()));
    }
    System.err.println();
    return false;
}


} // class Semantics

