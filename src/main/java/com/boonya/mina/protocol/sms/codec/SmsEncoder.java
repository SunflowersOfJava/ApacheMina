package com.boonya.mina.protocol.sms.codec;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import com.boonya.mina.protocol.sms.bean.SmsDataProtocal.Sms;

public class SmsEncoder extends ProtocolEncoderAdapter {

	private final Charset charset = StandardCharsets.UTF_8;

	private int prefixLength = 4;

	private int maxDataLength = 1024;

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		Sms sms = (Sms) message;

		String content = sms.toByteString().toStringUtf8();

		IoBuffer buffer = IoBuffer.allocate(content.length()).setAutoExpand(true);

		buffer.putPrefixedString(content, prefixLength, charset.newEncoder());

		if (buffer.position() > maxDataLength) {
			throw new IllegalArgumentException("Data length: "
					+ buffer.position());
		}

		buffer.flip();
		out.write(buffer);
	}

}
