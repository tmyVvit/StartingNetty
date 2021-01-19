package netty4coreprinciple.rpc.consumer;

import netty4coreprinciple.rpc.api.IRpcCalculateService;
import netty4coreprinciple.rpc.api.IRpcHelloService;
import netty4coreprinciple.rpc.consumer.proxy.RpcProxy;

public class RpcConsumer {
    public static void main(String[] args) {
        IRpcHelloService rpcHelloService = RpcProxy.create(IRpcHelloService.class);
        System.out.println(rpcHelloService.hello("Terry"));

        IRpcCalculateService service = RpcProxy.create(IRpcCalculateService.class);

        System.out.println("8 + 2 = " + service.add(8, 2));
        System.out.println("8 - 2 = " + service.sub(8, 2));
        System.out.println("8 * 2 = " + service.mult(8, 2));
        System.out.println("8 / 2 = " + service.div(8, 2));
    }
}
