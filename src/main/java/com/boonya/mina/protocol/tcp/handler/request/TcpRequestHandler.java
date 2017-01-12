package com.boonya.mina.protocol.tcp.handler.request;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import com.alibaba.fastjson.JSONObject;

public class TcpRequestHandler extends IoHandlerAdapter {

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		 JSONObject request = JSONObject.parseObject(message.toString());
		 // 根据传来的数据进行处理
		 System.out.println("发送JSON数据:"+request);
		 session.write(message);
	}
	
	

}
