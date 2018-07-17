package com.elasticsearch.esdemo;

import com.elasticsearch.esdemo.bean.Dog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author wangzhimin
 * @version create 2018/6/1 13:44
 */
@Component
public interface ElasticDogRepository extends ElasticsearchRepository<Dog,String> {



}
