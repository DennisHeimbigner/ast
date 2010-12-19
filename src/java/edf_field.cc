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

#include <edf_field.h>
#include <edf_helpers.h>
#include <edf_primitive_field.h>
#include <edf_string_field.h>
#include <edf_enum_field.h>
#include <edf_message_field.h>
#include <google/protobuf/descriptor.pb.h>
#include <google/protobuf/wire_format.h>
#include <google/protobuf/io/printer.h>
#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/strutil.h>

namespace google {
namespace protobuf {
namespace compiler {
namespace edf {

using internal::WireFormat;

void SetCommonFieldVariables(const FieldDescriptor* descriptor,
                             map<string, string>* vars)
{
    FieldDescriptor::Type fieldtype = descriptor->type();
    (*vars)["fieldname"] = descriptor->name();
    (*vars)["edffcnname"]  = edffcnname(fieldtype);
    switch (fieldtype) {
    case FieldDescriptor::TYPE_ENUM:
        (*vars)["fieldtype"] = descriptor->enum_type()->name();
        (*vars)["cfieldtype"] = descriptor->message_type()->name();
	break;
    case FieldDescriptor::TYPE_MESSAGE:
        (*vars)["fieldtype"] = descriptor->message_type()->name();
        (*vars)["cfieldtype"] = descriptor->message_type()->name();
	break;
    default:
        (*vars)["fieldtype"] = ctypename(fieldtype);
        (*vars)["cfieldtype"] = ctypename(fieldtype);
    }
}


FieldGenerator::~FieldGenerator() {}

/**
 * Generic struct member generator
 */
void FieldGenerator::GenerateStructMember(Printer* printer) const
{
    map<string,string> vars;
    SetCommonFieldVariables(descriptor_,&vars);

    switch (descriptor_->label()) {
        case FieldDescriptor::LABEL_REQUIRED:
            printer->Print(vars, "$cfieldtype$ $fieldname$;\n");
            break;

        case FieldDescriptor::LABEL_OPTIONAL:
            printer->Print(vars, "struct {\n");
            printer->Indent();
            printer->Print(vars, "bool_t defined;\n");
            printer->Print(vars, "$cfieldtype$ value[1];\n");
            printer->Outdent();
            printer->Print(vars, "} $fieldname$;\n");
	    break;

        case FieldDescriptor::LABEL_REPEATED:
            printer->Print(vars, "struct {\n");
            printer->Indent();
            printer->Print(vars, "size_t count;\n");
            printer->Print(vars, "$cfieldtype$* values;\n");
            printer->Outdent();
            printer->Print(vars, "} $fieldname$;\n");
            break;
    }
}

/**
 * Generic arm generator
 */
void FieldGenerator::GenerateArm(Printer* printer, const Descriptor* msg, Action action) const
{
    map<string, string> vars;
    FieldDescriptor::Type fieldtype = descriptor_->type();
    SetCommonFieldVariables(descriptor_,&vars);

    // Define some parent msg fields 
    vars["msgtypename"] = msg->name();
    vars["msgidname"] = type2id(msg->name());

#define CASE(tag,action) ((((int)tag)<<8)+((int)action))
    switch (CASE(descriptor_->label(),action)) {

    case CASE(FieldDescriptor::LABEL_REQUIRED,ENCODE):
	if(fieldtype == FieldDescriptor::TYPE_MESSAGE) {
	    printer->Print(vars,"err = edf_$edffcnname$_encode(edf,$msgidname$->$fieldname$);\n");
	}
	} else {
	    printer->Print(vars,"err = edf_$edffcnname$_encode(edf,$msgidname$->$fieldname$);\n");
	}
	printer->Print(vars,"if(err) goto done;\n");
	break;

    case CASE(FieldDescriptor::LABEL_OPTIONAL,ENCODE):
	printer->Print(vars,"if($msgidname$->$fieldname$.defined) {\n");
	printer->Indent();
	printer->Print(vars,"err = edf_$edffcnname$_encode(edf,$msgidname$->$fieldname$.value[0]);\n");
	printer->Print(vars,"if(err) goto done;\n");
	printer->Outdent();
	printer->Print("}\n");
	break;

    case CASE(FieldDescriptor::LABEL_REPEATED,ENCODE):
	printer->Print(vars,"for(i=0;i<$msgidname$->$fieldname$.count && !err;i++)\n");
	printer->Indent();
	printer->Print(vars,"err = edf_$edffcnname$_encode(edf,$msgidname$->$fieldname$.values[i]);\n");
	printer->Print(vars,"if(err) goto done;\n");
	printer->Outdent();
	break;

    case CASE(FieldDescriptor::LABEL_REQUIRED,DECODE):
        printer->Print(vars,"err = edf_$edffcnname$_decode(edf,&$msgidname$->$fieldname$);\n");
	break;

    case CASE(FieldDescriptor::LABEL_OPTIONAL,DECODE):
	printer->Print(vars,"$msgidname$->$fieldname$.defined=true;\n");
        printer->Print(vars,"err = edf_$edffcnname$_decode(edf,$msgidname$->$fieldname$.value);\n");
	break;

    case CASE(FieldDescriptor::LABEL_REPEATED,DECODE):
	if(fieldtype == FieldDescriptor::TYPE_MESSAGE) {
	    printer->Print(vars,"$fieldtype$* tmp;\n");
	} else {
	    printer->Print(vars,"$cfieldtype$ tmp;\n");
        }
        printer->Print(vars,"err = edf_$edffcnname$_decode(edf,&tmp);\n");
	printer->Print(vars,"if(err) break;\n");
	printer->Print(vars,"err = edf_append(edf,(void*)&$msgidname$->$fieldname$,sizeof(tmp),(void*)&tmp);\n");
	break;

    case CASE(FieldDescriptor::LABEL_REQUIRED,FREE):
	printer->Print(vars,"err = edf_$edffcnname$_free(edf,$msgidname$->$fieldname$);\n");
	printer->Print(vars,"if(err) goto done;\n");
	break;

    case CASE(FieldDescriptor::LABEL_OPTIONAL,FREE):
	printer->Print(vars,"if($msgidname$->$fieldname$.defined)\n");
	printer->Indent();
	printer->Print(vars,"err = edf_$edffcnname$_free(edf,$msgidname$->$fieldname$.value[0]);\n");
	printer->Outdent();
	printer->Print(vars,"if(err) goto done;\n");
	break;

    case CASE(FieldDescriptor::LABEL_REPEATED,FREE):
        printer->Print(vars,"for(i=0;i<$msgidname$->$fieldname$.count && !err;i++) {\n");
	printer->Indent();
	printer->Print(vars,"err = edf_$edffcnname$_free(edf,$msgidname$->$fieldname$.values[i]);\n");
	printer->Outdent();
	printer->Print("}\n");
	printer->Print(vars,"edf_free((void*)$msgidname$->$fieldname$.values);\n");
	printer->Print(vars,"if(err) goto done;\n");
	break;

    default: abort();
    }
}


FieldGeneratorMap::FieldGeneratorMap(const Descriptor* descriptor)
  : descriptor_(descriptor),
    field_generators_(
      new scoped_ptr<FieldGenerator>[descriptor->field_count()]) {
  // Construct all the FieldGenerators.
  for (int i = 0; i < descriptor->field_count(); i++) {
    field_generators_[i].reset(MakeGenerator(descriptor->field(i)));
  }
}

FieldGenerator* FieldGeneratorMap::MakeGenerator(const FieldDescriptor* field)
{
    switch (field->type()) {
      case FieldDescriptor::TYPE_MESSAGE:
        return new MessageFieldGenerator(field);
      case FieldDescriptor::TYPE_BYTES:
      case FieldDescriptor::TYPE_STRING:
        return new StringFieldGenerator(field);
      case FieldDescriptor::TYPE_ENUM:
        return new EnumFieldGenerator(field);
      default:
        return new PrimitiveFieldGenerator(field);
    }
}

FieldGeneratorMap::~FieldGeneratorMap() {}

const FieldGenerator& FieldGeneratorMap::get(
    const FieldDescriptor* field) const {
  GOOGLE_CHECK_EQ(field->containing_type(), descriptor_);
  return *field_generators_[field->index()];
}


}  // namespace edf
}  // namespace compiler
}  // namespace protobuf
}  // namespace google
