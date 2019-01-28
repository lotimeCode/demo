package com.elasticsearch.esdemo.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author wangzhimin
 * @version create 2018/11/2 18:27
 */
public class Demo2 {
    public static void main(String[] args) throws Exception{
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


        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Task> taskList = new ArrayList<>(10);
        for(int i=0;i<10;i++){
            taskList.add(new Task(i+""));
        }

        long start = System.currentTimeMillis();
        List<Future<String>> futureList = executorService.invokeAll(taskList);
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start));

        for(Future<String> future : futureList){
            System.out.println(future.get());
        }
        executorService.shutdownNow();

    }
}

class Task implements Callable<String>{
    private String num;

    public Task(String num) {
        this.num = num;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(5);
        return "task_" + this.num;
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
