package com.boonya.mina.quickstart.push;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 * 消息订阅客户端
 * 
 * @package com.boonya.mina.quickstart.push.SubscribeClient
 * @date   2017年1月10日  下午4:45:59
 * @author pengjunlin
 * @comment   
 * @update
 */
public class SubscribeClient {
	public static void main(String[] args) throws IOException {

        Socket socket = null;
        BufferedReader in = null;
        try {

            socket = new Socket("localhost", 8090);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 阻塞等待服务器发布的消息
            while (true) {
                String line = in.readLine(); 
                System.out.println("订阅消息："+line);
            }

        } finally {
            // 关闭连接
            in.close();
            socket.close();
        }
    }
}
