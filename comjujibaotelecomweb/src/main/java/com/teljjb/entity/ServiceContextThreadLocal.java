package com.teljjb.entity;

/**
 * @Filename ServiceContextThreadLocal.java
 * @Description
 * @Version 1.0
 * @Author lingmao
 * @Email 162283610@qq.com
 * @History <li>Author: lingmao</li>
 * <li>Date: 2016年4月7日</li>
 * <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
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
