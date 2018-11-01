package com.elasticsearch.esdemo.chain;

/**
 * @author wangzhimin
 * @version create 2018/10/19 20:03
 */
public class Task1 implements Task {
    @Override
    public void execute(String req, String data, String res,Chain chain) {
        System.out.println("execute task1 ...");
        res = req + " | " + data;
        System.out.println(res);
        chain.doTask(res,data,"task1");
    }
}
