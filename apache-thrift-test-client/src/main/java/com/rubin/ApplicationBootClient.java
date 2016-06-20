package com.rubin;

import com.rubin.domain.Request;
import com.rubin.enums.RequestType;
import com.rubin.service.HelloServcie;
import com.rubin.service.HelloWordService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Created by rubin on 16-6-19.
 */
@SpringBootApplication
public class ApplicationBootClient {

    public static void main(String[] args) throws TException {
        SpringApplication.run(ApplicationBootClient.class, args);
        /*TTransport transport = new TSocket("localhost", 7910);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);

        HelloServcie.Client helloService = new HelloServcie.Client(new TMultiplexedProtocol(protocol, "helloService"));

        HelloWordService.Client helloWorldService = new HelloWordService.Client(new TMultiplexedProtocol(protocol, "helloWordService"));

//        TimeUnit.SECONDS.sleep(10);

        System.out.println(helloService.helloString(null));

        System.out.println(helloWorldService.doAction(new Request(RequestType.QUERY_TIME, "rubin")));

        System.out.println(helloWorldService.doAction(new Request(RequestType.SAY_HELLO, "rubin")));

//        System.out.println(helloWorldService.doAction(new Request(null, "rubin")));

        transport.close();*/
    }

}
