## This is a automake file, part of Unidata's netCDF package.
# Copyright 2005, see the COPYRIGHT file for more information.

# This is the main automake file for netCDF. It builds the different
# netcdf directories. Not all directories are built, depending on the
# options selected during configure.

# This directory stores libtool macros, put there by aclocal.
ACLOCAL_AMFLAGS = -I m4

# These files get added to the distribution.
EXTRA_DIST =
# List of files to remove for a distclean
DISTCLEANFILES = Makefile
# List of files to remove for a maintainer-clean
MAINTAINERCLEANFILES =

#pkgconfigdir=$(libdir)/pkgconfig
#pkgconfig_DATA = ast.pc

# Build the runtime library
HSRC=\
ast.h \
ast_byteio.h \
ast_debug.h \
ast_encoding.h \
ast_util.h

CSRC=ast.c

libast_la_SOURCES = ${HSRC} ${CSRC}

lib_LTLIBRARIES = libast.la

# Install only ast.h ast_runtime.h ast_byteio.h
include_HEADERS = ast.h ast_runtime.h ast_byteio.h 
noinst_HEADERS = ast_curl.h ast_debug.h ast_internal.h ast_util.h

ast.c: ast_include.c ast_runtime.c ast_byteio.c ast_debug.c ast_util.c ast_protobuf.c
	cat $< >ast.c
