package nettyinaction.chapter13.udp.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import nettyinaction.chapter13.udp.pojo.LogEvent;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf data = datagramPacket.content();
        int idx = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);
        String filename = data.slice(0, idx).toString(StandardCharsets.UTF_8);
        String logMsg = data.slice(idx + 1, data.readableBytes()).toString(StandardCharsets.UTF_8);

        LogEvent event = new LogEvent(datagramPacket.sender(), System.currentTimeMillis(), filename, logMsg);
        list.add(event);
    }
}
