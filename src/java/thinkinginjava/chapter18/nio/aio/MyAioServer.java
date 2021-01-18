package thinkinginjava.chapter18.nio.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyAioServer {
    private final int port;

    public static void main(String[] args) {
        new MyAioServer(8000);
    }

    public MyAioServer(int port) {
        this.port = port;
        listen();
    }

    private void listen() {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(threadGroup);
            server.bind(new InetSocketAddress(port));
            System.out.println("服务器已启动，监听端口：" + port);

            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                final ByteBuffer buffer = ByteBuffer.allocate(1024);
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    System.out.println("I/O操作成功，开始获取数据");
                    try {
                        buffer.clear();
                        result.read(buffer).get();
                        buffer.flip();
                        System.out.println(new String(buffer.array()));
                        result.write(buffer);
                        buffer.flip();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            result.close();
                            server.accept(null, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("操作完成");
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("I/O操作失败: " + exc);
                }
            });

            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
