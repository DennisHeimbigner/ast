package unidata.ast.test;

import unidata.ast.runtime.*;
import static unidata.ast.runtime.ASTRuntime.*;

import java.io.IOException;


public class Primtests
{

static public class PrimRequired extends unidata.ast.runtime.AbstractMessage 
{
    int f_int32 = 0;
    long f_int64 = 0;
    int f_uint32 = 0;
    long f_uint64 = 0;
    int f_sint32 = 0;
    long f_sint64 = 0;
    int f_fixed32 = 0;
    long f_fixed64 = 0;
    int f_sfixed32 = 0;
    long f_sfixed64 = 0;
    double f_double = (double)0.0;
    float f_float = (float)0.0;

    public PrimRequired(ASTRuntime rt)
        throws IOException
    {
        super(rt);
    }

    public PrimRequired(ASTRuntime rt,
                    int f_int32,
                    long f_int64,
                    int f_uint32,
                    long f_uint64,
                    int f_sint32,
                    long f_sint64,
                    int f_fixed32,
                    long f_fixed64,
                    int f_sfixed32,
                    long f_sfixed64,
                    double f_double,
                    float f_float
                    )
    {
        super(rt);
        this.f_int32 = f_int32;
        this.f_int64 = f_int64;
        this.f_uint32 = f_uint32;
        this.f_uint64 = f_uint64;
        this.f_sint32 = f_sint32;
        this.f_sint64 = f_sint64;
        this.f_fixed32 = f_fixed32;
        this.f_fixed64 = f_fixed64;
        this.f_sfixed32 = f_sfixed32;
        this.f_sfixed64 = f_sfixed64;
        this.f_double = f_double;
        this.f_float = f_float;
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
        write_tag(Sort.Ast_int64,2);
        write_primitive(Sort.Ast_int64,f_int64);
        write_tag(Sort.Ast_uint32,3);
        write_primitive(Sort.Ast_uint32,f_uint32);
        write_tag(Sort.Ast_uint64,4);
        write_primitive(Sort.Ast_uint64,f_uint64);
        write_tag(Sort.Ast_sint32,5);
        write_primitive(Sort.Ast_sint32,f_sint32);
        write_tag(Sort.Ast_sint64,6);
        write_primitive(Sort.Ast_sint64,f_sint64);
        write_tag(Sort.Ast_fixed32,7);
        write_primitive(Sort.Ast_fixed32,f_fixed32);
        write_tag(Sort.Ast_fixed64,8);
        write_primitive(Sort.Ast_fixed64,f_fixed64);
        write_tag(Sort.Ast_sfixed32,9);
        write_primitive(Sort.Ast_sfixed32,f_sfixed32);
        write_tag(Sort.Ast_sfixed64,10);
        write_primitive(Sort.Ast_sfixed64,f_sfixed64);
        write_tag(Sort.Ast_double,11);
        write_primitive(Sort.Ast_double,f_double);
        write_tag(Sort.Ast_float,12);
        write_primitive(Sort.Ast_float,f_float);

    } /*PrimRequired_write*/

    public PrimRequired
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
            case 2: { // f_int64
                f_int64 = read_primitive_long(Sort.Ast_int64);
                } break;
            case 3: { // f_uint32
                f_uint32 = read_primitive_int(Sort.Ast_uint32);
                } break;
            case 4: { // f_uint64
                f_uint64 = read_primitive_long(Sort.Ast_uint64);
                } break;
            case 5: { // f_sint32
                f_sint32 = read_primitive_int(Sort.Ast_sint32);
                } break;
            case 6: { // f_sint64
                f_sint64 = read_primitive_long(Sort.Ast_sint64);
                } break;
            case 7: { // f_fixed32
                f_fixed32 = read_primitive_int(Sort.Ast_fixed32);
                } break;
            case 8: { // f_fixed64
                f_fixed64 = read_primitive_long(Sort.Ast_fixed64);
                } break;
            case 9: { // f_sfixed32
                f_sfixed32 = read_primitive_int(Sort.Ast_sfixed32);
                } break;
            case 10: { // f_sfixed64
                f_sfixed64 = read_primitive_long(Sort.Ast_sfixed64);
                } break;
            case 11: { // f_double
                f_double = read_primitive_double(Sort.Ast_double);
                } break;
            case 12: { // f_float
                f_float = read_primitive_float(Sort.Ast_float);
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        unmark();
        return this;
    } /*PrimRequired_read*/

