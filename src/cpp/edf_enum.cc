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

#include <iostream>
#include <set>
#include <map>

#include <edf_enum.h>
#include <edf_helpers.h>
#include <google/protobuf/io/printer.h>
#include <google/protobuf/stubs/strutil.h>

namespace google {
namespace protobuf {
namespace compiler {
namespace edf {

EnumGenerator::EnumGenerator(const EnumDescriptor* descriptor,
                             const string& dllexport_decl)
  : descriptor_(descriptor),
    dllexport_decl_(dllexport_decl)
{}

EnumGenerator::~EnumGenerator() {}

void EnumGenerator::GenerateDefinition(Printer* printer)
{
  map<string,string> vars;
  vars["typename"] = descriptor_->name();

  printer->Print(vars, "typedef enum $typename$ {\n");
  printer->Indent();

  const EnumValueDescriptor* min_value = descriptor_->value(0);
  const EnumValueDescriptor* max_value = descriptor_->value(0);

  for (int i = 0; i < descriptor_->value_count(); i++) {
    vars["name"] = descriptor_->value(i)->name();
    vars["number"] = SimpleItoa(descriptor_->value(i)->number());
    vars["prefix"] = (descriptor_->containing_type() == NULL) ?
      "" : classname_ + "_";

    if (i > 0) printer->Print(",\n");
    printer->Print(vars, "$prefix$$name$ = $number$");

    if (descriptor_->value(i)->number() < min_value->number()) {
      min_value = descriptor_->value(i);
    }
    if (descriptor_->value(i)->number() > max_value->number()) {
      max_value = descriptor_->value(i);
    }
  }

  printer->Outdent();
  printer->Print(vars, "} $typename$;\n");
}

void EnumGenerator::GenerateProcedures(Printer* printer)
{
    map<string, string> vars;
    vars["typename"] = descriptor_->name();
    vars["idname"] = type2id(descriptor_->name());
  
cerr << "xxx\n";

    // Decode procedure 
    printer->Print(vars, "\nint edf_$typename$_decode(EDF* edf, $typename$** ref_$idname$)\n{\n");
    printer->Indent();
    printer->Print(vars,
          "int key, err = ENOERR\n"
          "$typename$ $idname$;\n"
          "err = edf_varint_decode(edf,&$idname$,packed);\n"
          "if(!err && ref_$idname$ != NULL) *ref_$idname$ = $idname$;\n"
          "return err;\n");
    printer->Outdent();
    printer->Print(vars, "} /* edf_$typename$_decode */\n");
  
    // Encode procedure 
    printer->Print(vars, "\nint edf_$typename$_encode(EDF* edf, $typename$* $idname$)\n{\n");
    printer->Indent();
    printer->Print(vars,
          "int key, err = ENOERR\n"
          "err = edf_varint_encode(edf,$idname$,packed);\n"
          "return err;\n");
    printer->Outdent();
    printer->Print(vars, "} /* edf_$typename$_encode */\n");
  
    // Free procedure 
    printer->Print(vars, "\nint edf_$typename$_free(EDF* edf, $typename$* $idname$)\n{\n");
    printer->Indent();
    printer->Print(vars,
          "return ENOERR;\n");
    printer->Outdent();
    printer->Print(vars, "} /* edf_$typename$_free */\n");



}

}  // namespace edf
}  // namespace compiler
}  // namespace protobuf
}  // namespace google
