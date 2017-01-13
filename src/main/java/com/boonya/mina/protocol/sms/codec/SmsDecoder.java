package com.boonya.mina.protocol.sms.codec;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import com.boonya.mina.protocol.sms.bean.SmsDataProtocal.Sms;
import com.google.protobuf.ByteString;

public class SmsDecoder extends ProtocolDecoderAdapter{
	
	private final Charset charset = StandardCharsets.UTF_8;

	private int prefixLength = 4;

	private int maxDataLength = 1024;

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		// TODO Auto-generated method stub
		  if (in.prefixedDataAvailable(prefixLength, maxDataLength)) {
			  
              String msg = in.getPrefixedString(prefixLength,
                      charset.newDecoder());

              Sms sms = Sms.parseFrom(ByteString.copyFrom(msg,charset.name()));

              out.write(sms);
          }
	}

}
