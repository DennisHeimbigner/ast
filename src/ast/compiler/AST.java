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

// An instance of AST serves as the tree root

abstract public class AST
{
    // Verify that pass1 of semantics is catching all created AST nodes
    static List<AST> nodeset = new ArrayList<AST>();
    boolean visited = false; // for debugging


    static final int MAXEXTENSION = 536870911 ; //0x1FFFFFFF

    static final int MAXFIELDID = MAXEXTENSION;

    static final String DEFAULTNAME = ".DEFAULT";

    static final int MINGOOGLERANGE = 19000;
    static final int MAXGOOGLERANGE = 19999;

    public enum Cardinality {
	REQUIRED("required"), OPTIONAL("optional"), REPEATED("repeated");
	private final String name;
        Cardinality(String name) {this.name = name;}
	public String getName()   { return name; }
    }	

    // Define the kinds of AST objects to avoid having to do instanceof.
    public enum Sort {
	PACKAGE("package"), ENUM("enum"), ENUMFIELD("enumfield"),
	EXTEND("extend"), EXTENSIONRANGE("extensionrange"),
	FIELD("field"), MESSAGE("message"),
	OPTION("option"), RPC("rpc"), SERVICE("service"),
	PRIMITIVETYPE("primitivetype"), FILE("file"), ROOT("root");

	private final String name;
        Sort(String name) {this.name = name;}
	public String getName()   { return name; }
    }

    // Define the kinds or primitive types
    public enum PrimitiveSort {
	DOUBLE("double"), FLOAT("float"),
	INT32("int32"), INT64("int64"),
	UINT32("uint32"), UINT64("uint64"),
	SINT32("sint32"), SINT64("sint64"),
	FIXED32("fixed32"), FIXED64("fixed64"),
	SFIXED32("sfixed32"), SFIXED64("sfixed64"),
	STRING("string"), BYTES("bytes"),
	BOOL("bool");
	
	private final String name;
        PrimitiveSort(String name) {this.name = name;}
	public String getName()   { return name; }
    }

    // Capture location in file if possible
    static public class Position
    {
	int lineno = 0;
	int charno = 0;

        public Position() {}
        public Position(int line, int charno) {this.lineno = line; this.charno = charno;}

        public int getLine() {return lineno;}
        public int getChar() {return charno;}

	public boolean equals(Object o)
	{
	    if(o instanceof Position) {
		Position p = (Position)o;
		return (p.getLine() == lineno && p.getChar() == charno);
	    }
	    return false;
	}

	public String toString()
        {
	    return String.format("%d::%d",lineno,charno);
	}
    }

    //////////////////////////////////////////////////
    // Instance variables

    AST.Position position = null;
    AST.Sort sort = null;
    AST.Root root = null; // top-level root
    AST.File srcfile = null; // immediately containing src file node
    AST.Package packageroot = null; // immediately containing package
    AST parent = null;
    List<AST> children = null;
    String name = null;
    String qualifiedname = null;
    Object annotation = null;

    AST(AST.Sort sort)
    {
	this.sort = sort;
	nodeset.add(this);
    }

    public List<AST> getChildren() {return this.children;}
    public void setChildren(List<AST> children) {this.children = children;}
    public void addChildren(List<AST> children)
    {
        if(this.children == null)
            this.children = children;
        else
           this.children.addAll(children);
    }

    public void addChildren(AST children)
    {
        if(this.children == null)
	    this.children = new ArrayList<AST>();
        this.children.add(children);
    }

