package com.elasticsearch.esdemo;

import com.elasticsearch.esdemo.bean.Dog;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author wangzhimin
 * @version create 2018/6/1 14:05
 */
@RestController
@RequestMapping(value = "/api")
public class Controller {
    @Resource
    private EsServiceImpl esService;

    @RequestMapping(value = "/animals/dog",method = RequestMethod.GET)
    public List<Dog> queryDog(){
        return esService.queryAll();
    }

    @RequestMapping(value = "/all/{index}/{type}",method = RequestMethod.GET)
    public List<Map> queryAllByTemplate(@PathVariable(value = "index") String index, @PathVariable(value = "type")
            String type){
        return esService.queryAllByTemplate(index,type);
    }

    @RequestMapping(value = "/animals/dog/{id}",method = RequestMethod.GET)
    public List<Dog> queryDogById(@PathVariable(value = "id") String id){
        return esService.queryById(id);
    }

    @RequestMapping(value = "/animals/dog",method = RequestMethod.POST)
    public Dog saveDog(@RequestBody Dog dog){
        return esService.saveDog(dog);
    }


    @RequestMapping(value = "/{index}",method = RequestMethod.POST)
    public String createIndex(@PathVariable(value = "index") String index){
        return esService.createIndex(index);
    }

    @RequestMapping(value = "/{index}",method = RequestMethod.DELETE)
    public String deleteIndex(@PathVariable(value = "index") String index){
        return esService.deleteIndex(index);
    }


    /**
     * 保存日志信息
     */
    @RequestMapping(value = "/log/add",method = RequestMethod.GET)
    public void addLogToES(){
        esService.saveLogs();
    }

    @RequestMapping(value = "/log/query",method = RequestMethod.GET)
    public Map queryLogs(@RequestParam(value = "term") String term){
        return esService.queryLog(term);
    }

}
