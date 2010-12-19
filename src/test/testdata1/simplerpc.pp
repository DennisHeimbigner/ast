package .simplerpc;
message .simplerpc.RequestPreamble {
  required uint32 .simplerpc.RequestPreamble.size = 1;
  required string .simplerpc.RequestPreamble.domain = 2;
  required uint64 .simplerpc.RequestPreamble.request_id = 3;
}
message .simplerpc.ResponsePreamble {
  required uint32 .simplerpc.ResponsePreamble.size = 1;
  required uint64 .simplerpc.ResponsePreamble.request_id = 2;
}
message .simplerpc.DomainListRequest {
  required bool .simplerpc.DomainListRequest.get_service_defs = 1;
}
message .simplerpc.DomainInfo {
  required string .simplerpc.DomainInfo.domain = 1;
  optional string .simplerpc.DomainInfo.service = 2;
  optional bytes .simplerpc.DomainInfo.service_defs = 3;
}
message .simplerpc.DomainListResponse {
  repeated .simplerpc.DomainInfo .simplerpc.DomainListResponse.info = 1;
}
service .simplerpc.Builtin {
  rpc .simplerpc.Builtin.ListDomains (.simplerpc.DomainListRequest) returns (.simplerpc.DomainListResponse);
}
