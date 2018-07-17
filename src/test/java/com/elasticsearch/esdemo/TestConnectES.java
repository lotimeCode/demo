package com.elasticsearch.esdemo;

import com.alibaba.fastjson.JSON;
import com.elasticsearch.esdemo.bean.Log;
import com.elasticsearch.esdemo.bean.LogNew;
import org.apache.commons.lang.StringEscapeUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.transport.ConnectTransportException;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.util.*;

import static org.elasticsearch.common.unit.TimeValue.timeValueSeconds;

/**
 * @author wangzhimin
 * @version create 2018/6/1 17:38
 */
public class TestConnectES {
    private TransportClient client;
    @Before
    public void init() throws Exception{
//        Settings settings = Settings.builder()
//                .put("cluster.name", "elasticsearch")
//                .put("client.transport.sniff",false)
//                .build();
//        client = new PreBuiltTransportClient(settings);
//        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("118.31.73.111"), 9300));



        Settings settings = Settings.builder()
                .put("cluster.name", "ptp-es-test")
                .put("client.transport.sniff",false)
                .put("transport.tcp.connect_timeout",timeValueSeconds(3))
                .build();
        client = new PreBuiltTransportClient(settings);

        try {
            long start = System.currentTimeMillis();
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.164.97.10"), 9300));
            long end1 = System.currentTimeMillis();
            System.out.println("cost : " + (end1 - start));
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.164.97.11"), 9300));
            long end2 = System.currentTimeMillis();
            System.out.println("cost : " + (end2 - end1));
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.164.97.12"), 9300));
            long end3 = System.currentTimeMillis();
            System.out.println("cost : " + (end3 - end2));
        }catch (ElasticsearchException e){
            System.out.println("===========" + e.getMessage());
        }
    }

    @Test
    public void test(){
        List<DiscoveryNode> discoveryNodes  = client.connectedNodes();
        System.out.println("discoveryNodes : " + JSON.toJSONString(discoveryNodes));

        ClusterHealthResponse healthResponse = client.admin().cluster().prepareHealth().get();
        System.out.println("========== cluster:" + healthResponse.getClusterName() + ",   status:" +  healthResponse
                .getStatus() + ",   nodes Num:" + healthResponse.getNumberOfNodes());
    }

    /**
     * 全文查询
     */
    @Test
    public void test1(){
        SearchResponse searchResponse = client.prepareSearch("5678").setTypes("log").setQuery(QueryBuilders.matchQuery
                ("log", "sufficientzzzz")).execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for(SearchHit hit : hits) {
            System.out.println("search result : " + hit.getId() + " " + hit.getSource().get("logs"));
        }
    }

    /**
     * 指定字段查询返回字段
     */
    @Test
    public void test2(){
        SearchResponse searchResponse = client.prepareSearch("62908").setTypes("log")
                .setFetchSource("hostname","log")
                .setSize(10000)
                .setFrom(0)
                .execute()
                .actionGet();
        System.out.println(JSON.toJSONString(searchResponse));
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        System.out.println(hits.length);
        for(SearchHit hit : hits) {
            System.out.println("test2 === "+hit.getSourceAsMap().get("hostname"));
        }
    }

    /**
     * 对指定记录的查询
     */
    @Test
    public void test3(){
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("hostname","hzayq654-performertest.server.163.org"))
                .must(QueryBuilders.matchQuery("logs","AbstractSessionInputBuffer"));

        SearchResponse searchResponse = client.prepareSearch("log").setTypes("12345").setQuery(queryBuilder).execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for(SearchHit hit : hits) {
            System.out.println("==="+JSON.toJSONString(hit));
        }
    }

    /**
     * 根据id查询
     */
    @Test
    public void test4(){
        IdsQueryBuilder idsQueryBuilder = new IdsQueryBuilder();
        idsQueryBuilder.addIds("hzayq654-performertest.server.163.org");

        SearchResponse searchResponse = client.prepareSearch("log").setTypes("12345").setQuery(idsQueryBuilder).execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();
        System.out.println(JSON.toJSONString(searchHits));
        SearchHit[] hits = searchHits.getHits();
        for(SearchHit hit : hits) {
            System.out.println("==="+JSON.toJSONString(hit));
        }
    }

