package com.boonya.mina.protocol.selfdefine.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.boonya.mina.protocol.selfdefine.bean.FileContainer;

public class FileMessageServerHandler extends IoHandlerAdapter {

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务端会话已创建...");
		super.sessionCreated(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务端会话已打开...");
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务端会话已关闭...");
		super.sessionClosed(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务端连接空闲中...");
		super.sessionIdle(session, status);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务端发生异常了...");
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("服务器端接收到的消息：" + message);
		System.out.println("服务器端接收到的消息：解析消息中.........");
		if (message instanceof FileContainer) {
			FileContainer irc = (FileContainer) message;
			System.out.println("服务器端 获取成功 Tag:" + irc.getTag() + "\r\n " + "head len:"
					+ irc.getHeadlen() + "\r\nfilename: " + irc.getFilename()
					+ "\r\nfile len:"+irc.getFilelen()+"\r\noffset:"+irc.getOffset()+"\r\nchecksum:"+irc.getChecksum()+"\r\ndata:"+irc.getData().toString());
			
			session.write("success rescive");
			
		} else {
			System.out.println("服务器端获取消息类型不能解析......");
		}

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务器端开始发送消息...");
		super.messageSent(session, message);
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务器端输入已关闭...");
		super.inputClosed(session);
	}
	
	

}