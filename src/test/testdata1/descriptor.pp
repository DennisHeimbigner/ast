package .google.protobuf;
option .google.protobuf.java_package = com.google.protobuf;
option .google.protobuf.java_outer_classname = DescriptorProtos;
option .google.protobuf.optimize_for = SPEED;
message .google.protobuf.FileDescriptorSet {
  repeated .google.protobuf.FileDescriptorProto .google.protobuf.FileDescriptorSet.file = 1;
}
message .google.protobuf.FileDescriptorProto {
  optional string .google.protobuf.FileDescriptorProto.name = 1;
  optional string .google.protobuf.FileDescriptorProto.package = 2;
  repeated string .google.protobuf.FileDescriptorProto.dependency = 3;
  repeated .google.protobuf.DescriptorProto .google.protobuf.FileDescriptorProto.message_type = 4;
  repeated .google.protobuf.EnumDescriptorProto .google.protobuf.FileDescriptorProto.enum_type = 5;
  repeated .google.protobuf.ServiceDescriptorProto .google.protobuf.FileDescriptorProto.service = 6;
  repeated .google.protobuf.FieldDescriptorProto .google.protobuf.FileDescriptorProto.extension = 7;
  optional .google.protobuf.FileOptions .google.protobuf.FileDescriptorProto.options = 8;
}
message .google.protobuf.DescriptorProto {
  optional string .google.protobuf.DescriptorProto.name = 1;
  repeated .google.protobuf.FieldDescriptorProto .google.protobuf.DescriptorProto.field = 2;
  repeated .google.protobuf.FieldDescriptorProto .google.protobuf.DescriptorProto.extension = 6;
  repeated .google.protobuf.DescriptorProto .google.protobuf.DescriptorProto.nested_type = 3;
  repeated .google.protobuf.EnumDescriptorProto .google.protobuf.DescriptorProto.enum_type = 4;
  message .google.protobuf.DescriptorProto.ExtensionRange {
    optional int32 .google.protobuf.DescriptorProto.ExtensionRange.start = 1;
    optional int32 .google.protobuf.DescriptorProto.ExtensionRange.end = 2;
  }
  repeated .google.protobuf.DescriptorProto.ExtensionRange .google.protobuf.DescriptorProto.extension_range = 5;
  optional .google.protobuf.MessageOptions .google.protobuf.DescriptorProto.options = 7;
}
message .google.protobuf.FieldDescriptorProto {
  enum .google.protobuf.FieldDescriptorProto.Type {
    .google.protobuf.FieldDescriptorProto.Type.TYPE_DOUBLE = 1;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_FLOAT = 2;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_INT64 = 3;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_UINT64 = 4;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_INT32 = 5;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_FIXED64 = 6;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_FIXED32 = 7;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_BOOL = 8;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_STRING = 9;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_GROUP = 10;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_MESSAGE = 11;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_BYTES = 12;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_UINT32 = 13;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_ENUM = 14;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_SFIXED32 = 15;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_SFIXED64 = 16;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_SINT32 = 17;
    .google.protobuf.FieldDescriptorProto.Type.TYPE_SINT64 = 18;
  }
  enum .google.protobuf.FieldDescriptorProto.Label {
    .google.protobuf.FieldDescriptorProto.Label.LABEL_OPTIONAL = 1;
    .google.protobuf.FieldDescriptorProto.Label.LABEL_REQUIRED = 2;
    .google.protobuf.FieldDescriptorProto.Label.LABEL_REPEATED = 3;
  }
  optional string .google.protobuf.FieldDescriptorProto.name = 1;
  optional int32 .google.protobuf.FieldDescriptorProto.number = 3;
  optional .google.protobuf.FieldDescriptorProto.Label .google.protobuf.FieldDescriptorProto.label = 4;
  optional .google.protobuf.FieldDescriptorProto.Type .google.protobuf.FieldDescriptorProto.type = 5;
  optional string .google.protobuf.FieldDescriptorProto.type_name = 6;
  optional string .google.protobuf.FieldDescriptorProto.extendee = 2;
  optional string .google.protobuf.FieldDescriptorProto.default_value = 7;
  optional .google.protobuf.FieldOptions .google.protobuf.FieldDescriptorProto.options = 8;
}
message .google.protobuf.EnumDescriptorProto {
  optional string .google.protobuf.EnumDescriptorProto.name = 1;
  repeated .google.protobuf.EnumValueDescriptorProto .google.protobuf.EnumDescriptorProto.value = 2;
  optional .google.protobuf.EnumOptions .google.protobuf.EnumDescriptorProto.options = 3;
}
message .google.protobuf.EnumValueDescriptorProto {
  optional string .google.protobuf.EnumValueDescriptorProto.name = 1;
  optional int32 .google.protobuf.EnumValueDescriptorProto.number = 2;
  optional .google.protobuf.EnumValueOptions .google.protobuf.EnumValueDescriptorProto.options = 3;
}
message .google.protobuf.ServiceDescriptorProto {
  optional string .google.protobuf.ServiceDescriptorProto.name = 1;
  repeated .google.protobuf.MethodDescriptorProto .google.protobuf.ServiceDescriptorProto.method = 2;
  optional .google.protobuf.ServiceOptions .google.protobuf.ServiceDescriptorProto.options = 3;
}
message .google.protobuf.MethodDescriptorProto {
  optional string .google.protobuf.MethodDescriptorProto.name = 1;
  optional string .google.protobuf.MethodDescriptorProto.input_type = 2;
  optional string .google.protobuf.MethodDescriptorProto.output_type = 3;
  optional .google.protobuf.MethodOptions .google.protobuf.MethodDescriptorProto.options = 4;
}
message .google.protobuf.FileOptions {
  optional string .google.protobuf.FileOptions.java_package = 1;
  optional string .google.protobuf.FileOptions.java_outer_classname = 8;
  optional bool .google.protobuf.FileOptions.java_multiple_files = 10 [default = false]
;
  enum .google.protobuf.FileOptions.OptimizeMode {
    .google.protobuf.FileOptions.OptimizeMode.SPEED = 1;
    .google.protobuf.FileOptions.OptimizeMode.CODE_SIZE = 2;
    .google.protobuf.FileOptions.OptimizeMode.LITE_RUNTIME = 3;
  }
  optional .google.protobuf.FileOptions.OptimizeMode .google.protobuf.FileOptions.optimize_for = 9 [default = SPEED]
;
  optional bool .google.protobuf.FileOptions.cc_generic_services = 16 [default = true]
;
  optional bool .google.protobuf.FileOptions.java_generic_services = 17 [default = true]
;
  optional bool .google.protobuf.FileOptions.py_generic_services = 18 [default = true]
;
  repeated .google.protobuf.UninterpretedOption .google.protobuf.FileOptions.uninterpreted_option = 999;
  extensions .google.protobuf.FileOptions.$extensions.1 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.MessageOptions {
  optional bool .google.protobuf.MessageOptions.message_set_wire_format = 1 [default = false]
;
  optional bool .google.protobuf.MessageOptions.no_standard_descriptor_accessor = 2 [default = false]
;
  repeated .google.protobuf.UninterpretedOption .google.protobuf.MessageOptions.uninterpreted_option = 999;
  extensions .google.protobuf.MessageOptions.$extensions.2 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.FieldOptions {
  optional .google.protobuf.FieldOptions.CType .google.protobuf.FieldOptions.ctype = 1 [default = STRING]
;
  enum .google.protobuf.FieldOptions.CType {
    .google.protobuf.FieldOptions.CType.STRING = 0;
    .google.protobuf.FieldOptions.CType.CORD = 1;
    .google.protobuf.FieldOptions.CType.STRING_PIECE = 2;
  }
  optional bool .google.protobuf.FieldOptions.packed = 2;
  optional bool .google.protobuf.FieldOptions.deprecated = 3 [default = false]
;
  optional string .google.protobuf.FieldOptions.experimental_map_key = 9;
  repeated .google.protobuf.UninterpretedOption .google.protobuf.FieldOptions.uninterpreted_option = 999;
  extensions .google.protobuf.FieldOptions.$extensions.3 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.EnumOptions {
  repeated .google.protobuf.UninterpretedOption .google.protobuf.EnumOptions.uninterpreted_option = 999;
  extensions .google.protobuf.EnumOptions.$extensions.4 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.EnumValueOptions {
  repeated .google.protobuf.UninterpretedOption .google.protobuf.EnumValueOptions.uninterpreted_option = 999;
  extensions .google.protobuf.EnumValueOptions.$extensions.5 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.ServiceOptions {
  repeated .google.protobuf.UninterpretedOption .google.protobuf.ServiceOptions.uninterpreted_option = 999;
  extensions .google.protobuf.ServiceOptions.$extensions.6 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.MethodOptions {
  repeated .google.protobuf.UninterpretedOption .google.protobuf.MethodOptions.uninterpreted_option = 999;
  extensions .google.protobuf.MethodOptions.$extensions.7 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.UninterpretedOption {
  message .google.protobuf.UninterpretedOption.NamePart {
    required string .google.protobuf.UninterpretedOption.NamePart.name_part = 1;
    required bool .google.protobuf.UninterpretedOption.NamePart.is_extension = 2;
  }
  repeated .google.protobuf.UninterpretedOption.NamePart .google.protobuf.UninterpretedOption.name = 2;
  optional string .google.protobuf.UninterpretedOption.identifier_value = 3;
  optional uint64 .google.protobuf.UninterpretedOption.positive_int_value = 4;
  optional int64 .google.protobuf.UninterpretedOption.negative_int_value = 5;
  optional double .google.protobuf.UninterpretedOption.double_value = 6;
  optional bytes .google.protobuf.UninterpretedOption.string_value = 7;
}
