package netty4coreprinciple.simpletomcat.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import netty4coreprinciple.simpletomcat.netty.http.MyRequest1;
import netty4coreprinciple.simpletomcat.netty.http.MyResponse1;
import netty4coreprinciple.simpletomcat.netty.servlet.AbstractNettyServlet;

import java.util.Map;

public class MyTomcatHandler extends ChannelInboundHandlerAdapter {

    private final Map<String, AbstractNettyServlet> servletMapping;

    public MyTomcatHandler(Map<String, AbstractNettyServlet> servletMapping) {
        this.servletMapping = servletMapping;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("hello");
            HttpRequest request = (HttpRequest) msg;

            MyRequest1 myRequest1 = new MyRequest1(ctx, request);
            MyResponse1 myResponse1 = new MyResponse1(ctx, request);

            String url = myRequest1.getUrl();
            if (servletMapping.containsKey(url)) {
                servletMapping.get(url).service(myRequest1, myResponse1);
            } else {
                myResponse1.write("404 - Not Found");
            }

        }
    }
}