    public int
    getSize()
        throws IOException
    {
        int totalsize = 0;
        int fieldsize = 0;

        totalsize += getTagSize(Sort.Ast_packed,1);
        totalsize += getSize(Sort.Ast_int32,f_int32);

        totalsize += getTagSize(Sort.Ast_packed,2);
        totalsize += getSize(Sort.Ast_int64,f_int64);

        totalsize += getTagSize(Sort.Ast_packed,3);
        totalsize += getSize(Sort.Ast_uint32,f_uint32);

        totalsize += getTagSize(Sort.Ast_packed,4);
        totalsize += getSize(Sort.Ast_uint64,f_uint64);

        totalsize += getTagSize(Sort.Ast_packed,5);
        totalsize += getSize(Sort.Ast_sint32,f_sint32);

        totalsize += getTagSize(Sort.Ast_packed,6);
        totalsize += getSize(Sort.Ast_sint64,f_sint64);

        totalsize += getTagSize(Sort.Ast_packed,7);
        totalsize += getSize(Sort.Ast_fixed32,f_fixed32);

        totalsize += getTagSize(Sort.Ast_packed,8);
        totalsize += getSize(Sort.Ast_fixed64,f_fixed64);

        totalsize += getTagSize(Sort.Ast_packed,9);
        totalsize += getSize(Sort.Ast_sfixed32,f_sfixed32);

        totalsize += getTagSize(Sort.Ast_packed,10);
        totalsize += getSize(Sort.Ast_sfixed64,f_sfixed64);

        totalsize += getTagSize(Sort.Ast_packed,11);
        totalsize += getSize(Sort.Ast_double,f_double);

        totalsize += getTagSize(Sort.Ast_packed,12);
        totalsize += getSize(Sort.Ast_float,f_float);

        return totalsize;

    } /*PrimRequired.getSize*/

};


static public class PrimOptional extends unidata.ast.runtime.AbstractMessage 
{
    Integer f_int32 = null;
    Long f_int64 = null;
    Integer f_uint32 = null;
    Long f_uint64 = null;
    Integer f_sint32 = null;
    Long f_sint64 = null;
    Integer f_fixed32 = null;
    Long f_fixed64 = null;
    Integer f_sfixed32 = null;
    Long f_sfixed64 = null;
    Double f_double = null;
    Float f_float = null;

    public PrimOptional(ASTRuntime rt)
        throws IOException
    {
        super(rt);
    }

    public PrimOptional(ASTRuntime rt,
                    Integer f_int32,
                    Long f_int64,
                    Integer f_uint32,
                    Long f_uint64,
                    Integer f_sint32,
                    Long f_sint64,
                    Integer f_fixed32,
                    Long f_fixed64,
                    Integer f_sfixed32,
                    Long f_sfixed64,
                    Double f_double,
                    Float f_float
                    )
    {
        super(rt);
        this.f_int32 = f_int32;
        this.f_int64 = f_int64;
        this.f_uint32 = f_uint32;
        this.f_uint64 = f_uint64;
        this.f_sint32 = f_sint32;
        this.f_sint64 = f_sint64;
        this.f_fixed32 = f_fixed32;
        this.f_fixed64 = f_fixed64;
        this.f_sfixed32 = f_sfixed32;
        this.f_sfixed64 = f_sfixed64;
        this.f_double = f_double;
        this.f_float = f_float;
    }