    public AST.Position getPosition() {return position;}
    public void setPosition(AST.Position position) {this.position = position;}
    public AST.Sort getSort() {return this.sort;}
    public void setSort(AST.Sort astclass) {this.sort = astclass;}
    public AST.Root getRoot() {return root;}
    public void setRoot(AST.Root root) {this.root = root;}
    public AST.Package getPackage() {return packageroot;}
    public void setPackage(AST.Package packageroot) {this.packageroot = packageroot;}
    public AST.File getSrcFile() {return srcfile;}
    public void setSrcFile(AST.File srcfile) {this.srcfile = srcfile;}
    public AST getParent() {return parent;}
    public void setParent(AST parent) {this.parent = parent;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getQualifiedName() {return qualifiedname;}
    public void setQualifiedName(String qualifiedname) {this.qualifiedname = qualifiedname;}
    public Object getAnnotation() {return annotation;}
    public void setAnnotation(Object annotation) {this.annotation = annotation;}


// Convenience grouping class
static public class Type extends AST 
{
    Type(AST.Sort sort) {super(sort);}
}

// An instance of this is the root of the AST tree
static public class Root extends AST 
{
    List<AST> allnodes = null; // pre-order set of all nodes in the AST tree
    List<AST.Package> allpackages = null;
    List<AST.File> allfiles = null;
    AST.File rootfile = null;

    Root(String name)
    {
	super(AST.Sort.ROOT);
	setName("");
	setQualifiedName("");
	setAllNodes(new ArrayList<AST>());
	setAllPackages(new ArrayList<AST.Package>());
	setAllFiles(new ArrayList<AST.File>());
    }

    public List<AST> getAllNodes() {return this.allnodes;}
    public void setAllNodes(List<AST> allnodes) {this.allnodes = allnodes;}

    public List<AST.File> getAllFiles() {return this.allfiles;}
    public void setAllFiles(List<AST.File> allfiles) {this.allfiles = allfiles;}

    public List<AST.Package> getAllPackages() {return this.allpackages;}
    public void setAllPackages(List<AST.Package> allpackages) {this.allpackages = allpackages;}

    AST.File getRootFile() {return this.rootfile;}
    void setRootFile(AST.File f) {this.rootfile = f;}
}

static public class File extends AST 
{
    AST.Package filepackage = null;
    List<AST.File> imports = null;
    List<AST> decls = null;

    File(String name)
    {
	super(AST.Sort.FILE);
	setName(name);
    }

    AST.Package getFilePackage() {return this.filepackage;}
    void setFilePackage(AST.Package astpackage) {this.filepackage = astpackage;}
    List<AST.File> getImports() {return this.imports;}
    void setImports(List<AST.File> imports) {this.imports = imports;}
}

static public class Package extends AST
{
    List<AST.Message> messages = null;
    List<AST.Extend> extenders = null;
    List<AST.Enum> enums = null;
    List<AST.Option> options = null;
    List<AST.Service> services = null;

    public Package(String name)
    {
	super(AST.Sort.PACKAGE);
	setName(name);
    }

    public List<AST.Message> getMessages() {return this.messages;}
    public void setMessages(List<AST.Message> sessages) {this.messages = messages;}
    public List<AST.Extend> getExtenders() {return this.extenders;}
    public void setExtenders(List<AST.Extend> extenders) {this.extenders = extenders;}
    public List<AST.Enum> getEnums() {return this.enums;}
    public void setEnums(List<AST.Enum> enums) {this.enums = enums;}
    public List<AST.Option> getOptions() {return this.options;}
    public void setOptions(List<AST.Option> options) {this.options = options;}
    public List<AST.Service> getServices() {return this.services;}
    public void setServices(List<AST.Service> services) {this.services = services;}

}

static public class Enum extends AST
{
    List<AST.EnumField> enumfields = null;

    public Enum(String name)
    {
	super(AST.Sort.ENUM);
	setName(name);
    }

    public List<AST.EnumField> getEnumFields() {return this.enumfields;}
    public void setEnumFields(List<AST.EnumField> enumfields) {this.enumfields = enumfields;}
}

static public class EnumField extends AST
{
    int value;

    public EnumField(String name, int value)
    {
	super(AST.Sort.ENUMFIELD);
	setName(name);
        setValue(value);
    }

    public int getValue() {return this.value;}
    public void setValue(int value) {this.value = value;}
}

static public class Extend extends AST
{
    AST.Message message = null;
    List<AST.Field> fields = null;

    public Extend(String msgname)
    {
	super(AST.Sort.EXTEND);
	setAnnotation(msgname); // temporary storage place
    }

    public AST.Message getMessage() {return this.message;}
    public void setMessage(AST.Message message) {this.message = message;}
    public List<AST.Field> getFields() {return this.fields;}
    public void setFields(List<AST.Field> fields) {this.fields = fields;}

}

static public class ExtensionRange extends AST
{
    int start;
    int stop;

    public ExtensionRange(int start, int stop)
    {
	super(AST.Sort.EXTENSIONRANGE);
	setStart(start);
	setStop(stop);
    }

    public int getStart() {return this.start;}
    public void setStart(int start) {this.start = start;}
    public int getStop() {return this.stop;}
    public void setStop(int stop) {this.stop = stop;}

}

static public class Field extends AST
{
    AST.Cardinality cardinality = null;
    AST.Type fieldtype = null;
    int id;
    List<AST.Option> options = null;

    public Field(String name, AST.Cardinality cardinality, String fieldtype, int id)
    {
	super(AST.Sort.FIELD);
	setName(name);
	setCardinality(cardinality);
	setAnnotation(fieldtype); // temporary storage
	setId(id);
    }

    public AST.Cardinality getCardinality() {return this.cardinality;}
    public void setCardinality(AST.Cardinality cardinality) {this.cardinality = cardinality;}
    public AST.Type getType() {return this.fieldtype;}
    public void setType(AST.Type fieldtype) {this.fieldtype = fieldtype;}
    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}
    public List<AST.Option> getOptions() {return this.options;}
    public void setOptions(List<AST.Option> options) {this.options = options;}
}

static public class Message extends AST.Type
{
    // Filled in during Semantic processing
    List<AST.Field> fields = null;
    List<AST.Enum> enums = null;
    List<AST.Message> messages = null;
    List<AST.Extend> extenders = null;
    List<AST.ExtensionRange> extensionranges = null;
    List<AST.Option> options = null;

