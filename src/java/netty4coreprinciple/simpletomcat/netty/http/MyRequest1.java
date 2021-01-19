package netty4coreprinciple.simpletomcat.netty.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class MyRequest1 {

    private final ChannelHandlerContext context;
    private final HttpRequest httpRequest;
    private final QueryStringDecoder decoder;

    public MyRequest1(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.context = ctx;
        this.httpRequest = httpRequest;
        this.decoder = new QueryStringDecoder(getUrl());
    }

    public String getUrl() {
        return httpRequest.uri();
    }

    public String getMethod() {
        return httpRequest.method().name();
    }

    public Map<String, List<String>> getParameters() {
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (param == null) {
            return null;
        }
        return param.get(0);
    }
}
