package nettyinaction.chapter13.udp.pojo;

import java.net.InetSocketAddress;

public class LogEvent {
    public static final byte SEPARATOR = (byte) ':';
    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    // 用于传出消息
    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }
    // 用于传入消息
    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.received = received;
        this.logfile = logfile;
        this.msg = msg;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceived() {
        return received;
    }

    public InetSocketAddress getSource() {
        return source;
    }
}