    public void
    write()
        throws IOException
    {
        int size = 0;
        size = getSize();
        write_size(size);
        if(f_int32 != null) {
            write_tag(Sort.Ast_int32,1);
            write_primitive(Sort.Ast_int32,f_int32);
        }
        if(f_int64 != null) {
            write_tag(Sort.Ast_int64,2);
            write_primitive(Sort.Ast_int64,f_int64);
        }
        if(f_uint32 != null) {
            write_tag(Sort.Ast_uint32,3);
            write_primitive(Sort.Ast_uint32,f_uint32);
        }
        if(f_uint64 != null) {
            write_tag(Sort.Ast_uint64,4);
            write_primitive(Sort.Ast_uint64,f_uint64);
        }
        if(f_sint32 != null) {
            write_tag(Sort.Ast_sint32,5);
            write_primitive(Sort.Ast_sint32,f_sint32);
        }
        if(f_sint64 != null) {
            write_tag(Sort.Ast_sint64,6);
            write_primitive(Sort.Ast_sint64,f_sint64);
        }
        if(f_fixed32 != null) {
            write_tag(Sort.Ast_fixed32,7);
            write_primitive(Sort.Ast_fixed32,f_fixed32);
        }
        if(f_fixed64 != null) {
            write_tag(Sort.Ast_fixed64,8);
            write_primitive(Sort.Ast_fixed64,f_fixed64);
        }
        if(f_sfixed32 != null) {
            write_tag(Sort.Ast_sfixed32,9);
            write_primitive(Sort.Ast_sfixed32,f_sfixed32);
        }
        if(f_sfixed64 != null) {
            write_tag(Sort.Ast_sfixed64,10);
            write_primitive(Sort.Ast_sfixed64,f_sfixed64);
        }
        if(f_double != null) {
            write_tag(Sort.Ast_double,11);
            write_primitive(Sort.Ast_double,f_double);
        }
        if(f_float != null) {
            write_tag(Sort.Ast_float,12);
            write_primitive(Sort.Ast_float,f_float);
        }

    } /*PrimOptional_write*/

    public PrimOptional
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
                f_int32 = null;
                f_int32 = read_primitive_int(Sort.Ast_int32);
                } break;
            case 2: { // f_int64
                f_int64 = null;
                f_int64 = read_primitive_long(Sort.Ast_int64);
                } break;
            case 3: { // f_uint32
                f_uint32 = null;
                f_uint32 = read_primitive_int(Sort.Ast_uint32);
                } break;
            case 4: { // f_uint64
                f_uint64 = null;
                f_uint64 = read_primitive_long(Sort.Ast_uint64);
                } break;
            case 5: { // f_sint32
                f_sint32 = null;
                f_sint32 = read_primitive_int(Sort.Ast_sint32);
                } break;
            case 6: { // f_sint64
                f_sint64 = null;
                f_sint64 = read_primitive_long(Sort.Ast_sint64);
                } break;
            case 7: { // f_fixed32
                f_fixed32 = null;
                f_fixed32 = read_primitive_int(Sort.Ast_fixed32);
                } break;
            case 8: { // f_fixed64
                f_fixed64 = null;
                f_fixed64 = read_primitive_long(Sort.Ast_fixed64);
                } break;
            case 9: { // f_sfixed32
                f_sfixed32 = null;
                f_sfixed32 = read_primitive_int(Sort.Ast_sfixed32);
                } break;
            case 10: { // f_sfixed64
                f_sfixed64 = null;
                f_sfixed64 = read_primitive_long(Sort.Ast_sfixed64);
                } break;
            case 11: { // f_double
                f_double = null;
                f_double = read_primitive_double(Sort.Ast_double);
                } break;
            case 12: { // f_float
                f_float = null;
                f_float = read_primitive_float(Sort.Ast_float);
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        if(f_int32 == null) {
            f_int32 = Integer.valueOf(0);
        }
        if(f_int64 == null) {
            f_int64 = Long.valueOf(2);
        }
        if(f_uint32 == null) {
            f_uint32 = Integer.valueOf(0);
        }
        if(f_uint64 == null) {
            f_uint64 = Long.valueOf(4);
        }
        if(f_sint32 == null) {
            f_sint32 = Integer.valueOf(0);
        }
        if(f_sint64 == null) {
            f_sint64 = Long.valueOf(6);
        }
        if(f_fixed32 == null) {
            f_fixed32 = Integer.valueOf(0);
        }
        if(f_fixed64 == null) {
            f_fixed64 = Long.valueOf(8);
        }
        if(f_sfixed32 == null) {
            f_sfixed32 = Integer.valueOf(0);
        }
        if(f_sfixed64 == null) {
            f_sfixed64 = Long.valueOf(10);
        }
        if(f_double == null) {
            f_double = Double.valueOf((double)0.0);
        }
        if(f_float == null) {
            f_float = Float.valueOf(12);
        }
        unmark();
        return this;
    } /*PrimOptional_read*/

