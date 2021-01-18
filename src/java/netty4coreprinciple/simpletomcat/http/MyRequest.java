package netty4coreprinciple.simpletomcat.http;

import java.io.InputStream;

public class MyRequest {
    private String method;
    private String url;

    /***
     *  HTTP请求格式：（从Chrome中截取部分）
        我们只需要请求中的方法以及url

     GET /firstServlet.do HTTP/1.1
     Host: localhost:8080
     Connection: keep-alive
     Cache-Control: max-age=0
     sec-ch-ua: "Google Chrome";v="87", " Not;A Brand";v="99", "Chromium";v="87"
     sec-ch-ua-mobile: ?0
     Upgrade-Insecure-Requests: 1
     User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 11_1_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36
     Accept: text/html,application/xhtml+xml,application/xml;
     ***/
    public MyRequest(InputStream in) {
        try {
            String content = "";
            byte[] buff = new byte[1024];
            int len = 0;
            if ((len = in.read(buff)) > 0) {
                content = new String(buff, 0, len);
            }
            String line = content.split("\\n")[0];
            String[] arr = line.split("\\s");
            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }
}
