package com.rubin.controller;

import com.rubin.service.HelloServcie;
import com.rubin.thrift.config.annotation.Reference;
import org.apache.thrift.TException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rubin on 16-6-19.
 */
@RestController
public class HelloController {

    @Reference("helloService")
    private HelloServcie.Iface helloService;

    @RequestMapping("/test")
    public String test() throws TException {
        return helloService.helloString("ddd");
    }

    public void setHelloService(HelloServcie.Iface helloService) {
        this.helloService = helloService;
    }

}
