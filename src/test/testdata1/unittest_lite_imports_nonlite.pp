import unittest.proto;
import unittest_import.proto;
package .protobuf_unittest_lite_imports_nonlite;
import unittest.proto;
option .protobuf_unittest_lite_imports_nonlite.optimize_for = LITE_RUNTIME;
message .protobuf_unittest_lite_imports_nonlite.TestLiteImportsNonlite {
  optional .protobuf_unittest.TestAllTypes .protobuf_unittest_lite_imports_nonlite.TestLiteImportsNonlite.message = 1;
}
package .protobuf_unittest;
import unittest_import.proto;
option .protobuf_unittest.optimize_for = SPEED;
option .protobuf_unittest.java_outer_classname = UnittestProto;
message .protobuf_unittest.TestAllTypes {
  message .protobuf_unittest.TestAllTypes.NestedMessage {
    optional int32 .protobuf_unittest.TestAllTypes.NestedMessage.bb = 1;
  }
  enum .protobuf_unittest.TestAllTypes.NestedEnum {
    .protobuf_unittest.TestAllTypes.NestedEnum.FOO = 1;
    .protobuf_unittest.TestAllTypes.NestedEnum.BAR = 2;
    .protobuf_unittest.TestAllTypes.NestedEnum.BAZ = 3;
  }
  optional int32 .protobuf_unittest.TestAllTypes.optional_int32 = 1;
  optional int64 .protobuf_unittest.TestAllTypes.optional_int64 = 2;
  optional uint32 .protobuf_unittest.TestAllTypes.optional_uint32 = 3;
  optional uint64 .protobuf_unittest.TestAllTypes.optional_uint64 = 4;
  optional sint32 .protobuf_unittest.TestAllTypes.optional_sint32 = 5;
  optional sint64 .protobuf_unittest.TestAllTypes.optional_sint64 = 6;
  optional fixed32 .protobuf_unittest.TestAllTypes.optional_fixed32 = 7;
  optional fixed64 .protobuf_unittest.TestAllTypes.optional_fixed64 = 8;
  optional sfixed32 .protobuf_unittest.TestAllTypes.optional_sfixed32 = 9;
  optional sfixed64 .protobuf_unittest.TestAllTypes.optional_sfixed64 = 10;
  optional float .protobuf_unittest.TestAllTypes.optional_float = 11;
  optional double .protobuf_unittest.TestAllTypes.optional_double = 12;
  optional bool .protobuf_unittest.TestAllTypes.optional_bool = 13;
  optional string .protobuf_unittest.TestAllTypes.optional_string = 14;
  optional bytes .protobuf_unittest.TestAllTypes.optional_bytes = 15;
  optional group .protobuf_unittest.TestAllTypes.OptionalGroup = 16 {    optional int32 .protobuf_unittest.TestAllTypes.OptionalGroup.a = 17;
  }
  optional .protobuf_unittest.TestAllTypes.NestedMessage .protobuf_unittest.TestAllTypes.optional_nested_message = 18;
  optional .protobuf_unittest.ForeignMessage .protobuf_unittest.TestAllTypes.optional_foreign_message = 19;
  optional .protobuf_unittest_import.ImportMessage .protobuf_unittest.TestAllTypes.optional_import_message = 20;
  optional .protobuf_unittest.TestAllTypes.NestedEnum .protobuf_unittest.TestAllTypes.optional_nested_enum = 21;
  optional .protobuf_unittest.ForeignEnum .protobuf_unittest.TestAllTypes.optional_foreign_enum = 22;
  optional .protobuf_unittest_import.ImportEnum .protobuf_unittest.TestAllTypes.optional_import_enum = 23;
  optional string .protobuf_unittest.TestAllTypes.optional_string_piece = 24 [ctype = STRING_PIECE]
;
  optional string .protobuf_unittest.TestAllTypes.optional_cord = 25 [ctype = CORD]
;
  repeated int32 .protobuf_unittest.TestAllTypes.repeated_int32 = 31;
  repeated int64 .protobuf_unittest.TestAllTypes.repeated_int64 = 32;
  repeated uint32 .protobuf_unittest.TestAllTypes.repeated_uint32 = 33;
  repeated uint64 .protobuf_unittest.TestAllTypes.repeated_uint64 = 34;
  repeated sint32 .protobuf_unittest.TestAllTypes.repeated_sint32 = 35;
  repeated sint64 .protobuf_unittest.TestAllTypes.repeated_sint64 = 36;
  repeated fixed32 .protobuf_unittest.TestAllTypes.repeated_fixed32 = 37;
  repeated fixed64 .protobuf_unittest.TestAllTypes.repeated_fixed64 = 38;
  repeated sfixed32 .protobuf_unittest.TestAllTypes.repeated_sfixed32 = 39;
  repeated sfixed64 .protobuf_unittest.TestAllTypes.repeated_sfixed64 = 40;
  repeated float .protobuf_unittest.TestAllTypes.repeated_float = 41;
  repeated double .protobuf_unittest.TestAllTypes.repeated_double = 42;
  repeated bool .protobuf_unittest.TestAllTypes.repeated_bool = 43;
  repeated string .protobuf_unittest.TestAllTypes.repeated_string = 44;
  repeated bytes .protobuf_unittest.TestAllTypes.repeated_bytes = 45;
  repeated group .protobuf_unittest.TestAllTypes.RepeatedGroup = 46 {    optional int32 .protobuf_unittest.TestAllTypes.RepeatedGroup.a = 47;
  }
  repeated .protobuf_unittest.TestAllTypes.NestedMessage .protobuf_unittest.TestAllTypes.repeated_nested_message = 48;
  repeated .protobuf_unittest.ForeignMessage .protobuf_unittest.TestAllTypes.repeated_foreign_message = 49;
  repeated .protobuf_unittest_import.ImportMessage .protobuf_unittest.TestAllTypes.repeated_import_message = 50;
  repeated .protobuf_unittest.TestAllTypes.NestedEnum .protobuf_unittest.TestAllTypes.repeated_nested_enum = 51;
  repeated .protobuf_unittest.ForeignEnum .protobuf_unittest.TestAllTypes.repeated_foreign_enum = 52;
  repeated .protobuf_unittest_import.ImportEnum .protobuf_unittest.TestAllTypes.repeated_import_enum = 53;
  repeated string .protobuf_unittest.TestAllTypes.repeated_string_piece = 54 [ctype = STRING_PIECE]
;
  repeated string .protobuf_unittest.TestAllTypes.repeated_cord = 55 [ctype = CORD]
;
  optional int32 .protobuf_unittest.TestAllTypes.default_int32 = 61 [default = 41]
;
  optional int64 .protobuf_unittest.TestAllTypes.default_int64 = 62 [default = 42]
;
  optional uint32 .protobuf_unittest.TestAllTypes.default_uint32 = 63 [default = 43]
;
  optional uint64 .protobuf_unittest.TestAllTypes.default_uint64 = 64 [default = 44]
;
  optional sint32 .protobuf_unittest.TestAllTypes.default_sint32 = 65 [default = -45]
;
  optional sint64 .protobuf_unittest.TestAllTypes.default_sint64 = 66 [default = 46]
;
  optional fixed32 .protobuf_unittest.TestAllTypes.default_fixed32 = 67 [default = 47]
;
  optional fixed64 .protobuf_unittest.TestAllTypes.default_fixed64 = 68 [default = 48]
;
  optional sfixed32 .protobuf_unittest.TestAllTypes.default_sfixed32 = 69 [default = 49]
;
  optional sfixed64 .protobuf_unittest.TestAllTypes.default_sfixed64 = 70 [default = -50]
;
  optional float .protobuf_unittest.TestAllTypes.default_float = 71 [default = 51.5]
;
  optional double .protobuf_unittest.TestAllTypes.default_double = 72 [default = 52e3]
;
  optional bool .protobuf_unittest.TestAllTypes.default_bool = 73 [default = true]
;
  optional string .protobuf_unittest.TestAllTypes.default_string = 74 [default = hello]
;
  optional bytes .protobuf_unittest.TestAllTypes.default_bytes = 75 [default = world]
;
  optional .protobuf_unittest.TestAllTypes.NestedEnum .protobuf_unittest.TestAllTypes.default_nested_enum = 81 [default = BAR]
;
  optional .protobuf_unittest.ForeignEnum .protobuf_unittest.TestAllTypes.default_foreign_enum = 82 [default = FOREIGN_BAR]
;
  optional .protobuf_unittest_import.ImportEnum .protobuf_unittest.TestAllTypes.default_import_enum = 83 [default = IMPORT_BAR]
;
  optional string .protobuf_unittest.TestAllTypes.default_string_piece = 84 [ctype = STRING_PIECE, default = abc]
;
  optional string .protobuf_unittest.TestAllTypes.default_cord = 85 [ctype = CORD, default = 123]
;
}
message .protobuf_unittest.TestDeprecatedFields {
  optional int32 .protobuf_unittest.TestDeprecatedFields.deprecated_int32 = 1 [deprecated = true]
;
}
message .protobuf_unittest.ForeignMessage {
  optional int32 .protobuf_unittest.ForeignMessage.c = 1;
}
enum .protobuf_unittest.ForeignEnum {
  .protobuf_unittest.ForeignEnum.FOREIGN_FOO = 4;
  .protobuf_unittest.ForeignEnum.FOREIGN_BAR = 5;
  .protobuf_unittest.ForeignEnum.FOREIGN_BAZ = 6;
}
message .protobuf_unittest.TestAllExtensions {
  extensions .protobuf_unittest.TestAllExtensions.$extensions.127 {
  extensionrange 1 to 536870911
  }
}
extend .protobuf_unittest.$extend.128 {
  optional int32 .protobuf_unittest.$extend.optional_int32_extension = 1;
  optional int64 .protobuf_unittest.$extend.optional_int64_extension = 2;
  optional uint32 .protobuf_unittest.$extend.optional_uint32_extension = 3;
  optional uint64 .protobuf_unittest.$extend.optional_uint64_extension = 4;
  optional sint32 .protobuf_unittest.$extend.optional_sint32_extension = 5;
  optional sint64 .protobuf_unittest.$extend.optional_sint64_extension = 6;
  optional fixed32 .protobuf_unittest.$extend.optional_fixed32_extension = 7;
  optional fixed64 .protobuf_unittest.$extend.optional_fixed64_extension = 8;
  optional sfixed32 .protobuf_unittest.$extend.optional_sfixed32_extension = 9;
  optional sfixed64 .protobuf_unittest.$extend.optional_sfixed64_extension = 10;
  optional float .protobuf_unittest.$extend.optional_float_extension = 11;
  optional double .protobuf_unittest.$extend.optional_double_extension = 12;
  optional bool .protobuf_unittest.$extend.optional_bool_extension = 13;
  optional string .protobuf_unittest.$extend.optional_string_extension = 14;
  optional bytes .protobuf_unittest.$extend.optional_bytes_extension = 15;
  optional .protobuf_unittest.TestAllTypes.NestedMessage .protobuf_unittest.$extend.optional_nested_message_extension = 18;
  optional .protobuf_unittest.ForeignMessage .protobuf_unittest.$extend.optional_foreign_message_extension = 19;
  optional .protobuf_unittest_import.ImportMessage .protobuf_unittest.$extend.optional_import_message_extension = 20;
  optional .protobuf_unittest.TestAllTypes.NestedEnum .protobuf_unittest.$extend.optional_nested_enum_extension = 21;
  optional .protobuf_unittest.ForeignEnum .protobuf_unittest.$extend.optional_foreign_enum_extension = 22;
  optional .protobuf_unittest_import.ImportEnum .protobuf_unittest.$extend.optional_import_enum_extension = 23;
  optional string .protobuf_unittest.$extend.optional_string_piece_extension = 24 [ctype = STRING_PIECE]
;
  optional string .protobuf_unittest.$extend.optional_cord_extension = 25 [ctype = CORD]
;
  repeated int32 .protobuf_unittest.$extend.repeated_int32_extension = 31;
  repeated int64 .protobuf_unittest.$extend.repeated_int64_extension = 32;
  repeated uint32 .protobuf_unittest.$extend.repeated_uint32_extension = 33;
  repeated uint64 .protobuf_unittest.$extend.repeated_uint64_extension = 34;
  repeated sint32 .protobuf_unittest.$extend.repeated_sint32_extension = 35;
  repeated sint64 .protobuf_unittest.$extend.repeated_sint64_extension = 36;
  repeated fixed32 .protobuf_unittest.$extend.repeated_fixed32_extension = 37;
  repeated fixed64 .protobuf_unittest.$extend.repeated_fixed64_extension = 38;
  repeated sfixed32 .protobuf_unittest.$extend.repeated_sfixed32_extension = 39;
  repeated sfixed64 .protobuf_unittest.$extend.repeated_sfixed64_extension = 40;
  repeated float .protobuf_unittest.$extend.repeated_float_extension = 41;
  repeated double .protobuf_unittest.$extend.repeated_double_extension = 42;
  repeated bool .protobuf_unittest.$extend.repeated_bool_extension = 43;
  repeated string .protobuf_unittest.$extend.repeated_string_extension = 44;
  repeated bytes .protobuf_unittest.$extend.repeated_bytes_extension = 45;
  repeated .protobuf_unittest.TestAllTypes.NestedMessage .protobuf_unittest.$extend.repeated_nested_message_extension = 48;
  repeated .protobuf_unittest.ForeignMessage .protobuf_unittest.$extend.repeated_foreign_message_extension = 49;
  repeated .protobuf_unittest_import.ImportMessage .protobuf_unittest.$extend.repeated_import_message_extension = 50;
  repeated .protobuf_unittest.TestAllTypes.NestedEnum .protobuf_unittest.$extend.repeated_nested_enum_extension = 51;
  repeated .protobuf_unittest.ForeignEnum .protobuf_unittest.$extend.repeated_foreign_enum_extension = 52;
  repeated .protobuf_unittest_import.ImportEnum .protobuf_unittest.$extend.repeated_import_enum_extension = 53;
  repeated string .protobuf_unittest.$extend.repeated_string_piece_extension = 54 [ctype = STRING_PIECE]
;
  repeated string .protobuf_unittest.$extend.repeated_cord_extension = 55 [ctype = CORD]
;
  optional int32 .protobuf_unittest.$extend.default_int32_extension = 61 [default = 41]
;
  optional int64 .protobuf_unittest.$extend.default_int64_extension = 62 [default = 42]
;
  optional uint32 .protobuf_unittest.$extend.default_uint32_extension = 63 [default = 43]
;
  optional uint64 .protobuf_unittest.$extend.default_uint64_extension = 64 [default = 44]
;
  optional sint32 .protobuf_unittest.$extend.default_sint32_extension = 65 [default = -45]
;
  optional sint64 .protobuf_unittest.$extend.default_sint64_extension = 66 [default = 46]
;
  optional fixed32 .protobuf_unittest.$extend.default_fixed32_extension = 67 [default = 47]
;
  optional fixed64 .protobuf_unittest.$extend.default_fixed64_extension = 68 [default = 48]
;
  optional sfixed32 .protobuf_unittest.$extend.default_sfixed32_extension = 69 [default = 49]
;
  optional sfixed64 .protobuf_unittest.$extend.default_sfixed64_extension = 70 [default = -50]
;
  optional float .protobuf_unittest.$extend.default_float_extension = 71 [default = 51.5]
;
  optional double .protobuf_unittest.$extend.default_double_extension = 72 [default = 52e3]
;
  optional bool .protobuf_unittest.$extend.default_bool_extension = 73 [default = true]
;
  optional string .protobuf_unittest.$extend.default_string_extension = 74 [default = hello]
;
  optional bytes .protobuf_unittest.$extend.default_bytes_extension = 75 [default = world]
;
  optional .protobuf_unittest.TestAllTypes.NestedEnum .protobuf_unittest.$extend.default_nested_enum_extension = 81 [default = BAR]
;
  optional .protobuf_unittest.ForeignEnum .protobuf_unittest.$extend.default_foreign_enum_extension = 82 [default = FOREIGN_BAR]
;
  optional .protobuf_unittest_import.ImportEnum .protobuf_unittest.$extend.default_import_enum_extension = 83 [default = IMPORT_BAR]
;
  optional string .protobuf_unittest.$extend.default_string_piece_extension = 84 [ctype = STRING_PIECE, default = abc]
;
  optional string .protobuf_unittest.$extend.default_cord_extension = 85 [ctype = CORD, default = 123]
;
}
message .protobuf_unittest.TestNestedExtension {
  extend .protobuf_unittest.TestNestedExtension.$extend.129 {
    optional string .protobuf_unittest.TestNestedExtension.$extend.test = 1002 [default = test]
;
  }
}
message .protobuf_unittest.TestRequired {
  required int32 .protobuf_unittest.TestRequired.a = 1;
  optional int32 .protobuf_unittest.TestRequired.dummy2 = 2;
  required int32 .protobuf_unittest.TestRequired.b = 3;
  extend .protobuf_unittest.TestRequired.$extend.130 {
    optional .protobuf_unittest.TestRequired .protobuf_unittest.TestRequired.$extend.single = 1000;
    repeated .protobuf_unittest.TestRequired .protobuf_unittest.TestRequired.$extend.multi = 1001;
  }
  optional int32 .protobuf_unittest.TestRequired.dummy4 = 4;
  optional int32 .protobuf_unittest.TestRequired.dummy5 = 5;
  optional int32 .protobuf_unittest.TestRequired.dummy6 = 6;
  optional int32 .protobuf_unittest.TestRequired.dummy7 = 7;
  optional int32 .protobuf_unittest.TestRequired.dummy8 = 8;
  optional int32 .protobuf_unittest.TestRequired.dummy9 = 9;
  optional int32 .protobuf_unittest.TestRequired.dummy10 = 10;
  optional int32 .protobuf_unittest.TestRequired.dummy11 = 11;
  optional int32 .protobuf_unittest.TestRequired.dummy12 = 12;
  optional int32 .protobuf_unittest.TestRequired.dummy13 = 13;
  optional int32 .protobuf_unittest.TestRequired.dummy14 = 14;
  optional int32 .protobuf_unittest.TestRequired.dummy15 = 15;
  optional int32 .protobuf_unittest.TestRequired.dummy16 = 16;
  optional int32 .protobuf_unittest.TestRequired.dummy17 = 17;
  optional int32 .protobuf_unittest.TestRequired.dummy18 = 18;
  optional int32 .protobuf_unittest.TestRequired.dummy19 = 19;
  optional int32 .protobuf_unittest.TestRequired.dummy20 = 20;
  optional int32 .protobuf_unittest.TestRequired.dummy21 = 21;
  optional int32 .protobuf_unittest.TestRequired.dummy22 = 22;
  optional int32 .protobuf_unittest.TestRequired.dummy23 = 23;
  optional int32 .protobuf_unittest.TestRequired.dummy24 = 24;
  optional int32 .protobuf_unittest.TestRequired.dummy25 = 25;
  optional int32 .protobuf_unittest.TestRequired.dummy26 = 26;
  optional int32 .protobuf_unittest.TestRequired.dummy27 = 27;
  optional int32 .protobuf_unittest.TestRequired.dummy28 = 28;
  optional int32 .protobuf_unittest.TestRequired.dummy29 = 29;
  optional int32 .protobuf_unittest.TestRequired.dummy30 = 30;
  optional int32 .protobuf_unittest.TestRequired.dummy31 = 31;
  optional int32 .protobuf_unittest.TestRequired.dummy32 = 32;
  required int32 .protobuf_unittest.TestRequired.c = 33;
}
message .protobuf_unittest.TestRequiredForeign {
  optional .protobuf_unittest.TestRequired .protobuf_unittest.TestRequiredForeign.optional_message = 1;
  repeated .protobuf_unittest.TestRequired .protobuf_unittest.TestRequiredForeign.repeated_message = 2;
  optional int32 .protobuf_unittest.TestRequiredForeign.dummy = 3;
}
message .protobuf_unittest.TestForeignNested {
  optional .protobuf_unittest.TestAllTypes.NestedMessage .protobuf_unittest.TestForeignNested.foreign_nested = 1;
}
message .protobuf_unittest.TestEmptyMessage {
}
message .protobuf_unittest.TestEmptyMessageWithExtensions {
  extensions .protobuf_unittest.TestEmptyMessageWithExtensions.$extensions.131 {
  extensionrange 1 to 536870911
  }
}
message .protobuf_unittest.TestMultipleExtensionRanges {
  extensions .protobuf_unittest.TestMultipleExtensionRanges.$extensions.132 {
  extensionrange 42 to 536870911
  }
  extensions .protobuf_unittest.TestMultipleExtensionRanges.$extensions.133 {
  extensionrange 4143 to 4243
  }
  extensions .protobuf_unittest.TestMultipleExtensionRanges.$extensions.134 {
  extensionrange 65536 to 536870911
  }
}
message .protobuf_unittest.TestReallyLargeTagNumber {
  optional int32 .protobuf_unittest.TestReallyLargeTagNumber.a = 1;
  optional int32 .protobuf_unittest.TestReallyLargeTagNumber.bb = 268435455;
}
message .protobuf_unittest.TestRecursiveMessage {
  optional .protobuf_unittest.TestRecursiveMessage .protobuf_unittest.TestRecursiveMessage.a = 1;
  optional int32 .protobuf_unittest.TestRecursiveMessage.i = 2;
}
message .protobuf_unittest.TestMutualRecursionA {
  optional .protobuf_unittest.TestMutualRecursionB .protobuf_unittest.TestMutualRecursionA.bb = 1;
}
message .protobuf_unittest.TestMutualRecursionB {
  optional .protobuf_unittest.TestMutualRecursionA .protobuf_unittest.TestMutualRecursionB.a = 1;
  optional int32 .protobuf_unittest.TestMutualRecursionB.optional_int32 = 2;
}
message .protobuf_unittest.TestDupFieldNumber {
  optional int32 .protobuf_unittest.TestDupFieldNumber.a = 1;
}
message .protobuf_unittest.TestNestedMessageHasBits {
  message .protobuf_unittest.TestNestedMessageHasBits.NestedMessage {
    repeated int32 .protobuf_unittest.TestNestedMessageHasBits.NestedMessage.nestedmessage_repeated_int32 = 1;
    repeated .protobuf_unittest.ForeignMessage .protobuf_unittest.TestNestedMessageHasBits.NestedMessage.nestedmessage_repeated_foreignmessage = 2;
  }
  optional .protobuf_unittest.TestNestedMessageHasBits.NestedMessage .protobuf_unittest.TestNestedMessageHasBits.optional_nested_message = 1;
}
enum .protobuf_unittest.TestEnumWithDupValue {
  .protobuf_unittest.TestEnumWithDupValue.FOO1 = 1;
  .protobuf_unittest.TestEnumWithDupValue.BAR1 = 2;
  .protobuf_unittest.TestEnumWithDupValue.BAZ = 3;
  .protobuf_unittest.TestEnumWithDupValue.FOO2 = 1;
  .protobuf_unittest.TestEnumWithDupValue.BAR2 = 2;
}
enum .protobuf_unittest.TestSparseEnum {
  .protobuf_unittest.TestSparseEnum.SPARSE_A = 123;
  .protobuf_unittest.TestSparseEnum.SPARSE_B = 62374;
  .protobuf_unittest.TestSparseEnum.SPARSE_C = 12589234;
  .protobuf_unittest.TestSparseEnum.SPARSE_D = -15;
  .protobuf_unittest.TestSparseEnum.SPARSE_E = -53452;
  .protobuf_unittest.TestSparseEnum.SPARSE_F = 0;
  .protobuf_unittest.TestSparseEnum.SPARSE_G = 2;
}
message .protobuf_unittest.TestCamelCaseFieldNames {
  optional int32 .protobuf_unittest.TestCamelCaseFieldNames.PrimitiveField = 1;
  optional string .protobuf_unittest.TestCamelCaseFieldNames.StringField = 2;
  optional .protobuf_unittest.ForeignEnum .protobuf_unittest.TestCamelCaseFieldNames.EnumField = 3;
  optional .protobuf_unittest.ForeignMessage .protobuf_unittest.TestCamelCaseFieldNames.MessageField = 4;
  optional string .protobuf_unittest.TestCamelCaseFieldNames.StringPieceField = 5 [ctype = STRING_PIECE]
;
  optional string .protobuf_unittest.TestCamelCaseFieldNames.CordField = 6 [ctype = CORD]
;
  repeated int32 .protobuf_unittest.TestCamelCaseFieldNames.RepeatedPrimitiveField = 7;
  repeated string .protobuf_unittest.TestCamelCaseFieldNames.RepeatedStringField = 8;
  repeated .protobuf_unittest.ForeignEnum .protobuf_unittest.TestCamelCaseFieldNames.RepeatedEnumField = 9;
  repeated .protobuf_unittest.ForeignMessage .protobuf_unittest.TestCamelCaseFieldNames.RepeatedMessageField = 10;
  repeated string .protobuf_unittest.TestCamelCaseFieldNames.RepeatedStringPieceField = 11 [ctype = STRING_PIECE]
;
  repeated string .protobuf_unittest.TestCamelCaseFieldNames.RepeatedCordField = 12 [ctype = CORD]
;
}
message .protobuf_unittest.TestFieldOrderings {
  optional string .protobuf_unittest.TestFieldOrderings.my_string = 11;
  extensions .protobuf_unittest.TestFieldOrderings.$extensions.135 {
  extensionrange 2 to 10
  }
  optional int64 .protobuf_unittest.TestFieldOrderings.my_int = 1;
  extensions .protobuf_unittest.TestFieldOrderings.$extensions.136 {
  extensionrange 12 to 100
  }
  optional float .protobuf_unittest.TestFieldOrderings.my_float = 101;
}
extend .protobuf_unittest.$extend.137 {
  optional string .protobuf_unittest.$extend.my_extension_string = 50;
  optional int32 .protobuf_unittest.$extend.my_extension_int = 5;
}
message .protobuf_unittest.TestExtremeDefaultValues {
  optional bytes .protobuf_unittest.TestExtremeDefaultValues.escaped_bytes = 1 [default =   1abf
	v\'"ÿe]
;
  optional uint32 .protobuf_unittest.TestExtremeDefaultValues.large_uint32 = 2 [default = 0xFFFFFFFF]
;
  optional uint64 .protobuf_unittest.TestExtremeDefaultValues.large_uint64 = 3 [default = 0xFFFFFFFFFFFFFFFF]
;
  optional int32 .protobuf_unittest.TestExtremeDefaultValues.small_int32 = 4 [default = -0x7FFFFFFF]
;
  optional int64 .protobuf_unittest.TestExtremeDefaultValues.small_int64 = 5 [default = -0x7FFFFFFFFFFFFFFF]
;
  optional string .protobuf_unittest.TestExtremeDefaultValues.utf8_string = 6 [default = 341210264]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.zero_float = 7 [default = 0]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.one_float = 8 [default = 1]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.small_float = 9 [default = 1.5]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.negative_one_float = 10 [default = -1]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.negative_float = 11 [default = --]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.large_float = 12 [default = 2E8]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.small_negative_float = 13 [default = --]
;
  optional double .protobuf_unittest.TestExtremeDefaultValues.inf_double = 14 [default = inf]
;
  optional double .protobuf_unittest.TestExtremeDefaultValues.neg_inf_double = 15 [default = -inf]
;
  optional double .protobuf_unittest.TestExtremeDefaultValues.nan_double = 16 [default = nan]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.inf_float = 17 [default = inf]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.neg_inf_float = 18 [default = -inf]
;
  optional float .protobuf_unittest.TestExtremeDefaultValues.nan_float = 19 [default = nan]
;
}
message .protobuf_unittest.OneString {
  optional string .protobuf_unittest.OneString.data = 1;
}
message .protobuf_unittest.OneBytes {
  optional bytes .protobuf_unittest.OneBytes.data = 1;
}
message .protobuf_unittest.TestPackedTypes {
  repeated int32 .protobuf_unittest.TestPackedTypes.packed_int32 = 90 [packed = true]
;
  repeated int64 .protobuf_unittest.TestPackedTypes.packed_int64 = 91 [packed = true]
;
  repeated uint32 .protobuf_unittest.TestPackedTypes.packed_uint32 = 92 [packed = true]
;
  repeated uint64 .protobuf_unittest.TestPackedTypes.packed_uint64 = 93 [packed = true]
;
  repeated sint32 .protobuf_unittest.TestPackedTypes.packed_sint32 = 94 [packed = true]
;
  repeated sint64 .protobuf_unittest.TestPackedTypes.packed_sint64 = 95 [packed = true]
;
  repeated fixed32 .protobuf_unittest.TestPackedTypes.packed_fixed32 = 96 [packed = true]
;
  repeated fixed64 .protobuf_unittest.TestPackedTypes.packed_fixed64 = 97 [packed = true]
;
  repeated sfixed32 .protobuf_unittest.TestPackedTypes.packed_sfixed32 = 98 [packed = true]
;
  repeated sfixed64 .protobuf_unittest.TestPackedTypes.packed_sfixed64 = 99 [packed = true]
;
  repeated float .protobuf_unittest.TestPackedTypes.packed_float = 100 [packed = true]
;
  repeated double .protobuf_unittest.TestPackedTypes.packed_double = 101 [packed = true]
;
  repeated bool .protobuf_unittest.TestPackedTypes.packed_bool = 102 [packed = true]
;
  repeated .protobuf_unittest.ForeignEnum .protobuf_unittest.TestPackedTypes.packed_enum = 103 [packed = true]
;
}
message .protobuf_unittest.TestUnpackedTypes {
  repeated int32 .protobuf_unittest.TestUnpackedTypes.unpacked_int32 = 90 [packed = false]
;
  repeated int64 .protobuf_unittest.TestUnpackedTypes.unpacked_int64 = 91 [packed = false]
;
  repeated uint32 .protobuf_unittest.TestUnpackedTypes.unpacked_uint32 = 92 [packed = false]
;
  repeated uint64 .protobuf_unittest.TestUnpackedTypes.unpacked_uint64 = 93 [packed = false]
;
  repeated sint32 .protobuf_unittest.TestUnpackedTypes.unpacked_sint32 = 94 [packed = false]
;
  repeated sint64 .protobuf_unittest.TestUnpackedTypes.unpacked_sint64 = 95 [packed = false]
;
  repeated fixed32 .protobuf_unittest.TestUnpackedTypes.unpacked_fixed32 = 96 [packed = false]
;
  repeated fixed64 .protobuf_unittest.TestUnpackedTypes.unpacked_fixed64 = 97 [packed = false]
;
  repeated sfixed32 .protobuf_unittest.TestUnpackedTypes.unpacked_sfixed32 = 98 [packed = false]
;
  repeated sfixed64 .protobuf_unittest.TestUnpackedTypes.unpacked_sfixed64 = 99 [packed = false]
;
  repeated float .protobuf_unittest.TestUnpackedTypes.unpacked_float = 100 [packed = false]
;
  repeated double .protobuf_unittest.TestUnpackedTypes.unpacked_double = 101 [packed = false]
;
  repeated bool .protobuf_unittest.TestUnpackedTypes.unpacked_bool = 102 [packed = false]
;
  repeated .protobuf_unittest.ForeignEnum .protobuf_unittest.TestUnpackedTypes.unpacked_enum = 103 [packed = false]
;
}
message .protobuf_unittest.TestPackedExtensions {
  extensions .protobuf_unittest.TestPackedExtensions.$extensions.138 {
  extensionrange 1 to 536870911
  }
}
extend .protobuf_unittest.$extend.139 {
  repeated int32 .protobuf_unittest.$extend.packed_int32_extension = 90 [packed = true]
;
  repeated int64 .protobuf_unittest.$extend.packed_int64_extension = 91 [packed = true]
;
  repeated uint32 .protobuf_unittest.$extend.packed_uint32_extension = 92 [packed = true]
;
  repeated uint64 .protobuf_unittest.$extend.packed_uint64_extension = 93 [packed = true]
;
  repeated sint32 .protobuf_unittest.$extend.packed_sint32_extension = 94 [packed = true]
;
  repeated sint64 .protobuf_unittest.$extend.packed_sint64_extension = 95 [packed = true]
;
  repeated fixed32 .protobuf_unittest.$extend.packed_fixed32_extension = 96 [packed = true]
;
  repeated fixed64 .protobuf_unittest.$extend.packed_fixed64_extension = 97 [packed = true]
;
  repeated sfixed32 .protobuf_unittest.$extend.packed_sfixed32_extension = 98 [packed = true]
;
  repeated sfixed64 .protobuf_unittest.$extend.packed_sfixed64_extension = 99 [packed = true]
;
  repeated float .protobuf_unittest.$extend.packed_float_extension = 100 [packed = true]
;
  repeated double .protobuf_unittest.$extend.packed_double_extension = 101 [packed = true]
;
  repeated bool .protobuf_unittest.$extend.packed_bool_extension = 102 [packed = true]
;
  repeated .protobuf_unittest.ForeignEnum .protobuf_unittest.$extend.packed_enum_extension = 103 [packed = true]
;
}
message .protobuf_unittest.TestDynamicExtensions {
  enum .protobuf_unittest.TestDynamicExtensions.DynamicEnumType {
    .protobuf_unittest.TestDynamicExtensions.DynamicEnumType.DYNAMIC_FOO = 2200;
    .protobuf_unittest.TestDynamicExtensions.DynamicEnumType.DYNAMIC_BAR = 2201;
    .protobuf_unittest.TestDynamicExtensions.DynamicEnumType.DYNAMIC_BAZ = 2202;
  }
  message .protobuf_unittest.TestDynamicExtensions.DynamicMessageType {
    optional int32 .protobuf_unittest.TestDynamicExtensions.DynamicMessageType.dynamic_field = 2100;
  }
  optional fixed32 .protobuf_unittest.TestDynamicExtensions.scalar_extension = 2000;
  optional .protobuf_unittest.ForeignEnum .protobuf_unittest.TestDynamicExtensions.enum_extension = 2001;
  optional .protobuf_unittest.TestDynamicExtensions.DynamicEnumType .protobuf_unittest.TestDynamicExtensions.dynamic_enum_extension = 2002;
  optional .protobuf_unittest.ForeignMessage .protobuf_unittest.TestDynamicExtensions.message_extension = 2003;
  optional .protobuf_unittest.TestDynamicExtensions.DynamicMessageType .protobuf_unittest.TestDynamicExtensions.dynamic_message_extension = 2004;
  repeated string .protobuf_unittest.TestDynamicExtensions.repeated_extension = 2005;
  repeated sint32 .protobuf_unittest.TestDynamicExtensions.packed_extension = 2006 [packed = true]
;
}
message .protobuf_unittest.TestRepeatedScalarDifferentTagSizes {
  repeated fixed32 .protobuf_unittest.TestRepeatedScalarDifferentTagSizes.repeated_fixed32 = 12;
  repeated int32 .protobuf_unittest.TestRepeatedScalarDifferentTagSizes.repeated_int32 = 13;
  repeated fixed64 .protobuf_unittest.TestRepeatedScalarDifferentTagSizes.repeated_fixed64 = 2046;
  repeated int64 .protobuf_unittest.TestRepeatedScalarDifferentTagSizes.repeated_int64 = 2047;
  repeated float .protobuf_unittest.TestRepeatedScalarDifferentTagSizes.repeated_float = 262142;
  repeated uint64 .protobuf_unittest.TestRepeatedScalarDifferentTagSizes.repeated_uint64 = 262143;
}
message .protobuf_unittest.FooRequest {
}
message .protobuf_unittest.FooResponse {
}
service .protobuf_unittest.TestService {
  rpc .protobuf_unittest.TestService.Foo (.protobuf_unittest.FooRequest) returns (.protobuf_unittest.FooResponse);
  rpc .protobuf_unittest.TestService.Bar (.protobuf_unittest.BarRequest) returns (.protobuf_unittest.BarResponse);
}
message .protobuf_unittest.BarRequest {
}
message .protobuf_unittest.BarResponse {
}
package .protobuf_unittest_import;
option .protobuf_unittest_import.optimize_for = SPEED;
option .protobuf_unittest_import.java_package = com.google.protobuf.test;
message .protobuf_unittest_import.ImportMessage {
  optional int32 .protobuf_unittest_import.ImportMessage.d = 1;
}
enum .protobuf_unittest_import.ImportEnum {
  .protobuf_unittest_import.ImportEnum.IMPORT_FOO = 7;
  .protobuf_unittest_import.ImportEnum.IMPORT_BAR = 8;
  .protobuf_unittest_import.ImportEnum.IMPORT_BAZ = 9;
}
