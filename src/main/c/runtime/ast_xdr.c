/* --- xdr-c.c: public xdr c runtime implementation --- */
/*
 * Copyright 2008, Dave Benson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License
 * at http://www.apache.org/licenses/LICENSE-2.0 Unless
 * required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * Modified 1/20/2011 to support Unidata AST compiler.
 * Author: Dennis Heimbigner (dennis.heimbigner@ieee.org).
 */

#include "config.h"

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <rpc/xdr.h>

#include "ast_internal.h"

/* Define the maximum c-type size;
   should be max(sizeof(bytes_t),sizeof(uint64_t)
*/
#define MAXCTYPESIZE (sizeof(bytes_t)>sizeof(uint64_t)?sizeof(bytes_t):sizeof(uint64_t))

/* Map the primitives (Ast_sort) to the associated wiretype */
static int sort_wiretype_map[] = {
Ast_64bit,   /*Ast_double*/
Ast_32bit,   /*Ast_float*/
Ast_varint,  /*Ast_int32*/
Ast_varint,  /*Ast_int64*/
Ast_varint,  /*Ast_uint32*/
Ast_varint,  /*Ast_uint64*/
Ast_varint,  /*Ast_sint32*/
Ast_varint,  /*Ast_sint64*/
Ast_32bit,   /*Ast_fixed32*/
Ast_64bit,   /*Ast_fixed64*/
Ast_32bit,   /*Ast_sfixed32*/
Ast_64bit,   /*Ast_sfixed64*/
Ast_counted, /*Ast_string*/
Ast_counted, /*Ast_bytes*/
Ast_varint,  /*Ast_bool*/
Ast_varint,  /*Ast_enum*/
};

enum Sortsize { sizecount=0, size32=32, size64=64 };

/**************************************************/
/* Forwards */
/**************************************************/
static size_t uint32_encode(uint32_t value, byte_t* out);
static size_t int32_encode(int32_t value, byte_t* out);
static size_t sint32_encode(int32_t value, byte_t* out);
static size_t uint64_encode(uint64_t value, byte_t* out);
static size_t int64_encode(int64_t value, byte_t* out);
static size_t sint64_encode(int64_t value, byte_t* out);
static size_t fixed32_encode(uint32_t value, byte_t* out);
static size_t fixed64_encode(uint64_t value, byte_t* out);
static size_t boolean_encode(bool_t value, byte_t* out);
static size_t float32_encode(float value, byte_t* buffer);
static int float64_encode(double value, byte_t* buffer);
static uint32_t uint32_decode(const size_t len0, const byte_t* data);
static int32_t int32_decode(const size_t len, const byte_t* data);
static uint64_t uint64_decode(const size_t len, const byte_t* data);
static int64_t int64_decode(const size_t len, const byte_t* data);
static int32_t sint32_decode(const size_t, const byte_t* out);
static int64_t sint64_decode(const size_t, const byte_t* out);
static uint32_t fixed32_decode(const size_t,const byte_t* data);
static uint64_t fixed64_decode(const size_t,const byte_t* data);
static bool_t boolean_decode(const size_t len, const byte_t* data);
static float float32_decode(const size_t len, const byte_t* buffer);
static float float64_decode(const size_t len, const byte_t* buffer);
static ast_err readwirevalue(Ast_runtime* rt, const int wiretype, size_t* countp, uint8_t* buffer);
static ast_err readvarint(Ast_runtime* rt, size_t* countp);

static ast_err x_repeat_append(Ast_runtime* rt, const Ast_sort, const void*, void*);

/**************************************************/
/* Utilities */
/**************************************************/

static size_t
uint32_encode(uint32_t value, byte_t* out)
{
    return fixed32_encode(value,out);
}


static size_t
int32_encode(int32_t value, byte_t* out)
{
    return (int32_t)uint32_encode(value,out);
}

static size_t
sint32_encode(int32_t value, byte_t* out)
{
    return fixed32_encode(value,out);
}


static size_t
uint64_encode(uint64_t value, byte_t* out)
{
    return uint64_encode(value,out);
}

