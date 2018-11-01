package demo;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import sun.util.PreHashedMap;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhimin
 * @version create 2018/10/24 15:59
 */
public class AviatorDemo {

    public static void main(String[] args){
        String exp = "a-(b-c)";
        Expression expression = AviatorEvaluator.compile(exp);
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 100);
        env.put("b", 45);
        env.put("c", 15);
        long res = (Long) expression.execute(env);
        System.out.println(res);


        NavigableSet<Integer> navigableSet = new TreeSet<>();
        navigableSet.add(1);
        navigableSet.add(2);
        navigableSet.add(3);
        navigableSet.add(4);
        navigableSet.add(5);

        System.out.println(navigableSet.ceiling(3));


        HashMap<String,String> map = new HashMap<>();
        map.put(null,null);

        System.out.println(map.get(null));


        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("2323");
        },5,5, TimeUnit.SECONDS);

    }
}
