package com.elasticsearch.esdemo.chain;

/**
 * @author wangzhimin
 * @version create 2018/10/19 20:00
 */
public interface Task {

    void execute(String req,String data,String res,Chain chain);
}
