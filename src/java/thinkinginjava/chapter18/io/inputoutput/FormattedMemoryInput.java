package thinkinginjava.chapter18.io.inputoutput;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// 格式化的内存输入
public class FormattedMemoryInput {
    public static void main(String[] args) throws IOException {
        try (DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(
                        BufferedInputFile.read(Constants.DIR + "FormattedMemoryInput.java").getBytes(StandardCharsets.UTF_8)))) {
            while (in.available() > 0) {
                System.out.print((char) in.readByte());
            }
        } finally {
            System.err.println("End of stream");
        }
    }
}
