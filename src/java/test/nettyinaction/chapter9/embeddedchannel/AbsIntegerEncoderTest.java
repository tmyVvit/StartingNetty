package test.nettyinaction.chapter9.embeddedchannel; 

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nettyinaction.chapter9.embeddedchannel.AbsIntegerEncoder;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.*;

/** 
* AbsIntegerEncoder Tester. 
* 
* @author <Authors name> 
* @since <pre>12月 24, 2020</pre> 
* @version 1.0 
*/ 
public class AbsIntegerEncoderTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
    *
    */
    @Test
    public void testEncode() throws Exception {
        ByteBuf buf = Unpooled.buffer();
        for (int i=1; i < 10; i++) {
            buf.writeInt(i * (-1));
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());

        for (int i = 1; i < 10; i++) {
            assertEquals(i, (int) channel.readOutbound());
        }
        assertNull(channel.readOutbound());
    }


} 
