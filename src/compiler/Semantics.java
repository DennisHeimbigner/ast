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
    
//////////////////////////////////////////////////
// Constructor
public Semantics() {}

//////////////////////////////////////////////////

public boolean process(AST.Root root)
{
    PrintWriter w = new PrintWriter(System.err);
    AST.File f = root.getRootFile();
    AST.Package p = f.getFilePackage();
    if(!pass1(root,root,f,p)) return false;
    if(false) verify(root);
    if(!pass2(root)) return false;
    if(!pass4(root.getAllNodes())) return false;
    //Debug.printTree(root,w, true) ; w.flush();
    if(!pass5(root)) return false;
    if(!pass6(root.getAllNodes())) return false;
    if(!pass7(root.getAllNodes())) return false;
    List<AST> newallnodes = new ArrayList<AST>();
    if(!pass8(root,newallnodes)) return false;
    root.setAllNodes(newallnodes);

    if(false) {
    // Print two ways
    System.err.println("-------------------------");
    Debug.printTree(root,w);
    w.flush();
    System.err.println("-------------------------");
    Debug.print(root,w);
    w.flush();
    }

    return true;
}

void verify(AST.Root root)
{
    // verify that everynode is in allnodes
    for(AST ast : root.getAllNodes()) ast.visited = true;
    System.err.println("begin missing");
    for(AST ast : root.nodeset) {
	if(ast.visited) continue;
        if(ast instanceof AST.PrimitiveType) continue;
	// We have an uncaptured node
	System.err.printf("%s: %s\n",ast.getName(),ast.getSort().getName());
    }
    System.err.println("end missing");
}

/**
 * Pass 1 does the following:
 * - Collect all nodes
 * - Link all nodes to the tree root
 * - Link children to parent
 * - Link subnodes to src file and to package
 * - Collect all package nodes and all file nodes
 *
 * @param node The current node being walked
 * @param root The AST tree root
 * @param srcfile The current srcfile we are traversing
 * @return true if the processing succeeded.
*/

boolean
pass1(AST node, AST.Root root, AST.File srcfile, AST.Package currentpackage)
{
    root.getAllNodes().add(node);
    node.setRoot(root);
    node.setSrcFile(srcfile);
    node.setPackage(currentpackage);
    if(node.getSort() == AST.Sort.FILE) {
	AST.File f = (AST.File)node;
	srcfile = f;
	root.getAllFiles().add(f);
	AST.Package p = f.getFilePackage();
	if(p != null) {
        if(!pass1(p,root,srcfile,currentpackage)) return false;
  	    root.getAllPackages().add(p);
	    p.setPackageFile(f);
	    currentpackage = p;
	}	
    }
    // link children to parent & recurse
    if(node.getChildren() != null) {
        for(AST subnode: node.getChildren()) {
   	    subnode.setParent(node);
	    if(!pass1(subnode,root,srcfile,currentpackage)) return false;
	}
    }
    return true;
}

/**
 * Pass 2 does the following:
 * - Initialize the top level structure of the root, files, and packages
 *   => Invert the file/package relationship
 * - Mirroring file children to associated packages
 *
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/

boolean
pass2(AST.Root root)
{
    // The root's children now become the set of packages    
    root.getChildren().clear();
    for(AST.Package p: root.getAllPackages()) {
	root.addChild(p);
	p.setParent(root);
    }

    // Root has no containing file or package
    root.setSrcFile(null);
    root.setPackage(null);
    root.setRoot(root); // true for all nodes

    // Mirror file children to package
    for(AST.File f: root.getAllFiles()) {
        while(f.getFilePackage() == null)
            f = f.getSrcFile();
	AST.Package p = f.getFilePackage();
        for(AST child: f.getChildren()) {
	    p.addChild(child);
            child.setParent(p);
        }
    }
    return true;
}


/**
 * Pass 4 does the following:
 * - assign qualified names
 * - check for duplicate qualified names
 *
 * @param allnodes nodes in the AST tree
 * @return true if the processing succeeded.
*/

