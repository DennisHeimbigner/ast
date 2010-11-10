// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// http://code.google.com/p/protobuf/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

// Author: kenton@google.com (Kenton Varda)
//  Based on original Protocol Buffers design by
//  Sanjay Ghemawat, Jeff Dean, and others.

#include <edf_common.h>

#include <algorithm>
#include <google/protobuf/stubs/hash.h>
#include <map>
#include <vector>
#include <edf_message.h>
#include <edf_field.h>
#include <edf_enum.h>
#include <edf_extension.h>
#include <edf_helpers.h>
#include <google/protobuf/stubs/strutil.h>
#include <google/protobuf/io/printer.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format.h>
#include <google/protobuf/descriptor.pb.h>

namespace google {
namespace protobuf {
namespace compiler {
namespace edf {

using internal::WireFormat;
using internal::WireFormatLite;

namespace {

void PrintFieldComment(Printer* printer, const FieldDescriptor* field) {
    // Print the field's proto-syntax definition as a comment.  We don't want to
    // print group bodies so we cut off after the first line.
    string def = field->DebugString();
    printer->Print("// $def$\n",
      "def", def.substr(0, def.find_first_of('\n')));
}

struct FieldOrderingByNumber {
    inline bool operator()(const FieldDescriptor* a,
                           const FieldDescriptor* b) const {
      return a->number() < b->number();
    }
};

const char* kWireTypeNames[] = {
    "VARINT",
    "FIXED64",
    "LENGTH_DELIMITED",
    "START_GROUP",
    "END_GROUP",
    "FIXED32",
};

// Sort the fields of the given Descriptor by number into a new[]'d array
// and return it.
const FieldDescriptor** SortFieldsByNumber(const Descriptor* descriptor) {
    const FieldDescriptor** fields =
      new const FieldDescriptor*[descriptor->field_count()];
    for (int i = 0; i < descriptor->field_count(); i++) {
      fields[i] = descriptor->field(i);
    }
    sort(fields, fields + descriptor->field_count(),
         FieldOrderingByNumber());
    return fields;
}

// Functor for sorting extension ranges by their "start" field number.
struct ExtensionRangeSorter {
    bool operator()(const Descriptor::ExtensionRange* left,
                    const Descriptor::ExtensionRange* right) const {
      return left->start < right->start;
    }
};

// Returns true if the message type has any required fields.  If it doesn't,
// we can optimize out calls to its IsInitialized() method.
//
// already_seen is used to avoid checking the same type multiple times
// (and also to protect against recursion).
static bool HasRequiredFields(
      const Descriptor* type,
      hash_set<const Descriptor*>* already_seen) {
    if (already_seen->count(type) > 0) {
      // Since the first occurrence of a required field causes the whole
      // function to return true, we can assume that if the type is already
      // in the cache it didn't have any required fields.
      return false;
    }
    already_seen->insert(type);

    // If the type has extensions, an extension with message type could contain
    // required fields, so we have to be conservative and assume such an
    // extension exists.
    if (type->extension_range_count() > 0) return true;

    for (int i = 0; i < type->field_count(); i++) {
      const FieldDescriptor* field = type->field(i);
      if (field->is_required()) {
        return true;
      }
      if (field->cpp_type() == FieldDescriptor::CPPTYPE_MESSAGE) {
        if (HasRequiredFields(field->message_type(), already_seen)) {
          return true;
        }
      }
    }

    return false;
}

static bool HasRequiredFields(const Descriptor* type) {
    hash_set<const Descriptor*> already_seen;
    return HasRequiredFields(type, &already_seen);
}

}

// ===================================================================


MessageGenerator::MessageGenerator(const Descriptor* descriptor,
                                     const string& dllexport_decl)
    : descriptor_(descriptor),
      classname_(ClassName(descriptor)),
      dllexport_decl_(dllexport_decl),
      field_generators_(descriptor),
      nested_generators_(new scoped_ptr<MessageGenerator>[
        descriptor->nested_type_count()]),
      enum_generators_(new scoped_ptr<EnumGenerator>[
        descriptor->enum_type_count()]),
      extension_generators_(new scoped_ptr<ExtensionGenerator>[
        descriptor->extension_count()])
{

    for (int i = 0; i < descriptor->nested_type_count(); i++) {
      nested_generators_[i].reset(
        new MessageGenerator(descriptor->nested_type(i), dllexport_decl));
    }

    for (int i = 0; i < descriptor->enum_type_count(); i++) {
      enum_generators_[i].reset(
        new EnumGenerator(descriptor->enum_type(i), dllexport_decl));
    }

    for (int i = 0; i < descriptor->extension_count(); i++) {
      extension_generators_[i].reset(
        new ExtensionGenerator(descriptor->extension(i), dllexport_decl));
    }
}

MessageGenerator::~MessageGenerator() {}


void MessageGenerator::GenerateEnumDefinitions(Printer* printer) const
{
    for (int i = 0; i < descriptor_->nested_type_count(); i++) {
      nested_generators_[i]->GenerateEnumDefinitions(printer);
    }

    for (int i = 0; i < descriptor_->enum_type_count(); i++) {
      enum_generators_[i]->GenerateDefinition(printer);
    }
}

void MessageGenerator::GenerateStructTypedef(Printer* printer) const
{
    for (int i = 0; i < descriptor_->nested_type_count(); i++) {
      nested_generators_[i]->GenerateStructTypedef(printer);
    }
    printer->Print("typedef struct $structname$ $structname$;\n",
                   "structname", descriptor_->name());
}

void MessageGenerator::GenerateStructDefinition(Printer* printer) const
{
    for (int i = 0; i < descriptor_->nested_type_count(); i++) {
      nested_generators_[i]->GenerateStructDefinition(printer);
    }

    map<string, string> vars;
    vars["structname"] = descriptor_->name();

    if (dllexport_decl_.empty()) {
      vars["dllexport"] = "";
    } else {
      vars["dllexport"] = dllexport_decl_ + " ";
    }

    printer->Print(vars,
      "struct $dllexport$ $structname$\n"
      "{\n");

    // Generate fields.
    printer->Indent();
    for (int i = 0; i < descriptor_->field_count(); i++) {
      const FieldDescriptor *field = descriptor_->field(i);
      field_generators_.get(field).GenerateStructMember(printer);
    }
    printer->Outdent();
    printer->Print(vars, "};\n\n");

}

void MessageGenerator::GenerateMessageProcedureDecls(Printer* printer, bool is_submessage) const
{
    for (int i = 0; i < descriptor_->nested_type_count(); i++) {
      nested_generators_[i]->GenerateMessageProcedureDecls(printer, true);
    }

    std::map<string, string> vars;
    vars["typename"] = descriptor_->name();
    vars["idname"] = type2id(descriptor_->name());

    printer->Print(vars,
                   "EXTERNC int edf_$typename$_encode(EDF* edf, $typename$* $idname$);\n"
                  );
    printer->Print(vars,
                   "EXTERNC int edf_$typename$_decode(EDF* edf, $typename$** ref_$idname$);\n"
                  );
    printer->Print(vars,
                   "EXTERNC int edf_$typename$_free(EDF* edf, $typename$* $idname$);\n"
                  );
}

void MessageGenerator::GenerateEnumProcedures(Printer* printer) const
{
    for (int i = 0; i < descriptor_->nested_type_count(); i++) {
      nested_generators_[i]->GenerateEnumProcedures(printer);
    }

    for (int i = 0; i < descriptor_->enum_type_count(); i++) {
      enum_generators_[i]->GenerateProcedures(printer);
    }
}

void MessageGenerator::GenerateProcedures(Printer* printer) const
{
    map<string, string> vars;

    for (int i = 0; i < descriptor_->nested_type_count(); i++) {
      nested_generators_[i]->GenerateProcedures(printer);
    }

    vars["typename"] = descriptor_->name();
    vars["idname"] = type2id(descriptor_->name());

    // Decode procedure 
    printer->Print(vars, "int edf_$typename$_decode(EDF* edf, $typename$** ref_$idname$)\n{\n");
    printer->Indent();
    printer->Print(vars,
          "int i, key, err = EDF_NOERR;\n"
          "$typename$* $idname$;\n"
          "*ref_$idname$ = NULL;\n"
          "$idname$ = ($typename$*)malloc(sizeof($typename$));\n"
          "if($idname$ == NULL) return EDF_ENOMEM;\n"
          "while(!err && (key = edf_readc(edf)) >= 0) {\n");
    printer->Indent();
    printer->Print(vars,
          "int wiretype = (key|0x7);\n"
          "int tag = (key >> 3);\n"
          "switch (tag) {\n"
          );
    for (int i = 0; i < descriptor_->field_count(); i++) {
	const FieldDescriptor* field = descriptor_->field(i);
	printer->Print("case $tag$: {\n","tag",SimpleItoa(field->number()));
        printer->Indent();
        field_generators_.get(field).GenerateArm(printer,descriptor_,DECODE);
        printer->Print("} break;\n");
        printer->Outdent();
    }
    // add default
    printer->Print("default:\n");
    printer->Indent();
    printer->Print("err = edf_skip_field(edf);\n");
    printer->Outdent();
    printer->Print("};\n"); // switch
    printer->Outdent();
    printer->Print("};\n"); // while
    printer->Print("return err;\n");
    printer->Outdent();
    printer->Print(vars, "} /* edf_$typename$_decode */\n");

    // Encode procedure 
    printer->Print("\n");
    printer->Print(vars, "int edf_$typename$_encode(EDF* edf, $typename$* $idname$)\n{\n");
    printer->Indent();
    printer->Print(vars,"int i, err = EDF_NOERR;\n");
    for (int i = 0; i < descriptor_->field_count(); i++) {
	const FieldDescriptor* field = descriptor_->field(i);
        field_generators_.get(field).GenerateArm(printer,descriptor_,ENCODE);
    }
    printer->Print(vars,"goto done;\n");
    printer->Outdent();
    printer->Print(vars,"done:\n");
    printer->Indent();
    printer->Print(vars,"return err;\n");
    printer->Outdent();
    printer->Print(vars,"} /* edf_$typename$_encode */\n");

    // Free procedure 
    printer->Print("\n");
    printer->Print(vars, "int edf_$typename$_free(EDF* edf, $typename$* $idname$)\n{\n");
    printer->Indent();
    printer->Print(vars,"int i, err = EDF_NOERR;\n");
    for (int i = 0; i < descriptor_->field_count(); i++) {
	const FieldDescriptor* field = descriptor_->field(i);
        field_generators_.get(field).GenerateArm(printer,descriptor_,FREE);
    }
    printer->Print(vars,"goto done;\n");
    printer->Outdent();
    printer->Print(vars,"done:\n");
    printer->Indent();
    printer->Print(vars,"return err;\n");
    printer->Outdent();
    printer->Print(vars,"} /* edf_$typename$_free */\n");

    // Init procedure 
    printer->Print("\n");
    printer->Print(vars, "int edf_$typename$_init(EDF* edf, $typename$* $idname$)\n{\n");
    printer->Indent();
    printer->Print(vars,"memset((void*)$idname$,0,sizeof($typename$));\n");
    printer->Outdent();
    printer->Print(vars, "} /* edf_$typename$_free */\n");
}

}  // namespace edf
}  // namespace compiler
}  // namespace protobuf
}  // namespace google
