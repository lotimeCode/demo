package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wangzhimin
 * @version create 2018/10/15 19:53
 */
public class LogProxy implements InvocationHandler {
    static int num = 0;
    private Object object;

    public Object getProxyObject(Object o){
        object=o;
        try{
            return Proxy.newProxyInstance(this.getClass().getClassLoader(),o.getClass().getInterfaces(),this);
        }catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }

    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke ...");
        Object result;
        try {
            result = method.invoke(object, args);
        }finally {
            num++;
        }
        System.out.println("after invoke ...");
        return result;
    }
}
