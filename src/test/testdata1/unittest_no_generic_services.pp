package .google.protobuf.no_generic_services_test;
option .google.protobuf.no_generic_services_test.cc_generic_services = false;
option .google.protobuf.no_generic_services_test.java_generic_services = false;
option .google.protobuf.no_generic_services_test.py_generic_services = false;
message .google.protobuf.no_generic_services_test.TestMessage {
  optional int32 .google.protobuf.no_generic_services_test.TestMessage.a = 1;
  extensions .google.protobuf.no_generic_services_test.TestMessage.$extensions.159 {
  extensionrange 1000 to 536870911
  }
}
enum .google.protobuf.no_generic_services_test.TestEnum {
  .google.protobuf.no_generic_services_test.TestEnum.FOO = 1;
}
extend .google.protobuf.no_generic_services_test.$extend.160 {
  optional int32 .google.protobuf.no_generic_services_test.$extend.test_extension = 1000;
}
service .google.protobuf.no_generic_services_test.TestService {
  rpc .google.protobuf.no_generic_services_test.TestService.Foo (.google.protobuf.no_generic_services_test.TestMessage) returns (.google.protobuf.no_generic_services_test.TestMessage);
}
