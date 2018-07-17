package com.elasticsearch.esdemo.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;


/**
 * @author wangzhimin
 * @version create 2018/6/1 11:38
 */
@Document(indexName = "animals",type = "dog", shards = 1,replicas = 0, refreshInterval = "-1")
public class Dog {
    @Id
    private String id;
    @Field
    private String name;
    @Field
    private Integer age;
    @Field
    private String characteristic;
    @Field
    private String address;
    @Field
    private Integer price;
    @Field
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
