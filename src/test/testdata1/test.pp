package .foo;
message .foo.Person {
  required string .foo.Person.name = 1;
  required int32 .foo.Person.id = 2;
  optional string .foo.Person.email = 3;
  enum .foo.Person.PhoneType {
    .foo.Person.PhoneType.MOBILE = 0;
    .foo.Person.PhoneType.HOME = 1;
    .foo.Person.PhoneType.WORK = 2;
  }
  message .foo.Person.PhoneNumber {
    required string .foo.Person.PhoneNumber.number = 1;
    optional .foo.Person.PhoneType .foo.Person.PhoneNumber.type = 2 [DEFAULT = HOME]
;
  }
  repeated .foo.Person.PhoneNumber .foo.Person.phone = 4;
}
message .foo.LookupResult {
  optional .foo.Person .foo.LookupResult.person = 1;
}
message .foo.Name {
  optional string .foo.Name.name = 1;
}
service .foo.DirLookup {
  rpc .foo.DirLookup.ByName (.foo.Name) returns (.foo.LookupResult);
}
