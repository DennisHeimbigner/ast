CCSRC=\
edf_enum.cc \
edf_enum_field.cc \
edf_extension.cc \
edf_field.cc \
edf_file.cc \
edf_generator.cc \
edf_helpers.cc \
edf_message.cc \
edf_message_field.cc \
edf_primitive_field.cc \
edf_string_field.cc \
printer.cc \
main.cc


HDRS=\
edf_common.h \
edf_enum_field.h \
edf_enum.h \
edf_extension.h \
edf_field.h \
edf_file.h \
edf_generator.h \
edf_helpers.h \
edf_message_field.h \
edf_message.h \
edf_primitive_field.h \
edf_string_field.h \
printer.h

OBJ=$(CCSRC:.cc=.o)

INCL=-I. -I.. -I../runtime
#LIBS=-L/machine/local/lib -lprotoc -lprotobuf -pthread
LIBS=-L/usr/local/lib -lprotoc -lprotobuf
RPATH=-W,-rpath

#CXXFLAGS=--static -g -O0
CXXFLAGS= -g -O0


all: protocc

clean::
	rm -f $(OBJ) protocc protocc.exe
	rm -f t.pb.h t.pb.c

protocc: $(OBJ)
	g++ $(CXXFLAGS) -o protocc ${OBJ} $(LIBS)


$(OBJ): ${CCSRC} ${HDRS}
	g++ -g -O0 $(INCL) -c ${CCSRC}

t::
	./protocc --edf_out=. t.proto
	gcc $(INCL) -c t.c


E1=-e 's|io::Printer|Printer|g'
EC=-e '/^.*Sanjay.Ghemawat/rsedfix'
# EH=-e '/^\#define.GOOGLE_PROTOBUF_COMPILER/rsedfix'

fix::
	rm -fr sedfix
	$(MAKE) sedfix
	for f in $(CCSRC) ; do \
	  sed $(E1) $(EC) <save/$$f >./$$f ; \
	done
	for f in $(HDRS) ; do \
	  sed $(E1) $(EH) <save/$$f >./$$f ; \
	done

sedfix: Makefile
	echo "" >>sedfix
	echo '#include <edf_common.h>' >>sedfix
