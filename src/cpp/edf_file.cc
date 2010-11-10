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
#include <edf_file.h>
#include <edf_enum.h>
#include <edf_extension.h>
#include <edf_helpers.h>
#include <edf_message.h>
#include <edf_field.h>
#include <google/protobuf/io/printer.h>
#include <google/protobuf/descriptor.pb.h>
#include <google/protobuf/stubs/strutil.h>
#include <iostream>

namespace google {
namespace protobuf {
namespace compiler {
namespace edf {

// ===================================================================

FileGenerator::FileGenerator(const FileDescriptor* file,
                             const string& dllexport_decl)
  : file_(file),
    message_generators_(
      new scoped_ptr<MessageGenerator>[file->message_type_count()]),
    enum_generators_(
      new scoped_ptr<EnumGenerator>[file->enum_type_count()]),
    extension_generators_(
      new scoped_ptr<ExtensionGenerator>[file->extension_count()]),
    dllexport_decl_(dllexport_decl) {

  for (int i = 0; i < file->message_type_count(); i++) {
    message_generators_[i].reset(
      new MessageGenerator(file->message_type(i), dllexport_decl));
  }

  for (int i = 0; i < file->enum_type_count(); i++) {
    enum_generators_[i].reset(
      new EnumGenerator(file->enum_type(i), dllexport_decl));
  }

  for (int i = 0; i < file->extension_count(); i++) {
    extension_generators_[i].reset(
      new ExtensionGenerator(file->extension(i), dllexport_decl));
  }

  SplitStringUsing(file_->package(), ".", &package_parts_);
}

FileGenerator::~FileGenerator() {}

void FileGenerator::GenerateHeader(Printer* printer)
{
  string basename = StripProto(file_->name());
  // Generate top initial header lines
  printer->Print(
    "/* Generated by the protocol buffer compiler.  DO NOT EDIT! */\n"
    "\n"
    "#ifndef _$filename$_\n"
    "#define _$filename$_\n"
    "\n",
    "filename", uppercase(basename));

  printer->Print(
    "#include <edf.h>\n"
    "\n");

  printer->Print("\n"); printer->Print(kThickSeparator);printer->Print("\n");

  // Generate top-level enum definitions
  for (int i = 0; i < file_->enum_type_count(); i++) {
    enum_generators_[i]->GenerateDefinition(printer);
  }

  // Generate enum definitions nested within messages
  for (int i = 0; i < file_->message_type_count(); i++) {
    message_generators_[i]->GenerateEnumDefinitions(printer);
  }

  printer->Print("\n"); printer->Print(kThickSeparator);printer->Print("\n");

  // Generate message struct forward definitions
  for (int i = 0; i < file_->message_type_count(); i++) {
    message_generators_[i]->GenerateStructTypedef(printer);
  }

  printer->Print("\n"); printer->Print(kThickSeparator);printer->Print("\n");

  // Generate struct definitions.
  printer->Print("\n");
  for (int i = 0; i < file_->message_type_count(); i++) {
    message_generators_[i]->GenerateStructDefinition(printer);
  }

  printer->Print("\n"); printer->Print(kThickSeparator);printer->Print("\n");

  // Declare the edf function externs.
  for (int i = 0; i < file_->message_type_count(); i++) {
    message_generators_[i]->GenerateMessageProcedureDecls(printer,false);
  }

  printer->Print("\n"); printer->Print(kThickSeparator);printer->Print("\n");

  // Declare extension identifiers.
  for (int i = 0; i < file_->extension_count(); i++) {
    extension_generators_[i]->GenerateDeclaration(printer);
  }

  printer->Print("\n"); printer->Print(kThickSeparator);printer->Print("\n");

  // Generate final header lines
  printer->Print(
    "\n"
    "\n\n#endif /* _$filename$_ */\n",
    "filename", uppercase(basename));

}

void FileGenerator::GenerateSource(Printer* printer)
{
  // Generate source includes
  printer->Print(
    "/* Generated by the protocol buffer compiler.  DO NOT EDIT! */\n"
    "\n"
    "#include <$basename$.h>\n",
    "basename", StripProto(file_->name()));

  // Generate enum encode/decode/free procedure bodies

  printer->Print("\n/* --- enums --- */\n\n");

  // Generate top-level enum definitions
  for (int i = 0; i < file_->enum_type_count(); i++) {
    enum_generators_[i]->GenerateProcedures(printer);
  }

  // Generate enum definitions nested within messages
  for (int i = 0; i < file_->message_type_count(); i++) {
    message_generators_[i]->GenerateEnumProcedures(printer);
  }

  // Generate encode/decode/free procedures for each message type
  printer->Print("\n/* --- messages --- */\n\n");

  for (int i = 0; i < file_->message_type_count(); i++) {
    message_generators_[i]->GenerateProcedures(printer);
  }
}

}  // namespace edf
}  // namespace compiler
}  // namespace protobuf
}  // namespace google