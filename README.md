# ApacheMina
Apache Mina是一个能够帮助用户开发高性能和高伸缩性网络应用程序的框架。它通过Javanio技术基于TCP/IP和UDP/IP协议提供了抽象的、事件驱动的、异步的API。

ApacheMINA是一个网络应用程序框架，用来帮助用户简单地开发高性能和高可扩展性的网络应用程序。它提供了一个通过Java NIO在不同的传输例如TCP/IP和UDP/IP上抽象的事件驱动的异步API。
#Apache MINA 也称为:
● NIO 框架库
● 客户端服务器框架库
● 一个网络套接字库
#MINA虽然简单但是仍然提供了全功能的网络应用程序框架：
● 为不同的传输类型提供了统一的API:
○ 通过Java NIO提供TCP/IP 和 UDP/IP支持
○ 通过RXTX提供串口通讯(RS232)
○ In-VM管道通讯
○ 你能实现你自己的API!
● 过滤器作为一个扩展特性; 类似Servlet过滤器
● 低级和高级的API:
○ 低级: 使用字节缓存(ByteBuffers)
○ 高级: 使用用户定义的消息对象(objects)和编码(codecs)
● 高度定制化线程模型:
○ 单线程
○ 一个线程池
○ 一个以上的线程池(也就是SEDA)
● 使用Java 5 SSL引擎提供沙盒(Out-of-the-box) SSL · TLS · StartTLS支持
● 超载保护和传输流量控制
● 利用模拟对象进行单元测试
● JMX管理能力
● 通过StreamIoHandler提供基于流的I/O支持
● 和知名的容器(例如PicoContainer、Spring)集成
● 从MINA平滑的迁移到Netty， MINA是Netty的前辈。
