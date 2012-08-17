
package unidata.ast.test;

import unidata.ast.runtime.ASTRuntime;
import unidata.ast.runtime.AbstractIO;
import unidata.ast.runtime.ByteIO;
import unidata.ast.runtime.ASTException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static unidata.ast.test.P.*;

public class T
{

static public void
main(String[] argv)
{
    try {
        new Testprim3().test();
    } catch (Exception e) {
	e.printStackTrace();
	System.exit(1);
    }
    System.exit(0);
}

static abstract class TestCommon
{
ByteArrayOutputStream ostream = null;
ByteArrayInputStream istream = null;
byte[] contents = null;
ByteIO io = null;
ASTRuntime rt = null;

String testname = null;

TestCommon()
        throws IOException
{
    this("unknown");
}

TestCommon(String testname)
        throws IOException
{
    this.testname = testname;
    ostream = new ByteArrayOutputStream();
    istream = null;
    contents = null;
    io = new ByteIO(ostream);
    rt = new ASTRuntime(io);
}

abstract void write() throws IOException;
abstract void read() throws IOException;
abstract boolean compare();

boolean nulltest(Object o1, Object o2)
{
    if(o1 == null && o2 != null) return false;
    if(o1 != null && o2 == null) return false;
    return true;
}

// Null handling comparators
boolean eq(Integer i, Integer j)
    {return (i == j || (i != null && i.compareTo(j)==0));}
boolean eq(Long i, Long j)
    {return (i == j || (i != null && i.compareTo(j)==0));}
boolean eq(Float i, Float j)
    {return (i == j || (i != null && i.compareTo(j)==0));}
boolean eq(Double i, Double j)
    {return (i == j || (i != null && i.compareTo(j)==0));}

boolean eq(int[] iv, int[] jv)
{
    if(iv == jv) return true;
    if(iv == null || jv == null) return false;
    if(iv.length != jv.length) return false;
    for(int i=0;i<iv.length;i++) {if(iv[i] != jv[i]) return false;}
    return true;
}

boolean eq(long[] iv, long[] jv)
{
    if(iv == jv) return true;
    if(iv == null || jv == null) return false;
    if(iv.length != jv.length) return false;
    for(int i=0;i<iv.length;i++) {if(iv[i] != jv[i]) return false;}
    return true;
}

boolean eq(double[] iv, double[] jv)
{
    if(iv == jv) return true;
    if(iv == null || jv == null) return false;
    if(iv.length != jv.length) return false;
    for(int i=0;i<iv.length;i++) {if(iv[i] != jv[i]) return false;}
    return true;
}

boolean eq(float[] iv, float[] jv)
{
    if(iv == jv) return true;
    if(iv == null || jv == null) return false;
    if(iv.length != jv.length) return false;
    for(int i=0;i<iv.length;i++) {if(iv[i] != jv[i]) return false;}
    return true;
}

boolean eq(boolean[] iv, boolean[] jv)
{
    if(iv == jv) return true;
    if(iv == null || jv == null) return false;
    if(iv.length != jv.length) return false;
    for(int i=0;i<iv.length;i++) {if(iv[i] != jv[i]) return false;}
    return true;
}

boolean eq(String[] iv, String[] jv)
{
    if(iv == jv) return true;
    if(iv == null || jv == null) return false;
    if(iv.length != jv.length) return false;
    for(int i=0;i<iv.length;i++) {if(!iv[i].equals(jv[i])) return false;}
    return true;
}

boolean eq(byte[] iv, byte[] jv)
{
    if(iv.length != jv.length) return false;
    for(int i=0;i<iv.length;i++) {if(iv[i] != jv[i]) return false;}
    return true;
}

boolean eq(byte[][] iv, byte[][] jv)
{
    if(iv == jv) return true;
    if(iv == null || jv == null) return false;
    if(iv.length != jv.length) return false;
    for(int i=0;i<iv.length;i++) {if(!eq(iv[i],jv[i])) return false;}
    return true;
}


boolean
test()
    throws IOException
{
    /* Write the input into the runtime buffer */
    write();

    /* Reverse direction */
    contents = ostream.toByteArray();
    istream = new ByteArrayInputStream(contents);
    io.setStream(istream);
    io.setAvail(contents.length);
    /* Reconstruct a PrimRequired instance from contents */
    read();

    /* Compare */
    boolean ok = compare();

    if(ok)
	System.err.println("*** PASS: "+testname);
    else
	System.err.println("*** FAIL: content mismatch: "+testname);
    return ok;
}

}//TestCommon

static class Testprim3 extends TestCommon
{
    PrimRepeated input, output;

int[] fill(int n, int[] base)
{base = new int[n]; for(int i=0;i<n;i++) base[i] = (n*100)+i; return base;}

long[] fill(int n, long[] base)
{base = new long[n]; for(int i=0;i<n;i++) base[i] = (n*100)+i; return base;}

float[] fill(int n, float[] base)
{base = new float[n]; for(int i=0;i<n;i++) base[i] = (n*100)+i; return base;}

double[] fill(int n, double[] base)
{base = new double[n]; for(int i=0;i<n;i++) base[i] = (n*100)+i; return base;}


public
Testprim3()
    throws IOException
{
    super("testprim3");
    int i,val;
    input = new PrimRepeated(rt);
    output = null;
    /* Fill in input; leave odd fields undefined */
    val = 0;
    input.f_int64 = fill(++val,input.f_int64);
    input.f_uint64 = fill(++val,input.f_uint64);
    input.f_sint64 = fill(++val,input.f_sint64);
    input.f_fixed64 = fill(++val,input.f_fixed64);
    input.f_sfixed64 = fill(++val,input.f_sfixed64);
    input.f_float = fill(++val,input.f_float);
    input.f_int32 = fill(++val,input.f_int32);
    input.f_uint32 = fill(++val,input.f_uint32);
    input.f_sint32 = fill(++val,input.f_sint32);
    input.f_fixed32 = fill(++val,input.f_fixed32);
    input.f_sfixed32 = fill(++val,input.f_sfixed32);
    input.f_double = fill(++val,input.f_double);
}

void write() throws IOException {input.write();}
void read() throws IOException {output = new PrimRepeated(rt).read();}

boolean
compare()
{
    if(!eq(input.f_int64,output.f_int64)) return false;
    if(!eq(input.f_uint64,output.f_uint64)) return false;
    if(!eq(input.f_sint64,output.f_sint64)) return false;
    if(!eq(input.f_fixed64,output.f_fixed64)) return false;
    if(!eq(input.f_sfixed64,output.f_sfixed64)) return false;
    if(!eq(input.f_float,output.f_float)) return false;
    if(!eq(input.f_int32,output.f_int32)) return false;
    if(!eq(input.f_uint32,output.f_uint32)) return false;
    if(!eq(input.f_sint32,output.f_sint32)) return false;
    if(!eq(input.f_fixed32,output.f_fixed32)) return false;
    if(!eq(input.f_sfixed32,output.f_sfixed32)) return false;
    if(!eq(input.f_double,output.f_double)) return false;

    return true;
}
} //Testprim3

}
