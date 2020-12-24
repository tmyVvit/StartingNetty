package nettyinaction.chapter8.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

// 该代码引导了一个使用NIO TCP传输的客户端
public class ClientBootstrapSample {
    public void clientStart() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf o) throws Exception {
                        System.out.println("received data!");
                    }
                });
        /**
         * 在引导过程中，调用connect()或者bind()之前必需调用以下方法来设置所需的组件
         *    * group()
         *    * channel() 或者 channelFactory()
         *    * handler()
         * 否则会导致IllegalStateException。尤其是handler()方法，因为它将配置好ChannelPipeline
         */
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println("Connection established");
                } else {
                    System.err.println("Connection attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new ClientBootstrapSample().clientStart();
    }
}
