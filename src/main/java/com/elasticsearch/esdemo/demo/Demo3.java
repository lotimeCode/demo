package com.elasticsearch.esdemo.demo;

import com.alibaba.fastjson.JSON;
import com.elasticsearch.esdemo.bean.Dog;
import com.elasticsearch.esdemo.bean.Log;
import org.apache.commons.beanutils.BeanUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author wangzhimin
 * @version create 2018/11/15 9:37
 */
public class Demo3 {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    public static void main(String[] args) throws Exception {

//        Map<Integer,String> map = new HashMap<>(5);
//        map.put(1,"11111");
//        map.put(2,"22222");
//        map.put(3,"33333");
//        map.put(4,"44444");
//        map.put(5,"55555");
//
//
//        Map<Integer,String> map2 = new HashMap<>();
//        map2.putAll(map);
//
//        System.out.println(JSON.toJSONString(map));
//        System.out.println(JSON.toJSONString(map2));
//
//        map2.remove(5);
//
//        System.out.println(JSON.toJSONString(map));
//        System.out.println(JSON.toJSONString(map2));


//        Dom dom = new Dom(12, "dom1");
//        HashMap<Integer, Dom> map = new HashMap<>();
//        map.put(12, dom);
//
//        HashMap<Integer, Dom> copy = (HashMap<Integer, Dom>) copy(map);
//        System.out.println(JSON.toJSONString(map));
//        System.out.println(JSON.toJSONString(copy));
//
//
//        copy.get(12).setName("lily");
//
//        System.out.println(JSON.toJSONString(map));
//        System.out.println(JSON.toJSONString(copy));
//
//        copy.remove(12);
//        System.out.println(JSON.toJSONString(map));
//        System.out.println(JSON.toJSONString(copy));



        testExchange();

    }

    public static void testExchange(){
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(()->{
            try{
                exchanger.exchange("lotime001");
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        service.submit(()->{
            String x = "wzm002";
            try {
                String y = exchanger.exchange("");
                System.out.println(y);
            }catch (Exception e){

            }
        });

        service.shutdown();
    }



    public static Object copy(Object o) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(o);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        return ois.readObject();
    }


    public void funq1() {
        long start = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("1111");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(8);
                System.out.println("2222");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("3333");
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("cost : " + (end - start));
    }
}


class Dom implements Serializable {

    private static final long serialVersionUID = -7540483643740898346L;

    public Dom(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
