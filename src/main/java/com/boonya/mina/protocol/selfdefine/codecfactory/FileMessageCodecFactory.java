package com.boonya.mina.protocol.selfdefine.codecfactory;

import java.nio.charset.Charset;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import com.boonya.mina.protocol.selfdefine.decoder.FileMessageDecoder;
import com.boonya.mina.protocol.selfdefine.encoder.FileMessageEncoder;

public class FileMessageCodecFactory extends DemuxingProtocolCodecFactory {
	
	private FileMessageDecoder decoder;
	
	private FileMessageEncoder encoder;

	public FileMessageCodecFactory() {
		this.decoder = new FileMessageDecoder(Charset.forName("utf-8"));
		this.encoder = new FileMessageEncoder(Charset.forName("utf-8"));
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return (ProtocolDecoder) decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return (ProtocolEncoder) encoder;
	}

}
