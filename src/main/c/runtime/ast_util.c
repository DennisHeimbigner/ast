#include "config.h"

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdarg.h>

#include "ast_internal.h"

static int logstate = 1;

void ast_logset(int tf) {logstate = tf;}


void
ast_log(const char* fmt, ...)
{
    va_list ap;

    if(logstate == 1) {
        va_start(ap, fmt); 
	vfprintf(stderr,fmt,ap);
    }
}

/* Given an error number, return an error message. */
const char *
ast_strerror(ast_err err)
{
   /* System error? */
   if(err > 0) {
      const char *cp = (const char *) strerror(err);
      if(cp == NULL)
	 return "Unknown Error";
      return cp;
   }

   /* If we're here, this is a netcdf error code. */
    switch(err) {
    case AST_NOERR: return "no error";
    case AST_ENOMEM: return "Out of memory";
    case AST_EARG: return "Illegal argument";
    case AST_EOF: return "EOF encountered";
    case AST_EIO: return "I/O error";
    case AST_EIOMODE: return "Incorrect I/O mode";
    case AST_ECURL: return "Curl failure";
    case AST_EENCODER: return "Encoder mismatch";
    case AST_EWIRETYPE: return "Illegal wiretype value";
    case AST_ESORT: return "Illegal sort value";
    case AST_ESHORT: return "Not enough data from wire";
    case AST_EVARINT: return "Malformed varint";
    default:
        return "Unknown Error";
   }
}


