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

// An instance of AST serves as the tree root

abstract public class AST
{
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
	EXTEND("extend"), EXTENSIONS("extensions"),
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
    static public class Location
    {
        public Location (Position loc) {}
        public Location (Position begin, Position end) {}
        public String toString () { return "<location>";}
    }

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
    // Assign indices to all nodes
    static int uid = 0;
    static int primitiveuid = 0; // separate numbering for primitives
    int index;

    //////////////////////////////////////////////////
    // Instance variables

    Position position = null;
    Sort sort = null;
    Root root = null; // top-level root
    File srcfile = null; // immediately containing src file node
    Package packageroot = null; // immediately containing package
    AST parent = null;
    String name = null;
    String qualifiedname = null;
    Object annotation = null;
    List<AST> childset= null; // immediate children
    List<AST> nodeset = null; // All nodes under this node;
			      // null except for root and packages

    int refcount = 0;

    AST(Sort sort)
    {
	this.sort = sort;
	if(nodeset == null) nodeset = new ArrayList<AST>();
        if(childset == null) childset = new ArrayList<AST>();
        setuid();
    }

    protected void setuid() {index = ++uid;}
    public int getId() {return index;}

    public int setRefCount(int n)
	{int old = refcount ; refcount = n; return old;}
    public void addRefCount(int n) {refcount += n;}

    public List<AST> getChildSet() {return this.childset;}
    public void setChildSet(List<AST> children) {this.childset = children;}

    public List<AST> getNodeSet() {return this.nodeset;}
    public void setNodeSet(List<AST> nodeset) {this.nodeset = nodeset;}

    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public Sort getSort() {return this.sort;}
    public void setSort(Sort astclass) {this.sort = astclass;}
    public Root getRoot() {return root;}
    public void setRoot(Root root) {this.root = root;}
    public Package getPackage() {return packageroot;}
    public void setPackage(Package packageroot) {this.packageroot = packageroot;}
    public File getSrcFile() {return srcfile;}
    public void setSrcFile(File srcfile) {this.srcfile = srcfile;}
    public AST getParent() {return parent;}
    public void setParent(AST parent) {this.parent = parent;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getQualifiedName() {return qualifiedname;}
    public void setQualifiedName(String qualifiedname) {this.qualifiedname = qualifiedname;}
    public Object getAnnotation() {return annotation;}
    public void setAnnotation(Object annotation) {this.annotation = annotation;}

    public String toString() {
        if(getQualifiedName() != null) return getQualifiedName();
        if(getName() != null) return getName();
        return super.toString();
    }

// Convenience grouping class
static public class Type extends AST 
{
    Type(Sort sort) {super(sort);}
}

// An instance of this is the root of the AST tree
static public class Root extends AST 
{
    List<Package> packageset = null;
    List<File> fileset = null;
    List<PrimitiveType> primitivetypes = null;
    File rootfile = null;

    Root(String name)
    {
	super(Sort.ROOT);
	setName("");
        setPackage(null);
        setSrcFile(null);
	setNodeSet(new ArrayList<AST>());
	setPackageSet(new ArrayList<Package>());
	setFileSet(new ArrayList<File>());
    }

    public List<File> getFileSet() {return this.fileset;}
    public void setFileSet(List<File> fileset) {this.fileset = fileset;}

    public List<Package> getPackageSet() {return this.packageset;}
    public void setPackageSet(List<Package> packageset) {this.packageset = packageset;}

    public List<PrimitiveType> getPrimitiveTypes() {return this.primitivetypes;}
    public void setPrimitiveTypes(List<PrimitiveType> primitivetypes) {this.primitivetypes = primitivetypes;}

    File getRootFile() {return this.rootfile;}
    void setRootFile(File f) {this.rootfile = f;}
}

static public class File extends AST 
{
    Package filepackage = null;
    List<File> imports = null;
    List<AST> decls = null;

    File(String name)
    {
	super(Sort.FILE);
	setName(name);
    }

    Package getFilePackage() {return this.filepackage;}
    void setFilePackage(Package astpackage) {this.filepackage = astpackage;}
    List<File> getImports() {return this.imports;}
    void setImports(List<File> imports) {this.imports = imports;}
}

static public class Package extends AST
{
    File packagefile = null; // inverse of AST.File.filepackage

    List<Message> messages = null;
    List<Extend> extenders = null;
    List<Enum> enums = null;
    List<Option> options = null;
    List<Service> services = null;

    public Package(String name)
    {
	super(Sort.PACKAGE);
	setName(name);
    }

    File getPackageFile() {return this.packagefile;}
    void setPackageFile(File astfile) {this.packagefile = astfile;}

    public List<Message> getMessages() {return this.messages;}
    public void setMessages(List<Message> sessages) {this.messages = messages;}
    public List<Extend> getExtenders() {return this.extenders;}
    public void setExtenders(List<Extend> extenders) {this.extenders = extenders;}
    public List<Enum> getEnums() {return this.enums;}
    public void setEnums(List<Enum> enums) {this.enums = enums;}
    public List<Option> getOptions() {return this.options;}
    public void setOptions(List<Option> options) {this.options = options;}
    public List<Service> getServices() {return this.services;}
    public void setServices(List<Service> services) {this.services = services;}

}

static public class Enum extends AST.Type
{
    List<EnumField> enumfields = null;

    public Enum(String name)
    {
	super(Sort.ENUM);
	setName(name);
    }

    public List<EnumField> getEnumFields() {return this.enumfields;}
    public void setEnumFields(List<EnumField> enumfields) {this.enumfields = enumfields;}
}

static public class EnumField extends AST
{
    int value;

    public EnumField(String name, int value)
    {
	super(Sort.ENUMFIELD);
	setName(name);
        setValue(value);
    }

    public int getValue() {return this.value;}
    public void setValue(int value) {this.value = value;}
}

static public class Extend extends AST
{
    Message message = null;
    List<Field> fields = null;

    public Extend(String msgname)
    {
	super(Sort.EXTEND);
	setAnnotation(msgname); // temporary storage place
    }

    public Message getMessage() {return this.message;}
    public void setMessage(Message message) {this.message = message;}
    public List<Field> getFields() {return this.fields;}
    public void setFields(List<Field> fields) {this.fields = fields;}

}


// Helper class for storing stop-start pairs
static public class Range
{
    int stop = 0;
    int start = 0;
    public Range(int start, int stop) {this.start=start; this.stop=stop;}
}

static public class Extensions extends AST
{
    List<Range> ranges = null;

    public Extensions()
    {
	super(Sort.EXTENSIONS);
    }

    public List<AST.Range> getRanges() {return this.ranges;}
    public void setRanges(List<AST.Range> ranges) {this.ranges = ranges;}
}

static public class Field extends AST
{
    Cardinality cardinality = null;
    Type fieldtype = null;
    int id;
    List<Option> options = null;

    public Field(String name, Cardinality cardinality, String fieldtype, int id)
    {
	super(Sort.FIELD);
	setName(name);
	setCardinality(cardinality);
	setAnnotation(fieldtype); // temporary storage
	setId(id);
    }

    public Cardinality getCardinality() {return this.cardinality;}
    public void setCardinality(Cardinality cardinality) {this.cardinality = cardinality;}
    public Type getType() {return this.fieldtype;}
    public void setType(Type fieldtype) {this.fieldtype = fieldtype;}
    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}
    public List<Option> getOptions() {return this.options;}
    public void setOptions(List<Option> options) {this.options = options;}
}

static public class Message extends Type
{
    // Filled in during Semantic processing
    List<Field> fields = null;
    List<Enum> enums = null;
    List<Message> messages = null;
    List<Extend> extenders = null;
    List<Extensions> extensions = null;
    List<Option> options = null;

