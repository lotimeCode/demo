package com.elasticsearch.esdemo.demo;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

/**
 * @author wangzhimin
 * @version create 2018/11/2 18:27
 */
public class Demo2 {
    public static void main(String[] args){
        String str = "lotim  shi     aaa";
        for(String s : str.split("[\\s,|]+")){
            System.out.println(s);
        }


        List<String> list = Arrays.asList(str.split("[\\s,|]+"));
        System.out.println(list.size());

//        User user = new User("www",12);
//        AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class,"age");
//
//        System.out.println(updater.addAndGet(user,14));
//
//
//        DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            System.out.println(new Date(DATE_FORMAT.parse("2015-10-20 10:10:10").getTime()));
//        }catch (Exception e){
//
//        }

    }
}

class User{
    private String name;
    public volatile int age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
