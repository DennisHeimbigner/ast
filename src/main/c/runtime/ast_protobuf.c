/* --- protobuf-c.c: public protobuf c runtime implementation --- */
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

/* max length of a 32 varint Must hold at least lub(32/7) = 5 */
#define MAXVARINTSIZE32 = 10;

/* max length of a 64 varint Must hold at least lub(64/7) = 10 */
#define MAXVARINTSIZE64 = 10;

#define MAXVARINTSIZE MAXVARINTSIZE64

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
static ast_err pb_repeat_append(Ast_runtime* rt, const Ast_sort, const void*, void*);

/**************************************************/
/* Utilities */
/**************************************************/

/* return the zigzag-encoded 32-bit unsigned int from a 32-bit signed int */

static uint32_t
zigzag32(int32_t v)
{
  if(v < 0)
    return((uint32_t)(-v))*  2 - 1;
  else
    return v*  2;
}

/* return the zigzag-encoded 64-bit unsigned int from a 64-bit signed int */

static uint64_t
zigzag64(int64_t v)
{
  if(v < 0)
    return((uint64_t)(-v))*  2 - 1;
  else
    return v*  2;
}

static int32_t
unzigzag32(uint32_t v)
{
  if(v&1)
    return -(v>>1) - 1;
  else
    return v>>1;
}

static int64_t
unzigzag64(uint64_t v)
{
  if(v&1)
    return -(v>>1) - 1;
  else
    return v>>1;
}

/* Pack an unsigned 32-bit integer in base-128 encoding, and
   return the number of bytes needed: this will be 5 or less.
*/

static size_t
uint32_encode(uint32_t value, byte_t* out)
{
  unsigned rv = 0;
  if(value >= 0x80)
    {
      out[rv++] = value | 0x80;
      value >>= 7;
      if(value >= 0x80)
        {
          out[rv++] = value | 0x80;
          value >>= 7;
          if(value >= 0x80)
            {
              out[rv++] = value | 0x80;
              value >>= 7;
              if(value >= 0x80)
                {
                  out[rv++] = value | 0x80;
                  value >>= 7;
                }
            }
        }
    }
  /* assert: value<128*/
  out[rv++] = value;
  return rv;
}

/* Pack a 32-bit signed integer, returning the number of
   bytes needed.  Negative numbers are packed as
   twos-complement 64-bit integers.
*/

static size_t
int32_encode(int32_t value, byte_t* out)
{
  if(value < 0)
    {
      out[0] = value | 0x80;
      out[1] =(value>>7) | 0x80;
      out[2] =(value>>14) | 0x80;
      out[3] =(value>>21) | 0x80;
      out[4] =(value>>28) | 0x80;
      out[5] = out[6] = out[7] = out[8] = 0xff;
      out[9] = 0x01;
      return 10;
    }
  else
    return uint32_encode(value, out);
}

/* Pack a 32-bit integer in zigwag encoding.*/

static size_t
sint32_encode(int32_t value, byte_t* out)
{
  return uint32_encode(zigzag32(value), out);
}

/* Pack a 64-bit unsigned integer that fits in a 64-bit uint,
   using base-128 encoding.
*/

static size_t
uint64_encode(uint64_t value, byte_t* out)
{
  uint32_t hi = value>>32;
  uint32_t lo = value;
  unsigned rv;
  if(hi == 0)
    return uint32_encode((uint32_t)lo, out);
  out[0] =(lo) | 0x80;
  out[1] =(lo>>7) | 0x80;
  out[2] =(lo>>14) | 0x80;
  out[3] =(lo>>21) | 0x80;
  if(hi < 8)
    {
      out[4] =(hi<<4) |(lo>>28);
      return 5;
    }
  else
    {
      out[4] =((hi&7)<<4) |(lo>>28) | 0x80;
      hi >>= 3;
    }
  rv = 5;
  while(hi >= 128)
    {
      out[rv++] = hi | 0x80;
      hi >>= 7;
    }
  out[rv++] = hi;
  return rv;
}

static size_t
int64_encode(int64_t value, byte_t* out)
{
    return (int64_t)uint64_encode((uint64_t)value,out);
}

/* Pack a 64-bit signed integer in zigzag encoding,
   return the size of the packed output.
  (Max returned value is 10)
*/

