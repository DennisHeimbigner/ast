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
import static unidata.protobuf.compiler.Debug.*;

public class ProtobufSemantics extends Semantics
{

//////////////////////////////////////////////////
// Constructor
public ProtobufSemantics() {}

static boolean ctrl = true;

//////////////////////////////////////////////////

public boolean
process(AST.Root root, String[] argv)
{
    PrintWriter w = new PrintWriter(System.err);
    Debug.printprops.qualified = true;
//    Debug.printprops.useuid = true;

    if(!collectglobalnodesets(root,root)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\ncollectglobalnodesets:");
        Debug.printTreeNodes(root,w);        
    }

    if(!collectsubtrees(root)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\ncollectsubtrees:");
	Debug.printTreeNodes(root,w);
    }

    if(!setfilelink(root,null)) return false;
    if(ctrl&& Debug.enabled("trace.semantics.steps")) {
        w.println("\nsetfilelink:");
	Debug.printTreeNodes(root,w);
    }

    if(!setpackagelink(root)) return false;
    if(ctrl&& Debug.enabled("trace.semantics.steps")) {
        w.println("\nsetpackagelink:");
	Debug.printTreeNodes(root,w);
    }

    if(!collectnodesets(root)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\ncollectnodesets:");
	Debug.printTreeNodes(root,w);
    }

    if(!setscopenames(root)) return false;
    Debug.printprops.useuid = false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\nsetscopenames:");
	Debug.printTreeNodes(root,w);
    }

    if(!qualifynames(root)) return false;
    Debug.printprops.useuid = false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\nqualifynames:");
	Debug.printTreeNodes(root,w);
    }

    if(!checkduplicatenames(root)) return false;
    Debug.printprops.useuid = false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\ncheckduplicatenames:");
	Debug.printTreeNodes(root,w);
    }

    if(!dereference(root)) return false;
    if(ctrl && Debug.enabled("trace.semantics.steps")) {
        w.println("\ndereference:");
	Debug.printTreeNodes(root,w);
    }

    if(!applyExtensions(root)) return false;

    if(!checkmisc1(root)) return false;
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
    for(AST ast : root.getNodeSet()) {
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
collectglobalnodesets(AST node, AST.Root root)
{
    if(node == null) return true;
    root.getNodeSet().add(node);
    node.setRoot(root);
    // Collect selected sets of nodes
    switch (node.getSort()) {
    case FILE:
	root.getFileSet().add((AST.File)node);
	// Make sure the package is included
	if(!collectglobalnodesets(((AST.File)node).getFilePackage(),root)) return false;
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
            if(!collectglobalnodesets(subnode,root)) return false;
        }
    }
    return true;
}

/**
 * Pass does the following:
 * - for each node collect the set of nodes in the subtree below it
 *   [this is, roughly, the transitive closure of the child set]
 *   Special cases:
 *   1. Each file node excludes any contained file subtrees
 *
 * @param root AST.Root
 * @return true if the processing succeeded.
*/
boolean
collectsubtrees(AST.Root root)
{
    for(AST.File file: root.getFileSet()) {
        if(!collectsubtreesr(file)) return false;
    }
    return true;
}
 
/**
 * Recursive part of collectsubtrees
 *
 * @param thisnode An AST node whose subtree will be computed
 * @return true if no error, false otherwise
*/
boolean
collectsubtreesr(AST thisnode)
{
    List<AST> thisset = new ArrayList<AST>();
    for(AST node: thisnode.getChildSet()) {
	if(node.getSort() != AST.Sort.FILE) {
	    if(!collectsubtreesr(node)) return false;
        }
        thisset.add(node);
        thisset.addAll(node.getNodeSet());
    }
    thisnode.setNodeSet(thisset);
    return true;
}

/**
 * Pass does the following:
 * - Link subnodes to src file
 * - Verify that each file has at most one package 
 *
 * @param node The current root for a subtree
 * @param currentfile The currently containing file
 * @return true if the processing succeeded.
*/

