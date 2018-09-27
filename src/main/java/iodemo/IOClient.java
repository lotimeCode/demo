package iodemo;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author wangzhimin
 * @version create 2018/9/27 11:33
 */
public class IOClient {

    public static void main(String[] args) {
        startThread("hello world 111");
        startThread("hello world 222");
    }

    public static void startThread(String str){
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": " + str).getBytes());
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