boolean
pass4(List<AST> allnodes)
{
    // Assign qualified names
    for(AST ast1 : allnodes) {
	if(ast1.getSort() == AST.Sort.ROOT) continue;
        String qualname = Util.computequalifiedname(ast1);
        ast1.setQualifiedName(qualname);
        if(ast1.getQualifiedName() == null) return false;
        for(AST ast2 : allnodes) {
            if(ast2 == ast1 || ast2.qualifiedname == null) continue;
            if(ast2.qualifiedname.equals(ast1.qualifiedname)) {
                semerror(ast1,"Duplicate qualified names:"+ast1.qualifiedname);
                return false;
            }
        }
    }
    return true;
}

/**
 * Pass 5 does the following:
 * - Deref all references to other objects
 *
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/

boolean
pass5(AST.Root root)
{
    boolean found;
    String qualname;
    List<AST> matches;
    List<AST> allnodes = root.getAllNodes();
    for(AST node: allnodes) {
	switch (node.getSort()) {
	case EXTEND:
	    // deref the msg name
	    AST.Extend extender = (AST.Extend)node;
	    String msgname = (String)extender.getAnnotation();
	    extender.setAnnotation(null);
	    qualname = Util.qualify(msgname,extender);
	    matches = Util.findbyname(qualname,allnodes);
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
				     + node.name);
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
 * Pass 6 does the following:
 * - Divide element lists into separate groups
 *
 * @param allnodes The set of all collected nodes
 * @return true if the processing succeeded.
*/

