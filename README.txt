Introduction
------------
The installation is a mix of ant and make
based actitivies, with some redundancy.

Install
-------
1. Edit build.xml and modify the "prefix" property to
   define the location for installation. The default
   it the local directory "target".

2. Invoke "ant install" to build the various libraries (both C and Java)
   and install in ${prefix}/ast.jar and ${prefix}/{bin,lib,include}.

Invocation
----------

1. Invoke the compiler using a command similar to this:
       java -jar ${prefix}/ast.jar XXX.proto
   This compiles XXX.proto and produces XXX.{c,h}.

2. Invoke (e.g.) gcc to compile and load a test program.
	gcc -o test.exe testast.c XXX.c -L${prefix} -last

