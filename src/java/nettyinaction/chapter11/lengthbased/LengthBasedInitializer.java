package nettyinaction.chapter11.lengthbased;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

//基于长度的协议有两种类型
//   FixedLengthFrameDecoder       提取指定长度的帧
//   LengthFieldBasedFrameDecoder  根据编码进帧头部中的长度值提取帧
public class LengthBasedInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                // 解码将帧长度编码到帧起始的前8个字节的消息
                .addLast(new LengthFieldBasedFrameDecoder(64*1024, 0, 8))
                .addLast(new FrameHandler());
    }

    public static class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
            // do something
        }
    }
}
