package com.elasticsearch.esdemo.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @author wangzhimin
 * @version create 2018/11/15 16:20
 */
public class Demo3Test {
    public static String URL = "https://api.netease.im/call/ecp/startcall.action";
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    public static HttpClient HTTP_CLIENT = null;
    public static final PoolingClientConnectionManager CONN_MANAGER = new PoolingClientConnectionManager();

    @Before
    public void init(){
        HTTP_CLIENT = new DefaultHttpClient(CONN_MANAGER);
        HTTP_CLIENT.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000); //设置连接超时时间为5秒
        HTTP_CLIENT.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 30000); //设置读取的超时时间为10000秒。
    }

    @Test
    public void funq1() {
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("callerAcc","grp.stoneboy");
        bodyMap.put("caller","18329172496");
        bodyMap.put("callee","18804636934");
        bodyMap.put("maxDur","300");
        try {
            Response result = httpPost(URL, bodyMap, createHeader());
            String ss = result.getContent();
            System.out.println(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response httpPost(String url, Map<String, String> params, Map<String, String> headerMap) {

        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                nvps.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));

        for (Map.Entry<String, String> headerEntry : headerMap.entrySet()) {
            httpPost.addHeader(headerEntry.getKey(), headerEntry.getValue());
        }

        return httpExecute(httpPost);
    }

    private static Response httpExecute(HttpUriRequest request) {

        Response response = new Response();
        HttpEntity entity = null;
        try {
            HttpResponse httpResponse = HTTP_CLIENT.execute(request);
            entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            response.code = httpResponse.getStatusLine().getStatusCode();
            response.content = content;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    public static class Response {

        /**
         * response http status code
         * if code = -1, http execute error
         */
        public int code = -1;

        /**
         * response http content
         */
        public String content;

        @Override
        public String toString() {
            return "HttpUtils.Response [code=" + code + ", content=" + content + "]";
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    protected Map<String, String> createHeader() {
        Map<String, String> headMap = new HashMap<String, String>();
        int inonce = new Random().nextInt(1000);
        String nonce = String.valueOf(inonce);

        headMap.put("AppKey", "968dc8586afdb56746e236ebad3c631b");
        headMap.put("Nonce", nonce);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = getCheckSum("061deebcca76", nonce, curTime);
        headMap.put("CurTime", curTime);
        headMap.put("CheckSum", checkSum);
        headMap.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        return headMap;
    }

    public String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest
                    = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes("utf-8"));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
}