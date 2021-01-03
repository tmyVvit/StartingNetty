package thinkinginjava.chapter18.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static thinkinginjava.chapter18.nio.GetChannel.PATH;

public class MyNioClient {

    public static void main(String[] args) throws IOException {
        new MyNioClient().start(9999);
    }

    public void start(int port) throws IOException {
        SocketChannel client = SocketChannel.open(new InetSocketAddress(port));
        client.configureBlocking(false);
        Selector selector = Selector.open();

        client.register(selector, SelectionKey.OP_READ);
        FileChannel fileChannel = new FileInputStream(PATH + "client").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (fileChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            client.write(byteBuffer);
            byteBuffer.clear();
        }

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer response = ByteBuffer.allocate(1024);
                    int readbytes = channel.read(response);
                    if (readbytes > 0) {
                        response.flip();
                        System.out.println(new java.lang.String(response.array(), 0, readbytes));
                    }
                }
                iterator.remove();
            }
        }
    }
}
