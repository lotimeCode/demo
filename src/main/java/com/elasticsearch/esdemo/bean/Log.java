package com.elasticsearch.esdemo.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author wangzhimin
 * @version create 2018/6/5 10:48
 */
@Document(indexName = "log",type = "12345", shards = 1,replicas = 0, refreshInterval = "-1")
public class Log {

    @Id
    private String hostname;
    @Field
    private String logs;


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }
}
