package com.elasticsearch.esdemo.chain;

import java.util.List;

/**
 * @author wangzhimin
 * @version create 2018/10/19 20:07
 */
public class Chain {
    private int index = 0;
    private List<Task> tasks;

    public Chain(List<Task> tasks){
        this.tasks = tasks;
    }
    public void doTask(String request,String data, String response){
        System.out.println(index);
        if (this.index < tasks.size()) {
            tasks.get(index++).execute(request, data,response, this);
        }
    }
}
