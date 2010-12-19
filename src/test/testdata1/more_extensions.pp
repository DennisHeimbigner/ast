package .google.protobuf.internal;
message .google.protobuf.internal.TopLevelMessage {
  optional .google.protobuf.internal.ExtendedMessage .google.protobuf.internal.TopLevelMessage.submessage = 1;
}
message .google.protobuf.internal.ExtendedMessage {
  extensions .google.protobuf.internal.ExtendedMessage.$extensions.15 {
  extensionrange 1 to 536870911
  }
}
message .google.protobuf.internal.ForeignMessage {
  optional int32 .google.protobuf.internal.ForeignMessage.foreign_message_int = 1;
}
extend .google.protobuf.internal.$extend.16 {
  optional int32 .google.protobuf.internal.$extend.optional_int_extension = 1;
  optional .google.protobuf.internal.ForeignMessage .google.protobuf.internal.$extend.optional_message_extension = 2;
  repeated int32 .google.protobuf.internal.$extend.repeated_int_extension = 3;
  repeated .google.protobuf.internal.ForeignMessage .google.protobuf.internal.$extend.repeated_message_extension = 4;
}