    public int
    getSize()
        throws IOException
    {
        int totalsize = 0;
        int fieldsize = 0;

        if(f_int32 != null) {
            totalsize += getTagSize(Sort.Ast_packed,1);
            totalsize += getSize(Sort.Ast_int32,f_int32);
        }

        if(f_int64 != null) {
            totalsize += getTagSize(Sort.Ast_packed,2);
            totalsize += getSize(Sort.Ast_int64,f_int64);
        }

        if(f_uint32 != null) {
            totalsize += getTagSize(Sort.Ast_packed,3);
            totalsize += getSize(Sort.Ast_uint32,f_uint32);
        }

        if(f_uint64 != null) {
            totalsize += getTagSize(Sort.Ast_packed,4);
            totalsize += getSize(Sort.Ast_uint64,f_uint64);
        }

        if(f_sint32 != null) {
            totalsize += getTagSize(Sort.Ast_packed,5);
            totalsize += getSize(Sort.Ast_sint32,f_sint32);
        }

        if(f_sint64 != null) {
            totalsize += getTagSize(Sort.Ast_packed,6);
            totalsize += getSize(Sort.Ast_sint64,f_sint64);
        }

        if(f_fixed32 != null) {
            totalsize += getTagSize(Sort.Ast_packed,7);
            totalsize += getSize(Sort.Ast_fixed32,f_fixed32);
        }

        if(f_fixed64 != null) {
            totalsize += getTagSize(Sort.Ast_packed,8);
            totalsize += getSize(Sort.Ast_fixed64,f_fixed64);
        }

        if(f_sfixed32 != null) {
            totalsize += getTagSize(Sort.Ast_packed,9);
            totalsize += getSize(Sort.Ast_sfixed32,f_sfixed32);
        }

        if(f_sfixed64 != null) {
            totalsize += getTagSize(Sort.Ast_packed,10);
            totalsize += getSize(Sort.Ast_sfixed64,f_sfixed64);
        }

        if(f_double != null) {
            totalsize += getTagSize(Sort.Ast_packed,11);
            totalsize += getSize(Sort.Ast_double,f_double);
        }

        if(f_float != null) {
            totalsize += getTagSize(Sort.Ast_packed,12);
            totalsize += getSize(Sort.Ast_float,f_float);
        }

        return totalsize;

    } /*PrimOptional.getSize*/

};


static public class PrimRepeated extends unidata.ast.runtime.AbstractMessage 
{
    int[] f_int32 = null;
    long[] f_int64 = null;
    int[] f_uint32 = null;
    long[] f_uint64 = null;
    int[] f_sint32 = null;
    long[] f_sint64 = null;
    int[] f_fixed32 = null;
    long[] f_fixed64 = null;
    int[] f_sfixed32 = null;
    long[] f_sfixed64 = null;
    double[] f_double = null;
    float[] f_float = null;

