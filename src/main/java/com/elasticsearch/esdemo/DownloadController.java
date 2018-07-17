package com.elasticsearch.esdemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangzhimin
 * @version create 2018/6/5 9:54
 */
@RestController
@RequestMapping("/test")
public class DownloadController {

    @Resource
    private DownloadService downloadService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Map test(){
        Map<String,String> map = new HashMap<>();
        map.put("code","200");
        return map;
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void testDownload(HttpServletResponse response){
        downloadService.downloadLog(response);
    }
}
