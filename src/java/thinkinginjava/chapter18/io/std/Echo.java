package thinkinginjava.chapter18.io.std;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

// 从标准输入中读取
// System.out和System.err已经被包装成了PrintStream对象，但是System.in是一个未经过加工的InputStream
// 这意味着我们可以立即使用System.out和System.err，但在读取System.in之前必须对其进行包装
public class Echo {
    public static void main(String[] args) throws IOException {
        try (BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {
            String s;
            while((s = stdin.readLine()) != null && s.length() != 0) {
                System.out.println(s.toUpperCase(Locale.ROOT));
            }
        }
    }
}