    public PrimRepeated(ASTRuntime rt)
        throws IOException
    {
        super(rt);
    }

    public PrimRepeated(ASTRuntime rt,
                    int[] f_int32,
                    long[] f_int64,
                    int[] f_uint32,
                    long[] f_uint64,
                    int[] f_sint32,
                    long[] f_sint64,
                    int[] f_fixed32,
                    long[] f_fixed64,
                    int[] f_sfixed32,
                    long[] f_sfixed64,
                    double[] f_double,
                    float[] f_float
                    )
    {
        super(rt);
        this.f_int32 = f_int32;
        this.f_int64 = f_int64;
        this.f_uint32 = f_uint32;
        this.f_uint64 = f_uint64;
        this.f_sint32 = f_sint32;
        this.f_sint64 = f_sint64;
        this.f_fixed32 = f_fixed32;
        this.f_fixed64 = f_fixed64;
        this.f_sfixed32 = f_sfixed32;
        this.f_sfixed64 = f_sfixed64;
        this.f_double = f_double;
        this.f_float = f_float;
    }

    public void
    write()
        throws IOException
    {
        int size = 0;
        size = getSize();
        write_size(size);
        write_tag(Sort.Ast_packed,1);
        write_primitive_packed(Sort.Ast_int32,f_int32);
        if(f_int64 != null)
            for(int i=0;i<f_int64.length;i++) {
                write_tag(Sort.Ast_int64,2);
                write_primitive(Sort.Ast_int64,f_int64[i]);
            }
        write_tag(Sort.Ast_packed,3);
        write_primitive_packed(Sort.Ast_uint32,f_uint32);
        if(f_uint64 != null)
            for(int i=0;i<f_uint64.length;i++) {
                write_tag(Sort.Ast_uint64,4);
                write_primitive(Sort.Ast_uint64,f_uint64[i]);
            }
        write_tag(Sort.Ast_packed,5);
        write_primitive_packed(Sort.Ast_sint32,f_sint32);
        if(f_sint64 != null)
            for(int i=0;i<f_sint64.length;i++) {
                write_tag(Sort.Ast_sint64,6);
                write_primitive(Sort.Ast_sint64,f_sint64[i]);
            }
        write_tag(Sort.Ast_packed,7);
        write_primitive_packed(Sort.Ast_fixed32,f_fixed32);
        if(f_fixed64 != null)
            for(int i=0;i<f_fixed64.length;i++) {
                write_tag(Sort.Ast_fixed64,8);
                write_primitive(Sort.Ast_fixed64,f_fixed64[i]);
            }
        write_tag(Sort.Ast_packed,9);
        write_primitive_packed(Sort.Ast_sfixed32,f_sfixed32);
        if(f_sfixed64 != null)
            for(int i=0;i<f_sfixed64.length;i++) {
                write_tag(Sort.Ast_sfixed64,10);
                write_primitive(Sort.Ast_sfixed64,f_sfixed64[i]);
            }
        write_tag(Sort.Ast_packed,11);
        write_primitive_packed(Sort.Ast_double,f_double);
        if(f_float != null)
            for(int i=0;i<f_float.length;i++) {
                write_tag(Sort.Ast_float,12);
                write_primitive(Sort.Ast_float,f_float[i]);
            }

    } /*PrimRepeated_write*/

