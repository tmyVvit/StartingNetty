package chapter2.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class MyEchoServer {
    private final int port;
    public MyEchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: " + MyEchoServer.class.getName() + "<port>");
            return ;
        }
        int port = Integer.parseInt(args[0]);
        new MyEchoServer(port).start();
    }

    public void start() throws Exception {
        final MyEchoServerHandler serverHandler = new MyEchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)    // 指定所使用的NIO传输channel
                    .localAddress(new InetSocketAddress(port))  // 使用指定的端口设置套接字地址
                    // 添加 EchoServerHandler到子Channel的ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception{
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            // 异步地绑定服务器，调用sync()方法阻塞等待直到绑定完成
            ChannelFuture future = b.bind().sync();
            // 获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            future.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
