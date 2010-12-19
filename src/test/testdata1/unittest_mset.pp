package .protobuf_unittest;
option .protobuf_unittest.optimize_for = SPEED;
message .protobuf_unittest.TestMessageSet {
  option .protobuf_unittest.TestMessageSet.message_set_wire_format = true;
  extensions .protobuf_unittest.TestMessageSet.$extensions.153 {
  extensionrange 4 to 536870911
  }
}
message .protobuf_unittest.TestMessageSetContainer {
  optional .protobuf_unittest.TestMessageSet .protobuf_unittest.TestMessageSetContainer.message_set = 1;
}
message .protobuf_unittest.TestMessageSetExtension1 {
  extend .protobuf_unittest.TestMessageSetExtension1.$extend.154 {
    optional .protobuf_unittest.TestMessageSetExtension1 .protobuf_unittest.TestMessageSetExtension1.$extend.message_set_extension = 1545008;
  }
  optional int32 .protobuf_unittest.TestMessageSetExtension1.i = 15;
}
message .protobuf_unittest.TestMessageSetExtension2 {
  extend .protobuf_unittest.TestMessageSetExtension2.$extend.155 {
    optional .protobuf_unittest.TestMessageSetExtension2 .protobuf_unittest.TestMessageSetExtension2.$extend.message_set_extension = 1547769;
  }
  optional string .protobuf_unittest.TestMessageSetExtension2.str = 25;
}
message .protobuf_unittest.RawMessageSet {
  repeated group .protobuf_unittest.RawMessageSet.Item = 1 {    required int32 .protobuf_unittest.RawMessageSet.Item.type_id = 2;
    required bytes .protobuf_unittest.RawMessageSet.Item.message = 3;
  }
}
