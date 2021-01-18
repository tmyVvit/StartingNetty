package thinkinginjava.chapter18.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

import static thinkinginjava.chapter18.nio.GetChannel.PATH;

public class MyNioServer {

    public static void main(String[] args) throws IOException {
        new MyNioServer().start(9999);
    }

    public void start(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel client = serverSocketChannel.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    FileChannel out = FileChannel.open(Paths.get(PATH+"server"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                    int len = 0;
                    while ((len =client.read(buffer)) > 0) {
                        buffer.flip();
                        out.write(buffer);
                        System.out.println(new String(buffer.array(), 0, len));
                        buffer.clear();
                    }
                    client.write(ByteBuffer.wrap("server write success!".getBytes()));
                }
                iterator.remove();
            }
        }
    }
}
