package com.rubin.service.impl;

import com.rubin.domain.Request;
import com.rubin.enums.RequestType;
import com.rubin.exception.RequestException;
import com.rubin.service.HelloWordService;
import com.rubin.thrift.config.annotation.Service;
import org.apache.thrift.TException;

import java.util.Date;

/**
 * Created by rubin on 16-6-11.
 */
@Service("helloWordService")
public class HelloWorldServiceImpl implements HelloWordService.Iface {

    @Override
    public String doAction(Request request) throws RequestException, TException {
        if(request == null || request.getName() == null || request.getType() == null) {
            throw new RequestException();
        }
        String result = "Hello,";
        if(RequestType.SAY_HELLO == request.getType()) {
            result += request.getName();
        } else {
            result += ", Now is" + new Date();
        }
        return result;
    }

}
