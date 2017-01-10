package com.boonya.mina.protocol.pack;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.boonya.mina.protocol.pack.bean.ProtocalPackage;
import com.boonya.mina.protocol.pack.codecfactory.ProtocolPackageCodecFactory;
import com.boonya.mina.protocol.pack.handler.ProtocolPackageClientHandler;

/**
 * 自定义消息协议-客户端
 * 
 * @package com.boonya.mina.protocol.pack.ProtocolPackageClient
 * @date 2017年1月10日 上午10:47:28
 * @author pengjunlin
 * @comment
 * @update
 */
public class ProtocolPackageClient {

	/**
	 * 发送消息
	 */
	public void sendMsg() {
		// 创建客户端连接器 基于tcp/ip
		NioSocketConnector connector = new NioSocketConnector();

		// 连接的地址和端口
		SocketAddress address = new InetSocketAddress("localhost", 8888);

		// 获取过滤器链
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();

		// 配置日志过滤器和自定义编解码器
		chain.addLast("logger", new LoggingFilter());
		chain.addLast("codec", new ProtocolCodecFilter(	new ProtocolPackageCodecFactory()));

		// 添加处理器
		connector.setHandler(new ProtocolPackageClientHandler());

		// 　连接到服务器　
		ConnectFuture future = connector.connect(address);

		// 等待连接创建完成
		future.awaitUninterruptibly();

		// 会话创建后发送消息到服务器
		ProtocalPackage msg=new ProtocalPackage((byte) 0x01, "This is data string！");
		future.getSession().write(msg);

		// 等待28000毫秒后连接断开
		future.getSession().getCloseFuture().awaitUninterruptibly(28000);

		// 关闭连接
		connector.dispose();
	}

	/**
	 * 客户端入口方法
	 * 
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		new ProtocolPackageClient().sendMsg();
	}
}
