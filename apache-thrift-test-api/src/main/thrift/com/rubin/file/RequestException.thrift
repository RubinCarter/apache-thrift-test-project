namespace java com.rubin.exception
namespace py com.rubin.exception

exception RequestException {
    1: required i32 code;
    2: optional string reason;
}