package com.elasticsearch.esdemo;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.search.SearchHit;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author wangzhimin
 * @version create 2018/6/7 16:51
 */
public class EsUtil {


    /**
     * 将搜索结果转变为日志bean
     * @param searchHit
     * @return
     */
    public static <T> Optional<T> getBeanFromSearchHits(SearchHit searchHit, Class<T> clazz){
        T bean = JSON.parseObject(JSON.toJSONString(searchHit.getSourceAsMap()),clazz);
        return Optional.of(bean);
    }


    /**
     * 将搜索结果转换为bean list
     * @param searchHits
     * @return
     */
    public static <T> List<T> getBeanListFromSearchHits(SearchHit[] searchHits,Class<T> clazz){
        if(searchHits == null || searchHits.length == 0){
            return Collections.emptyList();
        }
        List<T> logVOList = new ArrayList<>(searchHits.length);
        for(int i=0;i<searchHits.length;i++){
            logVOList.add(getBeanFromSearchHits(searchHits[i],clazz).get());
        }
        return logVOList;
    }


    public static <T> Map<String,Object> getSourceFromBean(T t){
        return JSON.parseObject(JSON.toJSONString(t),HashMap.class);
    }


}
