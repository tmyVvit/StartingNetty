package nettyinaction.chapter7.taskschedule;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nettyinaction.chapter2.server.MyEchoServerHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class NettyTimePostpone {
    public void executeAfterSeconds(int seconds, String taskName) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(8888))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter());
                        }
                    });
            // EventLoop扩展了ScheduledExecutorService
            // 如果要周期性执行任务可以调用scheduleAtFixedRate()方法
            group.next().schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println(taskName +"executed "+ seconds + " seconds later");
                }
            }, seconds, TimeUnit.SECONDS);

            ChannelFuture future = b.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        NettyTimePostpone timePostpone = new NettyTimePostpone();
        timePostpone.executeAfterSeconds(3, "netty task");
    }
}
