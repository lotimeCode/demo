package com.elasticsearch.esdemo.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangzhimin
 * @version create 2018/7/12 16:49
 */
public class MyService {

    private volatile int a=12;

    private AtomicInteger integer = new AtomicInteger(12);
    public static void main(String[] args) {
        Service service = new Service();
        Thread1 thread1 = new Thread1(service);
        Thread2 thread2 = new Thread2(service);
        thread1.start();
        thread2.start();
    }
}

class Thread1 extends Thread{
    Service service;
    public Thread1(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        this.service.add("aa");
    }
}

class Thread2 extends Thread{
    Service service;
    public Thread2(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        this.service.add("bb");
    }
}

class Service {
    int num = 0;
    public synchronized void add(String name) {
        try {

            if (name.equals("aa")) {
                num = 100;
                System.out.println("a is over");
                Thread.sleep(1000);
            } else {
                num = 200;
                System.out.println("b is over");
            }
            System.out.println(name + " num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}