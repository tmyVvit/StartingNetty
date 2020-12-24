package nettyinaction.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.*;

/** 
* LineBasedHandlerInitializer Tester. 
* 
* @author <Authors name> 
* @since <pre>12月 24, 2020</pre> 
* @version 1.0 
*/ 
public class LineBasedHandlerInitializerTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }
    @Test
    public void testLineBasedFrameDecoder() throws Exception {
        ByteBuf byteBuf = Unpooled.buffer();
        for (int i =1; i < 10; i++) {
            byteBuf.writeInt(i);
            if (i % 3 == 0) {
                byteBuf.writeBytes("\r\n".getBytes());
            }
        }

        EmbeddedChannel channel = new EmbeddedChannel(new LineBasedFrameDecoder(64 * 1024));
        assertTrue(channel.writeInbound(byteBuf));
        assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        assertEquals(12, read.readableBytes());
        read = channel.readInbound();
        assertEquals(12, read.readableBytes());
        read = channel.readInbound();
        assertEquals(12, read.readableBytes());
        assertNull(channel.readInbound());

    }


} 
