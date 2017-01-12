package com.boonya.mina.protocol.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import com.boonya.mina.protocol.tcp.handler.response.TcpResponseHandler;

public class TcpServer {

	private int port = 8292;
	
	private int idleTime = 1800;
	
	private int bufferSize = 1024;
	
	private SocketAcceptor acceptor;

	public void start() {
		acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
		TextLineCodecFactory lineCodec = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),LineDelimiter.WINDOWS.getValue());
		lineCodec.setDecoderMaxLineLength(2 * 1024 * 1024);
		lineCodec.setEncoderMaxLineLength(2 * 1024 * 1024);
		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(lineCodec));
		acceptor.getFilterChain().addLast("exceutor",new ExecutorFilter(Executors.newCachedThreadPool()));
		acceptor.setHandler(new TcpResponseHandler());
		acceptor.setReuseAddress(true);
		acceptor.setBacklog(10240);
		acceptor.getSessionConfig().setReuseAddress(true);
		acceptor.getSessionConfig().setReadBufferSize(bufferSize);
		acceptor.getSessionConfig().setReceiveBufferSize(bufferSize);
		acceptor.getSessionConfig().setTcpNoDelay(true);
		acceptor.getSessionConfig().setSoLinger(-1);
		// 单位秒
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTime);

		try {
			acceptor.bind(new InetSocketAddress(port));
			System.out.println("服务器已成功监听端口:"+port);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void stop() {
		acceptor.dispose();
	}
	
	public static void main(String[] args) {
		new TcpServer().start();
	}

}
