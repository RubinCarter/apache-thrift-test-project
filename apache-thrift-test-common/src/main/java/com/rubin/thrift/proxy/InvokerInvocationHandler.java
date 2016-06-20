package com.rubin.thrift.proxy;

import org.apache.thrift.TServiceClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by rubin on 16-6-19.
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private TServiceClient invoker;

    public InvokerInvocationHandler(TServiceClient invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(invoker, args);
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), invoker.getClass().getInterfaces(), this);
    }

}
