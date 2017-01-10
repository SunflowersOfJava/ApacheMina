package com.boonya.mina.protocol.selfdefine;

import java.net.InetSocketAddress;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.boonya.mina.protocol.selfdefine.bean.FileMessage;
import com.boonya.mina.protocol.selfdefine.bean.FilePiece;
import com.boonya.mina.protocol.selfdefine.codecfactory.FileMessageCodecFactory;
import com.boonya.mina.protocol.selfdefine.handler.FileMessageClientHandler;

public class FileMessageClient {

	private static String HOST = "localhost";

	private static int PORT = 8082;

	public static void main(String[] args) {
		// 创建一个非阻塞的客户端程序
		IoConnector connector = new NioSocketConnector();
		// 设置链接超时时间
		connector.setConnectTimeoutMillis(30000);
		// 添加过滤器
		connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new FileMessageCodecFactory()));
		// 添加业务逻辑处理器类
		connector.setHandler(new FileMessageClientHandler());
		IoSession session = null;
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(	HOST, PORT));// 创建连接
			future.awaitUninterruptibly();// 等待连接创建完成
			session = future.getSession();// 获得session

			FilePiece piece = new FilePiece("C:\\xmlhttp-1.0.jar", 0);

			FileMessage ir = new FileMessage(piece);

			session.write(ir);// 发送消息

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("客户端链接异常...");
		}

		session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
		connector.dispose();
	}

}
