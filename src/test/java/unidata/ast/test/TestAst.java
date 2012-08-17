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

package unidata.ast.test;

import unidata.ast.runtime.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static unidata.ast.test.Primtests.*;
import static unidata.ast.test.Msgtests.*;
import static unidata.ast.test.Enumtests.*;
import static unidata.ast.test.Bytetests.*;

public class TestAst
{

static public void
main(String[] argv)
{
    String scode = System.getProperty("encoding");
    ASTRuntime.Encoder encoding = ASTRuntime.Encoder.getEncoder(scode);
    if(encoding == null)
	encoding = ASTRuntime.Encoder.Protobuf;

    try {
        new Testprim1(encoding).test();
        new Testprim2(encoding).test();
        new Testprim3(encoding).test();
        new Testmsg1(encoding).test();
        new Testenum1(encoding).test();
        new Testbytes1(encoding).test();
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
ASTRuntime.Encoder encoding = null;

String testname = null;

TestCommon()
        throws IOException
{
    this(ASTRuntime.Encoder.Protobuf);
}

TestCommon(ASTRuntime.Encoder encoding)
        throws IOException
{
    this.testname = this.getClass().getName();
    int dollar = this.testname.lastIndexOf('$');
    if(dollar >= 0)
        this.testname = this.testname.substring(dollar+1);
    ostream = new ByteArrayOutputStream();
    istream = null;
    contents = null;
    io = new ByteIO(ostream);
    rt = new ASTRuntime(encoding,io);
}

abstract void write() throws IOException;
abstract void read() throws IOException;
abstract boolean compare();


// Null handling comparators
boolean eq(Integer i, Integer j)
    {return (i == j || (i != null && i.compareTo(j)==0));}
boolean eq(Long i, Long j)
    {return (i == j || (i != null && i.compareTo(j)==0));}
boolean eq(Float i, Float j)
    {return (i == j || (i != null && i.compareTo(j)==0));}
boolean eq(Double i, Double j)
    {return (i == j || (i != null && i.compareTo(j)==0));}
boolean eq(String i, String j)
    {return (i == j || (i != null && i.equals(j)));}

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

boolean eq(boolean[] iv, boolean[] jv)
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
eq(Msgtest.Submsg s1, Msgtest.Submsg s2)
{
    if(s1 == s2) return true;
    if(s1 == null || s2 == null) return false;
    return (s1.f_int32 == s2.f_int32);
}

boolean eq(Msgtest.Submsg[] iv, Msgtest.Submsg[] jv)
{
    if(iv == null || jv == null) return (iv == jv);
    if(iv.length != jv.length) return false;
    for(int i=0;i<iv.length;i++) {
        if(!eq(iv[i],jv[i]))
            return false;
    }
    return true;
}

boolean
eq(Enumtest.Testenum x, Enumtest.Testenum y)
{
    if(x == y) return true;
    if(x == null || y == null) return false;
    return (x.equals(y));
}

//////////////////////////////////////////////////

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

static class Testprim1 extends TestCommon
{
    PrimRequired input, output;

public
Testprim1(ASTRuntime.Encoder encoding)
        throws IOException
{
    super(encoding);
    input = new PrimRequired(rt);
    output = null;
    /* Fill in input */
    input.f_int32 = (int)1;
    input.f_int64 = (long)2;
    input.f_uint32 = (int)3;
    input.f_uint64 = (long)4;
    input.f_sint32 = (int)5;
    input.f_sint64 = (long)6;
    input.f_fixed32 = (int)7;
    input.f_fixed64 = (long)8;
    input.f_sfixed32 = (int)9;
    input.f_sfixed64 = (long)10;
    input.f_double = (double)11;
    input.f_float = (float)12;
}

void write() throws IOException {input.write();}
void read() throws IOException {output = new PrimRequired(rt).read();}

boolean
compare()
{
    if(input.f_int32 != output.f_int32) return false;
    if(input.f_int64 != output.f_int64) return false;
    if(input.f_uint32 != output.f_uint32) return false;
    if(input.f_uint64 != output.f_uint64) return false;
    if(input.f_sint32 != output.f_sint32) return false;
    if(input.f_sint64 != output.f_sint64) return false;
    if(input.f_fixed32 != output.f_fixed32) return false;
    if(input.f_fixed64 != output.f_fixed64) return false;
    if(input.f_sfixed32 != output.f_sfixed32) return false;
    if(input.f_sfixed64 != output.f_sfixed64) return false;
    if(input.f_double != output.f_double) return false;
    if(input.f_float != output.f_float) return false;
    return true;
}
} //Testprim1

static class Testprim2 extends TestCommon
{
    PrimOptional input, output, expected;

public
Testprim2(ASTRuntime.Encoder encoding)
    throws IOException
{
    super(encoding);
    output = null;
    input = new PrimOptional(rt,
			null,              //input.f_int32 
                        Long.valueOf(2),   //input.f_int64 
                        null,              //input.f_uint32 
                        Long.valueOf(4),   //input.f_uint64 
                        null,              //input.f_sint32 
                        Long.valueOf(6),   //input.f_sint64 
                        null,              //input.f_fixed32 
                        Long.valueOf(8),   //input.f_fixed64 
                        null,              //input.f_sfixed32 
                        Long.valueOf(10),  //input.f_sfixed64 
                        null,              //input.f_double 
                        Float.valueOf(12)  //input.f_float 
                        );
    // Because of defaulting, we need a separate object
    // defining the expected result.
    expected = new PrimOptional(rt,
			Integer.valueOf(0), //input.f_int32 
                        Long.valueOf(2),    //input.f_int64 
                        Integer.valueOf(0), //input.f_uint32 
                        Long.valueOf(4),    //input.f_uint64 
                        Integer.valueOf(0), //input.f_sint32 
                        Long.valueOf(6),    //input.f_sint64 
                        Integer.valueOf(0), //input.f_fixed32 
                        Long.valueOf(8),    //input.f_fixed64 
                        Integer.valueOf(0), //input.f_sfixed32 
                        Long.valueOf(10),   //input.f_sfixed64 
                        Double.valueOf(0),   //input.f_double 
                        Float.valueOf(12)   //input.f_float 
			);
}

void write() throws IOException {input.write();}
void read() throws IOException {output = new PrimOptional(rt).read();}

boolean
compare()
{
    if(!eq(expected.f_int32,output.f_int32)) return false;
    if(!eq(expected.f_int64,output.f_int64)) return false;
    if(!eq(expected.f_uint32,output.f_uint32)) return false;
    if(!eq(expected.f_uint64,output.f_uint64)) return false;
    if(!eq(expected.f_sint32,output.f_sint32)) return false;
    if(!eq(expected.f_sint64,output.f_sint64)) return false;
    if(!eq(expected.f_fixed32,output.f_fixed32)) return false;
    if(!eq(expected.f_fixed64,output.f_fixed64)) return false;
    if(!eq(expected.f_sfixed32,output.f_sfixed32)) return false;
    if(!eq(expected.f_sfixed64,output.f_sfixed64)) return false;
    if(!eq(expected.f_double,output.f_double)) return false;
    if(!eq(expected.f_float,output.f_float)) return false;
    return true;
}
} //Testprim2

static class Testprim3 extends TestCommon
{
    PrimRepeated input, output;
    int val = 0;

int[] fillint()
{
    int[] array = new int[++val];
    for(int i=0;i<val;i++) array[i] = (val<<8)+i;
    return array;
}

long[] filllong()
{
    long[] array = new long[++val];
    for(int i=0;i<val;i++) array[i] = (val<<8)+i;
    return array;
}

double[] filldouble()
{
    double[] array = new double[++val];
    for(int i=0;i<val;i++) array[i] = (val<<8)+i;
    return array;
}

float[] fillfloat()
{
    float[] array = new float[++val];
    for(int i=0;i<val;i++) array[i] = (val<<8)+i;
    return array;
}

public
Testprim3(ASTRuntime.Encoder encoding)
    throws IOException
{
    super(encoding);
    input = new PrimRepeated(rt);
    output = null;
    /* Fill in input; leave odd fields undefined */
    val = 0;
    input.f_int32 = fillint();
    input.f_int64 = filllong();
    input.f_uint32 = fillint();
    input.f_uint64 = filllong();
    input.f_sint32 = fillint();
    input.f_sint64 = filllong();
    input.f_fixed32 = fillint();
    input.f_fixed64 = filllong();
    input.f_sfixed32 = fillint();
    input.f_sfixed64 = filllong();
    input.f_double = filldouble();
    input.f_float = fillfloat();
}

void write() throws IOException {input.write();}
void read() throws IOException {output = new PrimRepeated(rt).read();}

boolean
compare()
{
    int i;

    if(!eq(input.f_int32,output.f_int32)) return false;
    if(!eq(input.f_int64,output.f_int64)) return false;
    if(!eq(input.f_uint32,output.f_uint32)) return false;
    if(!eq(input.f_uint64,output.f_uint64)) return false;
    if(!eq(input.f_sint32,output.f_sint32)) return false;
    if(!eq(input.f_sint64,output.f_sint64)) return false;
    if(!eq(input.f_fixed32,output.f_fixed32)) return false;
    if(!eq(input.f_fixed64,output.f_fixed64)) return false;
    if(!eq(input.f_sfixed32,output.f_sfixed32)) return false;
    if(!eq(input.f_sfixed64,output.f_sfixed64)) return false;
    if(!eq(input.f_double,output.f_double)) return false;
    if(!eq(input.f_float,output.f_float)) return false;

    return true;
}
} //Testprim3


static class Testmsg1 extends TestCommon
{
    Msgtest input, output;

Msgtest.Submsg
create_submsg(int val)
    throws IOException
{
    Msgtest.Submsg submsg = new Msgtest.Submsg(rt);
    submsg.f_int32 = val;
    return submsg;
}

public
Testmsg1(ASTRuntime.Encoder encoding)
    throws IOException
{
    super(encoding);
    input = new Msgtest(rt);
    output = null;
    // Fill in input
    input.rqmsg = create_submsg(17);
    input.opmsg = create_submsg(19);
    input.rpmsg = new Msgtest.Submsg[2];
    input.rpmsg[0] = create_submsg(23);
    input.rpmsg[1] = create_submsg(29);
}

void write() throws IOException {input.write();}
void read() throws IOException {
    output = new Msgtest(rt);
    output.read();
}

boolean
compare()
{
    if(!eq(input.rqmsg,output.rqmsg)) return false;
    if(!eq(input.opmsg,output.opmsg)) return false;
    if(!eq(input.rpmsg,output.rpmsg)) return false;
    return true;
}

} //Testmsg1


static class Testenum1 extends TestCommon
{
    Enumtest input, output;

public
Testenum1(ASTRuntime.Encoder encoding)
    throws IOException
{
    super(encoding);
    int i;
    input = new Enumtest(rt);
    output = null;
    // Fill in input
    input.renum = Enumtest.Testenum.ECON1;
    input.oenum = Enumtest.Testenum.ECON2;
    input.penum = new Enumtest.Testenum[]{Enumtest.Testenum.ECON3,Enumtest.Testenum.ECON4};
}

void write() throws IOException {input.write();}
void read() throws IOException {output = new Enumtest(rt).read();}

boolean
compare()
{
    int i;
    if(!eq(input.renum,output.renum)) return false;
    if(!eq(input.oenum,output.oenum)) return false;
    if(input.penum != null) {
        for(i=0;i<input.penum.length;i++)
	    if(!eq(input.penum[i],output.penum[i])) return false;
    }
    return true;
}

} //Testenum1

static class Testbytes1 extends TestCommon
{
    Bytetest input, output, expected;

static String hex = "0123456789abcdef";

static byte[]
byteconstant(String con)
{
    if(con.startsWith("0x")) con = con.substring(2);
    if(con.length() % 2 == 1) con = con + "0";
    con = con.toLowerCase();
    int blen = con.length()/2;
    byte[] bytes = new byte[blen];
    for(int i=0,j=0;i<con.length();i+=2,j++) {
	int d0 = hex.indexOf(con.charAt(i));
	int d1 = hex.indexOf(con.charAt(i+1));
	if(d0 < 0 || d1 < 0) return null;
	bytes[j] = (byte)((d0 << 4) | d1);
    }
    return bytes;
}

public
Testbytes1(ASTRuntime.Encoder encoding)
    throws IOException
{
    super(encoding);
    int i;
    input = new Bytetest(rt);
    output = null;
    // Fill in input
    input.rqstring = "";
    input.rqbytes = byteconstant("0xabc");
    input.opstring = null;
    input.opbytes = byteconstant("1234");
    input.rpstring = new String[]{"hello","","0123456789"};
    input.rpbytes = new byte[][] {byteconstant("135"),byteconstant("abc123")};

    expected = new Bytetest(rt);
    expected.rqstring = input.rqstring;
    expected.rqbytes = input.rqbytes;
    expected.opstring = "hello";
    expected.opbytes = input.opbytes;
    expected.rpstring = input.rpstring;
    expected.rpbytes = input.rpbytes;
}

void write() throws IOException {input.write();}
void read() throws IOException {output = new Bytetest(rt).read();}

boolean
compare()
{
    int i;
    if(!eq(expected.rqstring,output.rqstring)) return false;
    if(!eq(expected.rqbytes,output.rqbytes)) return false;
    if(!eq(expected.opstring,output.opstring)) return false;
    if(!eq(expected.opbytes,output.opbytes)) return false;
    if(!eq(expected.rpstring,output.rpstring)) return false;
    if(!eq(expected.rpbytes,output.rpbytes)) return false;
    return true;
}

} //Testbytes1

}



