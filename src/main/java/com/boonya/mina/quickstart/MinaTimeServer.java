package com.boonya.mina.quickstart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * MinaTimeServer 服务端
 * 
 * @packge com.boonya.mina.quickstart.MinaTimeServer
 * @date 2016年6月1日 上午9:49:34
 * @author pengjunlin
 * @comment
 * @update
 */
public class MinaTimeServer {
	
	 // 定义一个NioSocketAcceptor(服务端)监听端口
	 private static final int PORT = 9123;
	
	/**
	 * @throws IOException 
	 * 主函数
	 * 
	 * @MethodName: main
	 * @Description:
	 * @param args
	 * @throws
	 */
	public static void main(String[] args) throws IOException {
		// 第一步，我们需要一个监听进来的链接：一个基于TCP/IP的SocketAcceptor
		IoAcceptor acceptor = new NioSocketAcceptor();

		// 添加过滤器配置，过滤器会日志记录最新的session和接收到的消息
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// 将二进制数据转化为协议对应的消息类型，TextLineCodecFactory处理text类型的数据
		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

		// 使用对应的服务器handler 被用于与客户端提供请求时间服务
		acceptor.setHandler(new MinaTimeServerHandler());

		// 设置被用户客户端请求的NioSocketAcceptor 设置
		// 对buffer设定缓冲区大小，目的是告知潜在的操作系统分配多大空间去处理进来的消息
		acceptor.getSessionConfig().setReadBufferSize(2048);
		// 指定空闲session的空闲时间，IdleStatus设置超时action
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		
		// 绑定服务端socket通信端口
		acceptor.bind( new InetSocketAddress(PORT) );
	}

}
