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

#include <edf_generator.h>

#include <vector>
#include <utility>

#include <edf_file.h>
#include <edf_helpers.h>
#include <google/protobuf/io/printer.h>
#include <google/protobuf/io/zero_copy_stream.h>
#include <google/protobuf/descriptor.pb.h>

namespace google {
namespace protobuf {
namespace compiler {
namespace edf {

EDFGenerator::EDFGenerator() {}
EDFGenerator::~EDFGenerator() {}

bool EDFGenerator::Generate(const FileDescriptor* file,
                            const string& parameter,
                            OutputDirectory* output_directory,
                            string* error) const {
  vector<pair<string, string> > options;
  ParseGeneratorParameter(parameter, &options);

  // -----------------------------------------------------------------
  // parse generator options

  string dllexport_decl;

  for (int i = 0; i < options.size(); i++) {
    if (options[i].first == "dllexport_decl") {
      dllexport_decl = options[i].second;
    } else {
      *error = "Unknown generator option: " + options[i].first;
      return false;
    }
  }

  // -----------------------------------------------------------------


  string basename = StripProto(file->name());

  FileGenerator file_generator(file, dllexport_decl);

  // Generate header.
  {
    scoped_ptr<io::ZeroCopyOutputStream> output(
      output_directory->Open(basename + ".h"));
    Printer printer(output.get(), '$');
    file_generator.GenerateHeader(&printer);
  }

  // Generate c file.
  {
    scoped_ptr<io::ZeroCopyOutputStream> output(
      output_directory->Open(basename + ".c"));
    Printer printer(output.get(), '$');
    file_generator.GenerateSource(&printer);
  }

  return true;
}

}  // namespace edf
}  // namespace compiler
}  // namespace protobuf
}  // namespace google