static size_t
sint64_encode(int64_t value, byte_t* out)
{
  return uint64_encode(zigzag64(value), out);
}

/* Pack a 32-bit value, little-endian.
   Used for fixed32, sfixed32, float)
*/

static size_t
fixed32_encode(uint32_t value, byte_t* out)
{
#ifdef LITTLE_ENDIAN
  memcpy(out, &value, 4);
#else
  out[0] = value;
  out[1] = value>>8;
  out[2] = value>>16;
  out[3] = value>>24;
#endif
  return 4;
}

/* Pack a 64-bit fixed-length value.
  (Used for fixed64, sfixed64, double)
*/

static size_t
fixed64_encode(uint64_t value, byte_t* out)
{
#ifdef LITTLE_ENDIAN
  memcpy(out, &value, 8);
#else
  fixed32_encode(value, out);
  fixed32_encode(value>>32, out+4);
#endif
  return 8;
}

/* Pack a boolean as 0 or 1, even though the bool_t
   can really assume any integer value.
*/

static size_t
boolean_encode(bool_t value, byte_t* out)
{
  *out = value ? 1 : 0;
  return 1;
}

/* Decode a 32 bit varint */

static uint32_t
uint32_decode(const size_t len0, const byte_t* data)
{
  uint32_t rv;
  size_t len = len0;

  if(len > 5) len = 5;
  rv = data[0] & 0x7f;
  if(len > 1) {
      rv |=((data[1] & 0x7f) << 7);
      if(len > 2) {
          rv |=((data[2] & 0x7f) << 14);
          if(len > 3) {
              rv |=((data[3] & 0x7f) << 21);
              if(len > 4)
                rv |=(data[4] << 28);
            }
        }
  }
  return rv;
}

static int32_t
int32_decode(const size_t len, const byte_t* data)
{
  return (int32_t)uint32_decode(len,data);
}

/* Decode possibly 64-bit varint*/
static uint64_t
uint64_decode(const size_t len, const byte_t* data)
{
  unsigned shift, i;
  uint64_t rv;

  if(len < 5) {
    rv = uint32_decode(len, data);
  } else {
    rv =((data[0] & 0x7f))
              |((data[1] & 0x7f)<<7)
              |((data[2] & 0x7f)<<14)
              |((data[3] & 0x7f)<<21);
    shift = 28;
    for(i = 4; i < len; i++) {
      rv |=(((uint64_t)(data[i]&0x7f)) << shift);
      shift += 7;
    }
  }
  return rv;
}

static int64_t
int64_decode(const size_t len, const byte_t* data)
{
  return (int64_t)uint64_decode(len, data);
}

/* Decode arbitrary varint upto 64bit */

static uint64_t
varint_decode(const size_t buflen, const byte_t* buffer, size_t* countp)
{
  unsigned shift, i;
  uint64_t rv = 0;
  size_t count = 0;

  if(buflen == 0 || buffer == NULL) {goto done;}

  for(count=0,shift=0,i=0;i<buflen;i++,shift+=7) {
    byte_t byte = buffer[i];
    count++;
    rv |= ((byte & 0x7f) << shift);
    if((byte & 0x80)==0) break;
  }

done:
  if(countp)* countp = count;    
  return rv;
}

static uint32_t
fixed32_decode(const byte_t* data)
{
  uint32_t rv;
#ifdef LITTLE_ENDIAN
   memcpy(&rv,data,4);
#else
  rv = (data[0] |(data[1] << 8) |(data[2] << 16) |(data[3] << 24));
#endif
  return rv;
}

static uint64_t
fixed64_decode(const byte_t* data)
{
  uint64_t rv;

#ifdef LITTLE_ENDIAN
  memcpy(&rv,data,8);
#else
  rv = fixed32_decode(data);
  rv2 = fixed32_decode(data+4);
  rv = (rv | (rv2 <<32));
#endif
  return rv;
}

