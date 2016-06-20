package com.rubin.thrift.autoconfig;

import com.rubin.thrift.config.spring.AnnotationBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Created by rubin on 16-6-19.
 */
@Configuration
@AutoConfigureAfter(AnnotationBean.class)
public class ThriftAutoConfiguration  {

    @Bean
    public AnnotationBean annotationBean() {
        return new AnnotationBean();
    }

}
