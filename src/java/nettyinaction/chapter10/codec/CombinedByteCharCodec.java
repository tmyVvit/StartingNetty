package nettyinaction.chapter10.codec;

import io.netty.channel.CombinedChannelDuplexHandler;
import nettyinaction.chapter10.decoder.ByteToCharDecoder;
import nettyinaction.chapter10.encoder.CharToByteEncoder;

// CombinedChannelDuplexHandler 可以利用已有的编码器、解码器构建一个编解码器
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
    public CombinedByteCharCodec() {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
