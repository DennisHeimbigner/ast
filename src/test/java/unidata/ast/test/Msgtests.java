package unidata.ast.test;

import unidata.ast.runtime.*;
import static unidata.ast.runtime.ASTRuntime.*;

import java.io.IOException;


public class Msgtests
{

static public class Msgtest extends unidata.ast.runtime.AbstractMessage 
{

    static public class Submsg     extends unidata.ast.runtime.AbstractMessage     
    {
        int f_int32 = 0;

        public Submsg(ASTRuntime rt)
            throws IOException
        {
            super(rt);
        }

        public Submsg(ASTRuntime rt,
              int f_int32
              )
    {
        super(rt);
        this.f_int32 = f_int32;
    }

    public void
    write()
        throws IOException
    {
        int size = 0;
        size = getSize();
        write_size(size);
        write_tag(Sort.Ast_int32,1);
        write_primitive(Sort.Ast_int32,f_int32);

    } /*Submsg_write*/

    public Submsg
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
            case 1: { // f_int32
                f_int32 = read_primitive_int(Sort.Ast_int32);
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        unmark();
        return this;
    } /*Submsg_read*/

    public int
    getSize()
        throws IOException
    {
        int totalsize = 0;
        int fieldsize = 0;

        totalsize += getTagSize(Sort.Ast_packed,1);
        totalsize += getSize(Sort.Ast_int32,f_int32);

        return totalsize;

    } /*Submsg.getSize*/

};


Submsg rqmsg = null;
Submsg opmsg = null;
Submsg[] rpmsg = null;

public Msgtest(ASTRuntime rt)
    throws IOException
{
    super(rt);
}

public Msgtest(ASTRuntime rt,
               Submsg rqmsg,
               Submsg opmsg,
               Submsg[] rpmsg
               )
    {
        super(rt);
        this.rqmsg = rqmsg;
        this.opmsg = opmsg;
        this.rpmsg = rpmsg;
    }

    public void
    write()
        throws IOException
    {
        int size = 0;
        size = getSize();
        write_size(size);
        write_tag(Sort.Ast_message,1);
        rqmsg.write();
        if(opmsg != null) {
            write_tag(Sort.Ast_message,2);
            opmsg.write();
        }
        if(rpmsg != null)
            for(int i=0;i<rpmsg.length;i++) {
                write_tag(Sort.Ast_message,3);
                rpmsg[i].write();
            }

    } /*Msgtest_write*/

    public Msgtest
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
            case 1: { // rqmsg
                if(wiretype[0] != Wiretype.Ast_counted)
                    throw new ASTException("Wiretype not Ast_counted: "+wiretype);
                rqmsg = new Submsg(rt).read();
                } break;
            case 2: { // opmsg
                if(wiretype[0] != Wiretype.Ast_counted)
                    throw new ASTException("Wiretype not Ast_counted: "+wiretype);
                opmsg = new Submsg(rt).read();
                } break;
            case 3: { // rpmsg
                Submsg tmp;
                if(wiretype[0] != Wiretype.Ast_counted)
                    throw new ASTException("Wiretype not Ast_counted: "+wiretype);
                rpmsg = (Submsg[])repeat_extend((Object)rpmsg,Submsg.class);
                rpmsg[rpmsg.length-1] = new Submsg(rt).read();
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        unmark();
        return this;
    } /*Msgtest_read*/

    public int
    getSize()
        throws IOException
    {
        int totalsize = 0;
        int fieldsize = 0;

        totalsize += getTagSize(Sort.Ast_packed,1);
        fieldsize = rqmsg.getSize();
        totalsize += getMessageSize(fieldsize);

        if(opmsg != null) {
            totalsize += getTagSize(Sort.Ast_packed,2);
            fieldsize = opmsg.getSize();
            totalsize += getMessageSize(fieldsize);
        }

        for(int i=0;i<rpmsg.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,3);
            fieldsize = rpmsg[i].getSize();
            totalsize += getMessageSize(fieldsize);
        }

        return totalsize;

    } /*Msgtest.getSize*/

};


}
