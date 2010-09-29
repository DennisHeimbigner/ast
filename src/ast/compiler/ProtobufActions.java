/* Copyright 2009, UCAR/Unidata and OPeNDAP, Inc.
   See the COPYRIGHT file for more information. */
/****************************************************/

package unidata.protobuf.ast.compiler;

import java.io.*;
import java.util.*;

import static unidata.protobuf.ast.compiler.ProtobufParser.*;

public abstract class ProtobufActions
{

//////////////////////////////////////////////////
// Class State

//////////////////////////////////////////////////
// Instance State

    ProtobufLexer lexstate = null;
    ProtobufActions state = null; /* Slight kludge */
    ASTFactory astfactory = null;
    ProtobufParser.Location currentlocation = null;
    AST.Root ast = null; // root node of the AST
    String filename = null;

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
}

//////////////////////////////////////////////////
// Get/set

public void setLocation(ProtobufParser.Location loc) {currentlocation = loc;}
public ProtobufParser.Location getLocation() {return currentlocation;}

public AST.Root getAST() {return ast;}

//////////////////////////////////////////////////
// Access into the DapParser for otherwise inaccessible fiels

abstract public boolean parse(String filename,Reader stream) throws IOException;
abstract public Object parseError(String msg);
abstract int getDebugLevel();
abstract void setDebugLevel(int level);

//////////////////////////////////////////////////
// Parser actions

void notimplemented(String s)
{
    parseError(s+" not implemented.");
    return;
}

// Construct tree root
void
protobufroot(Object root0)
{
    // Store in state
    AST.Root root = (AST.Root)root0
    f.setName(filename);
    this.ast = astfactory.newRoot(filename);
    this.ast.setRootFile(f);
}


Object
protobuffile(Object package0, Object imports0, Object decllist0)
{
    AST.File f = astfactory.newFile(null); // we don't know the file name at this point
    f.setFilePackage((AST.Package)package0);
    // concat for now; divide later
    f.addChildren((List<AST>)imports0);
    f.addChildren((List<AST>)decllist0);
    f.setPosition(position());
    return f;
}

Object
packagedecl(Object name0)
{
    String name = (String)name0;
    if(name == null) name = "";
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
useroption(Object option0)
{
    AST.Option option = (AST.Option)option0;
    option.setUserDefined(true);
    return option;
}

Object
message(Object name0, Object body0)
{
    AST.Message node = astfactory.newMessage((String)name0);
    node.addChildren((List<AST>)body0);
    node.setPosition(position());
    return node;
}

Object
extend(Object type0, Object fieldlist0)
{
    AST.Extend node = astfactory.newExtend((String)type0);
    node.addChildren((List<AST>)fieldlist0);
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
    node.addChildren((List<AST>)enumlist0);
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
enumfield(Object name0, Object intvalue0)
{
    int value = 0;
    try {
	value = Integer.parseInt((String)intvalue0);
    } catch (NumberFormatException nfe) {
	return parseError("Illegal enum field value: "+intvalue0);
    }
    AST.EnumField node = astfactory.newEnumField((String)name0,value);
    node.setPosition(position());
    return node;
}

Object
service(Object name0, Object caselist0)
{
    AST.Service node = astfactory.newService((String)name0);
    node.addChildren((List<AST>)caselist0);
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
rpc(Object name0, Object type0, Object returntype0)
{
    AST.Rpc node = astfactory.newRpc((String)name0,(String)type0,(String)returntype0);
    node.setPosition(position());
    return node;
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
    node.addChildren((List<AST>)options0);
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
extensionlist(Object list0, Object decl0)
{
    List<AST.ExtensionRange> list = (List<AST.ExtensionRange>)list0;
    if(list == null) list = new ArrayList<AST.ExtensionRange>();
    if(decl0 != null) list.add((AST.ExtensionRange)decl0);
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
       || start >= AST.MAXFIELDID || stop >= AST.MAXFIELDID) {
	return parseError(String.format("Illegal Extension range: %d..%d",
			  start,stop));
    }
    // Make sure that the range does not overlap the google
    // reserved range(s);
    if((start >= AST.MINGOOGLERANGE && start <= AST.MAXGOOGLERANGE)
       || (stop>= AST.MINGOOGLERANGE && stop <= AST.MAXGOOGLERANGE)) {
	return parseError("Extension range overlaps google reserved range");
    }
    AST.ExtensionRange node = astfactory.newExtensionRange(start,stop);
    node.setPosition(position());
    return node;
}

Object
identifier(Object s)
{
    if(((String)s).indexOf('.') >= 0)
        return parseError("Expected IDENTIFIER, found path: "+s);
    return s;
}

AST.Position
position()
{
    ProtobufParser.Location loc = this.getLocation();
    return (loc == null ? null : loc.begin);
}


} // class ProtobufActions
