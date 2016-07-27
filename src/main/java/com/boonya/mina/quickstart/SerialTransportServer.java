package com.boonya.mina.quickstart;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialAddress.FlowControl;
import org.apache.mina.transport.serial.SerialAddress.Parity;
import org.apache.mina.transport.serial.SerialAddress.StopBits;
import org.apache.mina.transport.serial.SerialConnector;

public class SerialTransportServer {
	
	public static void main(String[] args) {
		IoConnector connector= new SerialConnector();
		/*SerialAddress portAddress=new SerialAddress("/dev/ttyS0", 38400, 8, StopBits.BITS_1,  Parity.NONE, FlowControl.NONE );
		
		ConnectFuture future = connector.connect( portAddress );
		future.await();
		IoSession sessin = future.getSession();*/
	}

}
