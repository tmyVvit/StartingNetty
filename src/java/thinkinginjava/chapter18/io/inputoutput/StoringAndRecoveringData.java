package thinkinginjava.chapter18.io.inputoutput;

import java.io.*;

// 存储和恢复数据
public class StoringAndRecoveringData {
    private static final String FILE_NAME = Constants.DIR + "data.txt";
    public static void main(String[] args) throws IOException {
        DataOutputStream out = null;
        DataInputStream in = null;
        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(FILE_NAME)));
            out.writeDouble(3.1415);
            out.writeUTF("That was pi");
            out.writeDouble(1.41413);
            out.writeUTF("Square root of 2");
            out.close();
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(FILE_NAME)));
            System.out.println(in.readDouble());
            System.out.println(in.readUTF());
            System.out.println(in.readDouble());
            System.out.println(in.readUTF());
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException ignore) { }
            if (in != null)
                try {
                    in.close();
                } catch (IOException ignore) {}
        }
    }
}
