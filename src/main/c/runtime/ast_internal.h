/*********************************************************************
 *   Copyright 2010, UCAR/Unidata
 *   See netcdf/COPYRIGHT file for copying and redistribution conditions.
 *   $Id$
 *   $Header$
 *********************************************************************/

/**
 This .h includes the others
 needed by internal .c files.
 */

#ifndef ASTINTERNAL_H
#define ASTINTERNAL_H

#include "ast_runtime.h"
#include "ast_debug.h"
#include "ast_encoding.h"
#include "ast_util.h"
#include "ast_byteio.h"

/* Define the known encodings */

extern Ast_encoding* protobuf_encoding;
extern Ast_encoding* xdr_encoding;

#endif /*AST_H*/
