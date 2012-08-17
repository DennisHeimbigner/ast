package unidata.ast.test;

import unidata.ast.runtime.*;
import static unidata.ast.runtime.ASTRuntime.*;

import java.io.IOException;


public class Enumtests
{

static public class Enumtest extends unidata.ast.runtime.AbstractMessage 
{
    static public enum Testenum {
        ECON1(1),
        ECON2(2),
        ECON3(3),
        ECON4(4),
        ECON5(5),
        ECON6(6);
        private final int value;
        public int getValue() {return value;}
        Testenum(int value) {this.value = value;};
        static Testenum toEnum(int i) {
            switch (i) {
            case 1: return Testenum.ECON1;
            case 2: return Testenum.ECON2;
            case 3: return Testenum.ECON3;
            case 4: return Testenum.ECON4;
            case 5: return Testenum.ECON5;
            case 6: return Testenum.ECON6;
            default: return null;
            }
        }
    } //enum Testenum

    Testenum renum = null;
    Testenum oenum = null;
    Testenum[] penum = null;

    public Enumtest(ASTRuntime rt)
        throws IOException
    {
        super(rt);
    }

    public Enumtest(ASTRuntime rt,
                Testenum renum,
                Testenum oenum,
                Testenum[] penum
                )
    {
        super(rt);
        this.renum = renum;
        this.oenum = oenum;
        this.penum = penum;
    }

    public void
    write()
        throws IOException
    {
        int size = 0;
        size = getSize();
        write_size(size);
        write_tag(Sort.Ast_enum,1);
        write_primitive(Sort.Ast_enum,renum.getValue());
        if(oenum != null) {
            write_tag(Sort.Ast_enum,2);
            write_primitive(Sort.Ast_enum,oenum.getValue());
        }
        if(penum != null)
            for(int i=0;i<penum.length;i++) {
                write_tag(Sort.Ast_enum,3);
                write_primitive(Sort.Ast_enum,penum[i].getValue());
            }

    } /*Enumtest_write*/

    public Enumtest
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
            case 1: { // renum
                renum = Testenum.toEnum(read_primitive_int(Sort.Ast_int32));
                } break;
            case 2: { // oenum
                oenum = Testenum.toEnum(read_primitive_int(Sort.Ast_int32));
                } break;
            case 3: { // penum
                penum = (Testenum[])repeat_extend((Object)penum,Testenum.class);
                penum[penum.length-1] = Testenum.toEnum(read_primitive_int(Sort.Ast_int32));
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        unmark();
        return this;
    } /*Enumtest_read*/

    public int
    getSize()
        throws IOException
    {
        int totalsize = 0;
        int fieldsize = 0;

        totalsize += getTagSize(Sort.Ast_packed,1);
        totalsize += getSize(Sort.Ast_enum,renum.getValue());

        if(oenum != null) {
            totalsize += getTagSize(Sort.Ast_packed,2);
            totalsize += getSize(Sort.Ast_enum,oenum.getValue());
        }

        for(int i=0;i<penum.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,3);
            totalsize += getSize(Sort.Ast_enum,penum[i].getValue());
        }

        return totalsize;

    } /*Enumtest.getSize*/

};


}
