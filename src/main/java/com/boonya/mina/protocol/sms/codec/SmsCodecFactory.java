package com.boonya.mina.protocol.sms.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class SmsCodecFactory implements ProtocolCodecFactory {
	
	private SmsEncoder encoder; 
	
	private SmsDecoder decoder; 
	
	public SmsCodecFactory(){
		this.encoder=new SmsEncoder();
		this.decoder=new SmsDecoder();
	}
	

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}
 
   
}
