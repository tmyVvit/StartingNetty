package thinkinginjava.chapter18.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

// 旧IO类库中有三个类被修改了：FileInputStream, FileOutputStream以及RandomAccessFile。

/**
 *  getChannel()将会产生一个FileChannel，通道可以向它传送用于读写的ByteBuffer，并且可以锁定文件的某些区域用于独占式访问
 */
public class GetChannel {
    private static final int BSIZE = 1024;
    public static final String PATH = "./src/java/thinkinginjava/chapter18/nio/";
    private static final String WFILE = PATH + "data";
    public static void main(String[] args) throws Exception {
        FileChannel fc = new FileOutputStream(WFILE).getChannel();
        // 使用wrap()方法将已存在的字节数组包装到ByteBuffer中，此时不会再复制底层数组，而是把它直接作为所产生的ByteBuffer的存储器
        fc.write(ByteBuffer.wrap("Some text ".getBytes(StandardCharsets.UTF_8)));
        fc.close();
        fc = new RandomAccessFile(WFILE, "rw").getChannel();
        // 移动到末尾
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("Some more ".getBytes(StandardCharsets.UTF_8)));
        fc.close();
        fc = new FileInputStream(WFILE).getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        // 调用flip()方法，做好让别人从ByteBuffer读取字节的准备。当需要继续读取字节到ByteBuffer时，需要调用clear()方法
        buff.flip();
        while (buff.hasRemaining()) {
            System.out.print((char) buff.get());
        }
    }
}
