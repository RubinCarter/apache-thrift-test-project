package com.rubin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Created by rubin on 16-6-11.
 */
@SpringBootApplication
public class ApplicationBootServer {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ApplicationBootServer.class, args);
        synchronized (ApplicationBootServer.class) {
            while (true) {
                ApplicationBootServer.class.wait();
            }
        }
    }

}
