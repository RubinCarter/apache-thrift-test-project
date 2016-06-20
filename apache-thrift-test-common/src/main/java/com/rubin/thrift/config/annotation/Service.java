package com.rubin.thrift.config.annotation;

import java.lang.annotation.*;

/**
 * Created by rubin on 16-6-18.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@org.springframework.stereotype.Service
public @interface Service {

    String value() default "";

}
