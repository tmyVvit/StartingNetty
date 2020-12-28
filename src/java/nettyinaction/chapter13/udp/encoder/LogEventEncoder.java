package nettyinaction.chapter13.udp.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import nettyinaction.chapter13.udp.pojo.LogEvent;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress remoteAddr;
    public LogEventEncoder(InetSocketAddress remoteAddr) {
        this.remoteAddr = remoteAddr;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LogEvent logEvent, List<Object> list) throws Exception {
        byte[] file = logEvent.getLogfile().getBytes(StandardCharsets.UTF_8);
        byte[] msg = logEvent.getMsg().getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = channelHandlerContext.alloc().buffer(file.length + msg.length + 1);
        buf.writeBytes(file);
        buf.writeByte(LogEvent.SEPARATOR);
        buf.writeBytes(msg);
        list.add(new DatagramPacket(buf, remoteAddr));
    }
}
