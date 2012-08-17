
/*********************************************************************
 *   Copyright 2010, UCAR/Unidata
 *   See netcdf/COPYRIGHT file for copying and redistribution conditions.
 *   $Id$
 *   $Header$
 *********************************************************************/

#ifndef AST_RUNTIME_H
#define AST_RUNTIME_H

/* These may already be defined */
#ifdef HAVE_STDINT_H
#include <stdint.h>
#else
typedef unsigned char        uint8_t;
typedef unsigned short       uint16_t;
typedef unsigned int         uint32_t;
typedef unsigned long long   uint64_t;
typedef char        int8_t;
typedef short       int16_t;
typedef int         int32_t;
typedef long long   int64_t;
#endif

#ifndef HAVE_STDBOOL_H
#define true 1
#define false 0
#define TRUE 1
#define FALSE 0
#endif

/* rpc/types.h also defines bool_t */
#ifndef _RPC_TYPES_H
typedef unsigned int bool_t;
#endif

typedef uint8_t byte_t;

typedef struct bytes_t{
    size_t nbytes;
    unsigned char* bytes;
} bytes_t;

/* Define a null value for bytes_t */
extern bytes_t bytes_t_null;

typedef struct repeat_t{
    size_t count;
    void* values;
} repeat_t;

/* Define a null value for repeat_t */
extern repeat_t repeat_t_null;

/* Forward types */
typedef struct Ast_encoding Ast_encoding;
typedef struct Ast_runtime Ast_runtime;
typedef struct Ast_io Ast_io;
typedef struct Ast_resources Ast_resources;

/* Define the known encoders */
typedef enum Ast_encoder {
Encoder_protobuf = 0,
Encoder_xdr      = 1,
Encoder_count    = 2
} Ast_encoder;

/*Define error codes */

typedef enum ast_err {
AST_NOERR      = ( 0),
AST_ENOMEM     = (-1),
AST_EARG       = (-2),

/* IO related errors */
AST_EOF        = (-50),
AST_EIO        = (-51),
AST_EIOMODE    = (-52),

/* Protocol related errors */
AST_ECURL      = (-100),
AST_EENCODER   = (-101),
AST_EWIRETYPE  = (-102),
AST_ESORT      = (-103),
AST_ESHORT     = (-104),

/* Error for protobuf encoding */
AST_EVARINT    = (-150),
} ast_err;

/* Define primitive types enum */
typedef enum Ast_sort {
Ast_double   = 0,
Ast_float    = 1,
Ast_int32    = 2,
Ast_uint32   = 3,
Ast_int64    = 4,
Ast_uint64   = 5,
Ast_sint32   = 6,
Ast_sint64   = 7,
Ast_fixed32  = 8,
Ast_fixed64  = 9,
Ast_sfixed32 = 10,
Ast_sfixed64 = 11,
Ast_string   = 12,
Ast_bytes    = 13,
Ast_bool     = 14,
Ast_enum     = 15,
Ast_message  = 16,
Ast_packed   = 17
} Ast_sort;

/**
 * Define MAXTYPESIZE to be at least big enough
 * to hold instance of any possible primitives
 * except string and byte and as encoded using any
 * of the possible encodings (e.g. int32, int64, varint, etc)
 * => Must hold at least lub(64/7) = 10 */
#define MAXTYPESIZE 16

/* Define wiretypes */
typedef enum Ast_wiretype {
Ast_varint     = 0, /* int32, int64, uint32, uint64, sint32, sint64, bool, enum*/
Ast_64bit      = 1, /* fixed64, sfixed64, double*/
Ast_counted    = 2, /* Length-delimited: string, bytes, embedded messages, packed repeated fields*/
Ast_startgroup = 3, /* Start group (deprecated) */
Ast_endgroup   = 4, /* end group (deprecated) */
Ast_32bit      = 5, /* fixed32, sfixed32, float*/
} Ast_wiretype;

/* Define the field modes */
typedef enum Ast_fieldmode {
Ast_required = 0,
Ast_optional = 1,
Ast_repeated = 2,
} Ast_fieldmode;

