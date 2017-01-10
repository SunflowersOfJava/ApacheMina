package com.boonya.mina.protocol.pack.codecfactory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import com.boonya.mina.protocol.pack.decoder.ProtocolPackageDecoder;
import com.boonya.mina.protocol.pack.encoder.ProtocolPackageEncoder;

public class ProtocolPackageCodecFactory implements ProtocolCodecFactory {

	private final ProtocolPackageDecoder decoder;

	private final ProtocolPackageEncoder encoder;

	// 构造
	public ProtocolPackageCodecFactory() {
		encoder = new ProtocolPackageEncoder();
		decoder = new ProtocolPackageDecoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}
}
