package com.boonya.mina.protocol.selfdefine.encoder;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import com.boonya.mina.protocol.selfdefine.bean.FileMessage;
import com.boonya.mina.protocol.selfdefine.bean.FileProtocol;

public class FileMessageEncoder implements MessageEncoder<FileProtocol>{

	private Charset charset;
	
	public FileMessageEncoder(Charset charset){
		this.charset=charset;
	}
	
	@Override
	public void encode(IoSession session, FileProtocol message,
			ProtocolEncoderOutput out) throws Exception {
		System.out.println(">>>>>>开始执行编码工作....................!");
		
		IoBuffer buf=IoBuffer.allocate(1024).setAutoExpand(true);
		
		if(message instanceof FileMessage){
			
			FileMessage req=(FileMessage) message;
			buf.put(req.getTag());
			buf.putShort((short)req.getHeaderlen());
			buf.put(req.getFilename());
			buf.putInt(req.getFileLen());
			buf.putInt(req.getOffset());
			buf.put(req.getChecksum());
			buf.put(req.getTmp());
			buf.put(req.getData());
			
		}else{
			System.out.println(">>>>>>消息类型错误....................!");
		}
				
		buf.flip();
		
		out.write(buf);
	}

}
