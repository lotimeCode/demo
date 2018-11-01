package com.elasticsearch.esdemo.demo;

import java.util.concurrent.TimeUnit;

/**
 * @author wangzhimin
 * @version create 2018/10/23 9:12
 */
public class ThreadLocalDemo {
    public static void main(String[] args) throws Exception{
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("123");
        TimeUnit.SECONDS.sleep(5);
        System.out.println(threadLocal.get());
    }
}
