package nettyinaction.chapter13.udp.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nettyinaction.chapter13.udp.pojo.LogEvent;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent logEvent) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(logEvent.getReceived())
                .append("[")
                .append(logEvent.getSource().toString())
                .append("] [")
                .append(logEvent.getLogfile())
                .append("] : ")
                .append(logEvent.getMsg());
        System.out.println(sb.toString());
    }
}
