package netty4coreprinciple.simpletomcat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import netty4coreprinciple.simpletomcat.netty.handler.MyTomcatHandler;
import netty4coreprinciple.simpletomcat.netty.servlet.AbstractNettyServlet;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyTomcat1 {
    private int port;
    private final Map<String, AbstractNettyServlet> servletMapping = new HashMap<>();

    private final Properties webxml = new Properties();

    private void init() {
        try {
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream in = new FileInputStream(WEB_INF + "netty4coreprinciple/simpletomcat/netty/web1.properties");

            webxml.load(in);

            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");

                    AbstractNettyServlet servlet = (AbstractNettyServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, servlet);
                } else if ("port".equalsIgnoreCase(key)) {
                    port = Integer.parseInt(webxml.getProperty(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        init();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new HttpServerCodec())
                                    .addLast(new MyTomcatHandler(servletMapping));
                        }
                    })
                    // 针对主线程的配置，分配线程最大数128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 针对子线程的配置，保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("MyTomcat1已启动，监听端口：" + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new MyTomcat1().start();
    }
}
