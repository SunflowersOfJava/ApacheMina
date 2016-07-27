package com.boonya.mina.quickstart;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * 时间服务器端-消息处理器
 * 
 * @packge com.boonya.mina.quickstart.MinaTimeServerHandler
 * @date 2016年6月1日 上午9:55:42
 * @author pengjunlin
 * @comment Mina中*Handler继承自IoHandler
 * @update
 */
public class MinaTimeServerHandler extends IoHandlerAdapter {
	/**
	 * 处理异常捕获方法（如不定义此方法，异常可能不会被正确捕获）
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)	throws Exception {
		// 打印异常输出
		cause.printStackTrace();
		// 有时候需要关闭session,根据实际情况决定
		session.close();
	}

	/**
	 * 消息接收处理方法
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void messageReceived(IoSession session, Object message)	throws Exception {
		String str = message.toString();
		// 如果从客户端接收到'quit'指令，session将会被关闭
		if (str.trim().equalsIgnoreCase("quit")) {
			session.close();
			return;
		}
		// 根据str指令做相应的处理
		// 。。。。。。。。

		Date date = new Date();
		// write back to the client,如果没有定义codec,收到的数据是一个IoBuffer object（此处没有采用message）
		session.write(date.toString());
		System.out.println("Message written...");
	}

	/**
	 * session空闲处理方法
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)throws Exception {
		System.out.println("IDLE " + session.getIdleCount(status));
	}

}