static bool_t
boolean_decode(const size_t len, const byte_t* data)
{
  int i;
  bool_t tf;

  tf = 0;
  for(i = 0; i < len; i++) {
    if(data[i] & 0x7f) tf = 1;
  }
  return tf;
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
	    if(pb_read(buffer, i, 1) != 1)
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
        if(pb_read(buffer, 0, count) != count)
	    ATHROW(status,done);
	break;
    case Ast_64bit:
	count = 8;
        if(pb_read(buffer, 0, count) != count)
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
pb_skip_field(Ast_runtime* rt, const int wiretype, const int fieldno)
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
        status = pb_read(rt,buffer,0,len);
        if(status != AST_NOERR) ATHROW(status,done);
	break;
    case Ast_64bit:
	len = 8;
        status = pb_read(rt,buffer,0,len);
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
	    size_t actual = pb_read(rt,buffer,0,n);
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
pb_get_tagsize(Ast_runtime* rt, const Ast_sort sort, const int fieldno)
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
pb_get_size(Ast_runtime* rt, const Ast_sort sort, const void* valuep)
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
pb_get_size_packed(Ast_runtime* rt, const Ast_sort sort, const void* valp)
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
pb_get_message_size(Ast_runtime* rt, const size_t size)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t msize = uint32_encode(size,buffer);
    return size+msize;
}

static size_t
pb_get_tag_size(Ast_runtime* rt, const Ast_sort sort, const int fieldno)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    int wiretype = sort_wiretype_map[sort];
    size_t count = encode_tag(wiretype,fieldno, buffer);
    return count;
}

/**************************************************/

static ast_err
pb_write_tag(Ast_runtime* rt, const Ast_sort sort, const int fieldno)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t count;

    count = encode_tag(rt,sort,fieldno);
    /* Write it out */
    if(pb_write(rt,buffer,0,count) != count)
	AERR(status,AST_EIO,done);

done:
    return ACATCH(status);
}

/* Read the tag and return the relevant info */
static ast_err
pb_read_tag(Ast_runtime* rt, int* wiretypep, int* fieldnop)
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
pb_write_size(Ast_runtime* rt, const size_t size)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t len;

    /* write size as varint */
    len = uint32_encode(size, buffer);
    /* Write it out */
    if(pb_write(rt,buffer,0,len) != len)
	AERR(status,AST_EIO,done);
done:
    return ACATCH(status);
}

