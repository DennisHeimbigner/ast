## This is a automake file, part of Unidata's netCDF package.
# Copyright 2005, see the COPYRIGHT file for more information.

# This is the main automake file for netCDF. It builds the different
# netcdf directories. Not all directories are built, depending on the
# options selected during configure.

# This directory stores libtool macros, put there by aclocal.
ACLOCAL_AMFLAGS = -I m4

build_dir = target
astjar = ast.jar
main_class = "unidata.protobuf.ast.compiler.Main"

JAVASRC = src/main/java/ast/compiler/*.java src/main/java/ast/c/*.java src/main/java/ast/gnu/*.java 
JAVACLASSES = ${JAVASRC:*/%.java=target/classes/%.class}

SUBDIRS=src/main/c src/test/c src/main/java/ast

# These files get added to the distribution.
EXTRA_DIST =
# List of files to remove for a distclean
DISTCLEANFILES = Makefile
# List of files to remove for a maintainer-clean
MAINTAINERCLEANFILES =

EXTRA_DIST += build.xml CHANGES.txt LICENSE.txt NOTICE.txt README.txt 

##################################################
# Duplicates of the build.xml targets

.PHONEY: init jar compiler

all: jar

clean: init

init:
	rm -fr ${build_dir}
	mkdir ${build_dir}
	mkdir ${build_dir}/classes
	mkdir ${build_dir}/doc
	mkdir ${build_dir}/results

jar: compiler ${build_dir}/manifest.mf
	jar -cfM "${build_dir}/${astjar}" "${build_dir}/manifest.mf" -C ${build_dir}/classes unidata -C ${build_dir}/classes gnu

${build_dir}/manifest.mf:
	echo "Manifest-Version: 1.0" > ${build_dir}/manifest.mf
	echo "Main-class: ${main_class}" >> ${build_dir}/manifest.mf

compiler: ${JAVASRC}
	javac -d ${build_dir}/classes -classpath ${build_dir}/classes ${JAVASRC}
