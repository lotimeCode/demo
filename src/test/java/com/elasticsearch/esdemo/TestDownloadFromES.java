package com.elasticsearch.esdemo;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author wangzhimin
 * @version create 2018/7/3 15:24
 */
public class TestDownloadFromES {

    private TransportClient client;
    @Before
    public void init() throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", false)
                .build();
        client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("118.31.73.111"), 9300));
    }


    @Test
    public void testDl(){
        List<DiscoveryNode> discoveryNodes  = client.connectedNodes();
        System.out.println("discoveryNodes : " + JSON.toJSONString(discoveryNodes));

        int size = 1000;

        SearchHits searchHits = this.query(size,0);
        String fileName = this.getFileName(searchHits);
        this.writeLog(fileName,this.getLogs(searchHits));

        long total = searchHits.getTotalHits();
        long num = total / size + 1;
        for(int i = 1;i<num;i++){
            searchHits = this.query(size,i * size);
            this.writeLog(fileName,this.getLogs(searchHits));
        }
    }

    @Test
    public void queryScroll(){
        SearchResponse searchResponse = client.prepareSearch("numen-6.1.1-2018.07.02").setTypes("doc")
                .setQuery(QueryBuilders.matchQuery("beat.hostname","hzayq-gzyl-test-01"))
                .setSize(1000)
                .setScroll(new TimeValue(10000))
                .execute()
                .actionGet();
//        System.out.println("query context:" + JSON.toJSONString(searchResponse));
        SearchHits searchHits = searchResponse.getHits();
        System.out.println("size : " + searchHits.getHits().length);

        String fileName = this.getFileName(searchHits);
        this.writeLog(fileName,this.getLogs(searchHits));

        String scrollId = searchResponse.getScrollId();

        int i = 1;
        while(true){
            searchResponse = client.prepareSearchScroll(scrollId)
                    .setScroll(new TimeValue(10000))
                    .execute()
                    .actionGet();
            System.out.println("size : " + searchResponse.getHits().getHits().length);
            if(searchResponse.getHits().getHits().length == 0){
                break;
            }
            scrollId = searchResponse.getScrollId();
            this.writeLog(fileName,this.getLogs(searchResponse.getHits()));

            System.out.println("write times: " + i);
            i++;
        }
//        return searchHits;
    }

    private void writeLog(String fileName,String context){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            //则直接往文件中追加字符串
            FileWriter writer=new FileWriter(fileName,true);
            writer.write(context);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private SearchHits query(int size,int curr){
        SearchResponse searchResponse = client.prepareSearch("numen-6.1.1-2018.07.02").setTypes("doc")
                .setQuery(QueryBuilders.matchQuery("beat.hostname","hzayq-gzyl-test-01"))
                .setSize(size)
                .setFrom(curr)
                .execute()
                .actionGet();
//        System.out.println("query context:" + JSON.toJSONString(searchResponse));
        SearchHits searchHits = searchResponse.getHits();
        return searchHits;
    }

    private String getLogs(SearchHits searchHits){
        SearchHit[] hits = searchHits.getHits();
        StringBuilder sb = new StringBuilder();
        for(SearchHit hit : hits) {
            sb.append(hit.getSourceAsMap().get("message")).append("\r\n");
        }
        return sb.toString();
    }

    private String getFileName(SearchHits searchHits){
        SearchHit[] hits = searchHits.getHits();
        String path = hits[0].getSourceAsMap().get("source").toString();
        System.out.println("log path : " + path);

        String fileName = path.substring(path.lastIndexOf("/"));
        System.out.println("file name : " + fileName);
        fileName = "E:\\" + fileName;
        return fileName;
    }

}
