package proxy;

/**
 * @author wangzhimin
 * @version create 2018/10/15 19:58
 */
public class Main {
    public static void main(String[] args) {
        LogProxy logProxy = new LogProxy();
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        HelloWorldInteface helloWorldInteface = (HelloWorldInteface) logProxy.getProxyObject(new HelloWorldImpl());
        for(int i=0;i<6;i++){
            helloWorldInteface.sayHello();
        }
        System.out.println(LogProxy.num);
    }
}
