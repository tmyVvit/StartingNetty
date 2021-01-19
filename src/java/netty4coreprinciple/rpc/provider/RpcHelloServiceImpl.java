package netty4coreprinciple.rpc.provider;

import netty4coreprinciple.rpc.api.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {
    @Override
    public String hello(String name) {
        return "hello " + name + "!";
    }
}
