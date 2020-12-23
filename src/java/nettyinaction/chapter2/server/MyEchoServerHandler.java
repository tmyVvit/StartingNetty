package nettyinaction.chapter2.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ChannelHandler.Sharable // 标志一个ChannelHandler可被多个Channel安全地共享
public class MyEchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    // 对每个传入的消息都要调用
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " -- Server received: " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in); // 将接手到的消息写给发送者，而不冲刷出站消息
    }

    @Override
    // 通知ChannelInboundHandler最后一次对channelRead()的调用是当前批量读取中的最后一条消息
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // 将pending message冲刷到远程节点，并关闭channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    // 在读取操作期间有异常抛出时会调用
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
