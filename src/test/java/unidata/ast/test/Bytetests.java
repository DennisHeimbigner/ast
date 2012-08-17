package unidata.ast.test;

import unidata.ast.runtime.*;
import static unidata.ast.runtime.ASTRuntime.*;

import java.io.IOException;


public class Bytetests
{

static public class Bytetest extends unidata.ast.runtime.AbstractMessage 
{
    String rqstring = "";
    byte[] rqbytes = new byte[]{0x00};
    String opstring = null;
    byte[] opbytes = null;
    String[] rpstring = null;
    byte[][] rpbytes = null;

    public Bytetest(ASTRuntime rt)
        throws IOException
    {
        super(rt);
    }

    public Bytetest(ASTRuntime rt,
                String rqstring,
                byte[] rqbytes,
                String opstring,
                byte[] opbytes,
                String[] rpstring,
                byte[][] rpbytes
                )
    {
        super(rt);
        this.rqstring = rqstring;
        this.rqbytes = rqbytes;
        this.opstring = opstring;
        this.opbytes = opbytes;
        this.rpstring = rpstring;
        this.rpbytes = rpbytes;
    }

    public void
    write()
        throws IOException
    {
        int size = 0;
        size = getSize();
        write_size(size);
        write_tag(Sort.Ast_string,1);
        write_primitive(Sort.Ast_string,rqstring);
        write_tag(Sort.Ast_bytes,2);
        write_primitive(Sort.Ast_bytes,rqbytes);
        if(opstring != null) {
            write_tag(Sort.Ast_string,3);
            write_primitive(Sort.Ast_string,opstring);
        }
        if(opbytes != null) {
            write_tag(Sort.Ast_bytes,4);
            write_primitive(Sort.Ast_bytes,opbytes);
        }
        if(rpstring != null)
            for(int i=0;i<rpstring.length;i++) {
                write_tag(Sort.Ast_string,5);
                write_primitive(Sort.Ast_string,rpstring[i]);
            }
        if(rpbytes != null)
            for(int i=0;i<rpbytes.length;i++) {
                write_tag(Sort.Ast_bytes,6);
                write_primitive(Sort.Ast_bytes,rpbytes[i]);
            }

    } /*Bytetest_write*/

    public Bytetest
    read()
        throws IOException
    {
        int[] wiretype = new int[1];
        int[] fieldno = new int[1];
        int size = 0;
        int pos = 0;

        {int readsize = read_size();
        mark(readsize);}
        for(;;) {
            if(!read_tag(wiretype,fieldno)) break;
            switch (fieldno[0]) {
            case 1: { // rqstring
                rqstring = read_primitive_string(Sort.Ast_string);
                } break;
            case 2: { // rqbytes
                rqbytes = read_primitive_bytes(Sort.Ast_bytes);
                } break;
            case 3: { // opstring
                opstring = null;
                opstring = read_primitive_string(Sort.Ast_string);
                } break;
            case 4: { // opbytes
                opbytes = null;
                opbytes = read_primitive_bytes(Sort.Ast_bytes);
                } break;
            case 5: { // rpstring
                String tmp;
                tmp = read_primitive_string(Sort.Ast_string);
                rpstring = repeat_append(Sort.Ast_string,tmp,rpstring);
                } break;
            case 6: { // rpbytes
                byte[] tmp;
                tmp = read_primitive_bytes(Sort.Ast_bytes);
                rpbytes = repeat_append(Sort.Ast_bytes,tmp,rpbytes);
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        if(opstring == null) {
            opstring = "hello";
        }
        if(opbytes == null) {
            opbytes = new byte[]{0x00};
        }
        unmark();
        return this;
    } /*Bytetest_read*/

    public int
    getSize()
        throws IOException
    {
        int totalsize = 0;
        int fieldsize = 0;

        totalsize += getTagSize(Sort.Ast_packed,1);
        totalsize += getSize(Sort.Ast_string,rqstring);

        totalsize += getTagSize(Sort.Ast_packed,2);
        totalsize += getSize(Sort.Ast_bytes,rqbytes);

        if(opstring != null) {
            totalsize += getTagSize(Sort.Ast_packed,3);
            totalsize += getSize(Sort.Ast_string,opstring);
        }

        if(opbytes != null) {
            totalsize += getTagSize(Sort.Ast_packed,4);
            totalsize += getSize(Sort.Ast_bytes,opbytes);
        }

        for(int i=0;i<rpstring.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,5);
            totalsize += getSize(Sort.Ast_string,rpstring[i]);
        }

        for(int i=0;i<rpbytes.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,6);
            totalsize += getSize(Sort.Ast_bytes,rpbytes[i]);
        }

        return totalsize;

    } /*Bytetest.getSize*/

};


}
