> Netty是一款异步的事件驱动的网络应用程序框架，支持快速地开发可维护的高性能的面相协议的服务器和客户端。

### Netty的核心组件

##### Channel

Channel是Java NIO的一个基本构造。它代表一个到实体（一个硬件设备、一个文件、一个网络套接字或者一个能够执行一个或者多个不同的I/O操作的程序组件）的开放连接，如读操作和写操作。

可以把Channel看作是传入（入站）或者传出（出站）数据的载体。因此，它可以被打开或者被关闭，连接或者断开连接。

##### 回调

一个回调其实就是一个方法，一个指向已经被提供给另外一个方法的方法的引用。这使得后者可以在适当的时候调用前者。

Netty在内部使用了回调来处理事件；当一个回调被触发时，相关的事件可以被一个interfaceChannelHandler的实现处理。

```java
public class ConnectHandler extends ChannelInboundHanderAdapter {
  // 当一个新连接建立时，channelActive会被调用
	@Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("Client " + ctx.channel().remoteAddress() + " connected");
  }
}
```

##### Future

Future提供了另外一种在操作完成时通知应用程序的方式。这个对象可以看作是一个异步操作的结果的占位符；他将在未来的某个时刻完成，并提供对其结果的访问。  

##### 事件和ChannelHandler

Netty使用不同的事件来通知我们状态的改变或者操作的状态。这使得我们能够基于已经发生的事件来触发适当的动作。

Netty是一个网络编程框架，所以事件是按照它们与入站或出站数据流的相关性分类的。可能由入站数据或者相关的状态更改而触发的事件有：连接已被激活或连接失活、数据读取、用户事件、错误事件；出站事件是未来将会触发的某个动作的操作结果，这些动作包括：打开或者关闭到远程节点的连接、将数据写到或者冲刷到套接字。