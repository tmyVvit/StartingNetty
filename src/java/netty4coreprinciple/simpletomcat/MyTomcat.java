package netty4coreprinciple.simpletomcat;

import netty4coreprinciple.simpletomcat.http.MyRequest;
import netty4coreprinciple.simpletomcat.http.MyResponse;
import netty4coreprinciple.simpletomcat.servlet.MyAbstractServlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyTomcat {
    private int port;
    private ServerSocket server;
    private final Map<String, MyAbstractServlet> servletMapping = new HashMap<>();

    private final Properties webxml = new Properties();

    // 初始化，解析web.xml配置文件
    private void init() {
        // 加载web.xml文件，同时初始化servletmapping
        try {
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");

            webxml.load(fis);

            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");

                    MyAbstractServlet servlet = (MyAbstractServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, servlet);
                } else if ("port".equalsIgnoreCase(key)) {
                    port = Integer.parseInt(webxml.getProperty(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 服务就绪，完成ServerSocket准备工作
    public void start() {
        init();
        try {
            server = new ServerSocket(port);
            System.out.println("MyTomcat已启动，监听端口：" + port);

            while (true) {
                Socket client = server.accept();
                process(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 接受请求，完成每一次请求的处理
    private void process(Socket client) {
        try (InputStream in = client.getInputStream();
             OutputStream out = client.getOutputStream()) {
            MyRequest request = new MyRequest(in);
            MyResponse response = new MyResponse(out);

            String url = request.getUrl();

            if (servletMapping.containsKey(url)) {
                servletMapping.get(url).service(request, response);
            } else {
                response.write("404 - Not Found");
            }
            out.flush();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyTomcat().start();
    }
}
