package com.rubin.thrift.config;

/**
 * Created by rubin on 16-6-19.
 */
public class ServiceConfig<T> {

    // 服务名称
    private String path;

    //服务引用
    private T t;

    public ServiceConfig(String path, T t) {
        this.path = path;
        this.t = t;
    }

    public String getPath() {
        return path;
    }

    public T getT() {
        return t;
    }

}
