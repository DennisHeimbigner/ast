/*********************************************************************
 *   Copyright 2010, UCAR/Unidata
 *   See netcdf/COPYRIGHT file for copying and redistribution conditions.
 *   $Id$
 *   $Header$
 *********************************************************************/

// This code contains code taken from Google's protobuf implementation
// Google code copyright follows:
// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// http://code.google.com/p/protobuf/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


package unidata.ast.runtime;

import static unidata.ast.runtime.ASTRuntime.*;

/*
Procedures that are never called
directly by generated code
are placed in this class
as static methods.
*/

abstract public class Internal
{

/* IGNORE
static public int
jtypesize(int sort)
{
    switch(sort) {
    case Sort.Ast_double: return 8;
    case Sort.Ast_float: return 4;

    case Sort.Ast_enum:
    case Sort.Ast_bool:
    case Sort.Ast_int32:
    case Sort.Ast_sint32:
    case Sort.Ast_fixed32:
    case Sort.Ast_sfixed32:
    case Sort.Ast_uint32: return 4;

    case Sort.Ast_int64:
    case Sort.Ast_sint64:
    case Sort.Ast_fixed64:
    case Sort.Ast_sfixed64:
    case Sort.Ast_uint64: return 8;

    case Sort.Ast_string: return 8;
    case Sort.Ast_bytes: return 8;

    case Sort.Ast_message:return 8;

    default: assert(0);
    }
    return 0;
}
IGNORE*/
//////////////////////////////////////////////////



//////////////////////////////////////////////////


/* Return the number of bytes required to store the
   tag for the field, which includes 3 bits for
   the wire-type, and a single bit that denotes the end-of-tag.
*/
static public int
encode_tag(int wiretype, int fieldno, byte[] buffer)
{
    int key;
    int count;

    key = (wiretype | (fieldno << 3));
    /* convert key to varint */
    count = uint32_encode(key,buffer);
    return count;
}


/* Pack an unsigned 32-bit integer in base-128 encoding, and
   return the number of bytes needed: this will be 5 or
   less. Note that for java, unsigned ints are encoded
   as signed ints but with the bit pattern proper
   for unsigned ints.
*/

static public int
uint32_encode(int value, byte[] out)
{
    int count = 0;
    while (true) {
      if ((value & ~0x7F) == 0) {
	out[count++] = (byte)(value & 0x7f);
	break;
      } else {
	out[count++] = (byte)((value & 0x7f)|0x80);
        value >>>= 7;
      }
    }
    return count;
}

/* Pack a 32-bit signed integer, returning the number of
   bytes needed.  Negative numbers are packed as
   twos-complement 64-bit integers.
*/

static public int
int32_encode(int value, byte[] out)
{
  if(value >= 0)
    return uint32_encode(value,out);
  else
    return uint64_encode(value,out);
}

/*
Pack a 32-bit integer in zigwag encoding.
*/

static public int
sint32_encode(int value, byte[] out)
{
  return uint32_encode(zigzag32(value), out);
}

/* Pack a 64-bit unsigned integer that fits in a 64-bit uint,
   using base-128 encoding. */

static public int
int64_encode(long value, byte[] out)
{
    return uint64_encode(value,out);
}

static public int
uint64_encode(long value, byte[] out)
{
    int count = 0;
    while (true) {
      if ((value & ~0x7f) == 0) {
	out[count++] = (byte)(value & 0x7f);
	break;
      } else {
	out[count++] = (byte)((value & 0x7f)|0x80);
        value >>>= 7;
      }
    }
    return count;
}

/* Pack a 64-bit signed integer in zigzag encoding, return
   the size of the packed output.  (Max returned value is 10)
*/

static public int
sint64_encode(long value, byte[] out)
{
  return uint64_encode(zigzag64(value), out);
}

/* Pack a 32-bit value, little-endian.  Used for fixed32,
   sfixed32, float
*/

static public int
fixed32_encode(int value, byte[] out)
{
  // Protobuf apparently uses little endian
  out[0] = (byte)((value)&0xff);
  out[1] = (byte)((value>>>8)&0xff);
  out[2] = (byte)((value>>>16)&0xff);
  out[3] = (byte)((value>>>24)&0xff);
  return 4;
}

/* Pack a 64-bit fixed-length value.
   (Used for fixed64,sfixed64, double)
*/

/* Protobuf writes little endian */

static public int
fixed64_encode(long value, byte[] out)
{
  // Protobuf apparently uses little endian
  out[0] = (byte)((value)&0xff);
  out[1] = (byte)((value>>>8)&0xff);
  out[2] = (byte)((value>>>16)&0xff);
  out[3] = (byte)((value>>>24)&0xff);
  out[4] = (byte)((value>>>32)&0xff);
  out[5] = (byte)((value>>>40)&0xff);
  out[6] = (byte)((value>>>48)&0xff);
  out[7] = (byte)((value>>>56)&0xff);
  return 8;
}

/* Pack a boolean as 0 or 1, even though the bool_t
   can really assume any integer value.
*/

/* XXX: perhaps on some platforms "*out = !!value" would be
   a better impl, b/c that is idiotmatic c++ in some stl impls. */

static public int
bool_encode(boolean value, byte[] out)
{
  out[0] = (byte) (value ? 1 : 0);
  return 1;
}

static public int
float32_encode(float value, byte[] out)
{
   int i = Float.floatToIntBits(value);
   return fixed32_encode(i,out);
}

static public int
float64_encode(double value, byte[] out)
{
   long l = Double.doubleToLongBits(value);
   return fixed64_encode(l,out);
}


/* Decode a 32 bit varint */
static public int
uint32_decode(int len, byte[] data)
{
    int pos=0;
    byte tmp = data[pos++];
    if(tmp >= 0)
      return (int)tmp;
    int result = tmp & 0x7f;
    if ((tmp = data[pos++]) >= 0) {
      result |= tmp << 7;
    } else {
      result |= (tmp & 0x7f) << 7;
      if ((tmp = data[pos++]) >= 0) {
        result |= tmp << 14;
      } else {
        result |= (tmp & 0x7f) << 14;
        if ((tmp = data[pos++]) >= 0) {
          result |= tmp << 21;
        } else {
          result |= (tmp & 0x7f) << 21;
          result |= (tmp = data[pos++]) << 28;
	}
      }
    }
    return result;
}

static public int
int32_decode(int len, byte[] data)
{
    return uint32_decode(len, data);
}

/* Decode possibly 64-bit varint*/
static public long
uint64_decode(int len, byte[] data)
{
    int pos = 0;
    int shift = 0;
    long result = 0;
    while (shift < 64) {
      byte b = data[pos++];
      result |= (long)(b & 0x7F) << shift;
      if ((b & 0x80) == 0) break;
      shift += 7;
    }
    return result;
}

static public long
int64_decode(int len, byte[] data)
{
  return uint64_decode(len, data);
}

/* Decode arbitrary varint upto 64bit */
/*IGNORE
static public long
varint_decode(int len, byte[] valuebuffer, long[] countp)
{
  long shift, i;
  long rv = 0;
  long count = 0;

  for(count=0,shift=0,i=0;i<buflen;i++,shift+=7) {
    byte byt = valuebuffer[i];
    count++;
    rv = ((byt & 0x7f) << shift);
    if((byt & 0x80)==0) break;
  }
  countp[0] = count;
  return rv;
}
IGNORE*/

/* remember: protobuf writes little-endian */
static public int
fixed32_decode(int len, byte[] data)
{
  int rv = (
        (((int)data[0])&0xff)
      | (((int)data[1]&0xff) << 8)
      | (((int)data[2]&0xff) << 16)
      | (((int)data[3]&0xff) << 24)
      );
  return rv;
}

static public long
fixed64_decode(int len, byte[] data)
{
  long rv = 0;
      rv |= (((long)data[0])&0xff);
      rv |=(((long)data[1]&0xff) << 8);
      rv |=(((long)data[2]&0xff) << 16);
      rv |=(((long)data[3]&0xff) << 24);
      rv |=(((long)data[4]&0xff) << 32);
      rv |=(((long)data[5]&0xff) << 40);
      rv |=(((long)data[6]&0xff) << 48);
      rv |=(((long)data[7]&0xff) << 56);
  return rv;
}

static public boolean
bool_decode(int len, byte[] data)
{
  int i;
  boolean tf;

  tf = false;
  for(i = 0; i < len; i++) {
    if((data[i] & 0x7f) != 0) tf = true;
  }
  return tf;
}

static public double
float64_decode(int len, byte[] buffer)
{
   long ld = fixed64_decode(len, buffer);
   return Double.longBitsToDouble(ld);
}

static public float
float32_decode(int len, byte[] buffer)
{
   int i = fixed32_decode(len, buffer);
   return Float.intBitsToFloat(i);
}


/* return the zigzag-encoded 32-bit unsigned int from a 32-bit signed int */

static public int
zigzag32(int n)
{
  int zz = (n << 1) ^ (n >> 31);
  return zz;
}

/* return the zigzag-encoded 64-bit unsigned int from a 64-bit signed int */

static public long
zigzag64(long n)
{
    long zz = (n << 1) ^ (n >> 63);
    return zz;
}

static public int
unzigzag32(int n)
{
  int zz = (n >>> 1) ^ -(n & 1);
  return zz;
}

static public long
unzigzag64(long n)
{
  long zz = (n >>> 1) ^ -(n & 1);
  return zz;
}

static public int
getTagSize(int tag)
{
  return uint32_size(tag);
}

/* Return the number of bytes required to store
   a variable-length unsigned integer that fits in 32-bit uint
   in base-128 encoding. */

static public int
uint32_size(int value)
{
  if ((value & (0xffffffff <<  7)) == 0) return 1;
  if ((value & (0xffffffff << 14)) == 0) return 2;
  if ((value & (0xffffffff << 21)) == 0) return 3;
  if ((value & (0xffffffff << 28)) == 0) return 4;
  return 5;
}

/* Return the number of bytes required to store
   a variable-length signed integer that fits in 32-bit int
   in base-128 encoding. */
static public int
int32_size(int v)
{
  return uint32_size(v);
}

/* Return the number of bytes required to store
   a variable-length unsigned integer that fits in 64-bit uint
   in base-128 encoding. */
static public int
uint64_size(long value)
{
   if ((value & (0xffffffffffffffffL <<  7)) == 0) return 1;
   if ((value & (0xffffffffffffffffL << 14)) == 0) return 2;
   if ((value & (0xffffffffffffffffL << 21)) == 0) return 3;
   if ((value & (0xffffffffffffffffL << 28)) == 0) return 4;
   if ((value & (0xffffffffffffffffL << 35)) == 0) return 5;
   if ((value & (0xffffffffffffffffL << 42)) == 0) return 6;
   if ((value & (0xffffffffffffffffL << 49)) == 0) return 7;
   if ((value & (0xffffffffffffffffL << 56)) == 0) return 8;
   if ((value & (0xffffffffffffffffL << 63)) == 0) return 9;
   return 10;
}

/* Return the number of bytes required to store
   a variable-length unsigned integer that fits in 64-bit int
   in base-128 encoding.
*/
static public int
int64_size(long v)
{
  return uint64_size(v);
}

/* Return the number of bytes required to store
   a variable-length signed integer that fits in 32-bit int,
   converted to unsigned via the zig-zag algorithm,
   then packed using base-128 encoding. */

static public int
sint32_size(int v)
{
  return uint32_size(zigzag32(v));
}


/* Return the number of bytes required to store
   a variable-length signed integer that fits in 64-bit int,
   converted to unsigned via the zig-zag algorithm,
   then packed using base-128 encoding. */
static public int
sint64_size(long v)
{
  return uint64_size(zigzag64(v));
}


}
