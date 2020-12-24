package nettyinaction.chapter11.serialize;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public class MarshallingInitializer extends ChannelInitializer<Channel> {

    private final MarshallerProvider marshallerProvider;
    private final UnmarshallerProvider unmarshallerProvider;
    public MarshallingInitializer(MarshallerProvider marshallerProvider, UnmarshallerProvider unmarshallerProvider) {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                // 适用于适用JBoss Marshalling的节点
                // 添加MarshallingDecoder以将ByteBuf转换为POJO
                .addLast(new MarshallingDecoder(unmarshallerProvider))
                // 添加MarshallingEncoder以将POJO转换为ByteBuf
                .addLast(new MarshallingEncoder(marshallerProvider));
    }
}
