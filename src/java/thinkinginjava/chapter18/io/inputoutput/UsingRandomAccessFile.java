package thinkinginjava.chapter18.io.inputoutput;

import java.io.IOException;
import java.io.RandomAccessFile;

// 读写随机访问文件
public class UsingRandomAccessFile {
    private static final String FILE_NAME = Constants.DIR + "rtest.dat";
    private static void display() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
            for (int i = 0; i < 7; i++) {
                System.out.println("Value " + i + ": " + raf.readDouble());
            }
            System.out.println(raf.readUTF());
        }
    }

    public static void main(String[] args) throws IOException {
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(FILE_NAME, "rw");
            for(int i = 0; i < 7; i++) {
                rf.writeDouble(i * 1.414);
            }
            rf.writeUTF("the end of the file");
            rf.close();
            display();
            rf = new RandomAccessFile(FILE_NAME, "rw");
            // double 是8个字节，所以这里修改了第5个（从0开始计数）double数字
            rf.seek(5*8);
            rf.writeDouble(47.00001);
            rf.close();
            display();
        }finally {
            if (rf != null)
                rf.close();
        }
    }

}
