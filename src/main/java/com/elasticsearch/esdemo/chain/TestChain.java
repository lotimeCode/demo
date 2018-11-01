package com.elasticsearch.esdemo.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhimin
 * @version create 2018/10/19 20:15
 */
public class TestChain {
    public static void main(String[] args){
        List<Task> tasks = new ArrayList<>(3);
        tasks.add(new Task1());
        tasks.add(new Task2());
        tasks.add(new Task3());
        Chain chain = new Chain(tasks);
        chain.doTask("start","xxxxx","");
    }
}
