package nettyinaction.chapter11.serialize;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

// Protocol Buffers是由Google公司开发的、开源的数据交换格式  https://github.com/google/protobuf
//  ProtobufDecoder  使用protobuf进行解码
//  ProtobufEncoder  使用protobuf进行编码
//  ProtobufVarint32FrameDecoder  根据消息中的Google Protocol Buffers的 "Base 128 Varints"整型长度字段值动态地分隔所接收到的ByteBuf
//  ProtobufVarint32LengthFieldPrepender  想ByteBuf前追加一个Google Protocol Buffers的 "Base 128 Varints" 整型的长度字段值
public class ProtoBufInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {

    }
}
