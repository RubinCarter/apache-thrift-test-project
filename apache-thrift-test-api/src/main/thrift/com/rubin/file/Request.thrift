namespace java com.rubin.domain
namespace py com.rubin.domain

include "RequestType.thrift"

struct Request {
    1: required RequestType.RequestType type; //请求的类型，必选
    2: required string name; //发起请求者的姓名，必选
    3: optional i32 age; //发起请求者的年龄，可选
}