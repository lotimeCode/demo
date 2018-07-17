package com.elasticsearch.esdemo;

import com.alibaba.fastjson.JSON;
import com.elasticsearch.esdemo.bean.Log;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

/**
 * @author wangzhimin
 * @version create 2018/6/14 19:45
 */
public class TestTransfer {

    @Test
    public void test(){
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<testResults version=\"1.2\">\r\n<httpSample " +
                "t=\"6\" ts=\"1524640723944\" lb=\"8044\" rc=\"Non HTTP <mark>response</mark> code: org.apache.http" +
                ".NoHttpResponseException\" tn=\"THREAD GROUP 1-6\">\r\n  <responseData class=\"java.lang.String\">org.apache.http.NoHttpResponseException: www.baidu.com:80 failed to respond\r\n\tat org.apache.http.impl.conn.DefaultHttpResponseParser.parseHead(DefaultHttpResponseParser.java:143)\r\n\tat org.apache.http.impl.conn.DefaultHttpResponseParser.parseHead(DefaultHttpResponseParser.java:57)\r\n\tat org.apache.http.impl.io.AbstractMessageParser.parse(AbstractMessageParser.java:259)\r\n\tat org.apache.http.impl.AbstractHttpClientConnection.receiveResponseHeader(AbstractHttpClientConnection.java:281)\r\n\tat org.apache.http.impl.conn.DefaultClientConnection.receiveResponseHeader(DefaultClientConnection.java:259)\r\n\tat org.apache.http.impl.conn.ManagedClientConnectionImpl.receiveResponseHeader(ManagedClientConnectionImpl.java:209)\r\n\tat org.apache.jmeter.protocol.http.sampler.MeasuringConnectionManager$MeasuredConnection.receiveResponseHeader(MeasuringConnectionManager.java:212)\r\n\tat org.apache.http.protocol.HttpRequestExecutor.doReceiveResponse(HttpRequestExecutor.java:273)\r\n\tat org.apache.http.protocol.HttpRequestExecutor.execute(HttpRequestExecutor.java:125)\r\n\tat org.apache.http.impl.client.DefaultRequestDirector.tryExecute(DefaultRequestDirector.java:686)\r\n\tat org.apache.http.impl.client.DefaultRequestDirector.execute(DefaultRequestDirector.java:488)\r\n\tat org.apache.http.impl.client.AbstractHttpClient.doExecute(AbstractHttpClient.java:884)\r\n\tat org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:82)\r\n\tat org.apache.http.imp";

//        str = str.replace("<mark>","qq123456789qq");
//        str = str.replace("</mark>","qq987654321qq");
//        System.out.println(str);
//        String nreStr = StringEscapeUtils.escapeHtml(str);
//        System.out.println("-----------------"+nreStr);
//
//        nreStr.replace(StringEscapeUtils.escapeHtml("***"),"<mark>");
//        nreStr.replace(StringEscapeUtils.escapeHtml("+++"),"</mark>");

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                default:
                    buffer.append(c);
            }
        }

        String newStr = buffer.toString();
        System.out.println(newStr);

        newStr.replace("&lt;",'<' + "");
        newStr.replace("&gt;",'>' + "");
        System.out.println(newStr);
    }

    @Test
    public void test2(){
        int i =8;
        System.out.println(i>>1);
        System.out.println(i<<1);
    }
}
