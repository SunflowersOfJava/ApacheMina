package com.boonya.mina.protocol.sms;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.boonya.mina.protocol.sms.bean.SmsDataProtocal.Sms;
import com.boonya.mina.protocol.sms.codec.SmsCodecFactory;

public class SmsClient {
	
	 private static InetSocketAddress server = new InetSocketAddress("127.0.0.1", 9001);
	 
	    public static void main(String[] args) throws InterruptedException {
	 
	        // 客户端连接器
	        IoConnector connector = new NioSocketConnector();
	 
	        // 过滤器
	        connector.getFilterChain().addLast("codec",
	                new ProtocolCodecFilter(new SmsCodecFactory()));
	        connector.getFilterChain().addLast("threadPool",
	                new ExecutorFilter(Executors.newCachedThreadPool()));
	 
	        // 处理器
	        connector.setHandler(new IoHandlerAdapter() {
	 
	            @Override
	            public void sessionCreated(IoSession session) throws Exception {
	            }
	 
	            @Override
	            public void messageReceived(IoSession session, Object message)
	                    throws Exception {
	                System.out.println("服务器响应：");
	                System.out.println(((Sms) message).toString());
	            }
	 
	        });
	 
	        // 建立会话Session
	        IoSession session = null;
	        while (true) {
	            try {
	                ConnectFuture future = connector.connect(server);
	                future.awaitUninterruptibly(100, TimeUnit.SECONDS);
	                session = future.getSession();
	                if (null != session) {
	                    break;
	                }
	            } catch (RuntimeIoException e) {
	                System.err.println("Failed to connect with "
	                        + server.toString());
	                e.printStackTrace();
	                try {
	                    Thread.sleep(5000);
	                } catch (InterruptedException e1) {
	                    e1.printStackTrace();
	                }
	            }
	        }
	 
	        // 客户端输入
	        try (Scanner scanner = new Scanner(System.in);) {
	            while (true) {
	                String sender = "1814453211";
	                System.out.println("请输入收信息手机号:");
	                String receiver = scanner.nextLine();
	                System.out.println("请输入信息内容：");
	                String content = scanner.nextLine();
	 
	                Sms sms = Sms.newBuilder()
	                        .setProtocol("ip.weixin.com TC-C/2.0")
	                        .setSender(sender).setReceiver(receiver)
	                        .setContent(content).build();
	 
	                session.write(sms);
	 
	                Thread.sleep(10000);
	                System.out.println("是否继续，回车继续 , q or quit 退出：");
	                String line = scanner.nextLine();
	                if (line.trim().equalsIgnoreCase("q")
	                        || line.trim().equalsIgnoreCase("quit")) {
	                    break;
	                }
	            }
	        }
	        session.close(false);
	        connector.dispose();
	    }

}
