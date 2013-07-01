/*********************************************************************
 *   Copyright 2010, UCAR/Unidata
 *   See netcdf/COPYRIGHT file for copying and redistribution conditions.
 *   $Id$
 *   $Header$
 *********************************************************************/

#ifndef AST_BYTEIO_H
#define AST_BYTEIO_H

extern ast_err ast_byteio_new(Ast_runtime*, Ast_iomode, void* buf, size_t len, Ast_io**);

extern ast_err ast_byteio_count(Ast_runtime*, size_t*);

extern ast_err ast_byteio_content(Ast_runtime*, bytes_t*);

#endif /*AST_BYTEIO_H*/