    public Message(String name)
    {
	super(AST.Sort.MESSAGE);
	setName(name);
    }

    public List<AST.Field> getFields() {return this.fields;}
    public void setFields(List<AST.Field> fields) {this.fields = fields;}
    public List<AST.Enum> getEnums() {return this.enums;}
    public void setEnums(List<AST.Enum> enums) {this.enums = enums;}
    public List<AST.Message> getMessages() {return this.messages;}
    public void setMessages(List<AST.Message> messages) {this.messages = messages;}
    public List<AST.Extend> getExtenders() {return this.extenders;}
    public void setExtenders(List<AST.Extend> extenders) {this.extenders = extenders;}
    public List<AST.ExtensionRange> getExtensionRanges() {return this.extensionranges;}
    public void setExtensionRanges(List<AST.ExtensionRange> extensionRanges) {this.extensionranges = extensionranges;}
    public List<AST.Option> getOptions() {return this.options;}
    public void setOptions(List<AST.Option> options) {this.options = options;}
}

static public class Option extends AST
{
    String value;
    boolean userdefined = false;

    public Option(String name, String value)
    {
	super(AST.Sort.OPTION);
	setName(name);
	setValue(value);
    }

    public String getValue() {return this.value;}
    public void setValue(String value) {this.value = value;}
    public boolean getUserDefined() {return this.userdefined;}
    public void setUserDefined(boolean userdefined) {this.userdefined = userdefined;}

}

static public class Rpc extends AST
{
    AST.Type argtype = null;
    AST.Type returntype = null;

    public Rpc(String name, String argtype, String returntype)
    {
	super(AST.Sort.RPC);
	setName(name);
	// Use annotation to temporarily store the type names
	String[] names = new String[2];
	names[0] = argtype;
	names[1] = returntype;
	setAnnotation(names);
    }

    public AST.Type getArgType() {return this.argtype;}
    public void setArgType(AST.Type argtype) {this.argtype = argtype;}
    public AST.Type getReturnType() {return this.returntype;}
    public void setReturnType(AST.Type returntype) {this.returntype = returntype;}
}

static public class Service extends AST
{
    // Filled in during semantic processing
    List<AST.Option> options = null;
    List<AST.Rpc> rpcs = null;

    public Service(String name)
    {
	super(AST.Sort.SERVICE);
	setName(name);
    }

    public List<AST.Option> getOptions() {return this.options;}
    public void setOptions(List<AST.Option> options) {this.options = options;}
    public List<AST.Rpc> getRpcs() {return this.rpcs;}
    public void setRpcs(List<AST.Rpc> rpcs) {this.rpcs = rpcs;}
}

static public class PrimitiveType extends AST.Type
{
    AST.PrimitiveSort PrimitiveSort = null;

    public PrimitiveType(AST.PrimitiveSort PrimitiveSort)
    {
	super(AST.Sort.PRIMITIVETYPE);
	this.PrimitiveSort = PrimitiveSort;
        setName(PrimitiveSort.getName());
    }

    public AST.PrimitiveSort getPrimitiveSort() {return this.PrimitiveSort;}
    public void setPrimitiveSort(AST.PrimitiveSort PrimitiveSort) {this.PrimitiveSort = PrimitiveSort;}
}

}// class AST
