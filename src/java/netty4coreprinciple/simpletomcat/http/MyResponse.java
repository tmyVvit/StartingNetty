package netty4coreprinciple.simpletomcat.http;

import java.io.IOException;
import java.io.OutputStream;

public class MyResponse {
    private OutputStream out;
    public MyResponse(OutputStream out) {
        this.out = out;
    }

    public void write(String s) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("HTTP/1.1 200 ook \n")
                    .append("Content-Type: text/html;\n")
                    .append("\r\n")
                    .append(s);
            out.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
