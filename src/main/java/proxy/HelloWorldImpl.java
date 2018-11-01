package proxy;

/**
 * @author wangzhimin
 * @version create 2018/10/15 19:57
 */
public class HelloWorldImpl implements HelloWorldInteface {
    @Override
    public void sayHello() {
        System.out.println("hello world");
    }
}
