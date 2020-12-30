package thinkinginjava.chapter18.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static thinkinginjava.chapter18.nio.GetChannel.PATH;

/**
 * 内存文件映射允许创建和修改因为太大而不能放入内存的文件
 * 可以显著加快速度
 */
public class LargeMappedFiles {
    private static final int length = 0x8ffffff;
    public static void main(String[] args) throws Exception {
        MappedByteBuffer out = new RandomAccessFile(PATH+"mappedFile", "rw")
                                    // 必须指定映射文件的初始位置和映射区域的长度
                                    .getChannel().map(FileChannel.MapMode.READ_WRITE, 0, length);
        for (int i = 0; i < length; i++) {
            out.put((byte) 'x');
        }
        System.out.println("Finishing writing");
    }
}
