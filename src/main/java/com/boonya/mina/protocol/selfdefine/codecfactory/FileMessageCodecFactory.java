package com.boonya.mina.protocol.selfdefine.codecfactory;

import java.nio.charset.Charset;
import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import com.boonya.mina.protocol.selfdefine.bean.FileProtocol;
import com.boonya.mina.protocol.selfdefine.decoder.FileMessageDecoder;
import com.boonya.mina.protocol.selfdefine.encoder.FileMessageEncoder;

public class FileMessageCodecFactory extends DemuxingProtocolCodecFactory {
	
	private MessageDecoder decoder;
	 
	private FileMessageEncoder encoder;

	public FileMessageCodecFactory() {
		this.decoder = new FileMessageDecoder(Charset.forName("utf-8"));
		this.encoder = new FileMessageEncoder(Charset.forName("utf-8"));
		addMessageEncoder(FileProtocol.class, this.encoder);
		addMessageDecoder(this.decoder);
	}
	
	
}
