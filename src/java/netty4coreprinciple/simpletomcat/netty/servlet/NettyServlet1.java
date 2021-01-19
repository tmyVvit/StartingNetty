package netty4coreprinciple.simpletomcat.netty.servlet;

import netty4coreprinciple.simpletomcat.netty.http.MyRequest1;
import netty4coreprinciple.simpletomcat.netty.http.MyResponse1;

public class NettyServlet1 extends AbstractNettyServlet {
    @Override
    public void doGet(MyRequest1 request1, MyResponse1 response1) {
        response1.write("doGet: NettyServlet1");
    }

    @Override
    public void doPost(MyRequest1 request1, MyResponse1 response1) {
        response1.write("doPost: NettyServlet1");
    }
}
