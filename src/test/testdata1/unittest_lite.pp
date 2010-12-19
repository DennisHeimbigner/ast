import unittest_import_lite.proto;
package .protobuf_unittest;
import unittest_import_lite.proto;
option .protobuf_unittest.optimize_for = LITE_RUNTIME;
option .protobuf_unittest.java_package = com.google.protobuf;
message .protobuf_unittest.TestAllTypesLite {
  message .protobuf_unittest.TestAllTypesLite.NestedMessage {
    optional int32 .protobuf_unittest.TestAllTypesLite.NestedMessage.bb = 1;
  }
  enum .protobuf_unittest.TestAllTypesLite.NestedEnum {
    .protobuf_unittest.TestAllTypesLite.NestedEnum.FOO = 1;
    .protobuf_unittest.TestAllTypesLite.NestedEnum.BAR = 2;
    .protobuf_unittest.TestAllTypesLite.NestedEnum.BAZ = 3;
  }
  optional int32 .protobuf_unittest.TestAllTypesLite.optional_int32 = 1;
  optional int64 .protobuf_unittest.TestAllTypesLite.optional_int64 = 2;
  optional uint32 .protobuf_unittest.TestAllTypesLite.optional_uint32 = 3;
  optional uint64 .protobuf_unittest.TestAllTypesLite.optional_uint64 = 4;
  optional sint32 .protobuf_unittest.TestAllTypesLite.optional_sint32 = 5;
  optional sint64 .protobuf_unittest.TestAllTypesLite.optional_sint64 = 6;
  optional fixed32 .protobuf_unittest.TestAllTypesLite.optional_fixed32 = 7;
  optional fixed64 .protobuf_unittest.TestAllTypesLite.optional_fixed64 = 8;
  optional sfixed32 .protobuf_unittest.TestAllTypesLite.optional_sfixed32 = 9;
  optional sfixed64 .protobuf_unittest.TestAllTypesLite.optional_sfixed64 = 10;
  optional float .protobuf_unittest.TestAllTypesLite.optional_float = 11;
  optional double .protobuf_unittest.TestAllTypesLite.optional_double = 12;
  optional bool .protobuf_unittest.TestAllTypesLite.optional_bool = 13;
  optional string .protobuf_unittest.TestAllTypesLite.optional_string = 14;
  optional bytes .protobuf_unittest.TestAllTypesLite.optional_bytes = 15;
  optional group .protobuf_unittest.TestAllTypesLite.OptionalGroup = 16 {    optional int32 .protobuf_unittest.TestAllTypesLite.OptionalGroup.a = 17;
  }
  optional .protobuf_unittest.TestAllTypesLite.NestedMessage .protobuf_unittest.TestAllTypesLite.optional_nested_message = 18;
  optional .protobuf_unittest.ForeignMessageLite .protobuf_unittest.TestAllTypesLite.optional_foreign_message = 19;
  optional .protobuf_unittest_import.ImportMessageLite .protobuf_unittest.TestAllTypesLite.optional_import_message = 20;
  optional .protobuf_unittest.TestAllTypesLite.NestedEnum .protobuf_unittest.TestAllTypesLite.optional_nested_enum = 21;
  optional .protobuf_unittest.ForeignEnumLite .protobuf_unittest.TestAllTypesLite.optional_foreign_enum = 22;
  optional .protobuf_unittest_import.ImportEnumLite .protobuf_unittest.TestAllTypesLite.optional_import_enum = 23;
  optional string .protobuf_unittest.TestAllTypesLite.optional_string_piece = 24 [ctype = STRING_PIECE]
;
  optional string .protobuf_unittest.TestAllTypesLite.optional_cord = 25 [ctype = CORD]
;
  repeated int32 .protobuf_unittest.TestAllTypesLite.repeated_int32 = 31;
  repeated int64 .protobuf_unittest.TestAllTypesLite.repeated_int64 = 32;
  repeated uint32 .protobuf_unittest.TestAllTypesLite.repeated_uint32 = 33;
  repeated uint64 .protobuf_unittest.TestAllTypesLite.repeated_uint64 = 34;
  repeated sint32 .protobuf_unittest.TestAllTypesLite.repeated_sint32 = 35;
  repeated sint64 .protobuf_unittest.TestAllTypesLite.repeated_sint64 = 36;
  repeated fixed32 .protobuf_unittest.TestAllTypesLite.repeated_fixed32 = 37;
  repeated fixed64 .protobuf_unittest.TestAllTypesLite.repeated_fixed64 = 38;
  repeated sfixed32 .protobuf_unittest.TestAllTypesLite.repeated_sfixed32 = 39;
  repeated sfixed64 .protobuf_unittest.TestAllTypesLite.repeated_sfixed64 = 40;
  repeated float .protobuf_unittest.TestAllTypesLite.repeated_float = 41;
  repeated double .protobuf_unittest.TestAllTypesLite.repeated_double = 42;
  repeated bool .protobuf_unittest.TestAllTypesLite.repeated_bool = 43;
  repeated string .protobuf_unittest.TestAllTypesLite.repeated_string = 44;
  repeated bytes .protobuf_unittest.TestAllTypesLite.repeated_bytes = 45;
  repeated group .protobuf_unittest.TestAllTypesLite.RepeatedGroup = 46 {    optional int32 .protobuf_unittest.TestAllTypesLite.RepeatedGroup.a = 47;
  }
  repeated .protobuf_unittest.TestAllTypesLite.NestedMessage .protobuf_unittest.TestAllTypesLite.repeated_nested_message = 48;
  repeated .protobuf_unittest.ForeignMessageLite .protobuf_unittest.TestAllTypesLite.repeated_foreign_message = 49;
  repeated .protobuf_unittest_import.ImportMessageLite .protobuf_unittest.TestAllTypesLite.repeated_import_message = 50;
  repeated .protobuf_unittest.TestAllTypesLite.NestedEnum .protobuf_unittest.TestAllTypesLite.repeated_nested_enum = 51;
  repeated .protobuf_unittest.ForeignEnumLite .protobuf_unittest.TestAllTypesLite.repeated_foreign_enum = 52;
  repeated .protobuf_unittest_import.ImportEnumLite .protobuf_unittest.TestAllTypesLite.repeated_import_enum = 53;
  repeated string .protobuf_unittest.TestAllTypesLite.repeated_string_piece = 54 [ctype = STRING_PIECE]
;
  repeated string .protobuf_unittest.TestAllTypesLite.repeated_cord = 55 [ctype = CORD]
;
  optional int32 .protobuf_unittest.TestAllTypesLite.default_int32 = 61 [default = 41]
;
  optional int64 .protobuf_unittest.TestAllTypesLite.default_int64 = 62 [default = 42]
;
  optional uint32 .protobuf_unittest.TestAllTypesLite.default_uint32 = 63 [default = 43]
;
  optional uint64 .protobuf_unittest.TestAllTypesLite.default_uint64 = 64 [default = 44]
;
  optional sint32 .protobuf_unittest.TestAllTypesLite.default_sint32 = 65 [default = -45]
;
  optional sint64 .protobuf_unittest.TestAllTypesLite.default_sint64 = 66 [default = 46]
;
  optional fixed32 .protobuf_unittest.TestAllTypesLite.default_fixed32 = 67 [default = 47]
;
  optional fixed64 .protobuf_unittest.TestAllTypesLite.default_fixed64 = 68 [default = 48]
;
  optional sfixed32 .protobuf_unittest.TestAllTypesLite.default_sfixed32 = 69 [default = 49]
;
  optional sfixed64 .protobuf_unittest.TestAllTypesLite.default_sfixed64 = 70 [default = -50]
;
  optional float .protobuf_unittest.TestAllTypesLite.default_float = 71 [default = 51.5]
;
  optional double .protobuf_unittest.TestAllTypesLite.default_double = 72 [default = 52e3]
;
  optional bool .protobuf_unittest.TestAllTypesLite.default_bool = 73 [default = true]
;
  optional string .protobuf_unittest.TestAllTypesLite.default_string = 74 [default = hello]
;
  optional bytes .protobuf_unittest.TestAllTypesLite.default_bytes = 75 [default = world]
;
  optional .protobuf_unittest.TestAllTypesLite.NestedEnum .protobuf_unittest.TestAllTypesLite.default_nested_enum = 81 [default = BAR]
;
  optional .protobuf_unittest.ForeignEnumLite .protobuf_unittest.TestAllTypesLite.default_foreign_enum = 82 [default = FOREIGN_LITE_BAR]
;
  optional .protobuf_unittest_import.ImportEnumLite .protobuf_unittest.TestAllTypesLite.default_import_enum = 83 [default = IMPORT_LITE_BAR]
;
  optional string .protobuf_unittest.TestAllTypesLite.default_string_piece = 84 [ctype = STRING_PIECE, default = abc]
;
  optional string .protobuf_unittest.TestAllTypesLite.default_cord = 85 [ctype = CORD, default = 123]
;
}
message .protobuf_unittest.ForeignMessageLite {
  optional int32 .protobuf_unittest.ForeignMessageLite.c = 1;
}
enum .protobuf_unittest.ForeignEnumLite {
  .protobuf_unittest.ForeignEnumLite.FOREIGN_LITE_FOO = 4;
  .protobuf_unittest.ForeignEnumLite.FOREIGN_LITE_BAR = 5;
  .protobuf_unittest.ForeignEnumLite.FOREIGN_LITE_BAZ = 6;
}
message .protobuf_unittest.TestPackedTypesLite {
  repeated int32 .protobuf_unittest.TestPackedTypesLite.packed_int32 = 90 [packed = true]
;
  repeated int64 .protobuf_unittest.TestPackedTypesLite.packed_int64 = 91 [packed = true]
;
  repeated uint32 .protobuf_unittest.TestPackedTypesLite.packed_uint32 = 92 [packed = true]
;
  repeated uint64 .protobuf_unittest.TestPackedTypesLite.packed_uint64 = 93 [packed = true]
;
  repeated sint32 .protobuf_unittest.TestPackedTypesLite.packed_sint32 = 94 [packed = true]
;
  repeated sint64 .protobuf_unittest.TestPackedTypesLite.packed_sint64 = 95 [packed = true]
;
  repeated fixed32 .protobuf_unittest.TestPackedTypesLite.packed_fixed32 = 96 [packed = true]
;
  repeated fixed64 .protobuf_unittest.TestPackedTypesLite.packed_fixed64 = 97 [packed = true]
;
  repeated sfixed32 .protobuf_unittest.TestPackedTypesLite.packed_sfixed32 = 98 [packed = true]
;
  repeated sfixed64 .protobuf_unittest.TestPackedTypesLite.packed_sfixed64 = 99 [packed = true]
;
  repeated float .protobuf_unittest.TestPackedTypesLite.packed_float = 100 [packed = true]
;
  repeated double .protobuf_unittest.TestPackedTypesLite.packed_double = 101 [packed = true]
;
  repeated bool .protobuf_unittest.TestPackedTypesLite.packed_bool = 102 [packed = true]
;
  repeated .protobuf_unittest.ForeignEnumLite .protobuf_unittest.TestPackedTypesLite.packed_enum = 103 [packed = true]
;
}
message .protobuf_unittest.TestAllExtensionsLite {
  extensions .protobuf_unittest.TestAllExtensionsLite.$extensions.117 {
  extensionrange 1 to 536870911
  }
}
extend .protobuf_unittest.$extend.118 {
  optional int32 .protobuf_unittest.$extend.optional_int32_extension_lite = 1;
  optional int64 .protobuf_unittest.$extend.optional_int64_extension_lite = 2;
  optional uint32 .protobuf_unittest.$extend.optional_uint32_extension_lite = 3;
  optional uint64 .protobuf_unittest.$extend.optional_uint64_extension_lite = 4;
  optional sint32 .protobuf_unittest.$extend.optional_sint32_extension_lite = 5;
  optional sint64 .protobuf_unittest.$extend.optional_sint64_extension_lite = 6;
  optional fixed32 .protobuf_unittest.$extend.optional_fixed32_extension_lite = 7;
  optional fixed64 .protobuf_unittest.$extend.optional_fixed64_extension_lite = 8;
  optional sfixed32 .protobuf_unittest.$extend.optional_sfixed32_extension_lite = 9;
  optional sfixed64 .protobuf_unittest.$extend.optional_sfixed64_extension_lite = 10;
  optional float .protobuf_unittest.$extend.optional_float_extension_lite = 11;
  optional double .protobuf_unittest.$extend.optional_double_extension_lite = 12;
  optional bool .protobuf_unittest.$extend.optional_bool_extension_lite = 13;
  optional string .protobuf_unittest.$extend.optional_string_extension_lite = 14;
  optional bytes .protobuf_unittest.$extend.optional_bytes_extension_lite = 15;
  optional .protobuf_unittest.TestAllTypesLite.NestedMessage .protobuf_unittest.$extend.optional_nested_message_extension_lite = 18;
  optional .protobuf_unittest.ForeignMessageLite .protobuf_unittest.$extend.optional_foreign_message_extension_lite = 19;
  optional .protobuf_unittest_import.ImportMessageLite .protobuf_unittest.$extend.optional_import_message_extension_lite = 20;
  optional .protobuf_unittest.TestAllTypesLite.NestedEnum .protobuf_unittest.$extend.optional_nested_enum_extension_lite = 21;
  optional .protobuf_unittest.ForeignEnumLite .protobuf_unittest.$extend.optional_foreign_enum_extension_lite = 22;
  optional .protobuf_unittest_import.ImportEnumLite .protobuf_unittest.$extend.optional_import_enum_extension_lite = 23;
  optional string .protobuf_unittest.$extend.optional_string_piece_extension_lite = 24 [ctype = STRING_PIECE]
;
  optional string .protobuf_unittest.$extend.optional_cord_extension_lite = 25 [ctype = CORD]
;
  repeated int32 .protobuf_unittest.$extend.repeated_int32_extension_lite = 31;
  repeated int64 .protobuf_unittest.$extend.repeated_int64_extension_lite = 32;
  repeated uint32 .protobuf_unittest.$extend.repeated_uint32_extension_lite = 33;
  repeated uint64 .protobuf_unittest.$extend.repeated_uint64_extension_lite = 34;
  repeated sint32 .protobuf_unittest.$extend.repeated_sint32_extension_lite = 35;
  repeated sint64 .protobuf_unittest.$extend.repeated_sint64_extension_lite = 36;
  repeated fixed32 .protobuf_unittest.$extend.repeated_fixed32_extension_lite = 37;
  repeated fixed64 .protobuf_unittest.$extend.repeated_fixed64_extension_lite = 38;
  repeated sfixed32 .protobuf_unittest.$extend.repeated_sfixed32_extension_lite = 39;
  repeated sfixed64 .protobuf_unittest.$extend.repeated_sfixed64_extension_lite = 40;
  repeated float .protobuf_unittest.$extend.repeated_float_extension_lite = 41;
  repeated double .protobuf_unittest.$extend.repeated_double_extension_lite = 42;
  repeated bool .protobuf_unittest.$extend.repeated_bool_extension_lite = 43;
  repeated string .protobuf_unittest.$extend.repeated_string_extension_lite = 44;
  repeated bytes .protobuf_unittest.$extend.repeated_bytes_extension_lite = 45;
  repeated .protobuf_unittest.TestAllTypesLite.NestedMessage .protobuf_unittest.$extend.repeated_nested_message_extension_lite = 48;
  repeated .protobuf_unittest.ForeignMessageLite .protobuf_unittest.$extend.repeated_foreign_message_extension_lite = 49;
  repeated .protobuf_unittest_import.ImportMessageLite .protobuf_unittest.$extend.repeated_import_message_extension_lite = 50;
  repeated .protobuf_unittest.TestAllTypesLite.NestedEnum .protobuf_unittest.$extend.repeated_nested_enum_extension_lite = 51;
  repeated .protobuf_unittest.ForeignEnumLite .protobuf_unittest.$extend.repeated_foreign_enum_extension_lite = 52;
  repeated .protobuf_unittest_import.ImportEnumLite .protobuf_unittest.$extend.repeated_import_enum_extension_lite = 53;
  repeated string .protobuf_unittest.$extend.repeated_string_piece_extension_lite = 54 [ctype = STRING_PIECE]
;
  repeated string .protobuf_unittest.$extend.repeated_cord_extension_lite = 55 [ctype = CORD]
;
  optional int32 .protobuf_unittest.$extend.default_int32_extension_lite = 61 [default = 41]
;
  optional int64 .protobuf_unittest.$extend.default_int64_extension_lite = 62 [default = 42]
;
  optional uint32 .protobuf_unittest.$extend.default_uint32_extension_lite = 63 [default = 43]
;
  optional uint64 .protobuf_unittest.$extend.default_uint64_extension_lite = 64 [default = 44]
;
  optional sint32 .protobuf_unittest.$extend.default_sint32_extension_lite = 65 [default = -45]
;
  optional sint64 .protobuf_unittest.$extend.default_sint64_extension_lite = 66 [default = 46]
;
  optional fixed32 .protobuf_unittest.$extend.default_fixed32_extension_lite = 67 [default = 47]
;
  optional fixed64 .protobuf_unittest.$extend.default_fixed64_extension_lite = 68 [default = 48]
;
  optional sfixed32 .protobuf_unittest.$extend.default_sfixed32_extension_lite = 69 [default = 49]
;
  optional sfixed64 .protobuf_unittest.$extend.default_sfixed64_extension_lite = 70 [default = -50]
;
  optional float .protobuf_unittest.$extend.default_float_extension_lite = 71 [default = 51.5]
;
  optional double .protobuf_unittest.$extend.default_double_extension_lite = 72 [default = 52e3]
;
  optional bool .protobuf_unittest.$extend.default_bool_extension_lite = 73 [default = true]
;
  optional string .protobuf_unittest.$extend.default_string_extension_lite = 74 [default = hello]
;
  optional bytes .protobuf_unittest.$extend.default_bytes_extension_lite = 75 [default = world]
;
  optional .protobuf_unittest.TestAllTypesLite.NestedEnum .protobuf_unittest.$extend.default_nested_enum_extension_lite = 81 [default = BAR]
;
  optional .protobuf_unittest.ForeignEnumLite .protobuf_unittest.$extend.default_foreign_enum_extension_lite = 82 [default = FOREIGN_LITE_BAR]
;
  optional .protobuf_unittest_import.ImportEnumLite .protobuf_unittest.$extend.default_import_enum_extension_lite = 83 [default = IMPORT_LITE_BAR]
;
  optional string .protobuf_unittest.$extend.default_string_piece_extension_lite = 84 [ctype = STRING_PIECE, default = abc]
;
  optional string .protobuf_unittest.$extend.default_cord_extension_lite = 85 [ctype = CORD, default = 123]
;
}
message .protobuf_unittest.TestPackedExtensionsLite {
  extensions .protobuf_unittest.TestPackedExtensionsLite.$extensions.119 {
  extensionrange 1 to 536870911
  }
}
extend .protobuf_unittest.$extend.120 {
  repeated int32 .protobuf_unittest.$extend.packed_int32_extension_lite = 90 [packed = true]
;
  repeated int64 .protobuf_unittest.$extend.packed_int64_extension_lite = 91 [packed = true]
;
  repeated uint32 .protobuf_unittest.$extend.packed_uint32_extension_lite = 92 [packed = true]
;
  repeated uint64 .protobuf_unittest.$extend.packed_uint64_extension_lite = 93 [packed = true]
;
  repeated sint32 .protobuf_unittest.$extend.packed_sint32_extension_lite = 94 [packed = true]
;
  repeated sint64 .protobuf_unittest.$extend.packed_sint64_extension_lite = 95 [packed = true]
;
  repeated fixed32 .protobuf_unittest.$extend.packed_fixed32_extension_lite = 96 [packed = true]
;
  repeated fixed64 .protobuf_unittest.$extend.packed_fixed64_extension_lite = 97 [packed = true]
;
  repeated sfixed32 .protobuf_unittest.$extend.packed_sfixed32_extension_lite = 98 [packed = true]
;
  repeated sfixed64 .protobuf_unittest.$extend.packed_sfixed64_extension_lite = 99 [packed = true]
;
  repeated float .protobuf_unittest.$extend.packed_float_extension_lite = 100 [packed = true]
;
  repeated double .protobuf_unittest.$extend.packed_double_extension_lite = 101 [packed = true]
;
  repeated bool .protobuf_unittest.$extend.packed_bool_extension_lite = 102 [packed = true]
;
  repeated .protobuf_unittest.ForeignEnumLite .protobuf_unittest.$extend.packed_enum_extension_lite = 103 [packed = true]
;
}
message .protobuf_unittest.TestNestedExtensionLite {
  extend .protobuf_unittest.TestNestedExtensionLite.$extend.121 {
    optional int32 .protobuf_unittest.TestNestedExtensionLite.$extend.nested_extension = 12345;
  }
}
message .protobuf_unittest.TestDeprecatedLite {
  optional int32 .protobuf_unittest.TestDeprecatedLite.deprecated_field = 1 [deprecated = true]
;
}
package .protobuf_unittest_import;
option .protobuf_unittest_import.optimize_for = LITE_RUNTIME;
option .protobuf_unittest_import.java_package = com.google.protobuf;
message .protobuf_unittest_import.ImportMessageLite {
  optional int32 .protobuf_unittest_import.ImportMessageLite.d = 1;
}
enum .protobuf_unittest_import.ImportEnumLite {
  .protobuf_unittest_import.ImportEnumLite.IMPORT_LITE_FOO = 7;
  .protobuf_unittest_import.ImportEnumLite.IMPORT_LITE_BAR = 8;
  .protobuf_unittest_import.ImportEnumLite.IMPORT_LITE_BAZ = 9;
}
