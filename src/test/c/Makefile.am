## This is a automake file, part of Unidata's netCDF package.
# Copyright 2005, see the COPYRIGHT file for more information.

# This is the main automake file for netCDF. It builds the different
# netcdf directories. Not all directories are built, depending on the
# options selected during configure.

# This directory stores libtool macros, put there by aclocal.
ACLOCAL_AMFLAGS = -Im4

EXTRA_DIST =
DISTCLEANFILES = 
MAINTAINERCLEANFILES =
CLEANFILES =
AM_LDFLAGS =
AM_CFLAGS =

# Link to our assembled library.
AM_CFLAGS += -I${top_builddir}/target/include
AM_LDFLAGS += -L${top_builddir}/target/lib -last

TESTPROTO = Primtests.proto Bytetests.proto Msgtests.proto Enumtests.proto

testast_SOURCES = testast.c \
	Primtests.c Bytetests.c Msgtests.c Enumtests.c \
	Primtests.h Bytetests.h Msgtests.h Enumtests.h \
	testprim1.c testprim2.c testprim3.c testprim4.c \
	testenum1.c testmsg1.c \
	testprim1.h

TESTS = testast
check_PROGRAMS = ${TESTS}

.PHONEY: rebuild

ASTJAR = ${top_srcdir}/target/ast.jar
ASTCLASSES = ${top_srcdir}/target/classes
ASTMAIN = unidata.protobuf.ast.compiler.Main
ASTFLAGS = -Iast.h
JFLAGS = -ea -Dconfig_h=true

rebuild: ${TESTPROTO} ${ASTJAR}
	for t in ${TESTPROTO} ; do \
	java ${JFLAGS} -jar ${ASTJAR} ${ASTFLAGS} $${t}; \
	done


