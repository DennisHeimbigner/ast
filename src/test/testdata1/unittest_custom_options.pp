import descriptor.proto;
package .protobuf_unittest;
option .protobuf_unittest.file_opt1 = 9876543210;
import descriptor.proto;
enum .protobuf_unittest.MethodOpt1 {
  .protobuf_unittest.MethodOpt1.METHODOPT1_VAL1 = 1;
  .protobuf_unittest.MethodOpt1.METHODOPT1_VAL2 = 2;
}
message .protobuf_unittest.TestMessageWithCustomOptions {
  option .protobuf_unittest.TestMessageWithCustomOptions.message_set_wire_format = false;
  option .protobuf_unittest.TestMessageWithCustomOptions.message_opt1 = -56;
  optional string .protobuf_unittest.TestMessageWithCustomOptions.field1 = 1 [ctype = CORD, field_opt1 = 8765432109]
;
  enum .protobuf_unittest.TestMessageWithCustomOptions.AnEnum {
    .protobuf_unittest.TestMessageWithCustomOptions.AnEnum.ANENUM_VAL1 = 1;
    .protobuf_unittest.TestMessageWithCustomOptions.AnEnum.ANENUM_VAL2 = 2;
  }
}
message .protobuf_unittest.CustomOptionFooRequest {
}
message .protobuf_unittest.CustomOptionFooResponse {
}
service .protobuf_unittest.TestServiceWithCustomOptions {
  option .protobuf_unittest.TestServiceWithCustomOptions.service_opt1 = -9876543210;
  rpc .protobuf_unittest.TestServiceWithCustomOptions.Foo (.protobuf_unittest.CustomOptionFooRequest) returns (.protobuf_unittest.CustomOptionFooResponse);
}
message .protobuf_unittest.DummyMessageContainingEnum {
  enum .protobuf_unittest.DummyMessageContainingEnum.TestEnumType {
    .protobuf_unittest.DummyMessageContainingEnum.TestEnumType.TEST_OPTION_ENUM_TYPE1 = 22;
    .protobuf_unittest.DummyMessageContainingEnum.TestEnumType.TEST_OPTION_ENUM_TYPE2 = -23;
  }
}
message .protobuf_unittest.DummyMessageInvalidAsOptionType {
}
message .protobuf_unittest.CustomOptionMinIntegerValues {
  option .protobuf_unittest.CustomOptionMinIntegerValues.bool_opt = false;
  option .protobuf_unittest.CustomOptionMinIntegerValues.int32_opt = -0x80000000;
  option .protobuf_unittest.CustomOptionMinIntegerValues.int64_opt = -0x8000000000000000;
  option .protobuf_unittest.CustomOptionMinIntegerValues.uint32_opt = 0;
  option .protobuf_unittest.CustomOptionMinIntegerValues.uint64_opt = 0;
  option .protobuf_unittest.CustomOptionMinIntegerValues.sint32_opt = -0x80000000;
  option .protobuf_unittest.CustomOptionMinIntegerValues.sint64_opt = -0x8000000000000000;
  option .protobuf_unittest.CustomOptionMinIntegerValues.fixed32_opt = 0;
  option .protobuf_unittest.CustomOptionMinIntegerValues.fixed64_opt = 0;
  option .protobuf_unittest.CustomOptionMinIntegerValues.sfixed32_opt = -0x80000000;
  option .protobuf_unittest.CustomOptionMinIntegerValues.sfixed64_opt = -0x8000000000000000;
}
message .protobuf_unittest.CustomOptionMaxIntegerValues {
  option .protobuf_unittest.CustomOptionMaxIntegerValues.bool_opt = true;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.int32_opt = 0x7FFFFFFF;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.int64_opt = 0x7FFFFFFFFFFFFFFF;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.uint32_opt = 0xFFFFFFFF;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.uint64_opt = 0xFFFFFFFFFFFFFFFF;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.sint32_opt = 0x7FFFFFFF;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.sint64_opt = 0x7FFFFFFFFFFFFFFF;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.fixed32_opt = 0xFFFFFFFF;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.fixed64_opt = 0xFFFFFFFFFFFFFFFF;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.sfixed32_opt = 0x7FFFFFFF;
  option .protobuf_unittest.CustomOptionMaxIntegerValues.sfixed64_opt = 0x7FFFFFFFFFFFFFFF;
}
message .protobuf_unittest.CustomOptionOtherValues {
  option .protobuf_unittest.CustomOptionOtherValues.int32_opt = -100;
  option .protobuf_unittest.CustomOptionOtherValues.float_opt = 12.3456789;
  option .protobuf_unittest.CustomOptionOtherValues.double_opt = 1.234567890123456789;
  option .protobuf_unittest.CustomOptionOtherValues.string_opt = Hello, "World";
  option .protobuf_unittest.CustomOptionOtherValues.bytes_opt = Hello World;
  option .protobuf_unittest.CustomOptionOtherValues.enum_opt = TEST_OPTION_ENUM_TYPE2;
}
message .protobuf_unittest.SettingRealsFromPositiveInts {
  option .protobuf_unittest.SettingRealsFromPositiveInts.float_opt = 12;
  option .protobuf_unittest.SettingRealsFromPositiveInts.double_opt = 154;
}
message .protobuf_unittest.SettingRealsFromNegativeInts {
  option .protobuf_unittest.SettingRealsFromNegativeInts.float_opt = -12;
  option .protobuf_unittest.SettingRealsFromNegativeInts.double_opt = -154;
}
message .protobuf_unittest.ComplexOptionType1 {
  optional int32 .protobuf_unittest.ComplexOptionType1.foo = 1;
  optional int32 .protobuf_unittest.ComplexOptionType1.foo2 = 2;
  optional int32 .protobuf_unittest.ComplexOptionType1.foo3 = 3;
  extensions .protobuf_unittest.ComplexOptionType1.$extensions.72 {
  extensionrange 100 to 536870911
  }
}
message .protobuf_unittest.ComplexOptionType2 {
  optional .protobuf_unittest.ComplexOptionType1 .protobuf_unittest.ComplexOptionType2.bar = 1;
  optional int32 .protobuf_unittest.ComplexOptionType2.baz = 2;
  message .protobuf_unittest.ComplexOptionType2.ComplexOptionType4 {
    optional int32 .protobuf_unittest.ComplexOptionType2.ComplexOptionType4.waldo = 1;
  }
  optional .protobuf_unittest.ComplexOptionType2.ComplexOptionType4 .protobuf_unittest.ComplexOptionType2.fred = 3;
  extensions .protobuf_unittest.ComplexOptionType2.$extensions.73 {
  extensionrange 100 to 536870911
  }
}
message .protobuf_unittest.ComplexOptionType3 {
  optional int32 .protobuf_unittest.ComplexOptionType3.qux = 1;
  optional group .protobuf_unittest.ComplexOptionType3.ComplexOptionType5 = 2 {    optional int32 .protobuf_unittest.ComplexOptionType3.ComplexOptionType5.plugh = 3;
  }
}
extend .protobuf_unittest.$extend.74 {
  optional int32 .protobuf_unittest.$extend.quux = 7663707;
  optional .protobuf_unittest.ComplexOptionType3 .protobuf_unittest.$extend.corge = 7663442;
}
extend .protobuf_unittest.$extend.75 {
  optional int32 .protobuf_unittest.$extend.grault = 7650927;
  optional .protobuf_unittest.ComplexOptionType1 .protobuf_unittest.$extend.garply = 7649992;
}
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
  extensions .google.protobuf.FileOptions.$extensions.65 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.MessageOptions {
  optional bool .google.protobuf.MessageOptions.message_set_wire_format = 1 [default = false]
;
  optional bool .google.protobuf.MessageOptions.no_standard_descriptor_accessor = 2 [default = false]
;
  repeated .google.protobuf.UninterpretedOption .google.protobuf.MessageOptions.uninterpreted_option = 999;
  extensions .google.protobuf.MessageOptions.$extensions.66 {
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
  extensions .google.protobuf.FieldOptions.$extensions.67 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.EnumOptions {
  repeated .google.protobuf.UninterpretedOption .google.protobuf.EnumOptions.uninterpreted_option = 999;
  extensions .google.protobuf.EnumOptions.$extensions.68 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.EnumValueOptions {
  repeated .google.protobuf.UninterpretedOption .google.protobuf.EnumValueOptions.uninterpreted_option = 999;
  extensions .google.protobuf.EnumValueOptions.$extensions.69 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.ServiceOptions {
  repeated .google.protobuf.UninterpretedOption .google.protobuf.ServiceOptions.uninterpreted_option = 999;
  extensions .google.protobuf.ServiceOptions.$extensions.70 {
  extensionrange 1000 to 536870911
  }
}
message .google.protobuf.MethodOptions {
  repeated .google.protobuf.UninterpretedOption .google.protobuf.MethodOptions.uninterpreted_option = 999;
  extensions .google.protobuf.MethodOptions.$extensions.71 {
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
