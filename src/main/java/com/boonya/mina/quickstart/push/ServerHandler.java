package com.boonya.mina.quickstart.push;

import java.util.Collection;
import org.apache.log4j.Logger;
import org.apache.mina.core.IoUtil;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * IoHandler消息处理类-发布订阅模式
 * 
 * @package com.boonya.mina.quickstart.push.ServerHandler
 * @date   2017年1月10日  下午4:04:08
 * @author pengjunlin
 * @comment   
 * @update
 */
public class ServerHandler extends IoHandlerAdapter {

	public static Logger logger = Logger.getLogger(ServerHandler.class);

	// 从端口接受消息，会响应此方法来对消息进行处理
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		////////////////////发布/订阅//////////////////////
		 // 获取所有正在连接的IoSession
        Collection<IoSession> sessions = session.getService().getManagedSessions().values();
        // 将消息写到所有IoSession
        IoUtil.broadcast(message, sessions);
		////////////////////发布/订阅//////////////////////
		
		/*
		String msg = message.toString();
		if ("exit".equals(msg)) {
			// 如果客户端发来exit，则关闭该连接
			session.close(true);
		}
		// 向客户端发送消息
		Date date = new Date();
		session.write(date);
		logger.info("服务器接受消息成功...");
		super.messageReceived(session, message);*/
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
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.info("服务器进入空闲状态...");
		super.sessionIdle(session, status);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.info("服务器发送异常...");
		super.exceptionCaught(session, cause);
	}

}
