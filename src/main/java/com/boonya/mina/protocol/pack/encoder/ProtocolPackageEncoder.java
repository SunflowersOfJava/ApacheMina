package com.boonya.mina.protocol.pack.encoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.boonya.mina.protocol.pack.bean.ProtocalPackage;
/**
 * 协议包(解决半包、丢包、粘包)——编码器
 * 
 * @package com.boonya.mina.protocol.pack.encoder.ProtocolPackageEncoder
 * @date   2017年1月10日  下午2:29:03
 * @author pengjunlin
 * @comment   
 * @update
 */
public class ProtocolPackageEncoder extends ProtocolEncoderAdapter {
	// 用于打印日志信息
	private final static Logger log = LoggerFactory.getLogger(ProtocolPackageEncoder.class);

	// 编码 将数据包转成字节数组
	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		ProtocalPackage value = (ProtocalPackage) message;
		// 根据报文长度开辟空间
		IoBuffer buff = IoBuffer.allocate(value.getLength());
		// 设置为可自动扩展空间
		buff.setAutoExpand(true);
		// 将报文中的信息添加到buff中
		buff.putInt(value.getLength());
		if (value.getContent() != null) {
			buff.put(value.getContent().getBytes());
		}
		buff.flip();
		// 将报文发送出去
		out.write(buff);
	}

}