static size_t
pb_read_size(Ast_runtime* rt)
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
pb_write_primitive(Ast_runtime* rt, const Ast_sort sort, const void* valuep)
{
    ast_err status = AST_NOERR;
    byte_t buffer[MAXTYPESIZE];
    size_t count;

    /* Write the data in proper wiretype format using the sort */
    switch (sort) {
    case Ast_int32:
	count = int32_encode(*(int32_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_int64:
	count = int64_encode(*(int64_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_uint32:
	count = uint32_encode(*(uint32_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_uint64:
	count = uint64_encode(*(uint64_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_sint32:
	count = sint32_encode(*(int32_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_sint64:
	count = sint64_encode(*(int64_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_bool:
	count = boolean_encode(*(bool_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_enum:
	count = int32_encode(*(int32_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_fixed32: /* fall thru */
    case Ast_sfixed32: /* fall thru */
    case Ast_float:
	count = fixed32_encode(*(uint32_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_fixed64:  /* fall thru */
    case Ast_sfixed64: /* fall thru */
    case Ast_double:
	count = fixed64_encode(*(uint64_t*)valuep,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	break;
    case Ast_string: {
	char* stringvalue = *(char**)valuep;
	size_t len = strlen(stringvalue);
	count = uint32_encode(len,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	if(pb_write(rt,stringvalue,0,len) != count) AERR(status,AST_EIO,done);
	} break;
    case Ast_bytes: {
	bytes_t* bytevalue = (bytes_t*)valuep;		
	count = uint32_encode(bytevalue->nbytes,buffer);
	if(pb_write(rt,buffer,0,count) != count) AERR(status,AST_EIO,done);
	count = bytevalue->nbytes;
	if(pb_write(rt,bytevalue->bytes,0,count) != count) AERR(status,AST_EIO,done);
	} break;
    default:
	ATHROW(status,done);
	break;
    }
done:
    return ACATCH(status);
}

static ast_err
pb_write_primitive_packed(Ast_runtime* rt, const Ast_sort sort, const void* valuep)
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
    if(ast_write(rt,buffer,count) != count) {status = AST_EIO; goto done;}
    /* Now write each value */
    for(p=repeater->values,i=0;i<repeater->count;i++,p+=typesize) {
	status = ast_write_primitive_data(rt,sort,(void*)p);
        if(status != AST_NOERR) goto done;
    }

done:
    return ACATCH(status);
}


static ast_err
pb_read_primitive(Ast_runtime* rt, const Ast_sort sort, void* valuep)
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
        if(pb_read(rt,buffer,0,len) != len) AERR(status,AST_EOF,done);
	break;
    case Ast_64bit:
	len = 8;
        if(pb_read(rt,buffer,0,len) != len) AERR(status,AST_EOF,done);
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
	*((uint32_t*)valuep) = fixed32_decode(buffer);
	break;
    case Ast_sfixed32:
	if(valuep)
	*((int32_t*)valuep) = fixed32_decode(buffer);
	break;
    case Ast_fixed64:
	if(valuep)
	*((uint64_t*)valuep) = fixed64_decode(buffer);
	break;
    case Ast_sfixed64:
	if(valuep)
	*((int64_t*)valuep) = fixed64_decode(buffer);
	break;
    case Ast_float: {
	uint32_t value = fixed32_decode(buffer);
	if(valuep)
	memcpy(valuep,&value,sizeof(float));
	} break;
    case Ast_double: {
	uint64_t value = fixed64_decode(buffer);
	if(valuep)
	memcpy(valuep,&value,sizeof(double));
	} break;
    case Ast_string: {
	/* Count already holds the length of the string */
	char* stringvalue = (char*)ast_alloc(rt,count+1);
	if(stringvalue == NULL) AERR(status,AST_ENOMEM,done);
	if(pb_read(rt,(uint8_t*)stringvalue,0,count) != count)
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
	if(pb_read(rt,bytestring,0,count) != count) AERR(status,AST_EOF,done);
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
pb_read_primitive_packed(Ast_runtime* rt, const Ast_sort sort, void* valuep)
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
	    status = pb_repeat_append(rt,sort,(void*)buffer,repeater);
	}
    } else if(sort == Ast_bytes) {
	for(;;) {
	    status = ast_read_primitive_data(rt,sort,buffer);
            if(status != AST_EOF) break;
            if(status != AST_NOERR) {ATHROW(status,done);}
	    status = pb_repeat_append(rt,sort,(void*)buffer,repeater);
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
pb_repeat_append(Ast_runtime* rt, const Ast_sort sort, const void* newval, void* list0)
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

static void
pb_reclaim_string(Ast_runtime* rt, char* value)
{
    ast_free(rt,value);
}

static void
pb_reclaim_bytes(Ast_runtime* rt, bytes_t* value)
{
    ast_free(rt,value->bytes);
    ast_free(rt,value);
}

static Ast_encoding protobuf_static_encoding =
{

pb_skip_field, /*(Ast_runtime*, const int wiretype, const int fieldno);*/

pb_get_size, /*(Ast_runtime*, const Ast_sort, const void* valp);*/
pb_get_size_packed, /*(Ast_runtime*, const Ast_sort, const void* valp, size_t* sizep);*/
pb_get_message_size, /*(Ast_runtime*,const size_t size);*/
pb_get_tag_size, /*(Ast_runtime*, const Ast_sort, const int fieldno);*/

pb_write_tag, /*(Ast_runtime*, const Ast_sort, const int fieldno) ;*/
pb_read_tag, /*(Ast_runtime*, int* wiretype, int* fieldno);*/

pb_write_size, /*(Ast_runtime*, const size_t size);*/
pb_read_size, /*(Ast_runtime*);*/

pb_write_primitive, /*(Ast_runtime*, const Ast_sort, const void* valp);*/
pb_write_primitive_packed, /*(Ast_runtime*, const Ast_sort, const void* valp);*/

pb_read_primitive, /*(Ast_runtime*, const Ast_sort, void* valp);*/
pb_read_primitive_packed, /*(Ast_runtime*, const Ast_sort, void* valp);*/

pb_repeat_append, /*(Ast_runtime*, const Ast_sort, const void* newval, void* list);*/

pb_reclaim_string, /*(Ast_runtime*, char* value);*/
pb_reclaim_bytes, /*(Ast_runtime*, bytes_t* value);*/

}; /* protobuf_encoding */

Ast_encoding* protobuf_encoding = &static_protobuf_encoding;

