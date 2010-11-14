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

public interface AST
{
    static final int MAXEXTENSION = 536870911 ; //0x1FFFFFFF

    static final int MAXFIELDID = MAXEXTENSION;

    static final String DEFAULTNAME = "$DEFAULT";

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
	PACKAGE("package"), ENUM("enum"), ENUMVALUE("enumvalue"),
	EXTEND("extend"), EXTENSIONS("extensions"),
	FIELD("field"), MESSAGE("message"), GROUP("group"),
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

    static public class Position
    {
	int lineno = 0;
	int charno = 0;
	String filename = null;

        public Position() {}
        public Position(int line, int charno, String filename)
	{setLine(line); setChar(charno); setFile(filename);}

        public int getLine() {return lineno;}
        public int getChar() {return charno;}
        public String getFile() {return filename;}

        public void setLine(int lineno) {this.lineno = lineno;}
        public void setChar(int charno) {this.charno = charno;}
        public void setFile(String file) {this.filename = file;}

        public void moveLine(int delta)
	    {this.lineno += delta; if(this.lineno < 0) this.lineno = 0;}
        public void moveChar(int delta)
	    {this.charno += delta; if(this.charno < 0) this.charno = 0;}

	public boolean equals(Object o)
	{
	    if(o instanceof Position) {
		Position p = (Position)o;
		return (p.getLine() == lineno && p.getChar() == charno
		        && p.getFile().equals(filename));
	    }
	    return false;
	}

	public String toString()
        {
	    return String.format("%s:%d.%d",filename,lineno,charno);
	}

	public Position clone()
	{
	    return new Position(lineno,charno,filename);
	}

    }

    // Helper class for storing stop-start pairs
    static public class Range
    {
        int stop = 0;
        int start = 0;
        public Range(int start, int stop) {this.start=start; this.stop=stop;}
    }

    public void setuid();
    public int getId();

    public int setRefCount(int n);
    public void addRefCount(int n);

    public List<AST> getChildSet();
    public void setChildSet(List<AST> children);

    public List<AST> getNodeSet();
    public void setNodeSet(List<AST> nodeset);

    public Position getPosition();
    public void setPosition(Position position);
    public Sort getSort();
    public void setSort(Sort astclass);

    public Root getRoot();
    public void setRoot(Root root);

    public Package getPackage();
    public void setPackage(Package packageroot);

    public File getSrcFile();
    public void setSrcFile(File srcfile);

    public AST getParent();
    public void setParent(AST parent);
    public String getName();
    public void setName(String name);
    public String getQualifiedName();
    public void setQualifiedName(String qualifiedname);

    public Object getAnnotation();
    public void setAnnotation(Object annotation);

    public List<Option> getOptions();
    public void setOptions(List<Option> options);

    public String optionLookup(String key);
    public void setOptionMap(String key, String value);

    public String toString();

public interface Type extends AST {}

public interface Root extends AST 
{
    public List<File> getFileSet();
    public void setFileSet(List<File> fileset);

    public List<Package> getPackageSet();
    public void setPackageSet(List<Package> packageset);

    public List<PrimitiveType> getPrimitiveTypes();
    public void setPrimitiveTypes(List<PrimitiveType> primitivetypes);

    File getRootFile();
    void setRootFile(File f);
}

public interface File extends AST 
{
    Package getFilePackage();
    void setFilePackage(Package astpackage);
    List<File> getImports();
    void setImports(List<File> imports);
}

public interface Package extends AST
{
    File getPackageFile();
    void setPackageFile(File astfile);

    public List<Message> getMessages();
    public void setMessages(List<Message> sessages);
    public List<Extend> getExtenders();
    public void setExtenders(List<Extend> extenders);

    public List<Enum> getEnums();
    public void setEnums(List<Enum> enums);
    public List<Service> getServices();
    public void setServices(List<Service> services);
}

public interface Enum extends AST.Type
{
    public List<EnumValue> getEnumValues();
    public void setEnumValues(List<EnumValue> enumvalues);
}

public interface EnumValue extends AST
{
    public int getValue();
    public void setValue(int value);
}

public interface Extend extends AST
{
    public Message getMessage();
    public void setMessage(Message message);
    public List<Field> getFields();
    public void setFields(List<Field> fields);
    public List<Group> getGroups();
    public void setGroups(List<Group> groups);
}

public interface Extensions extends AST
{
    public List<AST.Range> getRanges();
    public void setRanges(List<AST.Range> ranges);
}

public interface Field extends AST
{
    public Cardinality getCardinality();
    public void setCardinality(Cardinality cardinality);
    public Type getType();
    public void setType(Type fieldtype);
    public int getId();
    public void setId(int id);
}

public interface Group extends AST.Field {}

public interface Message extends Type
{
    public List<Field> getFields();
    public void setFields(List<Field> fields);
    public List<Enum> getEnums();
    public void setEnums(List<Enum> enums);
    public List<Message> getMessages();
    public void setMessages(List<Message> messages);
    public List<Extend> getExtenders();
    public void setExtenders(List<Extend> extenders);
    public List<Extensions> getExtensions();
    public void setExtensions(List<Extensions> extension);
    public List<Group> getGroups();
    public void setGroups(List<Group> group);
}

public interface Option extends AST
{
    public String getValue();
    public void setValue(String value);
    public boolean getUserDefined();
    public void setUserDefined(boolean userdefined);

}

public interface Rpc extends AST
{
    public Type getArgType();
    public void setArgType(Type argtype);
    public Type getReturnType();
    public void setReturnType(Type returntype);
}

public interface Service extends AST
{
    public List<Rpc> getRpcs();
    public void setRpcs(List<Rpc> rpcs);
}

public interface PrimitiveType extends Type
{
    public PrimitiveSort getPrimitiveSort();
    public void setPrimitiveSort(PrimitiveSort PrimitiveSort);
}

}// interface AST
