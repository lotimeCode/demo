package com.elasticsearch.esdemo;

import com.elasticsearch.esdemo.bean.Log;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author wangzhimin
 * @version create 2018/6/5 9:33
 */
@Component
public class DownloadService {

    public static String FILE = "C:\\Users\\wangzhimin\\Downloads\\log%2F74272%2Fapp-71.photo.163.org%2Ferror_jmeter.log";
    public static String FILE_NAME = "hzayq-numen-performertest.server.163.org";
    public static String FILE_NAME_POSTFIX = "_error.log";


    @Resource
    private ElasticLogRepository logRepository;


    public void downloadLog(HttpServletResponse response) {
        // 一下两行关键的设置
        response.setContentType("text/plain");
        // filename指定默认的名字
        response.addHeader("Content-Disposition","attachment; filename=test01");

        StringBuffer write = new StringBuffer();

        try(ServletOutputStream outSTr = response.getOutputStream();
            BufferedOutputStream buff = new BufferedOutputStream(outSTr)) {
            write.append(logRepository.findById("hzayq-numen-performertest.server.163.org").orElse(new Log()).getLogs());
            buff.write(write.toString().getBytes("UTF-8"));
            buff.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String readTxt(String fileName){
        String enter = "\r\n";
        StringBuilder txt = new StringBuilder();
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(fileName)), "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)){
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                txt.append(lineTxt).append(enter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(txt.toString());
        return txt.toString();
    }
}
