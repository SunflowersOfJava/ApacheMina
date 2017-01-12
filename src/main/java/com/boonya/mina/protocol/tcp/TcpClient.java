package com.boonya.mina.protocol.tcp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.alibaba.fastjson.JSONObject;
import com.boonya.mina.protocol.tcp.handler.request.TcpRequestHandler;

public class TcpClient {

	private SocketConnector connector;
	
	private ConnectFuture future;
	
	private IoSession session;

	public boolean connect() {
		try {
			// 创建一个socket连接
			connector = new NioSocketConnector();
			// 设置链接超时时间
			connector.setConnectTimeoutMillis(3000);
			// 获取过滤器链
			DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
			// 添加编码过滤器 处理乱码、编码问题
			TextLineCodecFactory lineCodec = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),LineDelimiter.WINDOWS.getValue());
			lineCodec.setDecoderMaxLineLength(2 * 1024 * 1024);
			lineCodec.setEncoderMaxLineLength(2 * 1024 * 1024);
			filterChain.addLast("codec", new ProtocolCodecFilter(lineCodec));
			filterChain.addLast("exceutor",new ExecutorFilter(Executors.newCachedThreadPool()));

			// 消息核心处理器
			connector.setHandler(new TcpRequestHandler());

			// 连接服务器，知道端口、地址
			future = connector
					.connect(new InetSocketAddress("127.0.0.1", 8292));
			// 等待连接创建完成
			future.awaitUninterruptibly();
			// 获取当前session
			session = future.getSession();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void write(JSONObject request) throws Exception {
		session.write(request.toString());
	}

	public boolean close() {
		try {
			CloseFuture future = session.getCloseFuture();
			future.awaitUninterruptibly(1000);
			connector.dispose();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		TcpClient client=new TcpClient();
		
		if(client.connect()){
			JSONObject request=new JSONObject();
			request.put("id", 10000);
			request.put("name", "boonya");
			client.write(request);
			client.close();
		}
	}

}
