package com.boonya.mina.protocol.selfdefine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import com.boonya.mina.protocol.selfdefine.coderfactory.MessageCoderFactory;
import com.boonya.mina.protocol.selfdefine.handler.MessageServerHandler;

/**
 * 自定义消息协议-服务器
 * 
 * @package com.boonya.mina.protocol.MessageServer
 * @date 2017年1月10日 上午10:47:28
 * @author pengjunlin
 * @comment
 * @update
 */
public class MessageServer {

	/**
	 * 初始化服务器监听
	 */
	public void init() throws IOException {
		// 在服务器端创建一个监听连接的接收器 基于tcp/ip
		IoAcceptor acceptor = new NioSocketAcceptor();

		// 绑定的端口
		SocketAddress address = new InetSocketAddress("localhost", 8888);

		// 获取过滤器链
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

		// 添加日志过滤器
		chain.addLast("logger", new LoggingFilter());

		// 　配置自定义的编解码器　
		chain.addLast("codec", new ProtocolCodecFilter(
				new MessageCoderFactory()));

		// 添加数据处理的处理器
		acceptor.setHandler(new MessageServerHandler());

		// 进行配置信息的设置
		acceptor.getSessionConfig().setReadBufferSize(100);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// 绑定服务器端口　
		acceptor.bind(address);

		System.out.println("服务器开始在 8888 端口监听.......");
	}
	
	/**
	 * 服务器入口方法
	 * 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		new MessageServer().init();
	}
}