    public PrimRepeated
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
                f_int32 = read_primitive_packed_int(Sort.Ast_int32);
                } break;
            case 2: { // f_int64
                long tmp;
                tmp = read_primitive_long(Sort.Ast_int64);
                f_int64 = repeat_append(Sort.Ast_int64,tmp,f_int64);
                } break;
            case 3: { // f_uint32
                f_uint32 = read_primitive_packed_int(Sort.Ast_uint32);
                } break;
            case 4: { // f_uint64
                long tmp;
                tmp = read_primitive_long(Sort.Ast_uint64);
                f_uint64 = repeat_append(Sort.Ast_uint64,tmp,f_uint64);
                } break;
            case 5: { // f_sint32
                f_sint32 = read_primitive_packed_int(Sort.Ast_sint32);
                } break;
            case 6: { // f_sint64
                long tmp;
                tmp = read_primitive_long(Sort.Ast_sint64);
                f_sint64 = repeat_append(Sort.Ast_sint64,tmp,f_sint64);
                } break;
            case 7: { // f_fixed32
                f_fixed32 = read_primitive_packed_int(Sort.Ast_fixed32);
                } break;
            case 8: { // f_fixed64
                long tmp;
                tmp = read_primitive_long(Sort.Ast_fixed64);
                f_fixed64 = repeat_append(Sort.Ast_fixed64,tmp,f_fixed64);
                } break;
            case 9: { // f_sfixed32
                f_sfixed32 = read_primitive_packed_int(Sort.Ast_sfixed32);
                } break;
            case 10: { // f_sfixed64
                long tmp;
                tmp = read_primitive_long(Sort.Ast_sfixed64);
                f_sfixed64 = repeat_append(Sort.Ast_sfixed64,tmp,f_sfixed64);
                } break;
            case 11: { // f_double
                f_double = read_primitive_packed_double(Sort.Ast_double);
                } break;
            case 12: { // f_float
                float tmp;
                tmp = read_primitive_float(Sort.Ast_float);
                f_float = repeat_append(Sort.Ast_float,tmp,f_float);
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        unmark();
        return this;
    } /*PrimRepeated_read*/

    public int
    getSize()
        throws IOException
    {
        int totalsize = 0;
        int fieldsize = 0;

        for(int i=0;i<f_int32.length;i++) {
            totalsize += getTagSize(Sort.Ast_int32,1);
            totalsize += getSize(Sort.Ast_int32,f_int32[i]);
        }

        for(int i=0;i<f_int64.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,2);
            totalsize += getSize(Sort.Ast_int64,f_int64[i]);
        }

        for(int i=0;i<f_uint32.length;i++) {
            totalsize += getTagSize(Sort.Ast_uint32,3);
            totalsize += getSize(Sort.Ast_uint32,f_uint32[i]);
        }

        for(int i=0;i<f_uint64.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,4);
            totalsize += getSize(Sort.Ast_uint64,f_uint64[i]);
        }

        for(int i=0;i<f_sint32.length;i++) {
            totalsize += getTagSize(Sort.Ast_sint32,5);
            totalsize += getSize(Sort.Ast_sint32,f_sint32[i]);
        }

        for(int i=0;i<f_sint64.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,6);
            totalsize += getSize(Sort.Ast_sint64,f_sint64[i]);
        }

        for(int i=0;i<f_fixed32.length;i++) {
            totalsize += getTagSize(Sort.Ast_fixed32,7);
            totalsize += getSize(Sort.Ast_fixed32,f_fixed32[i]);
        }

        for(int i=0;i<f_fixed64.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,8);
            totalsize += getSize(Sort.Ast_fixed64,f_fixed64[i]);
        }

        for(int i=0;i<f_sfixed32.length;i++) {
            totalsize += getTagSize(Sort.Ast_sfixed32,9);
            totalsize += getSize(Sort.Ast_sfixed32,f_sfixed32[i]);
        }

        for(int i=0;i<f_sfixed64.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,10);
            totalsize += getSize(Sort.Ast_sfixed64,f_sfixed64[i]);
        }

        for(int i=0;i<f_double.length;i++) {
            totalsize += getTagSize(Sort.Ast_double,11);
            totalsize += getSize(Sort.Ast_double,f_double[i]);
        }

        for(int i=0;i<f_float.length;i++) {
            totalsize += getTagSize(Sort.Ast_packed,12);
            totalsize += getSize(Sort.Ast_float,f_float[i]);
        }

        return totalsize;

    } /*PrimRepeated.getSize*/

};


}
