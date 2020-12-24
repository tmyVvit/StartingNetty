package nettyinaction.chapter8.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 *  假设服务器正在处理一个客户端请求，这个请求需要服务器充当第三方系统的客户端
 *  这种情况下需要从已经被接受的子Channel中引导一个客户端Channel
 */
public class ServerBootstrapSampleII {
    public void serverStart() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        // ServerBootstrap需要两个 EventLoopGroup(可以是同一个实例)
        // 因为服务器需要两组不同的Channel，第一组只包含一个ServerChannel，代表服务器自身的已经绑定到某个本地端口的正在监听的套接字
        // 第二组将包含所有已创建的用来处理传入客户端连接的Channel
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture connectionFuture;
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        // 创建bootstrap实例以连接远程主机
                        Bootstrap bootstrap1 = new Bootstrap();
                        bootstrap1.channel(NioSocketChannel.class)
                                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                                        System.out.println("Received data");
                                    }
                                });
                        // 使用与分配给已被接受的子Channel相同的EventLoop
                        bootstrap1.group(ctx.channel().eventLoop());
                        connectionFuture = bootstrap1.connect(new InetSocketAddress("www.manning.com", 80));
                        connectionFuture.addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if (channelFuture.isSuccess()) {
                                    System.out.println("Connection to www.manning.com established");
                                } else {
                                    System.err.println("Connection to www.manning.com failed");
                                    channelFuture.cause().printStackTrace();
                                }
                            }
                        });
                    }
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        if (connectionFuture.isDone()) {
                            // do something
                        }
                    }
                });
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8888));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new ServerBootstrapSampleII().serverStart();
    }
}
