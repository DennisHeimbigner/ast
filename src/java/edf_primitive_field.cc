// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.    All rights reserved.
// http://code.google.com/p/protobuf/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//         * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//         * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//         * Neither the name of Google Inc. nor the names of its
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
//    Based on original Protocol Buffers design by
//    Sanjay Ghemawat, Jeff Dean, and others.

#include <edf_common.h>

#include <edf_primitive_field.h>
#include <edf_helpers.h>
#include <google/protobuf/io/printer.h>
#include <google/protobuf/wire_format.h>
#include <google/protobuf/stubs/strutil.h>

namespace google {
namespace protobuf {
namespace compiler {
namespace edf {

using internal::WireFormatLite;

namespace {

// For encodings with fixed sizes, returns that size in bytes.    Otherwise
// returns -1.
int FixedSize(FieldDescriptor::Type type) {
    switch (type) {
        case FieldDescriptor::TYPE_INT32     : return -1;
        case FieldDescriptor::TYPE_INT64     : return -1;
        case FieldDescriptor::TYPE_UINT32    : return -1;
        case FieldDescriptor::TYPE_UINT64    : return -1;
        case FieldDescriptor::TYPE_SINT32    : return -1;
        case FieldDescriptor::TYPE_SINT64    : return -1;
        case FieldDescriptor::TYPE_FIXED32 : return WireFormatLite::kFixed32Size;
        case FieldDescriptor::TYPE_FIXED64 : return WireFormatLite::kFixed64Size;
        case FieldDescriptor::TYPE_SFIXED32: return WireFormatLite::kSFixed32Size;
        case FieldDescriptor::TYPE_SFIXED64: return WireFormatLite::kSFixed64Size;
        case FieldDescriptor::TYPE_FLOAT     : return WireFormatLite::kFloatSize;
        case FieldDescriptor::TYPE_DOUBLE    : return WireFormatLite::kDoubleSize;

        case FieldDescriptor::TYPE_BOOL        : return WireFormatLite::kBoolSize;
        case FieldDescriptor::TYPE_ENUM        : return -1;

        case FieldDescriptor::TYPE_STRING    : return -1;
        case FieldDescriptor::TYPE_BYTES     : return -1;
        case FieldDescriptor::TYPE_GROUP     : return -1;
        case FieldDescriptor::TYPE_MESSAGE : return -1;

        // No default because we want the compiler to complain if any new
        // types are added.
    }
    GOOGLE_LOG(FATAL) << "Can't get here.";
    return -1;
}

#ifdef IGNORE
void SetPrimitiveVariables(const FieldDescriptor* descriptor,
                                                     map<string, string>* variables) {
    SetCommonFieldVariables(descriptor, variables);
    (*variables)["type"] = PrimitiveTypeName(descriptor->edf_type());
    int fixed_size = FixedSize(descriptor->type());
    if (fixed_size != -1) {
        (*variables)["fixed_size"] = SimpleItoa(fixed_size);
    }
    (*variables)["wire_format_field_type"] =
            "::google::protobuf::internal::WireFormatLite::" + FieldDescriptorProto_Type_Name(
                    static_cast<FieldDescriptorProto_Type>(descriptor->type()));
}
#endif

}    // namespace

// ===================================================================

PrimitiveFieldGenerator::
PrimitiveFieldGenerator(const FieldDescriptor* descriptor)
    : FieldGenerator(descriptor)
{
    //SetPrimitiveVariables(descriptor, &variables_);
}

PrimitiveFieldGenerator::~PrimitiveFieldGenerator() {}


}  // namespace edf
}  // namespace compiler
}  // namespace protobuf
}  // namespace google
