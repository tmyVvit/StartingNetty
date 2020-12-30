package thinkinginjava.chapter18.io.inputoutput;

import java.io.*;

// 基本的文件输出
public class BasicFileOutput {
    static final String FILE_NAME = "BasicFileOutput.out";
    public static void main(String[] args) throws IOException {
        try (BufferedReader in = new BufferedReader(new StringReader(BufferedInputFile.read(Constants.DIR + "BasicFileOutput.java")));
             PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Constants.DIR + FILE_NAME)))){
            int lineCount = 1;
            String s;
            while ((s = in.readLine()) != null) {
                out.println(lineCount++ + ": " + s);
            }
            // 注意，这里要显示调用out.close()，才会将缓冲区的数据刷新写入文件，下面才会读取到文件的内容
            out.close();
            System.out.println(BufferedInputFile.read(Constants.DIR + FILE_NAME));
        }
    }
}
