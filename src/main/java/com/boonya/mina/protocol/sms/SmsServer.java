package com.boonya.mina.protocol.sms;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import com.boonya.mina.protocol.sms.bean.SmsDataProtocal.Sms;
import com.boonya.mina.protocol.sms.codec.SmsCodecFactory;

public class SmsServer {
	
	 public static final int PORT = 9001;
	 
	    public static void main(String[] args) throws IOException {
	 
	        // 接收器
	        IoAcceptor acceptor = new NioSocketAcceptor();
	 
	        // 过滤器链
	        DefaultIoFilterChainBuilder builder = new DefaultIoFilterChainBuilder();
	 
	        LoggingFilter loggingFilter = new LoggingFilter();
	        loggingFilter.setExceptionCaughtLogLevel(LogLevel.DEBUG);
	 
	        builder.addLast("logging", loggingFilter);
	        builder.addLast("codec", new ProtocolCodecFilter(
	                new SmsCodecFactory()));
	        builder.addLast("threadPool",
	                new ExecutorFilter(Executors.newCachedThreadPool()));
	        acceptor.setFilterChainBuilder(builder);
	 
	        // 设置处理器IoHandler
	        acceptor.setHandler(new IoHandlerAdapter() {
	 
	            @Override
	            public void messageReceived(IoSession session, Object message)
	                    throws Exception {
	                Sms sms = (Sms) message;
	                System.out.println("客户端发来：");
	                System.out.println(sms.toString());
	 
	                // 服务器发送
	                Sms serverSms = Sms.newBuilder().setProtocol(sms.getProtocol())
	                        .setContent("OK").setReceiver(sms.getSender())
	                        .setSender(sms.getSender()).build();
	                session.write(serverSms);
	            }
	        });
	 
	        // 配置服务器(IoAccptor)
	        acceptor.getSessionConfig().setReadBufferSize(2048);
	        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	        // 绑定到指定IP和端口
	        acceptor.bind(new InetSocketAddress(PORT));
	    }

}
