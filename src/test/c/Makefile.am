## This is a automake file, part of Unidata's netCDF package.
# Copyright 2005, see the COPYRIGHT file for more information.

# This is the main automake file for netCDF. It builds the different
# netcdf directories. Not all directories are built, depending on the
# options selected during configure.

# This directory stores libtool macros, put there by aclocal.
ACLOCAL_AMFLAGS = -I m4

EXTRA_DIST =
DISTCLEANFILES = 
MAINTAINERCLEANFILES =
CLEANFILES =
AM_LDFLAGS =
AM_CFLAGS =

# Link to our assembled library.
AM_CFLAGS += -I${top_builddir}/target
AM_LDFLAGS += -L${top_builddir}/target/lib -last

TESTPROTO = Primtests.proto Bytetests.proto Msgtests.proto Enumtests.proto

testast_SOURCES = testast.c \
	Primtests.c Bytetests.c Msgtests.c Enumtests.c \
	Primtests.h Bytetests.h Msgtests.h Enumtests.h

TESTS = testast
check_PROGRAMS = ${TESTS}

CLEANFILES += \
	Primtests.c Bytetests.c Msgtests.c Enumtests.c \
	Primtests.h Bytetests.h Msgtests.h Enumtests.h

.PHONEY: rebuild

ASTJAR = ${top_srcdir}/target/ast.jar
ASTFLAGS = "-Iast.h"
DFLAGS="-Dconfig_h=true"

rebuild: ${TESTPROTO} ${ASTJAR}
	for t in ${TESTPROTO} ; do java ${DFLAGS} -jar ${ASTJAR} ${ASTFLAGS} $${t}; done


