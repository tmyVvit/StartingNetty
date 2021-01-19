package netty4coreprinciple.simpletomcat.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class MyResponse1 {

    private final ChannelHandlerContext context;
    private final HttpRequest request;

    public MyResponse1(ChannelHandlerContext ctx, HttpRequest request) {
        this.context = ctx;
        this.request = request;
    }

    public void write(String out) {
        try {
            if (out == null || out.length() == 0) {
                return ;
            }
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(out.getBytes(StandardCharsets.UTF_8)));
            response.headers().set("Content-Type", "text/html");
            context.write(response);
        } finally {
            context.flush();
            context.close();
        }
    }
}
