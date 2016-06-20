package com.rubin.service.impl;

import com.rubin.service.HelloServcie;
import com.rubin.thrift.config.annotation.Service;
import org.apache.thrift.TException;

/**
 * Created by rubin on 16-6-11.
 */
@Service("helloService")
public class HelloServiceImpl implements HelloServcie.Iface {

    @Override
    public String helloString(String para) throws TException {
        return "Hello World!!";
    }

    @Override
    public String helloNull() throws TException {
        return null;
    }

}
