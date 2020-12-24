package test.nettyinaction.chapter9.embeddedchannel; 

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nettyinaction.chapter9.embeddedchannel.FixedLengthFrameDecoder;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.*;

/** 
* FixedLengthFrameDecoder Tester. 
* 
* @author <Authors name> 
* @since <pre>12月 24, 2020</pre> 
* @version 1.0 
*/ 
public class FixedLengthFrameDecoderTest { 

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
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        // write bytes
        assertTrue(channel.writeInbound(input.retain()));
        assertTrue(channel.finish());
        // read message
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();
    }

    /**
    *
    * Method: decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
    *
    */
    @Test
    public void testDecode2() throws Exception {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        assertFalse(channel.writeInbound(input.readBytes(2)));
        assertTrue(channel.writeInbound(input.readBytes(7)));
        assertTrue(channel.finish());
        // read message
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buf.release();
    }


} 