//    /**
//     * 高亮(not ok)
//     */
//    @Test
//    public void test5(){
//        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
//                .must(QueryBuilders.matchQuery("desc","thing"));
//
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("desc");
//        highlightBuilder.requireFieldMatch(false);
//        highlightBuilder.preTags("<span>");
//        highlightBuilder.postTags("</span>");
//
//        SearchResponse searchResponse = client.prepareSearch("accounts").setTypes("person")
//                .setQuery(queryBuilder)
//                .highlighter(highlightBuilder)
//                .execute().actionGet();
////        for(SearchHit hit : searchResponse.getHits()){
//            System.out.println(JSON.toJSONString(searchResponse));
////        }
//
//    }

    /**
     * 查询高亮(OK)
     */
    @Test
    public void test6(){
        HighlightBuilder highlightBuilder1 = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("logs");
        highlightTitle.highlighterType("unified");
        highlightBuilder1.field(highlightTitle);
        highlightBuilder1.preTags("<span>");
        highlightBuilder1.postTags("</span>");
        highlightBuilder1.requireFieldMatch(false);

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("hostname","hzayq654-performertest.server.163.org"))
                .must(QueryBuilders.matchQuery("logs","AbstractSessionInputBuffer"));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.highlighter(highlightBuilder1);
        searchSourceBuilder.query(queryBuilder);


        SearchRequest request = new SearchRequest();
        request.source(searchSourceBuilder);
        request.indices("log");
        request.types("12345");
        SearchResponse searchResponse = client.search(request).actionGet();
        System.out.println(searchResponse);
    }


    @Test
    public void test11(){
        HighlightBuilder highlightBuilder1 = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("log");
        highlightBuilder1.field(highlightTitle);
        highlightBuilder1.preTags("<span>");
        highlightBuilder1.postTags("</span>");
        highlightBuilder1.requireFieldMatch(false);
        highlightBuilder1.fragmentSize(0);

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("hostname","hzayq-535353-performertest.server.163.org"))
                .must(QueryBuilders.matchQuery("log","sufficient"));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.highlighter(highlightBuilder1);
        searchSourceBuilder.query(queryBuilder);


        SearchRequest request = new SearchRequest();
        request.source(searchSourceBuilder);
        request.indices("5678");
        request.types("log");
        SearchResponse searchResponse = client.search(request).actionGet();
        System.out.println(searchResponse);
    }

    /**
     * 创建索引，设置mapping
     */
    @Test
    public void test7() throws Exception{
        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate("lotime005");
        XContentBuilder mapping = XContentFactory
                .jsonBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("hostname")
                            .field("type","text")
                            .field("analyzer","standard")
                        .endObject()
                        .startObject("log")
                            .field("type","text")
                            .field("analyzer","pattern")
                            .field("search_analyzer","standard")
                        .endObject()
                    .endObject()
                .endObject();
        System.out.println(mapping.string());
        createIndexRequestBuilder.addMapping("log",mapping);
        CreateIndexResponse createIndexResponse = createIndexRequestBuilder.execute().actionGet();
        System.out.println(JSON.toJSONString(createIndexResponse));
    }

    /**
     * 添加记录
     */
    @Test
    public void test8(){

        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<testResults version=\"1.2\">\r\n<httpSample " +
                "t=\"6\" ts=\"1524640723944\" lb=\"8044\" rc=\"Non HTTP response code: org.apache.http.NoHttpResponseException\" tn=\"THREAD GROUP 1-6\">\r\n  <responseData class=\"java.lang.String\">org.apache.http.NoHttpResponseException: www.baidu.com:80 failed to respond\r\n\tat org.apache.http.impl.conn.DefaultHttpResponseParser.parseHead(DefaultHttpResponseParser.java:143)\r\n\tat org.apache.http.impl.conn.DefaultHttpResponseParser.parseHead(DefaultHttpResponseParser.java:57)\r\n\tat org.apache.http.impl.io.AbstractMessageParser.parse(AbstractMessageParser.java:259)\r\n\tat org.apache.http.impl.AbstractHttpClientConnection.receiveResponseHeader(AbstractHttpClientConnection.java:281)\r\n\tat org.apache.http.impl.conn.DefaultClientConnection.receiveResponseHeader(DefaultClientConnection.java:259)\r\n\tat org.apache.http.impl.conn.ManagedClientConnectionImpl.receiveResponseHeade";

        IndexRequest indexRequest = new IndexRequest ("1111","log","test1234.server.163.org");

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("hostname", "test1234.server.163.org");
        jsonMap.put("log", StringEscapeUtils.escapeXml(str));

        indexRequest.source(jsonMap);

        IndexResponse indexResponse = client.index(indexRequest).actionGet();
        System.out.println(indexResponse);
    }

    /**
     * 删除索引下所有的记录
     */
    @Test
    public void test9(){
        DeleteIndexRequest request = new DeleteIndexRequest("8480");
        DeleteIndexResponse deleteIndexResponse = client.admin().indices().delete(request).actionGet();
        System.out.println(JSON.toJSONString(deleteIndexResponse));

    }


    /**
     * 查询索引下的所有数据
     */
    @Test
    public void test10(){
        SearchResponse searchResponse = client.prepareSearch("log").setTypes("12345").execute().actionGet();
        System.out.println(JSON.toJSONString(searchResponse));
    }

    /**
     * 健康检查，集群状态
     */
    @Test
    public void test12(){
        ClusterStatsResponse response = client.admin().cluster().prepareClusterStats().execute().actionGet();
        System.out.println(response);
    }


    /**
     * 测试添加多条记录耗时
     */
    @Test
    public void test13(){
        int num = 300;
        String index = "112233";
        String type = "log";
        String text = "just for test";

        List<String> hostnameList = new ArrayList<>(num);
        for(int i=0;i < num;i++){
            hostnameList.add("server-" + (i + 1) + "netease.163.org");
        }


        IndexRequest indexRequest = new IndexRequest ("1111","logs","333-performertest.server.163.org");

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("hostname", "333-performertest.server.163.org");
        jsonMap.put("log", "19:57:51.187 [main] DEBUG io.netty.util.ResourceLeakDetectorFactory - Loaded default ResourceLeakDetector: io.netty.util.ResourceLeakDetector@29a23c3d");
        indexRequest.source(jsonMap);

        IndexResponse indexResponse = client.index(indexRequest).actionGet();
        System.out.println(indexResponse);
    }


    @Test
    public void test14() throws Exception{
//        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate("filetest");
//        XContentBuilder mapping = XContentFactory
//                .jsonBuilder()
//                .startObject()
//                .startObject("properties")
//                .startObject("file")
//                .field("type","text")
//                .endObject()
//                .endObject()
//                .endObject();
//        System.out.println(mapping.string());
//        createIndexRequestBuilder.addMapping("tmp",mapping);
//        CreateIndexResponse createIndexResponse = createIndexRequestBuilder.execute().actionGet();
//        System.out.println(JSON.toJSONString(createIndexResponse));


        StringBuilder str = new StringBuilder();

        String pathname = "E:\\catalina_2018-06-09.log";
        File filename = new File(pathname);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        line = br.readLine();
        str.append(line);
        while (line != null) {
            line = br.readLine();
            str.append(line);
        }

        System.out.println("string length : " + str.length());


        IndexRequest indexRequest = new IndexRequest ("filetest","tmp","file001");

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("file", str.toString());

        indexRequest.source(jsonMap);

        IndexResponse indexResponse = client.index(indexRequest).actionGet();
        System.out.println(indexResponse);

    }

    @Test
    public void test15() throws Exception{
        IdsQueryBuilder idsQueryBuilder = new IdsQueryBuilder();
        idsQueryBuilder.addIds("file001");

        SearchResponse searchResponse = client.prepareSearch("filetest").setTypes("tmp").setQuery(idsQueryBuilder).execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for(SearchHit hit : hits) {
            File writename = new File("E:\\catalina_2018-06-12.download");
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(hit.getSourceAsMap().get("file").toString());
            out.flush();
            out.close();
        }
    }

    @Test
    public void test16(){
        List<LogNew> list = new ArrayList<>();

        SearchResponse searchResponse = client.prepareSearch("5678").setTypes("log").execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for(SearchHit hit : hits) {
            Optional<LogNew> optionalLogNew = EsUtil.getBeanFromSearchHits(hit,LogNew.class);
            list.add(optionalLogNew.get());
        }

        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void test17(){
        Map<String,String> map = new HashMap<>();
        map.put("log","update_0002");
        UpdateRequest updateRequest = new UpdateRequest("5678","log","hzayq-535353-performertest.server.163.org").doc(map);
        UpdateResponse updateResponse = client.update(updateRequest).actionGet();
        System.out.println(JSON.toJSONString(updateResponse));
    }


    @Test
    public void test18(){
        GetIndexResponse getIndexResponse = this.client.admin().indices().prepareGetIndex().execute().actionGet();
        String[]  index =  getIndexResponse.getIndices();
        System.out.println(JSON.toJSONString(index));
    }

    @Test
    public void terst19(){
        Log log = new Log();
        log.setHostname("host1");
        log.setLogs("error list ...");
        Map<String,Object> map = EsUtil.getSourceFromBean(log);
        System.out.println(map.get("hostname"));
        System.out.println(map.get("logs"));
    }

}
