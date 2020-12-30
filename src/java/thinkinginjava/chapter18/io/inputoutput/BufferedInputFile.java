package thinkinginjava.chapter18.io.inputoutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// 缓冲输入文件
public class BufferedInputFile {
    public static String read(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String s;
            StringBuilder sb = new StringBuilder();
            while ((s = in.readLine()) != null) {
                sb.append(s).append("\n");
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(read(Constants.DIR + "BufferedInputFile.java"));
    }
}
