package com.elasticsearch.esdemo;

import com.alibaba.fastjson.JSON;
import com.elasticsearch.esdemo.bean.Dog;
import com.elasticsearch.esdemo.bean.Log;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wangzhimin
 * @version create 2018/6/1 13:50
 */
@Service
public class EsServiceImpl {

    @Resource
    private ElasticDogRepository dogRepository;

    @Resource
    private ElasticLogRepository logRepository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Resource
    private DownloadService downloadService;

    public List<Dog> queryAll() {
        List<Dog> dogs = new ArrayList<>(16);
        Iterable<Dog> dogIterator = dogRepository.findAll();
        for(Iterator<Dog> iterator = dogIterator.iterator(); iterator.hasNext();){
            Dog dog = iterator.next();
            dogs.add(dog);
        }
        return dogs;
    }

    public List<Map> queryAllByTemplate(String index,String type) {
        List<Map> dogs = new ArrayList<>(16);
        Client client = elasticsearchTemplate.getClient();
        SearchRequestBuilder builder = client.prepareSearch(index).setTypes(type);
        SearchResponse searchResponse = builder.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        System.out.println(JSON.toJSONString(searchResponse));
        SearchHits searchHits = searchResponse.getHits();
        for(SearchHit hit : searchHits){
            Map<String,Object> dog =  hit.getSource();
            dogs.add(dog);
        }

        System.out.println(JSON.toJSONString(dogs));
        return dogs;
    }

    public List<Dog> queryById(String id) {
        List<Dog> dogs = new ArrayList<>(16);
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if(optionalDog.isPresent()){
            dogs.add(optionalDog.get());
        }
        return dogs;
    }

    public Dog saveDog(Dog dog) {
        dog.setId(UUID.randomUUID().toString());
        dogRepository.save(dog);
        return dog;
    }

    public String createIndex(String index) {
        CreateIndexResponse createIndexResponse = elasticsearchTemplate.getClient().admin().indices().prepareCreate
                (index).execute().actionGet();
        if(createIndexResponse.isAcknowledged()){
            return "success";
        }else{
            return "fail";
        }
    }

    public String deleteIndex(String index){
        DeleteIndexResponse deleteIndexResponse = elasticsearchTemplate.getClient().admin().indices().prepareDelete(index)
                .execute().actionGet();
        if(deleteIndexResponse.isAcknowledged()){
            return "success";
        }else{
            return "fail";
        }
    }


    /**
     * 保存日志
     */
    public void saveLogs(){
        Log log = new Log();
        log.setHostname("hzayq-numen-performertest.server.163.org");
        log.setLogs(downloadService.readTxt("C:\\Users\\wangzhimin\\Downloads\\log%2F74694%2Fhzayq-numen-performertest.server.163.org%2Ferror_jmeter.log"));
        logRepository.save(log);
    }


    public Map queryLog(String term){
        Map<String,String> logMap = new HashMap<>(16);
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("logs",term);
        Iterable<Log> logs = logRepository.search(queryBuilder);
        for(Iterator<Log> iterator = logs.iterator(); iterator.hasNext();){
            Log log = iterator.next();
            logMap.put(log.getHostname(),log.getLogs());
        }
        return logMap;
    }
}