boolean
pass6(List<AST> allnodes)
{
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
            for(AST ast: astpackage.getChildren()) {
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
            msg.extensionranges = new ArrayList<AST.ExtensionRange>();
            msg.options = new ArrayList<AST.Option>();
            for(AST ast: msg.getChildren()) {
                switch(ast.getSort()) {
                case FIELD: msg.fields.add((AST.Field)ast); break;
                case ENUM: msg.enums.add((AST.Enum)ast); break;
                case MESSAGE: msg.messages.add((AST.Message)ast); break;
                case EXTEND: msg.extenders.add((AST.Extend)ast); break;
                case OPTION: msg.options.add((AST.Option)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        case SERVICE:
            AST.Service svc = (AST.Service)node;
            svc.options = new ArrayList<AST.Option>();
            svc.rpcs = new ArrayList<AST.Rpc>();
            for(AST ast: svc.getChildren()) {
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
            for(AST ast: astenum.getChildren()) {
                switch(ast.getSort()) {
                case ENUMFIELD: astenum.getEnumFields().add((AST.EnumField)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            
            }
            break;
        case EXTEND:
            AST.Extend astextend = (AST.Extend)node;
            astextend.setFields(new ArrayList<AST.Field>());
            for(AST ast: astextend.getChildren()) {
                switch(ast.getSort()) {
                case FIELD: astextend.getFields().add((AST.Field)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        case EXTENSIONS:
            AST.Extensions astset = (AST.Extensions)node;
            astset.setExtensionRanges(new ArrayList<AST.ExtensionRange>());
            for(AST ast: astset.getChildren()) {
                switch(ast.getSort()) {
                case EXTENSIONRANGE:
		    astset.getExtensionRanges().add((AST.ExtensionRange)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        case FIELD:
            AST.Field astfield = (AST.Field)node;
            astfield.setOptions(new ArrayList<AST.Option>());
            for(AST ast: astfield.getChildren()) {
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
        case EXTENSIONRANGE:
        case OPTION:
        case RPC:
        case PRIMITIVETYPE:
    	    break;
        default: // should not happen
    	    assert(false) : "Illegal astcase";
      	    break;
        }
    }
    return true;
}

/**
 * Pass 7 does the following:
 * - Check that all msg ids appear legal and are not duplicates
 * - Check for duplicate enum field values 
 *
 * @param allnodes The set of all collected nodes
 * @return true if the processing succeeded.
*/

boolean
pass7(List<AST> allnodes)
{
    for(AST node: allnodes) {
        switch (node.getSort()) {
        case ENUM:
            // check for duplicates
            for(AST.EnumField field1: ((AST.Enum)node).getEnumFields()) {
                for(AST.EnumField field2: ((AST.Enum)node).getEnumFields()) {
                    if(field1 == field2) continue;
                    if(field1.value == field2.value) {
                        return semerror(node,"Duplicate enum field numbers: "+field1.value);
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
                        return semerror(node,"Duplicate message field numbers: "+field1.id);
                    }
                }
            }
            break;

        case FIELD:
            // Check legality of field number
            AST.Field field = (AST.Field)node;
            if(field.id < 0 || field.id >= AST.MAXFIELDID)
                return semerror(node,"Illegal message field id"+field.id);
            break;
        default:
	    break;
        }
    }
    return true;
}


/**
 * Pass8 does the following:
 * - Rebuild allnodes to be preorder
 *
 * @param root The AST tree root
 * @param newallnodes The new list of all nodes in preorder
 * @return true if the processing succeeded.
*/
boolean
pass8(AST node, List<AST> newallnodes)
{
    newallnodes.add(node);
    switch(node.getSort()) {
    case ROOT: {
	AST.Root r = (AST.Root)node;
	for(AST.Package p: r.getAllPackages()) pass8(p,newallnodes);	    
	} break;
    case PACKAGE: {
	AST.Package p = (AST.Package)node;
	pass8(p.getSrcFile(),newallnodes);
	for(AST.Option o: p.getOptions()) pass8(o,newallnodes);	    
	for(AST.Enum en: p.getEnums()) pass8(en,newallnodes);	    
	for(AST.Message m: p.getMessages()) pass8(m,newallnodes);
	for(AST.Extend ex: p.getExtenders()) pass8(ex,newallnodes);
	for(AST.Service s: p.getServices()) pass8(s,newallnodes);
	} break;
    case ENUM: {
	AST.Enum en = (AST.Enum)node;
	for(AST.EnumField ef: en.getEnumFields()) pass8(ef,newallnodes);
	} break;
    case EXTEND: {
	AST.Extend ex = (AST.Extend)node;
 	for(AST.Field f: ex.getFields()) pass8(f,newallnodes);
	} break;
    case FIELD: {
	AST.Field f = (AST.Field)node;
	for(AST.Option o: f.getOptions()) pass8(o,newallnodes);	    
	} break;
    case MESSAGE: {
	AST.Message m = (AST.Message)node;
	for(AST.Option o: m.getOptions()) pass8(o,newallnodes);	    
	for(AST.Enum e: m.getEnums()) pass8(e,newallnodes);	    
	for(AST.Message m2: m.getMessages()) pass8(m2,newallnodes);
 	for(AST.Field f: m.getFields()) pass8(f,newallnodes);
	for(AST.Extend ex: m.getExtenders()) pass8(ex,newallnodes);
	} break;
    case SERVICE: {
	AST.Service s = (AST.Service)node;
	for(AST.Option o: s.getOptions()) pass8(o,newallnodes);	    
	for(AST.Rpc r: s.getRpcs()) pass8(r,newallnodes);	    
	} break;
    case ENUMFIELD:
    case EXTENSIONS:
    case EXTENSIONRANGE:
    case FILE:
    case OPTION:
    case PRIMITIVETYPE:
    case RPC:
	break;
    }
    return true;
}

boolean
semerror(AST node, String msg)
{
    if(node != null && node.position != null) {
	System.err.println(String.format("Semantic error: %s; line %d\n",
			   msg, node.position.lineno));
    } else {
	System.err.println(String.format("Semantic error: %s\n",msg));
    }
    return false;
}


} // class Semantics

