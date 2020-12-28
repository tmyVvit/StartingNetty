package nettyinaction.chapter12.websocket.initializer;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class SecuredChatServerInitializer extends ChatServerInitializer {
    private final SslContext context;
    public SecuredChatServerInitializer(ChannelGroup group, SslContext sslContext) {
        super(group);
        this.context = sslContext;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        super.initChannel(ch);
        SSLEngine engine = context.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        ch.pipeline().addFirst(new SslHandler(engine));
    }
}
