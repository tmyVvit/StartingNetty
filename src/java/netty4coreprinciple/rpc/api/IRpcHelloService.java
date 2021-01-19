package netty4coreprinciple.rpc.api;

// 确认服务是否可用
public interface IRpcHelloService {
    String hello(String name);
}
