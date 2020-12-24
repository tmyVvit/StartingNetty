package nettyinaction.chapter9.embeddedchannel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import nettyinaction.chapter9.embeddedchannel.FrameChunkDecoder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** 
* FrameChunkDecoder Tester. 
* 
* @author <Authors name> 
* @since <pre>12月 24, 2020</pre> 
* @version 1.0 
*/ 
public class FrameChunkDecoderTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
    *
    */
    @Test
    public void testDecode() throws Exception {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));
        assertTrue(channel.writeInbound(input.readBytes(2)));
        try {
            channel.writeInbound(input.readBytes(4));
            Assert.fail();
        } catch (TooLongFrameException ignore) {}
        assertTrue(channel.writeInbound(input.readBytes(3)));
        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(2), read);

        read = channel.readInbound();
        assertEquals(buf.skipBytes(4).readSlice(3), read);
        read.release();
        buf.release();
    }

    /**
    *
    * Method: cumulate(ByteBufAllocator var1, ByteBuf var2, ByteBuf var3)
    *
    */
    @Test
    public void testCumulate() throws Exception {
    //TODO: Test goes here...
    }


} 
