package netty4coreprinciple.simpletomcat.netty.servlet;

import netty4coreprinciple.simpletomcat.netty.http.MyRequest1;
import netty4coreprinciple.simpletomcat.netty.http.MyResponse1;

public abstract class AbstractNettyServlet {
    public void service(MyRequest1 request1, MyResponse1 response1) {
        if ("GET".equalsIgnoreCase(request1.getMethod())) {
            doGet(request1, response1);
        } else {
            doPost(request1, response1);
        }
    }

    public abstract void doGet(MyRequest1 request1, MyResponse1 response1);
    public abstract void doPost(MyRequest1 request1, MyResponse1 response1);
}
