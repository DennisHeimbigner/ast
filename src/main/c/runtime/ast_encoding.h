/*********************************************************************
 *   Copyright 2010, UCAR/Unidata
 *   See netcdf/COPYRIGHT file for copying and redistribution conditions.
 *   $Id$
 *   $Header$
 *********************************************************************/

struct Ast_encoding {

ast_err	(*skip_field)(Ast_runtime*, const int wiretype, const int fieldno);

size_t	(*get_size)(Ast_runtime*, const Ast_sort, const void* valp);
size_t	(*get_size_packed)(Ast_runtime*, const Ast_sort, const void* valp);
size_t	(*get_message_size)(Ast_runtime*,const size_t msgsize);
size_t	(*get_tag_size)(Ast_runtime*, const Ast_sort, const int fieldno);

ast_err	(*write_tag)(Ast_runtime*, const Ast_sort, const int fieldno) ;
ast_err	(*read_tag)(Ast_runtime*, int* wiretype, int* fieldno);

ast_err	(*write_size)(Ast_runtime*, const size_t size);
size_t	(*read_size)(Ast_runtime*);

ast_err	(*write_primitive)(Ast_runtime*, const Ast_sort, const void* valp);
ast_err	(*write_primitive_packed)(Ast_runtime*, const Ast_sort, const void* valp);

ast_err	(*read_primitive)(Ast_runtime*, const Ast_sort, void* valp);
ast_err	(*read_primitive_packed)(Ast_runtime*, const Ast_sort, void* valp);

ast_err	(*repeat_append)(Ast_runtime*, const Ast_sort, const void* newval, void* list);

ast_err	(*reclaim_string)(Ast_runtime*, char* value);
ast_err	(*reclaim_bytes)(Ast_runtime*, bytes_t* value);

};
