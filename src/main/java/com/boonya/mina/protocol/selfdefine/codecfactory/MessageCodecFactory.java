package com.boonya.mina.protocol.selfdefine.codecfactory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.boonya.mina.protocol.selfdefine.decoder.MessageDecoder;
import com.boonya.mina.protocol.selfdefine.encoder.MessageEncoder;

/**
 *	编解码工厂
 */
public class MessageCodecFactory implements ProtocolCodecFactory{
	
	private MessageDecoder decoder;
	private MessageEncoder encoder;

	public MessageCodecFactory() {
		this.decoder = new MessageDecoder();
		this.encoder = new MessageEncoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

}
