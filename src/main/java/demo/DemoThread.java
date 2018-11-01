package demo;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangzhimin
 * @version create 2018/10/17 10:51
 */
public class DemoThread {
    public static void main(String[] args){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("11111");
            }
        },1,1,TimeUnit.SECONDS);


        String str = "111,2222,333,444,555";
        Lock lock = new ReentrantLock(false);

        String[] strs = str.split(",",2);
        System.out.println(Arrays.asList(strs));

    }
}
