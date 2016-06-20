namespace java com.rubin.service
namespace py com.rubin.service

include "Request.thrift"
include "RequestException.thrift"

service HelloWordService {

    string doAction(1: Request.Request request) throws (1:RequestException.RequestException qe);//可能抛出的异常

}