static size_t
int64_encode(int64_t value, byte_t* out)
{
    return uint64_encode((uint64_t)value,out);
}

static size_t
sint64_encode(int64_t value, byte_t* out)
{
  return uint64_encode(value, out);
}

/* Pack a 32-bit value, little-endian.
   Used for fixed32, sfixed32, float)
*/

static size_t
fixed32_encode(uint32_t value, byte_t* out)
{
  // xdr uses big endian
  out[3] = (byte_t)((value)&0xff);
  out[2] = (byte_t)((value>>8)&0xff);
  out[1] = (byte_t)((value>>16)&0xff);
  out[0] = (byte_t)((value>>24)&0xff);
  return 4;
}

static size_t
fixed64_encode(uint64_t value, byte_t* out)
{
  out[7] = (byte_t)((value)&0xff);
  out[6] = (byte_t)((value>>8)&0xff);
  out[5] = (byte_t)((value>>16)&0xff);
  out[4] = (byte_t)((value>>24)&0xff);
  out[3] = (byte_t)((value>>32)&0xff);
  out[2] = (byte_t)((value>>40)&0xff);
  out[1] = (byte_t)((value>>48)&0xff);
  out[0] = (byte_t)((value>>56)&0xff);
  return 8;
}

static size_t
boolean_encode(bool_t value, byte_t* out)
{
    return fixed32_encode((value ? 1 : 0),out);
}

static size_t
float32_encode(float value, byte_t* buffer)
{
   XDR xdr;
   xdrmem_create(&xdr,(char*)buffer,MAXTYPESIZE,XDR_ENCODE);
   xdr_float(&xdr,&value);
   return xdr_getpos(&xdr);
}

static int
float64_encode(double value, byte_t* buffer)
{
   XDR xdr;
   xdrmem_create(&xdr,(char*)buffer,MAXTYPESIZE,XDR_ENCODE);
   xdr_double(&xdr,&value);
   return xdr_getpos(&xdr);
}

static uint32_t
uint32_decode(const size_t len, const byte_t* data)
{
    return (uint32_t)fixed32_decode(len,data);
}

static int32_t
int32_decode(const size_t len, const byte_t* data)
{
  return (int32_t)uint32_decode(len,data);
}

static uint64_t
uint64_decode(const size_t len, const byte_t* data)
{
    return (uint64_t)fixed64_decode(len,data);
}

static int64_t
int64_decode(const size_t len, const byte_t* data)
{
  return (int64_t)uint64_decode(len, data);
}

static int32_t
sint32_decode(const size_t len, const byte_t* out)
{
    return (int32_t)fixed32_decode(len,out);
}

static int64_t
sint64_decode(const size_t len, const byte_t* out)
{
    return (int64_t)fixed64_decode(len,out);
}

static uint32_t
fixed32_decode(const size_t len, const byte_t* data)
{
  int rv = (
        (((int)data[3])&0xff)
      | (((int)data[2]&0xff) << 8)
      | (((int)data[1]&0xff) << 16)
      | (((int)data[0]&0xff) << 24)
      );
  return rv;
}

static uint64_t
fixed64_decode(const size_t len, const byte_t* data)
{
  uint64_t rv = 0;
      rv |= (((uint64_t)data[7])&0xff);
      rv |= (((uint64_t)data[6]&0xff) << 8);
      rv |= (((uint64_t)data[5]&0xff) << 16);
      rv |= (((uint64_t)data[4]&0xff) << 24);
      rv |= (((uint64_t)data[3]&0xff) << 32);
      rv |= (((uint64_t)data[2]&0xff) << 40);
      rv |= (((uint64_t)data[1]&0xff) << 48);
      rv |= (((uint64_t)data[0]&0xff) << 56);
  return rv;
}

static bool_t
boolean_decode(const size_t len, const byte_t* data)
{
    int i = fixed32_decode(len,data);
    return (i==0? false : true);
}

static float
float32_decode(const size_t len, const byte_t* buffer)
{
   XDR xdr;
   float value;
   xdrmem_create(&xdr,(char*)buffer,len,XDR_DECODE);
   xdr_float(&xdr,&value);
   return value;
}

