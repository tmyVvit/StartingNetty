package nettyinaction.chapter2.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ChannelHandler.Sharable
public class MyEchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    // 当服务器的连接已经建立之后将被调用
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " -- server connected");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    // 当从服务器接收到一条消息时被调用
    // 当该方法完成时，已经有了传入的消息，并且已经处理完了。
    // 当该方法返回时，SimpleChannelInboundHandler负责释放指向保存该消息的ByteBuf的内存引用
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " -- Client received: " +
                in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        t.printStackTrace();
        ctx.close();
    }
}
