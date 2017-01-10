package com.boonya.mina.protocol.pack.decoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 协议包(解决半包、丢包、粘包)——解码器
 * 
 * @package com.boonya.mina.protocol.pack.encoder.ProtocolPackageDecoder
 * @date   2017年1月10日  下午2:29:03
 * @author pengjunlin
 * @comment   
 * @update
 */
public class ProtocolPackageDecoder  extends CumulativeProtocolDecoder{
    //打印日志信息
    private final static Logger log = LoggerFactory.getLogger(ProtocolPackageDecoder.class);
    
    
   /* @Override  
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {  
          
        // 如果没有接收完Header部分（4字节），直接返回false  
        if(in.remaining() < 4) {  
            return false;  
        } else {  
              
            // 标记开始位置，如果一条消息没传输完成则返回到这个位置  
            in.mark();  
              
            byte[] bytes = new byte[4];  
            in.get(bytes); // 读取4字节的Header  
              
            int bodyLength = LittleEndian.getLittleEndianInt(bytes); // 按小字节序转int  
              
            // 如果body没有接收完整，直接返回false  
            if(in.remaining() < bodyLength) {  
                in.reset(); // IoBuffer position回到原来标记的地方  
                return false;  
            } else {  
                byte[] bodyBytes = new byte[bodyLength];  
                in.get(bodyBytes);  
                String body = new String(bodyBytes, "UTF-8");  
                out.write(body); // 解析出一条消息  
                return true;  
            }  
        }  
    }  */

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        int packHeadLenth = 4;  //包头长度(int 的长度) 根据自定义协议的包头的长度
        if(in.remaining() > packHeadLenth){  //说明缓冲区中有数据
            in.mark();//标记当前position，以便后继的reset操作能恢复position位置

            //获取数据包长度
            int len = in.getInt();
            log.info("len = "+len);

            //上面的get会改变remaining()的值

            if(in.remaining() <len - packHeadLenth) {
                //内容不够， 重置position到操作前，进行下一轮接受新数据
                in.reset();
                return false;
            }else{
                //内容足够
                in.reset(); //重置回复position位置到操作前
                byte[] packArray = new byte[len]; 
                in.get(packArray, 0, len); //获取整条报文

                //根据自己需要解析接收到的东西  我的例子 把收到的报文转成String
                String str = new String(packArray);
                out.write(str); //发送出去 就算完成了

                if(in.remaining() > 0){//如果读取一个完整包内容后还粘了包，就让父类再调用一次，进行下一次解析
                    return true;
                }
            }
        }
        return false;  //处理成功
    }
}
