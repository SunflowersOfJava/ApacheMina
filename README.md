# ApacheMina
Apache Mina是一个能够帮助用户开发高性能和高伸缩性网络应用程序的框架。它通过Javanio技术基于TCP/IP和UDP/IP协议提供了抽象的、事件驱动的、异步的API。<br/><br/>

ApacheMINA是一个网络应用程序框架，用来帮助用户简单地开发高性能和高可扩展性的网络应用程序。它提供了一个通过Java NIO在不同的传输例如TCP/IP和UDP/IP上抽象的事件驱动的异步API。<br/>

#Apache MINA 别称
(1) NIO 框架库<br/>
(2) 客户端服务器框架库<br/>
(3) 一个网络套接字库<br/>

#MINA网络应用程序框架
##为不同的传输类型提供了统一的API:<br/>
○ 通过Java NIO提供TCP/IP 和 UDP/IP支持<br/>
○ 通过RXTX提供串口通讯(RS232)<br/>
○ In-VM管道通讯<br/>
○ 你能实现你自己的API!<br/>

## 过滤器作为一个扩展特性; 类似Servlet过滤器<br/>
##低级和高级的API:<br/>
○ 低级: 使用字节缓存(ByteBuffers)<br/>
○ 高级: 使用用户定义的消息对象(objects)和编码(codecs)<br/>

## 高度定制化线程模型:<br/>
○ 单线程<br/>
○ 一个线程池<br/>
○ 一个以上的线程池(也就是SEDA)<br/>

##使用Java 5 SSL引擎提供沙盒(Out-of-the-box) SSL · TLS · StartTLS支持<br/>
##超载保护和传输流量控制<br/>
##利用模拟对象进行单元测试<br/>
##JMX管理能力<br/>
##通过StreamIoHandler提供基于流的I/O支持<br/>
##和知名的容器(例如PicoContainer、Spring)集成<br/>
##高版本的MINA支持串口通信<br/>
##从MINA平滑的迁移到Netty， MINA是Netty的前辈<br/>

#Mina编程实践

##com.boonya.mina.protocol
包括自定义协议实现示例

##com.boonya.mina.quickstart
包括简单的时间处理，push目录下的发布/订阅

##org.apache.mina
Apache Mina源代码

##个人专栏
Mina学习实践： http://blog.csdn.net/column/details/i-love-mina.html


