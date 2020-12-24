package nettyinaction.chapter8.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 我们可以使用ChannelInitializer将多个ChannelHandler添加到一个ChannelPipeline中
 *
 */
public class ServerBootstrapSampleIII {
    public void serverStart() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new HttpClientCodec());
                        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
                    }
                });
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(8888));
        future.sync();
    }
}