static float
float64_decode(const size_t len, const byte_t* buffer)
{
   XDR xdr;
   double value;
   xdrmem_create(&xdr,(char*)buffer,len,XDR_DECODE);
   xdr_double(&xdr,&value);
   return value;
}

static ast_err
readwirevalue(Ast_runtime* rt, const int wiretype, size_t* countp, uint8_t* buffer)
{
    int i;
    ast_err status = AST_NOERR;
    size_t count = 0;
    switch (wiretype) {
    case Ast_varint:
        for(i=0;i<sizeof(buffer);i++) {
	    if(x_read(buffer, i, 1) != 1)
		ATHROW(status,done);
	    if((0x80 & buffer[i]) == 0) {
                count = i+1;
                break;
            }
        }
	if(count == 0)
	    AERR(status,AST_EVARINT,done);
	break;
    case Ast_32bit:
	count = 4;
        if(x_read(buffer, 0, count) != count)
	    ATHROW(status,done);
	break;
    case Ast_64bit:
	count = 8;
        if(x_read(buffer, 0, count) != count)
	    ATHROW(status,done);
	break;
    case Ast_counted: /* get the count */
        if((status = readwirevalue(rt, Ast_varint, &count, buffer) != AST_NOERR))
	    ATHROW(status,done);
	count = int32_decode(count,buffer);
	break;
    default:
	AERR(status,AST_EWIRETYPE,done);
    }
    if(countp != NULL)
	*countp = count;
	
done:
    return ACATCH(status);
}

static ast_err
readvarint(Ast_runtime* rt, size_t* countp)
{
    return readwiretype(rt,Ast_varint,countp);
}

/**************************************************/
/* Struct Ast_encoding Procedures
/**************************************************/
/* Given an unknown field, skip past it */

static ast_err
x_skip_field(Ast_runtime* rt, const int wiretype, const int fieldno)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t len;
    switch (wiretype) {
    case Ast_varint:
        status = readvarint(rt,&len);
        if(status != AST_NOERR) ATHROW(status,done);
	break;
    case Ast_32bit:
	len = 4;
        status = x_read(rt,buffer,0,len);
        if(status != AST_NOERR) ATHROW(status,done);
	break;
    case Ast_64bit:
	len = 8;
        status = x_read(rt,buffer,0,len);
        if(status != AST_NOERR) ATHROW(status,done);
	break;
    case Ast_counted:
        status = readvarint(rt,&len);
        if(status != AST_NOERR) ATHROW(status,done);
        /* get the count */
	len = (size_t)uint32_decode(len,buffer);	
	/* Now skip "count" bytes */
	while(len > 0) {
	    size_t n = (len > sizeof(buffer) ? sizeof(buffer) : len);
	    size_t actual = x_read(rt,buffer,0,n);
	    if(actual < n)
		AERR(status,AST_ESHORT,done);
	}
	break;
    default:
	AERR(status,AST_EWIRETYPE,done);
    }

done:
    return ACATCH(status);
}


/**************************************************/
/* Size functions */

static size_t
x_get_tagsize(Ast_runtime* rt, const Ast_sort sort, const int fieldno)
{
    size_t count;
    int wiretype;
    byte_t buffer[MAXTYPESIZE];

    wiretype = sort_wiretype_map[sort];
    count = encode_tag(rt,wiretype,fieldno,buffer);
    return count;
}


