package .example;
message .example.Word {
  required string .example.Word.word = 1;
}
service .example.WordFuncs {
  rpc .example.WordFuncs.Uppercase (.example.Word) returns (.example.Word);
  rpc .example.WordFuncs.Lowercase (.example.Word) returns (.example.Word);
}
