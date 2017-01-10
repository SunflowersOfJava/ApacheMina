package com.boonya.mina.quickstart.push;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;
/**
 * 消息发布客户端
 * 
 * @package com.boonya.mina.quickstart.push.PublishClient
 * @date   2017年1月10日  下午4:45:43
 * @author pengjunlin
 * @comment   
 * @update
 */
public class PublishClient {
	
	public static void main(String[] args) throws IOException {

        Socket socket = null;
        OutputStream out = null;

        try {
        	String uuid=UUID.randomUUID().toString();
            socket = new Socket("localhost", 8090);
            out = socket.getOutputStream();
            System.out.println("发布消息:GET UUID="+uuid);
            // 发布信息到服务器
            out.write(("GET UUID="+uuid+"\r\n").getBytes()); 
            out.flush();

        } finally {
            // 关闭连接
            out.close();
            socket.close();
        }
    }

}
