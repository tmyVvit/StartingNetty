package netty4coreprinciple.simpletomcat.servlet;

import netty4coreprinciple.simpletomcat.http.MyRequest;
import netty4coreprinciple.simpletomcat.http.MyResponse;

public class FirstServlet extends MyAbstractServlet{
    @Override
    public void doGet(MyRequest request, MyResponse response) {
        this.doPost(request, response);
    }

    @Override
    public void doPost(MyRequest request, MyResponse response) {
        response.write("first servlet");
    }
}
