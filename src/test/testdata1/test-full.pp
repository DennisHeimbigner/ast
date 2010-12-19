package .foo;
message .foo.SubMess {
  required int32 .foo.SubMess.test = 4;
}
enum .foo.TestEnumSmall {
  .foo.TestEnumSmall.VALUE = 0;
  .foo.TestEnumSmall.OTHER_VALUE = 1;
}
enum .foo.TestEnum {
  .foo.TestEnum.VALUE0 = 0;
  .foo.TestEnum.VALUE1 = 1;
  .foo.TestEnum.VALUE127 = 127;
  .foo.TestEnum.VALUE128 = 128;
  .foo.TestEnum.VALUE16383 = 16383;
  .foo.TestEnum.VALUE16384 = 16384;
  .foo.TestEnum.VALUE2097151 = 2097151;
  .foo.TestEnum.VALUE2097152 = 2097152;
  .foo.TestEnum.VALUE268435455 = 268435455;
  .foo.TestEnum.VALUE268435456 = 268435456;
}
enum .foo.TestEnumDupValues {
  .foo.TestEnumDupValues.VALUE_A = 42;
  .foo.TestEnumDupValues.VALUE_B = 42;
  .foo.TestEnumDupValues.VALUE_C = 42;
  .foo.TestEnumDupValues.VALUE_D = 666;
  .foo.TestEnumDupValues.VALUE_E = 666;
  .foo.TestEnumDupValues.VALUE_F = 1000;
  .foo.TestEnumDupValues.VALUE_AA = 1000;
  .foo.TestEnumDupValues.VALUE_BB = 1001;
}
message .foo.TestFieldNo15 {
  required string .foo.TestFieldNo15.test = 15;
}
message .foo.TestFieldNo16 {
  required string .foo.TestFieldNo16.test = 16;
}
message .foo.TestFieldNo2047 {
  required string .foo.TestFieldNo2047.test = 2047;
}
message .foo.TestFieldNo2048 {
  required string .foo.TestFieldNo2048.test = 2048;
}
message .foo.TestFieldNo262143 {
  required string .foo.TestFieldNo262143.test = 262143;
}
message .foo.TestFieldNo262144 {
  required string .foo.TestFieldNo262144.test = 262144;
}
message .foo.TestFieldNo33554431 {
  required string .foo.TestFieldNo33554431.test = 33554431;
}
message .foo.TestFieldNo33554432 {
  required string .foo.TestFieldNo33554432.test = 33554432;
}
message .foo.TestMess {
  repeated int32 .foo.TestMess.test_int32 = 1;
  repeated sint32 .foo.TestMess.test_sint32 = 2;
  repeated sfixed32 .foo.TestMess.test_sfixed32 = 3;
  repeated int64 .foo.TestMess.test_int64 = 4;
  repeated sint64 .foo.TestMess.test_sint64 = 5;
  repeated sfixed64 .foo.TestMess.test_sfixed64 = 6;
  repeated uint32 .foo.TestMess.test_uint32 = 7;
  repeated fixed32 .foo.TestMess.test_fixed32 = 8;
  repeated uint64 .foo.TestMess.test_uint64 = 9;
  repeated fixed64 .foo.TestMess.test_fixed64 = 10;
  repeated float .foo.TestMess.test_float = 11;
  repeated double .foo.TestMess.test_double = 12;
  repeated bool .foo.TestMess.test_boolean = 13;
  repeated .foo.TestEnumSmall .foo.TestMess.test_enum_small = 14;
  repeated .foo.TestEnum .foo.TestMess.test_enum = 15;
  repeated string .foo.TestMess.test_string = 16;
  repeated bytes .foo.TestMess.test_bytes = 17;
  repeated .foo.SubMess .foo.TestMess.test_message = 18;
}
message .foo.TestMessPacked {
  repeated int32 .foo.TestMessPacked.test_int32 = 1 [packed = true]
;
  repeated sint32 .foo.TestMessPacked.test_sint32 = 2 [packed = true]
;
  repeated sfixed32 .foo.TestMessPacked.test_sfixed32 = 3 [packed = true]
;
  repeated int64 .foo.TestMessPacked.test_int64 = 4 [packed = true]
;
  repeated sint64 .foo.TestMessPacked.test_sint64 = 5 [packed = true]
;
  repeated sfixed64 .foo.TestMessPacked.test_sfixed64 = 6 [packed = true]
;
  repeated uint32 .foo.TestMessPacked.test_uint32 = 7 [packed = true]
;
  repeated fixed32 .foo.TestMessPacked.test_fixed32 = 8 [packed = true]
;
  repeated uint64 .foo.TestMessPacked.test_uint64 = 9 [packed = true]
;
  repeated fixed64 .foo.TestMessPacked.test_fixed64 = 10 [packed = true]
;
  repeated float .foo.TestMessPacked.test_float = 11 [packed = true]
;
  repeated double .foo.TestMessPacked.test_double = 12 [packed = true]
;
  repeated bool .foo.TestMessPacked.test_boolean = 13 [packed = true]
;
  repeated .foo.TestEnumSmall .foo.TestMessPacked.test_enum_small = 14 [packed = true]
;
  repeated .foo.TestEnum .foo.TestMessPacked.test_enum = 15 [packed = true]
;
}
message .foo.TestMessOptional {
  optional int32 .foo.TestMessOptional.test_int32 = 1;
  optional sint32 .foo.TestMessOptional.test_sint32 = 2;
  optional sfixed32 .foo.TestMessOptional.test_sfixed32 = 3;
  optional int64 .foo.TestMessOptional.test_int64 = 4;
  optional sint64 .foo.TestMessOptional.test_sint64 = 5;
  optional sfixed64 .foo.TestMessOptional.test_sfixed64 = 6;
  optional uint32 .foo.TestMessOptional.test_uint32 = 7;
  optional fixed32 .foo.TestMessOptional.test_fixed32 = 8;
  optional uint64 .foo.TestMessOptional.test_uint64 = 9;
  optional fixed64 .foo.TestMessOptional.test_fixed64 = 10;
  optional float .foo.TestMessOptional.test_float = 11;
  optional double .foo.TestMessOptional.test_double = 12;
  optional bool .foo.TestMessOptional.test_boolean = 13;
  optional .foo.TestEnumSmall .foo.TestMessOptional.test_enum_small = 14;
  optional .foo.TestEnum .foo.TestMessOptional.test_enum = 15;
  optional string .foo.TestMessOptional.test_string = 16;
  optional bytes .foo.TestMessOptional.test_bytes = 17;
  optional .foo.SubMess .foo.TestMessOptional.test_message = 18;
}
message .foo.TestMessRequiredInt32 {
  required int32 .foo.TestMessRequiredInt32.test = 42;
}
message .foo.TestMessRequiredSInt32 {
  required sint32 .foo.TestMessRequiredSInt32.test = 43;
}
message .foo.TestMessRequiredSFixed32 {
  required sfixed32 .foo.TestMessRequiredSFixed32.test = 100;
}
message .foo.TestMessRequiredInt64 {
  required int64 .foo.TestMessRequiredInt64.test = 1;
}
message .foo.TestMessRequiredSInt64 {
  required sint64 .foo.TestMessRequiredSInt64.test = 11;
}
message .foo.TestMessRequiredSFixed64 {
  required sfixed64 .foo.TestMessRequiredSFixed64.test = 12;
}
message .foo.TestMessRequiredUInt32 {
  required uint32 .foo.TestMessRequiredUInt32.test = 1;
}
message .foo.TestMessRequiredFixed32 {
  required fixed32 .foo.TestMessRequiredFixed32.test = 1;
}
message .foo.TestMessRequiredUInt64 {
  required uint64 .foo.TestMessRequiredUInt64.test = 1;
}
message .foo.TestMessRequiredFixed64 {
  required fixed64 .foo.TestMessRequiredFixed64.test = 1;
}
message .foo.TestMessRequiredFloat {
  required float .foo.TestMessRequiredFloat.test = 1;
}
message .foo.TestMessRequiredDouble {
  required double .foo.TestMessRequiredDouble.test = 1;
}
message .foo.TestMessRequiredBool {
  required bool .foo.TestMessRequiredBool.test = 1;
}
message .foo.TestMessRequiredEnum {
  required .foo.TestEnum .foo.TestMessRequiredEnum.test = 1;
}
message .foo.TestMessRequiredEnumSmall {
  required .foo.TestEnumSmall .foo.TestMessRequiredEnumSmall.test = 1;
}
message .foo.TestMessRequiredString {
  required string .foo.TestMessRequiredString.test = 1;
}
message .foo.TestMessRequiredBytes {
  required bytes .foo.TestMessRequiredBytes.test = 1;
}
message .foo.TestMessRequiredMessage {
  required .foo.SubMess .foo.TestMessRequiredMessage.test = 1;
}
message .foo.EmptyMess {
}
message .foo.DefaultRequiredValues {
  required int32 .foo.DefaultRequiredValues.v_int32 = 1 [default = -42]
;
  required uint32 .foo.DefaultRequiredValues.v_uint32 = 2 [default = 666]
;
  required int32 .foo.DefaultRequiredValues.v_int64 = 3 [default = 100000]
;
  required uint32 .foo.DefaultRequiredValues.v_uint64 = 4 [default = 100001]
;
  required float .foo.DefaultRequiredValues.v_float = 5 [default = 2.5]
;
  required double .foo.DefaultRequiredValues.v_double = 6 [default = 4.5]
;
  required string .foo.DefaultRequiredValues.v_string = 7 [default = hi mom
]
;
  required bytes .foo.DefaultRequiredValues.v_bytes = 8 [default = a   character]
;
}
message .foo.DefaultOptionalValues {
  optional int32 .foo.DefaultOptionalValues.v_int32 = 1 [default = -42]
;
  optional uint32 .foo.DefaultOptionalValues.v_uint32 = 2 [default = 666]
;
  optional int32 .foo.DefaultOptionalValues.v_int64 = 3 [default = 100000]
;
  optional uint32 .foo.DefaultOptionalValues.v_uint64 = 4 [default = 100001]
;
  optional float .foo.DefaultOptionalValues.v_float = 5 [default = 2.5]
;
  optional double .foo.DefaultOptionalValues.v_double = 6 [default = 4.5]
;
  optional string .foo.DefaultOptionalValues.v_string = 7 [default = hi mom
]
;
  optional bytes .foo.DefaultOptionalValues.v_bytes = 8 [default = a   character]
;
}
message .foo.AllocValues {
  optional bytes .foo.AllocValues.o_bytes = 1;
  repeated string .foo.AllocValues.r_string = 2;
  required string .foo.AllocValues.a_string = 3;
  required bytes .foo.AllocValues.a_bytes = 4;
  required .foo.DefaultRequiredValues .foo.AllocValues.a_mess = 5;
}
