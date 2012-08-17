
import unidata.protobuf.ast.runtime.*;
import static unidata.protobuf.ast.Ast_Runtime.*;


public class
Primtests
{

static public class
PrimRepeated
{
    int[] f_int32 = null;
    long[] f_int64 = null;
    long[] f_uint32 = null;
    long[] f_uint64 = null;
    int[] f_sint32 = null;
    long[] f_sint64 = null;
    long[] f_fixed32 = null;
    long[] f_fixed64 = null;
    int[] f_sfixed32 = null;
    long[] f_sfixed64 = null;
    double[] f_double = null;
    float[] f_float = null;

    public PrimRepeated(ASTRuntime rt)
    {
        super(rt);
    }
    public PrimRepeated(ASTRuntime rt,
                    int[] f_int32,
                    long[] f_int64,
                    long[] f_uint32,
                    long[] f_uint64,
                    int[] f_sint32,
                    long[] f_sint64,
                    long[] f_fixed32,
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

    public void PrimRepeated
    write()
        throws ASTException
    {
        for(int i=0;i<f_int32.length;i++) {
            write_tag(Ast_int32,1);
            write_primitive(Ast_int32,f_int32[i]);
        }
        for(int i=0;i<f_int64.length;i++) {
            write_tag(Ast_int64,2);
            write_primitive(Ast_int64,f_int64[i]);
        }
        for(int i=0;i<f_uint32.length;i++) {
            write_tag(Ast_uint32,3);
            write_primitive(Ast_uint32,f_uint32[i]);
        }
        for(int i=0;i<f_uint64.length;i++) {
            write_tag(Ast_uint64,4);
            write_primitive(Ast_uint64,f_uint64[i]);
        }
        for(int i=0;i<f_sint32.length;i++) {
            write_tag(Ast_sint32,5);
            write_primitive(Ast_sint32,f_sint32[i]);
        }
        for(int i=0;i<f_sint64.length;i++) {
            write_tag(Ast_sint64,6);
            write_primitive(Ast_sint64,f_sint64[i]);
        }
        for(int i=0;i<f_fixed32.length;i++) {
            write_tag(Ast_fixed32,7);
            write_primitive(Ast_fixed32,f_fixed32[i]);
        }
        for(int i=0;i<f_fixed64.length;i++) {
            write_tag(Ast_fixed64,8);
            write_primitive(Ast_fixed64,f_fixed64[i]);
        }
        for(int i=0;i<f_sfixed32.length;i++) {
            write_tag(Ast_sfixed32,9);
            write_primitive(Ast_sfixed32,f_sfixed32[i]);
        }
        for(int i=0;i<f_sfixed64.length;i++) {
            write_tag(Ast_sfixed64,10);
            write_primitive(Ast_sfixed64,f_sfixed64[i]);
        }
        for(int i=0;i<f_double.length;i++) {
            write_tag(Ast_double,11);
            write_primitive(Ast_double,f_double[i]);
        }
        for(int i=0;i<f_float.length;i++) {
            write_tag(Ast_float,12);
            write_primitive(Ast_float,f_float[i]);
        }

    done:
        return this;

    } /*PrimRepeated_write*/

    public void PrimRepeated
    read()
        throws ASTException
    {
        int[] wiretype = new int[1];
        int[] fieldno = new int[1];
        int pos;

        for(;;) {
            if(!read_tag(wiretype,fieldno)) break;
            switch (fieldno[0]) {
            case 1: { // f_int32
                int tmp;
                tmp = read_primitive_int32();
                f_int32 = repeat_append(Ast_int32,tmp,f_int32);
                } break;
            case 2: { // f_int64
                long tmp;
                tmp = read_primitive_int64();
                f_int64 = repeat_append(Ast_int64,tmp,f_int64);
                } break;
            case 3: { // f_uint32
                long tmp;
                tmp = read_primitive_uint32();
                f_uint32 = repeat_append(Ast_uint32,tmp,f_uint32);
                } break;
            case 4: { // f_uint64
                long tmp;
                tmp = read_primitive_uint64();
                f_uint64 = repeat_append(Ast_uint64,tmp,f_uint64);
                } break;
            case 5: { // f_sint32
                int tmp;
                tmp = read_primitive_sint32();
                f_sint32 = repeat_append(Ast_sint32,tmp,f_sint32);
                } break;
            case 6: { // f_sint64
                long tmp;
                tmp = read_primitive_sint64();
                f_sint64 = repeat_append(Ast_sint64,tmp,f_sint64);
                } break;
            case 7: { // f_fixed32
                long tmp;
                tmp = read_primitive_fixed32();
                f_fixed32 = repeat_append(Ast_fixed32,tmp,f_fixed32);
                } break;
            case 8: { // f_fixed64
                long tmp;
                tmp = read_primitive_fixed64();
                f_fixed64 = repeat_append(Ast_fixed64,tmp,f_fixed64);
                } break;
            case 9: { // f_sfixed32
                int tmp;
                tmp = read_primitive_sfixed32();
                f_sfixed32 = repeat_append(Ast_sfixed32,tmp,f_sfixed32);
                } break;
            case 10: { // f_sfixed64
                long tmp;
                tmp = read_primitive_sfixed64();
                f_sfixed64 = repeat_append(Ast_sfixed64,tmp,f_sfixed64);
                } break;
            case 11: { // f_double
                double tmp;
                tmp = read_primitive_double();
                f_double = repeat_append(Ast_double,tmp,f_double);
                } break;
            case 12: { // f_float
                float tmp;
                tmp = read_primitive_float();
                f_float = repeat_append(Ast_float,tmp,f_float);
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        return this;
    } /*PrimRepeated_read*/

    public long
    getSize()
    {
        long totalsize = 0;
        long fieldsize = 0;

        for(int i=0;i<f_int32.length;i++) {
            totalsize += getTagSize(Ast_counted,1);
            totalsize += getSize(Ast_int32,f_int32[i]);
        }

        for(int i=0;i<f_int64.length;i++) {
            totalsize += getTagSize(Ast_counted,2);
            totalsize += getSize(Ast_int64,f_int64[i]);
        }

        for(int i=0;i<f_uint32.length;i++) {
            totalsize += getTagSize(Ast_counted,3);
            totalsize += getSize(Ast_uint32,f_uint32[i]);
        }

        for(int i=0;i<f_uint64.length;i++) {
            totalsize += getTagSize(Ast_counted,4);
            totalsize += getSize(Ast_uint64,f_uint64[i]);
        }

        for(int i=0;i<f_sint32.length;i++) {
            totalsize += getTagSize(Ast_counted,5);
            totalsize += getSize(Ast_sint32,f_sint32[i]);
        }

        for(int i=0;i<f_sint64.length;i++) {
            totalsize += getTagSize(Ast_counted,6);
            totalsize += getSize(Ast_sint64,f_sint64[i]);
        }

        for(int i=0;i<f_fixed32.length;i++) {
            totalsize += getTagSize(Ast_counted,7);
            totalsize += getSize(Ast_fixed32,f_fixed32[i]);
        }

        for(int i=0;i<f_fixed64.length;i++) {
            totalsize += getTagSize(Ast_counted,8);
            totalsize += getSize(Ast_fixed64,f_fixed64[i]);
        }

        for(int i=0;i<f_sfixed32.length;i++) {
            totalsize += getTagSize(Ast_counted,9);
            totalsize += getSize(Ast_sfixed32,f_sfixed32[i]);
        }

        for(int i=0;i<f_sfixed64.length;i++) {
            totalsize += getTagSize(Ast_counted,10);
            totalsize += getSize(Ast_sfixed64,f_sfixed64[i]);
        }

        for(int i=0;i<f_double.length;i++) {
            totalsize += getTagSize(Ast_counted,11);
            totalsize += getSize(Ast_double,f_double[i]);
        }

        for(int i=0;i<f_float.length;i++) {
            totalsize += getTagSize(Ast_counted,12);
            totalsize += getSize(Ast_float,f_float[i]);
        }

        return totalsize;

    } /*PrimRepeated.getSize*/

};


static public class
PrimRequired
{
    int f_int32 = 0;
    long f_int64 = 0;
    long f_uint32 = 0;
    long f_uint64 = 0;
    int f_sint32 = 0;
    long f_sint64 = 0;
    long f_fixed32 = 0;
    long f_fixed64 = 0;
    int f_sfixed32 = 0;
    long f_sfixed64 = 0;
    double f_double = 0;
    float f_float = 0;

    public PrimRequired(ASTRuntime rt)
    {
        super(rt);
    }
    public PrimRequired(ASTRuntime rt,
                    int f_int32,
                    long f_int64,
                    long f_uint32,
                    long f_uint64,
                    int f_sint32,
                    long f_sint64,
                    long f_fixed32,
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

    public void PrimRequired
    write()
        throws ASTException
    {
        write_tag(Ast_int32,1);
        write_primitive(Ast_int32,f_int32);
        write_tag(Ast_int64,2);
        write_primitive(Ast_int64,f_int64);
        write_tag(Ast_uint32,3);
        write_primitive(Ast_uint32,f_uint32);
        write_tag(Ast_uint64,4);
        write_primitive(Ast_uint64,f_uint64);
        write_tag(Ast_sint32,5);
        write_primitive(Ast_sint32,f_sint32);
        write_tag(Ast_sint64,6);
        write_primitive(Ast_sint64,f_sint64);
        write_tag(Ast_fixed32,7);
        write_primitive(Ast_fixed32,f_fixed32);
        write_tag(Ast_fixed64,8);
        write_primitive(Ast_fixed64,f_fixed64);
        write_tag(Ast_sfixed32,9);
        write_primitive(Ast_sfixed32,f_sfixed32);
        write_tag(Ast_sfixed64,10);
        write_primitive(Ast_sfixed64,f_sfixed64);
        write_tag(Ast_double,11);
        write_primitive(Ast_double,f_double);
        write_tag(Ast_float,12);
        write_primitive(Ast_float,f_float);

    done:
        return this;

    } /*PrimRequired_write*/

    public void PrimRequired
    read()
        throws ASTException
    {
        int[] wiretype = new int[1];
        int[] fieldno = new int[1];
        int pos;

        for(;;) {
            if(!read_tag(wiretype,fieldno)) break;
            switch (fieldno[0]) {
            case 1: { // f_int32
                f_int32 = read_primitive_int32();
                } break;
            case 2: { // f_int64
                f_int64 = read_primitive_int64();
                } break;
            case 3: { // f_uint32
                f_uint32 = read_primitive_uint32();
                } break;
            case 4: { // f_uint64
                f_uint64 = read_primitive_uint64();
                } break;
            case 5: { // f_sint32
                f_sint32 = read_primitive_sint32();
                } break;
            case 6: { // f_sint64
                f_sint64 = read_primitive_sint64();
                } break;
            case 7: { // f_fixed32
                f_fixed32 = read_primitive_fixed32();
                } break;
            case 8: { // f_fixed64
                f_fixed64 = read_primitive_fixed64();
                } break;
            case 9: { // f_sfixed32
                f_sfixed32 = read_primitive_sfixed32();
                } break;
            case 10: { // f_sfixed64
                f_sfixed64 = read_primitive_sfixed64();
                } break;
            case 11: { // f_double
                f_double = read_primitive_double();
                } break;
            case 12: { // f_float
                f_float = read_primitive_float();
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        return this;
    } /*PrimRequired_read*/

    public long
    getSize()
    {
        long totalsize = 0;
        long fieldsize = 0;

        totalsize += getTagSize(Ast_counted,1);
        totalsize += getSize(Ast_int32,f_int32);

        totalsize += getTagSize(Ast_counted,2);
        totalsize += getSize(Ast_int64,f_int64);

        totalsize += getTagSize(Ast_counted,3);
        totalsize += getSize(Ast_uint32,f_uint32);

        totalsize += getTagSize(Ast_counted,4);
        totalsize += getSize(Ast_uint64,f_uint64);

        totalsize += getTagSize(Ast_counted,5);
        totalsize += getSize(Ast_sint32,f_sint32);

        totalsize += getTagSize(Ast_counted,6);
        totalsize += getSize(Ast_sint64,f_sint64);

        totalsize += getTagSize(Ast_counted,7);
        totalsize += getSize(Ast_fixed32,f_fixed32);

        totalsize += getTagSize(Ast_counted,8);
        totalsize += getSize(Ast_fixed64,f_fixed64);

        totalsize += getTagSize(Ast_counted,9);
        totalsize += getSize(Ast_sfixed32,f_sfixed32);

        totalsize += getTagSize(Ast_counted,10);
        totalsize += getSize(Ast_sfixed64,f_sfixed64);

        totalsize += getTagSize(Ast_counted,11);
        totalsize += getSize(Ast_double,f_double);

        totalsize += getTagSize(Ast_counted,12);
        totalsize += getSize(Ast_float,f_float);

        return totalsize;

    } /*PrimRequired.getSize*/

};


static public class
PrimOptional
{
    Integer f_int32 = null;
    Long f_int64 = null;
    Long f_uint32 = null;
    Long f_uint64 = null;
    Integer f_sint32 = null;
    Long f_sint64 = null;
    Long f_fixed32 = null;
    Long f_fixed64 = null;
    Integer f_sfixed32 = null;
    Long f_sfixed64 = null;
    Double f_double = null;
    Float f_float = null;

    public PrimOptional(ASTRuntime rt)
    {
        super(rt);
    }
    public PrimOptional(ASTRuntime rt,
                    Integer[] f_int32,
                    Long[] f_int64,
                    Long[] f_uint32,
                    Long[] f_uint64,
                    Integer[] f_sint32,
                    Long[] f_sint64,
                    Long[] f_fixed32,
                    Long[] f_fixed64,
                    Integer[] f_sfixed32,
                    Long[] f_sfixed64,
                    Double[] f_double,
                    Float[] f_float
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

    public void PrimOptional
    write()
        throws ASTException
    {
        if(f_int32 != null) {
            write_tag(Ast_int32,1);
            write_primitive(Ast_int32,f_int32);
        }
        if(f_int64 != null) {
            write_tag(Ast_int64,2);
            write_primitive(Ast_int64,f_int64);
        }
        if(f_uint32 != null) {
            write_tag(Ast_uint32,3);
            write_primitive(Ast_uint32,f_uint32);
        }
        if(f_uint64 != null) {
            write_tag(Ast_uint64,4);
            write_primitive(Ast_uint64,f_uint64);
        }
        if(f_sint32 != null) {
            write_tag(Ast_sint32,5);
            write_primitive(Ast_sint32,f_sint32);
        }
        if(f_sint64 != null) {
            write_tag(Ast_sint64,6);
            write_primitive(Ast_sint64,f_sint64);
        }
        if(f_fixed32 != null) {
            write_tag(Ast_fixed32,7);
            write_primitive(Ast_fixed32,f_fixed32);
        }
        if(f_fixed64 != null) {
            write_tag(Ast_fixed64,8);
            write_primitive(Ast_fixed64,f_fixed64);
        }
        if(f_sfixed32 != null) {
            write_tag(Ast_sfixed32,9);
            write_primitive(Ast_sfixed32,f_sfixed32);
        }
        if(f_sfixed64 != null) {
            write_tag(Ast_sfixed64,10);
            write_primitive(Ast_sfixed64,f_sfixed64);
        }
        if(f_double != null) {
            write_tag(Ast_double,11);
            write_primitive(Ast_double,f_double);
        }
        if(f_float != null) {
            write_tag(Ast_float,12);
            write_primitive(Ast_float,f_float);
        }

    done:
        return this;

    } /*PrimOptional_write*/

    public void PrimOptional
    read()
        throws ASTException
    {
        int[] wiretype = new int[1];
        int[] fieldno = new int[1];
        int pos;

        for(;;) {
            if(!read_tag(wiretype,fieldno)) break;
            switch (fieldno[0]) {
            case 1: { // f_int32
                f_int32 = null;
                f_int32 = read_primitive_int32();
                } break;
            case 2: { // f_int64
                f_int64 = null;
                f_int64 = read_primitive_int64();
                } break;
            case 3: { // f_uint32
                f_uint32 = null;
                f_uint32 = read_primitive_uint32();
                } break;
            case 4: { // f_uint64
                f_uint64 = null;
                f_uint64 = read_primitive_uint64();
                } break;
            case 5: { // f_sint32
                f_sint32 = null;
                f_sint32 = read_primitive_sint32();
                } break;
            case 6: { // f_sint64
                f_sint64 = null;
                f_sint64 = read_primitive_sint64();
                } break;
            case 7: { // f_fixed32
                f_fixed32 = null;
                f_fixed32 = read_primitive_fixed32();
                } break;
            case 8: { // f_fixed64
                f_fixed64 = null;
                f_fixed64 = read_primitive_fixed64();
                } break;
            case 9: { // f_sfixed32
                f_sfixed32 = null;
                f_sfixed32 = read_primitive_sfixed32();
                } break;
            case 10: { // f_sfixed64
                f_sfixed64 = null;
                f_sfixed64 = read_primitive_sfixed64();
                } break;
            case 11: { // f_double
                f_double = null;
                f_double = read_primitive_double();
                } break;
            case 12: { // f_float
                f_float = null;
                f_float = read_primitive_float();
                } break;
            default:
                skip_field(wiretype[0],fieldno[0]);
            } /*switch*/
        }/*for*/
        if(f_int32 == null) {
            f_int32 = null;
        }
        if(f_int64 == null) {
            f_int64 = null;
        }
        if(f_uint32 == null) {
            f_uint32 = null;
        }
        if(f_uint64 == null) {
            f_uint64 = null;
        }
        if(f_sint32 == null) {
            f_sint32 = null;
        }
        if(f_sint64 == null) {
            f_sint64 = null;
        }
        if(f_fixed32 == null) {
            f_fixed32 = null;
        }
        if(f_fixed64 == null) {
            f_fixed64 = null;
        }
        if(f_sfixed32 == null) {
            f_sfixed32 = null;
        }
        if(f_sfixed64 == null) {
            f_sfixed64 = null;
        }
        if(f_double == null) {
            f_double = null;
        }
        if(f_float == null) {
            f_float = null;
        }
        return this;
    } /*PrimOptional_read*/

    public long
    getSize()
    {
        long totalsize = 0;
        long fieldsize = 0;

        if(f_int32 != null) {
            totalsize += getTagSize(Ast_counted,1);
            totalsize += getSize(Ast_int32,f_int32);
        }

        if(f_int64 != null) {
            totalsize += getTagSize(Ast_counted,2);
            totalsize += getSize(Ast_int64,f_int64);
        }

        if(f_uint32 != null) {
            totalsize += getTagSize(Ast_counted,3);
            totalsize += getSize(Ast_uint32,f_uint32);
        }

        if(f_uint64 != null) {
            totalsize += getTagSize(Ast_counted,4);
            totalsize += getSize(Ast_uint64,f_uint64);
        }

        if(f_sint32 != null) {
            totalsize += getTagSize(Ast_counted,5);
            totalsize += getSize(Ast_sint32,f_sint32);
        }

        if(f_sint64 != null) {
            totalsize += getTagSize(Ast_counted,6);
            totalsize += getSize(Ast_sint64,f_sint64);
        }

        if(f_fixed32 != null) {
            totalsize += getTagSize(Ast_counted,7);
            totalsize += getSize(Ast_fixed32,f_fixed32);
        }

        if(f_fixed64 != null) {
            totalsize += getTagSize(Ast_counted,8);
            totalsize += getSize(Ast_fixed64,f_fixed64);
        }

        if(f_sfixed32 != null) {
            totalsize += getTagSize(Ast_counted,9);
            totalsize += getSize(Ast_sfixed32,f_sfixed32);
        }

        if(f_sfixed64 != null) {
            totalsize += getTagSize(Ast_counted,10);
            totalsize += getSize(Ast_sfixed64,f_sfixed64);
        }

        if(f_double != null) {
            totalsize += getTagSize(Ast_counted,11);
            totalsize += getSize(Ast_double,f_double);
        }

        if(f_float != null) {
            totalsize += getTagSize(Ast_counted,12);
            totalsize += getSize(Ast_float,f_float);
        }

        return totalsize;

    } /*PrimOptional.getSize*/

};


}
