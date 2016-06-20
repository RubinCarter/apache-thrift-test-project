package com.rubin.thrift.config.spring;

import com.rubin.thrift.common.utils.ConcurrentHashSet;
import com.rubin.thrift.config.ServiceConfig;
import com.rubin.thrift.config.annotation.Reference;
import com.rubin.thrift.config.annotation.Service;
import com.rubin.thrift.proxy.InvokerInvocationHandler;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.*;
import java.util.Set;

/**
 * Created by rubin on 16-6-19.
 */
@Configuration
public class AnnotationBean implements BeanPostProcessor {

    private Set<ServiceConfig<TProcessor>> serviceConfigs = new ConcurrentHashSet<>();

    private final Object key = new Object();

    private volatile Boolean isFirst = true;

    private volatile TTransport transport = null;
    private volatile TProtocol protocol = null;

    @Bean
    public ApplicationListener<ContextRefreshedEvent> applicationListener() {
        return new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event) {
                if(event.getApplicationContext().getParent() == null) {
                    if(serviceConfigs.size() > 0) {
                        try {
                            TMultiplexedProcessor processor = new TMultiplexedProcessor();
                            serviceConfigs.forEach(config -> processor.registerProcessor(config.getPath(), config.getT()));
                            TServerSocket serverSocket = new TServerSocket(7910);
                            TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();
                            TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverSocket)
                                    .processor(processor).protocolFactory(factory);
                            TServer server = new TThreadPoolServer(serverArgs);
                            server.serve();
                        } catch (TTransportException e) {
                            //
                        }
                    }
                }
            }
        };
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if(isProxyBean(bean)) {
            clazz = AopUtils.getTargetClass(bean);
        }
        Service service = clazz.getAnnotation(Service.class);
        if(service != null) {
            if(clazz.getInterfaces().length > 0) {
                try {
                    Class<?> interfaceName = clazz.getInterfaces()[0];
                    Constructor<?> constructor = Class.forName(interfaceName.getDeclaringClass().getName() + "$Processor").getConstructor(interfaceName);
                    TProcessor processorBean = (TProcessor)constructor.newInstance(bean);
                    serviceConfigs.add(new ServiceConfig<>(service.value(), processorBean));
                } catch (ReflectiveOperationException e) {
                    //
                }
            } else {
                ///出错
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if(isProxyBean(bean)) {
            clazz = AopUtils.getTargetClass(bean);
        }
        for (Method method : clazz.getMethods()) {
            try {
                String name = method.getName();
                if(name.length() > 3 && name.startsWith("set")
                        && method.getParameterTypes().length == 1
                        && Modifier.isPublic(method.getModifiers())
                        && ! Modifier.isStatic(method.getModifiers())) {
                    Reference reference = method.getAnnotation(Reference.class);
                    if(reference != null) {
                        Object refer = refer(reference, method.getParameterTypes()[0]);
                        method.invoke(bean, refer);
                    }
                }
            } catch (Exception e) {

            }
        }
        for (Field field : clazz.getDeclaredFields()) {
            try {
                if(!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Reference reference = field.getAnnotation(Reference.class);
                if(reference != null) {
                    Object refer = refer(reference, field.getType());
                    field.set(bean, refer);
                }
            } catch (Exception e) {
                //
            }
        }
        return bean;
    }

    private boolean isProxyBean(Object bean) {
        return AopUtils.isAopProxy(bean);
    }

    private Object refer(Reference reference, Class<?> interfaceName) throws ClassNotFoundException, NoSuchMethodException, TTransportException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if(isFirst) {
            synchronized (key) {
                if(isFirst) {
                    isFirst = false;
                    transport = new TSocket("localhost", 7910);
                    transport.open();
                    protocol = new TBinaryProtocol(transport);
                }
            }
        }
        Class<?> clazz = Class.forName(interfaceName.getDeclaringClass().getName() + "$Client");
        Constructor<?> constructor = clazz.getConstructor(TProtocol.class);
        TServiceClient instance = (TServiceClient)constructor.newInstance(new TMultiplexedProtocol(protocol, reference.value()));
        return new InvokerInvocationHandler(instance).getProxy();
    }

}
