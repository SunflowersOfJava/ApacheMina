package com.boonya.mina.quickstart;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialAddress.FlowControl;
import org.apache.mina.transport.serial.SerialAddress.Parity;
import org.apache.mina.transport.serial.SerialAddress.StopBits;
import org.apache.mina.transport.serial.SerialConnector;
/**
 * 串口通信
 * 
 * @package com.boonya.mina.quickstart.SerialTransportServer
 * @date   2017年1月11日  下午12:40:52
 * @author pengjunlin
 * @comment   更多参考：http://blog.csdn.net/yoara/article/details/37726817
 * @update
 */
public class SerialTransportServer {
	
	public static void main(String[] args) {
		//创建串口连接  
		//IoConnector connector= new SerialConnector();
		SerialConnector connector = new SerialConnector(); 
		
		//配置串口连接
		/*SerialAddress portAddress=new SerialAddress("/dev/ttyS0", 38400, 8, StopBits.BITS_1,  Parity.NONE, FlowControl.NONE );
		
		ConnectFuture future = connector.connect( portAddress );
		future.await();
		IoSession sessin = future.getSession();*/
	}

}
