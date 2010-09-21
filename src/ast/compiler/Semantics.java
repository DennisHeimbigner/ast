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

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class Semantics
{
    
//////////////////////////////////////////////////
// Constructor
public Semantics() {}

//////////////////////////////////////////////////

public boolean checksemantics(AST.File root)
{
    List<AST> allnodes = new ArrayList<AST>();
    if(!pass1(root,root,root,allnodes)) return false;
    verify(root, allnodes);
    root.allnodes = allnodes;
    Debug.printTree(root,new PrintWriter(System.out),true);
    if(!pass2(root,allnodes,root.getPackage().getName())) return false;
    Debug.printTree(root,new PrintWriter(System.out));
/*
    if(!pass2a(root,allnodes)) return false;
    if(!pass2b(allnodes)) return false;
    if(!pass4(root)) return false;
    if(!pass5(root)) return false;
    if(!pass6(root)) return false;
*/
    return true;
}

void verify(AST root, List<AST> allnodes)
{
if(false) {
    // verify that everynode is in allnodes
    for(AST ast : allnodes) ast.visited = true;
    System.err.println("begin missing");
    for(AST ast : root.nodeset) {
	if(ast.visited) continue;
        if(ast instanceof AST.PrimitiveType) continue;
	// We have an uncaptured node
	System.err.printf("%s: %s\n",ast.getName(),ast.getClassEnum().getName());
    }
    System.err.println("end missing");
}
}

/**
 * Pass 1 does the following:
 * 1. Collect all nodes
 * 2. Link all nodes to the tree root
 * 3. Link subnodes to container
 * 4. Link nodes to a file (for file nodes, link to the importing file)
 *
 * @param node The current node being walked
 * @param root The AST tree root
 * @param srcfile The current srcfile we are traversing
 * @param allnodes The set of all collected nodes
 * @return true if the processing succeeded.
*/

boolean
pass1(AST node, AST.File root, AST.File srcfile, List<AST> allnodes)
{
    allnodes.add(node);
    node.setRoot(root);
    // link to enclosing srcfile (or importing file)
    node.setSrcFile(srcfile);
    if(node.getClassEnum() == AST.ClassEnum.FILE) {
	srcfile = (AST.File)node;
    }
    // link children to container & recurse
    if(node.getContents() != null) {
        for(AST subnode: node.getContents()) {
   	    subnode.setContainer(node);
	    if(!pass1(subnode,root,srcfile,allnodes)) return false;
	}
    }
    return true;
}

/**
 * Pass 2 does the following:
 * 1. assign qualified names
 * 2. check for duplicate qualified names
 *
 * @param allnodes The set of all collected nodes
 * @return true if the processing succeeded.
*/

boolean
pass2(AST node, List<AST> allnodes, String qualpath)
{
    // Assign qualified names
    String qualname = qualpath+node.getName();
    for(AST ast : allnodes) {
        if(ast == node) continue;
        if(ast.qualifiedname == null) continue;
        if(qualname.equals(ast.qualifiedname))
  	    return semerror(node,"Duplicate names: "+qualname);
    }
    node.setQualifiedName(qualname);
    // recurse
    switch (node.classenum) {
    default:
	if(node.getContents() != null) {
	    for(AST subnode: node.getContents())
		pass2(subnode,allnodes,qualname);
	} break;
    }
    return true;
}


/**
 * Non-recursive pass 4 does the following:
 * 1. Deref all references to other objects
 *
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/

boolean
pass4(AST.File root, List<AST> allnodes)
{
    boolean found;
    String qualname;
    List<AST> matches;
    for(AST node: root.allnodes) {
	switch (node.getClassEnum()) {
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
	    qualname = Util.qualify(fieldtypename,field);
	    matches = Util.findbyname(qualname,allnodes);
	    found = false;
	    for(AST ast : matches) {
		if(ast instanceof AST.Type) {
		    field.fieldtype = (AST.Type)ast;
		    found = true;
		    break;
		}
	    }
	    if(!found) 
	        return semerror(node,"Field refers to undefined type: "
				     + node.name);
	    break;

	case RPC:
	    // deref the argtype name and the returntype name
	    AST.Rpc rpc = (AST.Rpc)node;
	    String[] names = (String[])rpc.getAnnotation();
	    rpc.setAnnotation(null);
	    qualname = Util.qualify(names[0],rpc);
	    matches = Util.findbyname(qualname,allnodes);
	    found = false;
	    for(AST ast : matches) {
		if(ast instanceof AST.Type) {
		    rpc.argtype = (AST.Type)ast;
		    found = true;
		    break;
		}
	    }
	    if(!found) 
	        return semerror(node,"Rpc argtype refers to undefined type: "
				     + node.name);
	    qualname = Util.qualify(names[1],rpc);
	    matches = Util.findbyname(qualname,allnodes);
	    found = false;
	    for(AST ast : matches) {
		if(ast instanceof AST.Type) {
		    rpc.returntype = (AST.Type)ast;
		    found = true;
		    break;
		}
	    }
	    if(!found) 
	        return semerror(node,"Rpc returntype refers to undefined type: "
				     + node.name);
	    break;

	default: break; // ignore
	}
    }
    return true;
}




/**
 * Pass 2 does the following:
 * 1. Divide element lists into separate groups
 * 2. collect all package nodes
 *
 * @param node The current node being walked
 * @param allnodes The set of all collected nodes
 * @param qualifiedname The qualified name to this point in the tree
 * @return true if the processing succeeded.
*/

boolean
pass5(AST.File root, List<AST> allnodes)
{
    // Divide contents lists
    for(AST node: allnodes) {
        switch (node.getClassEnum()) {
        case FILE:
            // Group the file contents
            AST.File file = (AST.File)node;
            file.filepackage = null;
            file.imports = new ArrayList<AST.File>();
            file.decls = new ArrayList<AST>();
            for(AST ast: file.getContents()) {
                switch(ast.getClassEnum()) {
                case PACKAGE: file.filepackage = (AST.Package)ast; break;
                case FILE: file.imports.add((AST.File)ast); break;
                default: file.decls.add(ast); break; // all else goes here
                }
            }
            break;
        case PACKAGE:
	    root.addAllPackages((AST.Package)node); // collect package nodes en pasant
            // After semantic processing, the Package becomes the
            // primary processing point
            AST.Package astpackage = (AST.Package)node;
            AST.File astfile = astpackage.getSrcFile();
            astpackage.setContents(new ArrayList<AST>());
            astpackage.messages = new ArrayList<AST.Message>();
            astpackage.extenders = new ArrayList<AST.Extend>();
            astpackage.enums = new ArrayList<AST.Enum>();
            astpackage.options = new ArrayList<AST.Option>();
            astpackage.services = new ArrayList<AST.Service>();
            for(AST ast: astfile.getContents()) {
                // attach all the non-file contents to the package
                if(ast.classenum != AST.ClassEnum.FILE)
                    astpackage.getContents().add(ast);
                switch(ast.classenum) {
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
            for(AST ast: msg.getContents()) {
                switch(ast.getClassEnum()) {
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
            for(AST ast: svc.getContents()) {
                switch(ast.getClassEnum()) {
                case OPTION: svc.options.add((AST.Option)ast); break;
                case RPC: svc.rpcs.add((AST.Rpc)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;          
        case ENUM:
            AST.Enum astenum = (AST.Enum)node;
            astenum.setEnumFields(new ArrayList<AST.EnumField>());
            for(AST ast: astenum.getContents()) {
                switch(ast.getClassEnum()) {
                case ENUMFIELD: astenum.getEnumFields().add((AST.EnumField)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            
            }
            break;
        case EXTEND:
            AST.Extend astextend = (AST.Extend)node;
            astextend.setFields(new ArrayList<AST.Field>());
            for(AST ast: astextend.getContents()) {
                switch(ast.getClassEnum()) {
                case FIELD: astextend.getFields().add((AST.Field)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        case FIELD:
            AST.Field astfield = (AST.Field)node;
            astfield.setOptions(new ArrayList<AST.Option>());
            for(AST ast: astfield.getContents()) {
                switch(ast.getClassEnum()) {
                case OPTION: astfield.getOptions().add((AST.Option)ast); break;
                case PACKAGE: astfield.setPackage((AST.Package)ast); break;
                default: assert(false) : "Illegal ast case"; break;
                }
            }
            break;
        // Cases where no extra action is required in pass
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
 * Pass 2a does the following:
 * 1. Link nodes to a package
 * 2. Group all package sub nodes
 *
 * @param allnodes List of allnodes
 * @return true if the processing succeeded.
*/

boolean
pass6(AST root, List<AST> allnodes)
{
    for(AST node: allnodes) {
        AST.File srcfile = node.getSrcFile();
        if(srcfile == null) srcfile = node.getRoot();
        AST.Package pack = srcfile.getFilePackage();
        assert (pack != null) : "Srcfile has no package: "+srcfile.getName();
        node.setPackage(pack);
    }
    // Setup all the package nodes
    for(AST.Package pnode: root.getAllPackages()) {
	pnode. messages = new ArrayList<AST.Message>();
	pnode. extenders = new ArrayList<AST.Extend>();
	pnode. enums = new ArrayList<AST.Enum>();
	pnode. options = new ArrayList<AST.Option>();
	pnode. services = new ArrayList<AST.Service>();
    }
    // Now collect groups package subnodes
    for(AST ast: allnodes) {
        AST.Package pack = ast.getPackage();
        assert (pack != null) : "Node has null package: "+ast.getName();
        switch(ast.getClassEnum()) {
	case MESSAGE: pack.messages.add((AST.Message)ast); break;
	case ENUM: pack.enums.add((AST.Enum)ast); break;
	case OPTION: pack.options.add((AST.Option)ast); break;
	case SERVICE: pack.services.add((AST.Service)ast); break;
	case EXTEND: pack.extenders.add((AST.Extend)ast); break;
        default: break; // ignore
	}
    }
    return true;
}

/**
 * Non-recursive pass 4 does the following:
 * 1. Check that all msg ids appear legal and are not duplicates
 * 2. Check for duplicate enum field values 
 *
 * @param root The AST tree root
 * @param allnodes The set of all collected nodes
 * @param qualifiedname The qualified name to this point in the tree
 * @return true if the processing succeeded.
*/

boolean
pass7(AST.File root)
{
    for(AST node: root.allnodes)
	if(!pass4walk(node,root)) return false;
    return true;
}

/**
 * Recursive procedure for pass4
 * @param node The current node
 * @param root The AST tree root
 * @param allnodes The set of all collected nodes
 * @param qualifiedname The qualified name to this point in the tree
 * @return true if the processing succeeded.
 */
boolean
pass4walk(AST node, AST.File root)
{
    switch (node.getClassEnum()) {
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
    case ENUMFIELD:
	AST.EnumField efield = (AST.EnumField)node;
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

    // Cases where no extra action is required in pass2
    case PACKAGE:
    case SERVICE:
    case EXTEND:
    case EXTENSIONRANGE:
    case OPTION:
    case RPC:
    case PRIMITIVETYPE:
	break;
    default: // should not happen
	assert(false) : "Illegal astcase";
	break;
    }
    return true;
}


/**
 * Non-recursive pass 6 does the following:
 * 1. Compute root.semanticnodes
 *
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/
boolean
pass8(AST.File root)
{
    // compute semantic node set
    // insert root as first element
    root.semanticnodes.add(root);
    // walk the package nodes in preorder
    for(int i=0;i<root.allpackages.size();i++) {
	AST packnode = root.allpackages.get(i);
	// Add all nodes in this package
        for(int j=0;j<root.allnodes.size();j++) {
	    AST node = root.allnodes.get(j);
	    if(node instanceof AST.Package
	       || node instanceof AST.File
	       || node == root) continue;
	    if(node.getPackage() == packnode)
		root.semanticnodes.add(node);
	}
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