boolean
setfilelink(AST node, AST.File currentfile)
{
    node.setSrcFile(currentfile);
    for(AST ast: node.getChildSet()) {
	if(ast.getSort() == AST.Sort.FILE) {
	    AST.File f = (AST.File)ast;
	    // verify only one package
	    AST.Package p = null;
	    for(AST n: f.getChildSet()) {
	        if(n.getSort() != AST.Sort.PACKAGE) continue;
	        if(p == null)
		    p = (AST.Package)n;
	        else if(n != p) {
                    semerror(f,"File: "+f.getName()+"; multiple package declarations");
	        }
	    }
	    currentfile = f;
	}
	// recurse using this file as the current file
	for(AST subnode: ast.getChildSet()) {
            if(subnode.getSort() == AST.Sort.FILE) continue; // files will be handled above
	    if(!setfilelink(subnode,currentfile)) return false;
	}
    }
    return true;
}

/**
 * Pass does the following:
 * - Link subnodes to closest containing package
 *   [note: implements #include semantics]
 *
 * @param node The current root for a subtree
 * @param currentpackage The currently containing package
 * @return true if the processing succeeded.
*/

boolean
setpackagelink(AST.Root root)
{
    for(AST.File f: root.getFileSet()) {
	AST.Package p = f.getFilePackage();
        if(p == null) continue;
	for(AST ast: f.getNodeSet()) {
	    ast.setPackage(p);
	}
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
collectnodesets(AST.Root root)
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
            svc.setRPCs(new ArrayList<AST.RPC>());
            for(AST ast: svc.getChildSet()) {
                switch(ast.getSort()) {
                case OPTION: svc.getOptions().add((AST.Option)ast); break;
                case RPC: svc.getRPCs().add((AST.RPC)ast); break;
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
                case PACKAGE: /*astfield.setPackage((AST.Package)ast);*/ assert(false); break;
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
 * - compute scope names
 *
 * @param root of the tree
 * @return true if the processing succeeded.
*/

boolean
setscopenames(AST.Root root)
{
    // Assign the scope names
    for(AST ast : root.getNodeSet()) {
	ast.setScopeName(AuxFcns.computescopename(ast));
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
    for(AST.File file : root.getFileSet()) {
	String qname = file.getScopeName();
	for(AST ast: file.getChildSet()) {
   	    if(!qualifynamesr(ast,qname)) return false;
	}
    }
    return true;
}

/**
 * Recursive companion to qualifynames
 *
 * @param root of the tree
 * @return true if the processing succeeded.
*/

boolean
qualifynamesr(AST ast, String qualprefix)
{
    String qname = null;
    switch (ast.getSort()) {

    // Following cases all do simple suffixing
    case ENUM:
    case ENUMVALUE:
    case FIELD:
    case MESSAGE:
    case RPC:
    case SERVICE:
	if(qualprefix == null) qualprefix = "";
	qname = qualprefix + "." + ast.getScopeName();
	break;

    case FILE:
	AST.File file = (AST.File)ast;
        if(file.getFilePackage() != null) {
	    qname = file.getFilePackage().getScopeName();
	} else {
	    qname = "";
	}
	break;	    

    // Ignore the prefix
    case PACKAGE:
    case PRIMITIVETYPE:
	qname = ast.getScopeName();
	break;

    // These do not have qualifiednames
    case EXTEND:
    case OPTION:
    case GROUP:
    case EXTENSIONS:
    case ROOT:
    case PAIR:
    case COMPOUNDCONSTANT:
    default: break;
    }
    ast.setQualifiedName(qname);
    for(AST subnode: ast.getChildSet()) {
   	if(!qualifynamesr(subnode,qname)) return false;
    }
    return true;
}

/**
 * Pass does the following:
 * - check for duplicate qualified names
 *
 * @param root of the tree
 * @return true if the processing succeeded.
*/

boolean
checkduplicatenames(AST.Root root)
{
    List<AST> allnodes = root.getNodeSet();
    for(AST ast1 : allnodes) {
        for(AST ast2 : allnodes) {
	    if(ast2 == ast1 || ast2.getQualifiedName() == null) continue;
	    // special case testing
	    if(ast2.getQualifiedName().equals(ast1.getQualifiedName())) {
		// report and keep going
		duperror(ast1,ast2,"Duplicate qualified name: "
			     +ast1.getQualifiedName());
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
	    String msgname = extender.getName();
	    matches = AuxFcns.findtypebyname(msgname,node);
	    if(matches.size() > 1)
	        return semerror(node,"Extend msg name is ambiguous: "
				     + msgname);
	    found = false;
	    AST match = null;
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
	    List<AST.Type> typematches = AuxFcns.findtypebyname(typename,node);
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
	    AST.RPC rpc = (AST.RPC)node;
	    String[] names = (String[])rpc.getAnnotation();
	    rpc.setAnnotation(null);
	    typematches = AuxFcns.findtypebyname(names[0],node);
	    if(typematches.size() == 0) {
	        return semerror(node,"RPC returntype refers to undefined type: "+names[0]);
	    } else if(typematches.size() > 1) {
		return duperror(typematches.get(0),typematches.get(1),
				"Duplicate qualified type names: "
				+typematches.get(0).getName());
	    } else {// typematches.size() == 1
   	        rpc.setArgType(typematches.get(0));
	    }
	    typematches = AuxFcns.findtypebyname(names[1],node);
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

static public boolean
applyExtensions(AST.Root root)
{
    List<AST> allnodes = root.getNodeSet();
    // Locate extension nodes and insert into the corresponding
    // base message; also mark fields as extensions
    for(AST ast1 : allnodes) {
	if(ast1.getSort() != AST.Sort.EXTEND) continue;
	AST.Extend extend = (AST.Extend)ast1;
	AST.Message base = extend.getMessage();
	List<Field> basefields = base.getFields();
	List<Field> newfields = new ArrayList<Field>();
	for(AST.Field efield: extend.getFields()) {
	    boolean ok = true;
	    boolean more = true;
	    // Check to see if a field of the same name or id
            // already exists
	    for(AST.Field mfield: basefields) {
		if(mfield.getName().equals(efield.getName())
		   || mfield.getId() == efield.getId()) {
		    System.err.printf("Extension field %s.%s duplicates Message field %s.%s\n",
					extend.getName(),efield.getName(),
					base.getName(),mfield.getName());

		} else
		    newfields.add(efield); // capture fields to add
	    }
        }
	for(AST.Field field: newfields) {
	    field.setExtend(extend);
	    basefields.add(field);
	    // fix links : package and parent
	    field.setSrcFile(base.getSrcFile());
	    field.setParent(base.getParent());
	    base.getFields().add(field);
	    // Recompute qualified name
	    field.setQualifiedName(AuxFcns.computequalifiedname(field));
	}
	base.setFields(AuxFcns.sortFieldIds(basefields));
    }
    return true;
}

/**
 * Pass does the following:
 * - Check that all msg ids appear legal and are not duplicates
 * - Check for duplicate enum field values 
 *
 * @param root AST.root
 * @return true if the processing succeeded.
*/

boolean
checkmisc1(AST.Root root)
{
    for(AST node: root.getNodeSet()) {
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
 * - Canonicalize certain names (e.g. DEFAULT)
 * - capture value of selected true/false options (e.g. packed)
 * - Also add the -D flags from the command line to the top file's
 *   global option map
 *
 * @param root The AST tree root
 * @return true if the processing succeeded.
*/
boolean
mapoptions(AST.Root root)
{
    /* Add in -D flags  and make default be to generate code*/
    AST.File topfile = root.getTopFile();
    for(String key: System.getProperties().stringPropertyNames()) {
	String value = System.getProperty(key);
	if(value != null) {
	    topfile.setOptionMap(key,value);
	}
    }

    for(AST ast: root.getNodeSet()) {
	if(ast.getOptions() != null && ast.getOptions().size() > 0)
	    for(AST.Option option: ast.getOptions()) {
                String optionname = option.getName();
                String optionvalue = option.getValue();
                if(optionname.equalsIgnoreCase("default")) optionname = "DEFAULT";
		ast.setOptionMap(optionname,optionvalue);
		if(optionname.equalsIgnoreCase("packed"))
		    ast.setIsPacked(getbooleanvalue(optionvalue));
	    }
    }
    return true;
}

static boolean
getbooleanvalue(String optionvalue)
{
    boolean ispacked = false;
    if(optionvalue.equalsIgnoreCase("true"))
	ispacked = true;
    else if(optionvalue.equalsIgnoreCase("false"))
	ispacked = false;
    else try {
	int num = Integer.parseInt(optionvalue);
	ispacked = (num != 0);
	} catch (NumberFormatException nfe) {} // ignore
    return ispacked;
}

} // class Semantics

