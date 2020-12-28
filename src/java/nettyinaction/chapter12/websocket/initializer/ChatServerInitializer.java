package nettyinaction.chapter12.websocket.initializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import nettyinaction.chapter12.websocket.handler.HttpRequestHandler;
import nettyinaction.chapter12.websocket.handler.TextWebSocketFrameHandler;

public class ChatServerInitializer extends ChannelInitializer<Channel> {

    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec()) //将字节解码为HttpRequest, HttpContent和LastHttpContent。并将HttpRequest, HttpContent和LastHttpContent编码为字节
                .addLast(new ChunkedWriteHandler())  // 写入一个文件的内容
                // 将一个HttpMessage和跟随它的多个HttpContent聚合为单个FullHttpRequest或者FullHttpResponse
                // 安装了这个之后，ChannelPipeline中的下一个ChannelHandler将只会收到完整的HTTP请求或者响应
                .addLast(new HttpObjectAggregator(64 * 1024))
                // 处理FullHttpRequest
                .addLast(new HttpRequestHandler("/ws"))
                // 按照WebSocket规范的要求，处理WebSocket升级握手
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                // 处理TextWebSocketFrame和握手完成事件
                .addLast(new TextWebSocketFrameHandler(group));
    }
}