/* Return on the wire # of bytes for this value */ 
static size_t
x_get_size(Ast_runtime* rt, const Ast_sort sort, const void* valuep)
{
    size_t count = 0;
    switch (sort) {
    case Ast_enum: /* fall thru */
    case Ast_int32:
	count = int32_size(*(int32_t*)valuep);
	break;
    case Ast_int64:
	count = int64_size(*(int64_t*)valuep);
	break;
    case Ast_uint32:
	count = uint32_size(*(uint32_t*)valuep);
	break;
    case Ast_uint64:
	count = uint64_size(*(uint64_t*)valuep);
	break;
    case Ast_sint32:
	count = sint32_size(*(int32_t*)valuep);
	break;
    case Ast_sint64:
	count = sint64_size(*(int64_t*)valuep);
	break;
    case Ast_bool:
	count = 1;
	break;
    case Ast_fixed32:
	count = 4;
	break;
    case Ast_sfixed32:
	count = 4;
	break;
    case Ast_fixed64:
	count = 8;
	break;
    case Ast_sfixed64:
	count = 8;
	break;
    case Ast_float:
	count = 4;
	break;
    case Ast_double:
	count = 8;
	break;
    case Ast_string:
	/* string count is size for length counter + strlen(string) */
	count = 0;
	if(valuep != NULL) {
	    char* stringvalue = (char*)valuep;
	    uint32_t slen = strlen(stringvalue);
	    count = uint32_size(slen);
	    count += slen;
	}
	break;
    case Ast_bytes:
	count = 0;
	if(valuep != NULL) {
	    bytes_t* bytedata = (bytes_t*)valuep;
	    count = uint32_size(bytedata->nbytes);
	    count += bytedata->nbytes;
	}
	break;
    default:
	break;
    }
    return count;
}

static size_t
x_get_size_packed(Ast_runtime* rt, const Ast_sort sort, const void* valp)
{
    int i;
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    int size = 0;
    const repeat_t* repeater = (const repeat_t*)valp;
    size_t len = repeater->count;
    void* valuep = repeater->values;
    uint32_t* xp32;
    uint64_t* xp64;

    switch (sort) {
    case Ast_enum:
    case Ast_bool:
    case Ast_int32:
    case Ast_uint32:
    case Ast_sint32:
	xp32 = (uint32_t*)valp;
	for(i=0;i<len;i++)
	    size += uint32_size(xp32[i]);
	break;
    case Ast_int64:
    case Ast_uint64:
    case Ast_sint64:
	xp64 = (uint64_t*)valp;
	for(i=0;i<len;i++)
	    size += uint64_size(xp64[i]);
	break;
    case Ast_float:
    case Ast_fixed32:
    case Ast_sfixed32:
	size = 4*len;
	break;
    case Ast_double:
    case Ast_fixed64:
    case Ast_sfixed64:
	size = 8*len;
	break;
    default:
	AERR(status,AST_ESORT,done);
    };

done:
    if(status != AST_NOERR)
	return 0;
    return size;
}

static size_t
x_get_message_size(Ast_runtime* rt, const size_t size)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t msize = uint32_encode(size,buffer);
    return size+msize;
}

static size_t
x_get_tag_size(Ast_runtime* rt, const Ast_sort sort, const int fieldno)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    int wiretype = sort_wiretype_map[sort];
    size_t count = encode_tag(wiretype,fieldno, buffer);
    return count;
}

/**************************************************/

static ast_err
x_write_tag(Ast_runtime* rt, const Ast_sort sort, const int fieldno)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t count;

    count = encode_tag(rt,sort,fieldno);
    /* Write it out */
    if(x_write(rt,buffer,0,count) != count)
	AERR(status,AST_EIO,done);

done:
    return ACATCH(status);
}

/* Read the tag and return the relevant info */
static ast_err
x_read_tag(Ast_runtime* rt, int* wiretypep, int* fieldnop)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t count;
    uint32_t key, wiretype, fieldno;

    /* Extract the wiretype + index */
    status = readvarint(rt,&count);
    if(status != AST_NOERR) ATHROW(status,done);

    /* convert from varint */
    key = uint32_decode(count,buffer);

    /* Extract the wiretype and fieldno */
    wiretype = (key & 0x7);
    fieldno = (key >> 3);

    /* return the fieldno and wiretype */
    if(fieldnop) *fieldnop = fieldno;
    if(wiretypep) *wiretypep = wiretype;

done:
    return ACATCH(status);
}

static ast_err
x_write_size(Ast_runtime* rt, const size_t size)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t len;

    /* write size as varint */
    len = uint32_encode(size, buffer);
    /* Write it out */
    if(x_write(rt,buffer,0,len) != len)
	AERR(status,AST_EIO,done);
done:
    return ACATCH(status);
}

static size_t
x_read_size(Ast_runtime* rt)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t count;
    size_t size;

    /* Extract the size */
    status = readvarint(rt,&count);
    if(status != AST_NOERR) ATHROW(status,done);
    /* convert from varint */
    size = uint32_decode(count,buffer);
