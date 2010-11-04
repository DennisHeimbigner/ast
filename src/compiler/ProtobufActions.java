/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */
/****************************************************/

package unidata.protobuf.compiler;

import java.io.*;
import java.util.*;

public abstract class ProtobufActions
{

//////////////////////////////////////////////////
// Class State

//////////////////////////////////////////////////
// Instance State

    ProtobufLexer lexstate = null;
    ProtobufActions state = null; /* Slight kludge */
    ASTFactory astfactory = null;
    AST.Root ast = null; // root node of the AST
    String filename = null;
    List<AST.PrimitiveType> primitives = null;
    String importfilename = null; // temporary storage    

//////////////////////////////////////////////////
// Constructors

ProtobufActions()
{
    this(null);
}

ProtobufActions(ASTFactory factory)
{
    state = this;
    if(factory == null) factory = new ASTFactoryDefault();
    this.astfactory = factory;
    // Construct the primitive type nodes
    primitives = new ArrayList<AST.PrimitiveType>();
    for(AST.PrimitiveSort prim: AST.PrimitiveSort.values()) {
	AST.PrimitiveType pt = factory.newPrimitiveType(prim);
	primitives.add(pt);
    }
}

public void
reset(String filename, Reader stream)
{
    this.filename = filename;
    lexstate.reset(state);
    lexstate.setStream(filename,stream);
}


//////////////////////////////////////////////////
// Get/set

public AST.Root getAST() {return ast;}

public List<String> getIncludePaths() {return (lexstate==null?null:lexstate.includepaths);}

public void setIncludePaths(List<String> paths)
    {if(lexstate != null) lexstate.includepaths = paths;}

//////////////////////////////////////////////////
// Access into the DapParser for otherwise inaccessible fiels

abstract public boolean parse(String filename,Reader stream) throws IOException;
abstract public Object parseError(String msg);
abstract public Object parseWarning(String msg);
abstract int getDebugLevel();
abstract void setDebugLevel(int level);

//////////////////////////////////////////////////
// Parser actions

// Construct tree root
void
protobufroot(Object file0)
{
    AST.File file = (AST.File)file0;
    file.setName(filename);
    this.ast = astfactory.newRoot("");
    this.ast.setRootFile(file);
    this.ast.getChildSet().add(file);
    // Place the set of primitive type nodes in the root
    this.ast.setPrimitiveTypes(primitives);

    // Create, if necessary, a pseudo-package for root file
    if(file.getFilePackage() == null) {
        AST.Package  p = astfactory.newPackage(null);
        p.setPosition(position());
	file.getChildSet().add(0,p);
	file.setFilePackage(p);
    }
    // If the file's package has no name, then make it
    // have basename of the file (i.e. without any trailing extension).
    // If the file name has no extension, add .proto to the package name
    if(file.getFilePackage().getName() == null) {
	String basename;
	int index = filename.lastIndexOf('.');
	if(index < 0) { // no extension
	    basename = filename + ".proto";
	} else {
	    basename = filename.substring(0,index);
	}
        // Escape the resulting name to convert dots to underscores
        file.getFilePackage().setName(AuxFcns.escapedname(basename));
    }
}

Object
protobuffile(Object decllist0)
{
    AST.File f = astfactory.newFile(null);
    f.getChildSet().addAll((List<AST>)decllist0);
    f.setPosition(position());
    AST.Package p = null;
    // See if the file has a package declaration (must be top level)
    // If so, then capture it and move to be first element in the decllist
    for(AST ast: f.getChildSet()) {
	if(ast.getSort() == AST.Sort.PACKAGE && p == null) {// First found is chosen package
	    p = (AST.Package)ast;
	}
    }
    if(p != null) { // move package to the front of the decl list
        f.getChildSet().remove(p);
	f.getChildSet().add(0,p);
    }
    f.setFilePackage(p);
    return f;
}

Object
packagedecl(Object name0)
{
    String name = (String)name0;
    AST.Package node = astfactory.newPackage(name);
    node.setPosition(position());
    return node;
}

Object
importlist(Object list0, Object import0)
{
    List<AST> list = (List<AST>)list0;
    if(list == null) list = new ArrayList<AST>();
    if(import0 != null) list.add((AST)import0);
    return list;
}

Object
importstmt(Object importname0, Object file0)
{
    AST.File file = (AST.File) file0;
    file.setName((String)importname0);
    return file;
}


Object
importprefix(Object filename0)
{
    // temporarily store the filename
    importfilename = (String)filename0;
    return filename0;
}

boolean
filepush()
{
    // push file stack
    boolean ok = true;
    try {
        ok = lexstate.pushFileStack(importfilename);
    } catch (Exception e) {  ok = false; }
    if(!ok) parseError("import file failure: "+importfilename);
    importfilename = null;
    return ok;
}

boolean
filepop()
{
    return true; //lexstate.popFileStack();
}

Object
decllist(Object list0, Object decl0)
{
    List<AST> list = (List<AST>)list0;
    if(list == null) list = new ArrayList<AST>();
    if(decl0 != null) list.add((AST)decl0);
    return list;
}

Object
option(Object name0, Object value0)
{
    AST.Option node = astfactory.newOption((String)name0,(String)value0);
    node.setPosition(position());
    return node;
}

Object
useroption(Object name0, Object value0)
{
    AST.Option node = astfactory.newOption((String)name0,(String)value0);
    node.setPosition(position());
    node.setUserDefined(true);
    return node;
}

Object
message(Object name0, Object body0)
{
    AST.Message node = astfactory.newMessage((String)name0);
    node.getChildSet().addAll((List<AST>)body0);
    node.setPosition(position());
    return node;
}

Object
extend(Object type0, Object fieldlist0)
{
    AST.Extend node = astfactory.newExtend("$extend",(String)type0);
    node.getChildSet().addAll((List<AST>)fieldlist0);
    node.setPosition(position());
    return node;
}

Object
fieldlist(Object list0, Object decl0)
{
    List<AST.Field> list = (List<AST.Field>)list0;
    if(list == null) list = new ArrayList<AST.Field>();
    if(decl0 != null) list.add((AST.Field)decl0);
    return list;
}

Object
enumtype(Object name0, Object enumlist0)
{
    AST.Enum node = astfactory.newEnum((String)name0);
    node.getChildSet().addAll((List<AST>)enumlist0);
    node.setPosition(position());
    return node;
}


Object
enumlist(Object list0, Object decl0)
{
    List<AST> list = (List<AST>)list0;
    if(list == null) list = new ArrayList<AST>();
    if(decl0 != null) list.add((AST)decl0);
    return list;
}

Object
enumfield(Object name0, Object intvalue0, Object options0)
{
    int value = 0;
    try {
	value = Integer.parseInt((String)intvalue0);
    } catch (NumberFormatException nfe) {
	return parseError("Illegal enum field value: "+intvalue0);
    }
    AST.EnumField node = astfactory.newEnumField((String)name0,value);
    if(options0 == null) options0 = new ArrayList<AST.Option>();
    node.getChildSet().addAll((List<AST>)options0);
    node.setPosition(position());
    return node;
}

Object
enumoptionlist(Object list0, Object decl0)
{
    List<AST.Option> list = (List<AST.Option>)list0;
    if(list == null) list = new ArrayList<AST.Option>();
    if(decl0 != null) list.add((AST.Option)decl0);
    return list;
}

Object
service(Object name0, Object caselist0)
{
    AST.Service node = astfactory.newService((String)name0);
    node.getChildSet().addAll((List<AST>)caselist0);
    node.setPosition(position());
    return node;
}

Object
servicecaselist(Object list0, Object decl0)
{
    List<AST> list = (List<AST>)list0;
    if(list == null) list = new ArrayList<AST>();
    if(decl0 != null) list.add((AST)decl0);
    return list;
}

Object
rpc(Object name0, Object type0, Object returntype0, Object optionlist)
{
    AST.Rpc node = astfactory.newRpc((String)name0,(String)type0,(String)returntype0);
    node.setChildSet((List<AST>)optionlist);
    node.setPosition(position());
    return node;
}

Object
optionstmtlist(Object list0, Object optionstmt0)
{
    List<AST.Option> list = (List<AST.Option>)list0;
    if(list == null) list = new ArrayList<AST.Option>();
    if(optionstmt0 != null) list.add((AST.Option)optionstmt0);
    return list;
}

Object
messageelementlist(Object list0, Object decl0)
{
    List<AST> list = (List<AST>)list0;
    if(list == null) list = new ArrayList<AST>();
    if(decl0 != null) list.add((AST)decl0);
    return list;
}

Object
field(Object cardinality0, Object type0, Object name0, Object id0, Object options0)
{
    AST.Cardinality cardinality = null;
    int id;

    if(options0 == null) options0 = new ArrayList<AST.Option>();

    for(AST.Cardinality card: AST.Cardinality.values()) {
        if(card.getName().equalsIgnoreCase((String)cardinality0))
	    cardinality = card;
    }
    if(cardinality == null)
  	return parseError("Illegal field cardinality: "+cardinality0);

    try {
	id = Integer.parseInt((String)id0);
    } catch (NumberFormatException nfe) {
  	return parseError("Illegal message field id: "+id0);
    }
    AST.Field node = astfactory.newField((String)name0,
			cardinality,
			(String)type0,
			id);
    node.getChildSet().addAll((List<AST>)options0);
    node.setPosition(position());
    return node;
}

Object
fieldoptionlist(Object list0, Object decl0)
{
    List<AST.Option> list = (List<AST.Option>)list0;
    if(list == null) list = new ArrayList<AST.Option>();
    if(decl0 != null) list.add((AST.Option)decl0);
    return list;
}

Object
group(Object cardinality0, Object name0, Object id0, Object msgbody)
{
    AST.Cardinality cardinality = null;
    int id;

    for(AST.Cardinality card: AST.Cardinality.values()) {
        if(card.getName().equalsIgnoreCase((String)cardinality0))
	    cardinality = card;
    }
    if(cardinality == null)
  	return parseError("Illegal field cardinality: "+cardinality0);

    try {
	id = Integer.parseInt((String)id0);
    } catch (NumberFormatException nfe) {
  	return parseError("Illegal message field id: "+id0);
    }
    AST.Group node = astfactory.newGroup((String)name0,cardinality,id);
    node.getChildSet().addAll((List<AST>)msgbody);
    node.setPosition(position());
    return node;
}

Object
extensions(Object list0)
{
    // everynode has a name of some sort
    AST.Extensions node = astfactory.newExtensions("$extensions");
    node.setRanges((List<AST.Range>)list0);
    return node;
}

Object
extensionlist(Object list0, Object decl0)
{
    List<AST.Range> list = (List<AST.Range>)list0;
    if(list == null) list = new ArrayList<AST.Range>();
    if(decl0 != null) list.add((AST.Range)decl0);
    return list;
}

Object
extensionrange(Object start0, Object stop0)
{
    int start = 0;
    int stop = 0;
    try {
	start = Integer.parseInt((String)start0);
    } catch (NumberFormatException nfe) {
	return parseError("Illegal extendsion range start value");
    }
    if(stop0 == null)
	stop = AST.MAXEXTENSION;
    else try {
	stop = Integer.parseInt((String)stop0);
    } catch (NumberFormatException nfe) {
	return parseError("Illegal extension range stop value");
    }
    if(start < 0 || start < 0
       || start > AST.MAXFIELDID || stop > AST.MAXFIELDID) {
	return parseError(String.format("Illegal Extension range: %d..%d",
			  start,stop));
    }
    // Make sure that the range does not overlap the google
    // reserved range(s);
    if((start >= AST.MINGOOGLERANGE && start <= AST.MAXGOOGLERANGE)
       || (stop>= AST.MINGOOGLERANGE && stop <= AST.MAXGOOGLERANGE)) {
	return parseError("Extension range overlaps google reserved range");
    }
    AST.Range range = new AST.Range(start,stop);
    return range;
}

boolean
illegalname(Object s)
{
    if(((String)s).indexOf('.') >= 0) {
        parseError("Expected  NAME, found path: "+s.toString());
	return true;
    }
    return false;
}

AST.Position
position()
{
    return lexstate.pos.clone();
}

public void startsymbol(ProtobufLexer.IDstate symbolkind)
{
    lexstate.idstate = symbolkind;
}

public void endsymbol()
{
    lexstate.idstate = ProtobufLexer.IDstate.NOKEYWORDID;
}

void notimplemented(String s)
{
    parseError(s+" not implemented.");
    return;
}

} // class ProtobufActions