/* Max depth of the message tree */
#define MAX_STACK_SIZE 1024

typedef enum Ast_iomode {Ast_read, Ast_write} Ast_iomode;

/* Define the resource management operators */
struct Ast_resources {
void*   data;
struct Ast_resource_ops {
void*	(*alloc)(Ast_runtime*, const size_t len);
void*	(*realloc)(Ast_runtime*, void* memory, const size_t len);
void	(*free)(Ast_runtime*, void* mem);
} *ops;
};

/* Define the io operators */
struct Ast_io {
uint64_t   id; /* to identify who created this io object */
Ast_iomode mode;
void*      stream; /* io specific state */
struct Ast_io_ops {
ast_err  (*read)(Ast_runtime*, byte_t*, const size_t len, size_t*);
ast_err  (*write)(Ast_runtime*, const byte_t* buf, const size_t len);
ast_err  (*mark)(Ast_runtime*, const size_t avail);
ast_err  (*unmark)(Ast_runtime*);
ast_err  (*close)(Ast_runtime*);
} *ops;
};

struct Ast_runtime {
    Ast_runtime* next; /* Linked list of runtimes */
    void* data; /* opaque runtime state */
    Ast_io* io;
    Ast_encoding* encoder;
    Ast_resources* resources;
};

/**************************************************/
/* Constructor */

extern ast_err Ast_runtime_new(Ast_resources*,Ast_runtime**);

/**************************************************/
/* get/set */

extern ast_err ast_setio(Ast_runtime*,Ast_io*);
extern ast_err ast_setencoding(Ast_runtime*,Ast_encoder);

/**************************************************/
/* Misc. (see ast_util) */
extern const char* ast_strerror(ast_err err);
extern void ast_logset(int tf);
extern void ast_log(const char* fmt, ...);

/**************************************************/
/* wrappers for the dispatch table */

extern ast_err	ast_read(Ast_runtime*, byte_t* buf, const size_t len, size_t* red);
extern ast_err	ast_write(Ast_runtime*, const byte_t* buf, const size_t len);
extern ast_err	ast_mark(Ast_runtime*, const size_t avail);
extern ast_err	ast_unmark(Ast_runtime*);

extern void*	ast_alloc(Ast_runtime*, const size_t len);
extern void*	ast_realloc(Ast_runtime*, void*, const size_t len);
extern void	ast_free(Ast_runtime*, void* mem);

extern ast_err	ast_skip_field(Ast_runtime*, const int wiretype, const int fieldno);

extern size_t	ast_get_size(Ast_runtime*, const Ast_sort sort, const void* valp);
extern size_t	ast_get_size_packed(Ast_runtime*, const Ast_sort sort, const void* valp);
extern size_t	ast_get_message_size(Ast_runtime*, const size_t size);
extern size_t	ast_get_tag_size(Ast_runtime*, const Ast_sort sort, const int fieldno);

extern ast_err	ast_write_tag(Ast_runtime*, const Ast_sort sort, const int fieldno) ;
extern bool_t	ast_read_tag(Ast_runtime*, int* wiretype, int* fieldno);

extern ast_err	ast_write_size(Ast_runtime*, const size_t size);
extern size_t	ast_read_size(Ast_runtime*);

extern ast_err	ast_write_primitive(Ast_runtime*, const Ast_sort sort, const void* valp);
extern ast_err	ast_write_primitive_packed(Ast_runtime*, const Ast_sort sort, const void* valp);

extern ast_err	ast_read_primitive(Ast_runtime*, const Ast_sort sort, void* valp);
extern ast_err	ast_read_primitive_packed(Ast_runtime*, const Ast_sort sort, void* valp);

extern ast_err	ast_repeat_append(Ast_runtime*, const Ast_sort sort, const void* newval, void* list);

extern ast_err	ast_reclaim_string(Ast_runtime*, char* value);
extern ast_err	ast_reclaim_bytes(Ast_runtime*, bytes_t* value);


#endif /*AST_RUNTIME_H*/
