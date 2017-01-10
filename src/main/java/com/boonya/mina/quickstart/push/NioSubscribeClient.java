package com.boonya.mina.quickstart.push;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息订阅客户端
 * 
 * @package com.boonya.mina.quickstart.push.NioSubscribeClient
 * @date 2017年1月10日 下午4:45:59
 * @author pengjunlin
 * @comment
 * @update
 */
public class NioSubscribeClient {

	Logger logger = LoggerFactory.getLogger(NioSubscribeClient.class);

	NioSocketConnector connector;

	IoSession session;

	String host = "localhost";

	int port = 8090;

	/**
	 * 使用过滤器连实现客户端重连
	 * 
	 */
	public void filterQuest() throws InterruptedException {
		connector = new NioSocketConnector(); // 创建连接客户端
		connector.setConnectTimeoutMillis(30000); // 设置连接超时
		// 断线重连回调拦截器
		connector.getFilterChain().addFirst("reconnection",
				new IoFilterAdapter() {
					@Override
					public void sessionClosed(NextFilter nextFilter,
							IoSession ioSession) throws Exception {
						for (;;) {
							try {
								Thread.sleep(3000);
								ConnectFuture future = connector.connect();
								future.awaitUninterruptibly();// 等待连接创建成功
								session = future.getSession();// 获取会话
								if (session.isConnected()) {
									logger.info("断线重连["
											+ connector
													.getDefaultRemoteAddress()
													.getHostName()
											+ ":"
											+ connector
													.getDefaultRemoteAddress()
													.getPort() + "]成功");
									break;
								}
							} catch (Exception ex) {
								logger.info("重连服务器登录失败,3秒再连接一次:"
										+ ex.getMessage());
							}
						}
					}
				});

		TextLineCodecFactory factory = new TextLineCodecFactory(
				Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
				LineDelimiter.WINDOWS.getValue());
		factory.setDecoderMaxLineLength(10240);
		factory.setEncoderMaxLineLength(10240);
		// 加入解码器
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(factory));
		// 添加处理器
		connector.setHandler(new IoHandlerAdapter());
		connector.getSessionConfig().setReceiveBufferSize(10240); // 设置接收缓冲区的大小
		connector.getSessionConfig().setSendBufferSize(10240); // 设置输出缓冲区的大小
		connector.setDefaultRemoteAddress(new InetSocketAddress(host, port));// 设置默认访问地址
		for (;;) {
			try {
				ConnectFuture future = connector.connect();
				// 等待连接创建成功
				future.awaitUninterruptibly();
				// 获取会话
				session = future.getSession();
				logger.error("连接服务端"
						+ host
						+ ":"
						+ port
						+ "[成功]"
						+ ",时间:"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
				break;
			} catch (RuntimeIoException e) {
				logger.error(
						"连接服务端"
								+ host
								+ ":"
								+ port
								+ "失败"
								+ ",时间:"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date())
								+ ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:"
								+ e.getMessage(), e);
				Thread.sleep(5000);// 连接失败后,重连间隔5s
			}
		}
	}

	/**
	 * 加入空闲检测机制-实现客户端重连
	 * 
	 * <li>空闲检测机制需要在创建客户端时，加入空闲超时，然后在处理器handler端的sessionIdle方法中加入一个预关闭连接的方法。
	 * 让Session关闭传递到监听器或者拦截器的sessionClose方法中实现重连。</li>
	 */
	public void idleCheck() throws InterruptedException {
		connector = new NioSocketConnector(); // 创建连接客户端
		connector.setConnectTimeoutMillis(30000); // 设置连接超时
		// 断线重连回调拦截器
		connector.getFilterChain().addFirst("reconnection",
				new IoFilterAdapter() {
					@Override
					public void sessionClosed(NextFilter nextFilter,
							IoSession ioSession) throws Exception {
						for (;;) {
							try {
								Thread.sleep(3000);
								ConnectFuture future = connector.connect();
								future.awaitUninterruptibly();// 等待连接创建成功
								session = future.getSession();// 获取会话
								if (session.isConnected()) {
									logger.info("断线重连["
											+ connector
													.getDefaultRemoteAddress()
													.getHostName()
											+ ":"
											+ connector
													.getDefaultRemoteAddress()
													.getPort() + "]成功");
									break;
								}
							} catch (Exception ex) {
								logger.info("重连服务器登录失败,3秒再连接一次:"
										+ ex.getMessage());
							}
						}
					}
				});

		connector.getFilterChain().addLast("mdc", new MdcInjectionFilter());
		TextLineCodecFactory factory = new TextLineCodecFactory(
				Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
				LineDelimiter.WINDOWS.getValue());
		factory.setDecoderMaxLineLength(10240);
		factory.setEncoderMaxLineLength(10240);
		// 加入解码器
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(factory));

		connector.getSessionConfig().setReceiveBufferSize(10240); // 设置接收缓冲区的大小
		connector.getSessionConfig().setSendBufferSize(10240);// 设置输出缓冲区的大小

		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30000); // 读写都空闲时间:30秒
		connector.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 40000);// 读(接收通道)空闲时间:40秒
		connector.getSessionConfig().setIdleTime(IdleStatus.WRITER_IDLE, 50000);// 写(发送通道)空闲时间:50秒

		// 添加处理器
		connector.setHandler(new SessionControlIoHandler());

		connector.setDefaultRemoteAddress(new InetSocketAddress(host, port));// 设置默认访问地址
		for (;;) {
			try {
				ConnectFuture future = connector.connect();
				// 等待连接创建成功
				future.awaitUninterruptibly();
				// 获取会话
				session = future.getSession();
				logger.error("连接服务端"
						+ host
						+ ":"
						+ port
						+ "[成功]"
						+ ",时间:"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()));
				break;
			} catch (RuntimeIoException e) {
				System.out.println("连接服务端"
						+ host
						+ ":"
						+ port
						+ "失败"
						+ ",时间:"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date())
						+ ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:"
						+ e.getMessage());
				logger.error(
						"连接服务端"
								+ host
								+ ":"
								+ port
								+ "失败"
								+ ",时间:"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date())
								+ ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:"
								+ e.getMessage(), e);
				Thread.sleep(5000);// 连接失败后,重连10次,间隔30s
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// new NioSubscribeClient().filterQuest();
		new NioSubscribeClient().idleCheck();
	}

}

class SessionControlIoHandler extends IoHandlerAdapter {

	Logger logger = LoggerFactory.getLogger(SessionControlIoHandler.class);

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.info("-客户端与服务端连接[空闲] - " + status.toString());
		if (session != null) {
			session.close(true);
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		logger.info("服务器收到消息...");
		System.out.println("收到消息：" + message);
		session.write(message);
	}

	// 向客服端发送消息后会调用此方法
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("服务器发送消息成功...");
		super.messageSent(session, message);
	}

	// 关闭与客户端的连接时会调用此方法
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("服务器与客户端断开连接...");
		super.sessionClosed(session);
	}

	// 服务器与客户端创建连接
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("服务器与客户端创建连接...");
		super.sessionCreated(session);
	}

	// 服务器与客户端连接打开
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("服务器与客户端连接打开...");
		super.sessionOpened(session);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.info("服务器发送异常...");
		super.exceptionCaught(session, cause);
	}

}
