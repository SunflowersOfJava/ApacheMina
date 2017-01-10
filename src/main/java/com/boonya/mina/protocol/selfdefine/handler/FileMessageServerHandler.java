package com.boonya.mina.protocol.selfdefine.handler;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import com.boonya.mina.protocol.selfdefine.bean.FileContainer;

public class FileMessageServerHandler implements IoHandler {

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof FileContainer) {
			FileContainer irc = (FileContainer) message;
			System.out.println("服务器端 获取成功 Tag:" + irc.getTag() + "\r\n " + "head len:"
					+ irc.getHeadlen() + "\r\nfilename: " + irc.getFilename()
					+ "\r\nfile len:"+irc.getFilelen()+"\r\noffset:"+irc.getOffset()+"\r\nchecksum:"+irc.getChecksum()+"\r\ndata:"+irc.getData().toString());
			
			session.write("success rescive");
			
		} else {
			System.out.println("获取失败");
		}

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		
	}

}