package thinkinginjava.chapter18.io.inputoutput;

import java.io.IOException;
import java.io.StringReader;

// 从内存输入
public class MemoryInput {
    public static void main(String[] args) throws IOException {
        try (StringReader in = new StringReader(BufferedInputFile.read(Constants.DIR + "MemoryInput.java"))) {
            int c;
            // 每次读取一个字节
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
        }
    }
}
