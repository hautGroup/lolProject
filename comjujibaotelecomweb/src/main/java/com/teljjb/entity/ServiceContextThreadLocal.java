package com.teljjb.entity;

public class ServiceContextThreadLocal {

    public static final ThreadLocal<ServiceContext> threadLocal = new ThreadLocal<ServiceContext>() {
        protected ServiceContext initialValue() {
            return new ServiceContext();
        }

        ;
    };

    public static void set(ServiceContext c) {
        threadLocal.set(c);
    }

    public static void reSet() {
        threadLocal.remove();
    }

    public static ServiceContext get() {
        return threadLocal.get();
    }

}