done:
    return (status == AST_NOERR ? size : 0);
}

/**************************************************/

static ast_err
x_write_primitive(Ast_runtime* rt, const Ast_sort sort, const void* valuep)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t count;

    /* Write the data in proper wiretype format using the sort */
    switch (sort) {
    case Ast_int32:
	count = int32_encode(*(int32_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_int64:
	count = int64_encode(*(int64_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_uint32:
	count = uint32_encode(*(uint32_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_uint64:
	count = uint64_encode(*(uint64_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_sint32:
	count = sint32_encode(*(int32_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_sint64:
	count = sint64_encode(*(int64_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_bool:
	count = boolean_encode(*(bool_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_enum:
	count = int32_encode(*(int32_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_fixed32: /* fall thru */
    case Ast_sfixed32: /* fall thru */
    case Ast_float:
	count = fixed32_encode(*(uint32_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_fixed64:  /* fall thru */
    case Ast_sfixed64: /* fall thru */
    case Ast_double:
	count = fixed64_encode(*(uint64_t*)valuep,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_string: {
	char* stringvalue = *(char**)valuep;
	size_t len = strlen(stringvalue);
	count = uint32_encode(len,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	if(x_write(rt,stringvalue,0,len) != count) AERR(status,AST_EIO,done);
	} break;
    case Ast_bytes: {
	bytes_t* bytevalue = (bytes_t*)valuep;		
	count = uint32_encode(bytevalue->nbytes,buffer);
	if(x_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	count = bytevalue->nbytes;
	if(x_write(rt,bytevalue->bytes,0,count) != count) AERR(status,AST_EIO,done);
	} break;
    default:
	ATHROW(status,done);
	break;
    }
done:
    return ACATCH(status);
}

static ast_err
x_write_primitive_packed(Ast_runtime* rt, const Ast_sort sort, const void* valuep)
{
    ast_err status = AST_NOERR;
    int i;
    byte_t buffer[MAXTYPESIZE];
    size_t count,size;
    const repeat_t* repeater = (const repeat_t*)valuep;
    char* p;
    size_t typesize = ctypesize(rt,sort);

    if(repeater->count == 0) goto done;

    /* Compute the size of what is to be written */
    for(p=repeater->values,size=0,i=0;i<repeater->count;i++,p+=typesize) {
	size += ast_get_size(rt,sort,(void*)p);
    }
    /* convert size to varint and write it out */
    count = uint32_encode(size,buffer);
    status = ast_write(rt,buffer,count);
    if(status != AST_NOERR) ATHROW(status,done);
    /* Now write each value */
    for(p=repeater->values,i=0;i<repeater->count;i++,p+=typesize) {
	status = ast_write_primitive_data(rt,sort,(void*)p);
        if(status != AST_NOERR) goto done;
    }

done:
    return ACATCH(status);
}


static ast_err
x_read_primitive(Ast_runtime* rt, const Ast_sort sort, void* valuep)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t count,len;
    uint32_t wiretype;

    /* compute the wiretype from the sort */
    wiretype = sort_wiretype_map[sort];

    /* Based on the wiretype, extract the proper number of bytes */
    switch (wiretype) {
    case Ast_varint:
        status = readvarint(rt,&len);
        if(status != AST_NOERR) ATHROW(status,done);
	break;
    case Ast_32bit:
	len = 4;
        if(x_read(rt,buffer,0,len) != len) AERR(status,AST_EOF,done);
	break;
    case Ast_64bit:
	len = 8;
        if(x_read(rt,buffer,0,len) != len) AERR(status,AST_EOF,done);
	break;
    case Ast_counted: /* get the count */
        status = readvarint(rt,&len);
        if(status != AST_NOERR) ATHROW(status,done);
	count = (size_t)uint64_decode(len,buffer);	
	break;
    default: ATHROW(status,done);
    }

    /* Now extract the value */
    switch (sort) {
    case Ast_enum: /* fall thru */
    case Ast_int32:
	if(valuep)
	*((int32_t*)valuep) = int32_decode(len,buffer);
	break;
    case Ast_int64:
	if(valuep)
	*((int64_t*)valuep) = int64_decode(len,buffer);
	break;
    case Ast_uint32:
	if(valuep)
	*((uint32_t*)valuep) = uint32_decode(len,buffer);
	break;
    case Ast_uint64:
	if(valuep)
	*((uint64_t*)valuep) = uint64_decode(len,buffer);
	break;
    case Ast_sint32:
	if(valuep)
	*((int32_t*)valuep) = unzigzag32(uint32_decode(len,buffer));
	break;
    case Ast_sint64:
	if(valuep)
	*((int64_t*)valuep) = unzigzag64(uint64_decode(len,buffer));
	break;
    case Ast_bool:
	if(valuep)
	*((bool_t*)valuep) = boolean_decode(len,buffer);
	break;
    case Ast_fixed32:
	if(valuep)
	*((int32_t*)valuep) = fixed32_decode(len,buffer);
	break;
    case Ast_sfixed32:
	if(valuep)
	*((int32_t*)valuep) = fixed32_decode(len,buffer);
	break;
    case Ast_fixed64:
	if(valuep)
	*((uint64_t*)valuep) = fixed64_decode(len,buffer);
	break;
    case Ast_sfixed64:
	if(valuep)
	*((int64_t*)valuep) = fixed64_decode(len,buffer);
	break;
    case Ast_float: {
	uint32_t value = fixed32_decode(len,buffer);
	if(valuep)
	memcpy(valuep,&value,sizeof(float));
	} break;
    case Ast_double: {
	uint64_t value = fixed64_decode(len,buffer);
	if(valuep)
	memcpy(valuep,&value,sizeof(double));
	} break;
    case Ast_string: {
	/* Count already holds the length of the string */
	char* stringvalue = (char*)ast_alloc(rt,count+1);
	if(stringvalue == NULL) AERR(status,AST_ENOMEM,done);
	if(x_read(rt,(uint8_t*)stringvalue,0,count) != count)
	    AERR(status,AST_EOF,done);
	stringvalue[count] = '\0';
	if(valuep)
	    *((char**)valuep) = stringvalue;
	else
	    ast_free(rt,(void*)stringvalue);
	} break;
    case Ast_bytes: {
	/* Count already holds the length of the byte string */
	byte_t* bytestring = (byte_t*)ast_alloc(rt,count);
	if(bytestring == NULL) AERR(status,AST_ENOMEM,done);
	if(x_read(rt,bytestring,0,count) != count) AERR(status,AST_EOF,done);
	if(valuep) {
	    ((bytes_t*)valuep)->nbytes = count;
	    ((bytes_t*)valuep)->bytes = bytestring;
	} else
	    ast_free(rt,(void*)bytestring);
	} break;
    default:
	AERR(status,AST_ESORT,done);
	break;
    }

done:
    return ACATCH(status);
}

static ast_err
x_read_primitive_packed(Ast_runtime* rt, const Ast_sort sort, void* valuep)
{
    int i;
    ast_err status = AST_NOERR;
    size_t count,len;
    repeat_t* repeater = (repeat_t*)valuep;
    size_t typesize = ast_ctypesize(rt,sort);
    size_t ninstances;
    char* p = NULL;
    byte_t buffer[MAXTYPESIZE];
    byte_t cbuffer[MAXCTYPESIZE];

    if(valuep == NULL) AERR(status,AST_EARG,done);

    /* wire type should always be Ast_counted */
    status = readwirevalue(rt,Ast_varint,&count,buffer);
    if(status != AST_NOERR) ATHROW(status,done);
    len = uint32_decode(count,buffer);

    if(isfixedsize(sort))
        ninstances = len/typesize;
    else
	ninstances = 0;

    /* mark the runtime */
    status = ast_mark(rt,len);
    if(status != AST_NOERR) ATHROW(status,done);

    /* set up the repeater */
    status = ast_repeat_setsize(rt,repeater,ninstances,typesize);
    if(status != AST_NOERR) ATHROW(status,done);

    if(isfixedsizesort(sort)) {
	char* p = (char*)repeater->values;
	for(i=0;i<ninstances;i++) {
	    status = ast_read_primitive_data(rt,sort,buffer);
            if(status != AST_NOERR) {ATHROW(status,done);}
	    memcpy(p,buffer,typesize);
	    p += typesize;
	}
    } else if(sort == Ast_string) {
	for(;;) {
	    status = ast_read_primitive_data(rt,sort,buffer);
            if(status != AST_EOF) break;
            if(status != AST_NOERR) {ATHROW(status,done);}
	    status = x_repeat_append(rt,sort,(void*)buffer,(void*)repeater);
	}
    } else if(sort == Ast_bytes) {
	for(;;) {
	    status = ast_read_primitive_data(rt,sort,buffer);
            if(status != AST_EOF) break;
            if(status != AST_NOERR) {ATHROW(status,done);}
	    status = x_repeat_append(rt,sort,(void*)buffer,(void*)repeater);
	}
    } else
	AERR(status,AST_ESORT,done);
    status = ast_unmark(rt);
    if(status != AST_NOERR) {ATHROW(status,done);}

done:
    return ACATCH(status);
}

static ast_err
repeat_extend(Ast_runtime* rt, repeat_t* list, size_t unitsize, size_t incr, void** where)
{
    ast_err status = AST_NOERR;
    ptrdiff_t offset = (ptrdiff_t)(list->count*unitsize);
    size_t newcount = (list->count + incr);
    void* newvalues = ast_realloc(rt,list->values,newcount*unitsize);
    if(newvalues == NULL) AERR(status,AST_ENOMEM,done);
    list->values = newvalues;
    list->count = newcount;
    if(where) *where = list->values+offset;
done:
    return ACATCH(status);    
}

static ast_err
x_repeat_append(Ast_runtime* rt, const Ast_sort sort, const void* newval, void* list0)
{
    ast_err status = AST_NOERR;
    repeat_t* list = (repeat_t*)list0;
    size_t typesize = ast_ctypesize(rt,sort);
    void* where;
    if(typesize == 0)
	AERR(status,AST_ESORT,done);
    status = repeat_extend(rt, list, typesize, 1, where);
    if(status != AST_NOERR) {ATHROW(status,done);}	
    memcpy(where,newval,typesize);		
done:
    return ACATCH(status);    
}

x_reclaim_string(Ast_runtime* rt, char* value)
{
    ast_free(rt,value);
}

x_reclaim_bytes(Ast_runtime* rt, bytes_t* value)
{
    ast_free(rt,value->bytes);
    ast_free(rt,value);
}

Ast_encoding encoding = {

x_skip_field, /*(Ast_runtime*, const int wiretype, const int fieldno);*/

x_get_size, /*(Ast_runtime*, const Ast_sort, const void* valp);*/
x_get_size_packed, /*(Ast_runtime*, const Ast_sort, const void* valp, size_t* sizep);*/
x_get_message_size, /*(Ast_runtime*,const size_t size);*/
x_get_tag_size, /*(Ast_runtime*, const Ast_sort, const int fieldno);*/

x_write_tag, /*(Ast_runtime*, const Ast_sort, const int fieldno) ;*/
x_read_tag, /*(Ast_runtime*, int* wiretype, int* fieldno);*/

x_write_size, /*(Ast_runtime*, const size_t size);*/
x_read_size, /*(Ast_runtime*);*/

x_write_primitive, /*(Ast_runtime*, const Ast_sort, const void* valp);*/
x_write_primitive_packed, /*(Ast_runtime*, const Ast_sort, const void* valp);*/

x_read_primitive, /*(Ast_runtime*, const Ast_sort, void* valp);*/
x_read_primitive_packed, /*(Ast_runtime*, const Ast_sort, void* valp);*/

x_repeat_append, /*(Ast_runtime*, const Ast_sort, const void* newval, void* list);*/

x_reclaim_string, /*(Ast_runtime*, char* value);*/
x_reclaim_bytes, /*(Ast_runtime*, bytes_t* value);*/

}; /* xdr_encoding */

Ast_encoding* xdr_encoding = &encoding;
