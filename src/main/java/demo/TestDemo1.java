package demo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author wangzhimin
 * @version create 2018/10/24 9:35
 */
public class TestDemo1 {
    private int a = 0;

    public static void main(String[] args){
        TestDemo1 testDemo1 = new TestDemo1(12);
    }

    public TestDemo1(int a) {
        this.a = a;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("post ...");
    }

    @PreDestroy
    public void pre(){
        System.out.println("pre destroy ...");
    }

}
