package .tutorial;
option .tutorial.java_package = com.example.tutorial;
option .tutorial.java_outer_classname = AddressBookProtos;
message .tutorial.Person {
  required string .tutorial.Person.name = 1;
  required int32 .tutorial.Person.id = 2;
  optional string .tutorial.Person.email = 3;
  enum .tutorial.Person.PhoneType {
    .tutorial.Person.PhoneType.MOBILE = 0;
    .tutorial.Person.PhoneType.HOME = 1;
    .tutorial.Person.PhoneType.WORK = 2;
  }
  message .tutorial.Person.PhoneNumber {
    required string .tutorial.Person.PhoneNumber.number = 1;
    optional .tutorial.Person.PhoneType .tutorial.Person.PhoneNumber.type = 2 [default = HOME]
;
  }
  repeated .tutorial.Person.PhoneNumber .tutorial.Person.phone = 4;
}
message .tutorial.AddressBook {
  repeated .tutorial.Person .tutorial.AddressBook.person = 1;
}
