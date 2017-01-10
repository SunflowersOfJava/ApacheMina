package com.boonya.mina.quickstart.push;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
/**
 * MINA TCP-Server
 * 
 * @package com.boonya.mina.quickstart.push.Server
 * @date   2017年1月10日  下午4:03:15
 * @author pengjunlin
 * @comment   
 * @update
 */
public class Server {

	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), "\r\n", "\r\n")));

		acceptor.setHandler(new ServerHandler());
		acceptor.bind(new InetSocketAddress(8090));
	}

}