    public Message(String name)
    {
	super(Sort.MESSAGE);
	setName(name);
    }

    public List<Field> getFields() {return this.fields;}
    public void setFields(List<Field> fields) {this.fields = fields;}
    public List<Enum> getEnums() {return this.enums;}
    public void setEnums(List<Enum> enums) {this.enums = enums;}
    public List<Message> getMessages() {return this.messages;}
    public void setMessages(List<Message> messages) {this.messages = messages;}
    public List<Extend> getExtenders() {return this.extenders;}
    public void setExtenders(List<Extend> extenders) {this.extenders = extenders;}
    public List<Extensions> getExtensions() {return this.extensions;}
    public void setExtensions(List<Extensions> extension) {this.extensions = extensions;}
    public List<Option> getOptions() {return this.options;}
    public void setOptions(List<Option> options) {this.options = options;}
}

static public class Option extends AST
{
    String value;
    boolean userdefined = false;

    public Option(String name, String value)
    {
	super(Sort.OPTION);
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
    Type argtype = null;
    Type returntype = null;

    public Rpc(String name, String argtype, String returntype)
    {
	super(Sort.RPC);
	setName(name);
	// Use annotation to temporarily store the type names
	String[] names = new String[2];
	names[0] = argtype;
	names[1] = returntype;
	setAnnotation(names);
    }

    public Type getArgType() {return this.argtype;}
    public void setArgType(Type argtype) {this.argtype = argtype;}
    public Type getReturnType() {return this.returntype;}
    public void setReturnType(Type returntype) {this.returntype = returntype;}
}

static public class Service extends AST
{
    // Filled in during semantic processing
    List<Option> options = null;
    List<Rpc> rpcs = null;

    public Service(String name)
    {
	super(Sort.SERVICE);
	setName(name);
    }

    public List<Option> getOptions() {return this.options;}
    public void setOptions(List<Option> options) {this.options = options;}
    public List<Rpc> getRpcs() {return this.rpcs;}
    public void setRpcs(List<Rpc> rpcs) {this.rpcs = rpcs;}
}

static public class PrimitiveType extends Type
{
    PrimitiveSort PrimitiveSort = null;

    public PrimitiveType(PrimitiveSort PrimitiveSort)
    {
	super(Sort.PRIMITIVETYPE);
	this.PrimitiveSort = PrimitiveSort;
        setName(PrimitiveSort.getName());
    }

    protected void setuid() {index = --primitiveuid;}

    public PrimitiveSort getPrimitiveSort() {return this.PrimitiveSort;}
    public void setPrimitiveSort(PrimitiveSort PrimitiveSort) {this.PrimitiveSort = PrimitiveSort;}
}

}// class AST
