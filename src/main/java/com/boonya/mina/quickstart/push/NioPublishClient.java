package com.boonya.mina.quickstart.push;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * 消息发布客户端
 * 
 * @package com.boonya.mina.quickstart.push.NioPublishClient
 * @date   2017年1月10日  下午4:45:43
 * @author pengjunlin
 * @comment   
 * @update
 */
public class NioPublishClient {
	
	/**
	 * NIO消息发布
	 */
	public void publish(){
		NioSocketConnector connector = new NioSocketConnector();

		// 连接的地址和端口
		SocketAddress address = new InetSocketAddress("localhost", 8090);

		// 获取过滤器链
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		// 配置日志过滤器和自定义编解码器
		chain.addLast("logger", new LoggingFilter());
		chain.addLast("codec", new ProtocolCodecFilter(	new TextLineCodecFactory(Charset.forName("UTF-8"))));

		// 添加处理器
		connector.setHandler(new IoHandlerAdapter(){
			@Override
			public void messageReceived(IoSession session, Object message)
					throws Exception {
				System.out.println("发布消息："+message);
				super.messageReceived(session, message);
			}

			@Override
			public void messageSent(IoSession session, Object message)
					throws Exception {
				System.out.println("发布消息：完成!");
				super.messageSent(session, message);
			}
			
		});

		// 　连接到服务器　
		ConnectFuture future = connector.connect(address);

		// 等待连接创建完成
		future.awaitUninterruptibly();
		
		// 会话创建后发送消息到服务器
		String msg = "这是您订阅的消息-由服务端发送!";
		future.getSession().write(msg);

		// 等待8000毫秒后连接断开
		future.getSession().getCloseFuture().awaitUninterruptibly(8000);

		// 关闭连接
		connector.dispose();
	}
	
	public static void main(String[] args) throws IOException {
		new NioPublishClient().publish(); 
    }

}
