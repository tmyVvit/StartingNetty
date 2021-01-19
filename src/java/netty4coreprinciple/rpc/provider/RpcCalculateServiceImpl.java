package netty4coreprinciple.rpc.provider;

import netty4coreprinciple.rpc.api.IRpcCalculateService;

public class RpcCalculateServiceImpl implements IRpcCalculateService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }

    @Override
    public int mult(int a, int b) {
        return a * b;
    }

    @Override
    public int div(int a, int b) {
        return a / b;
    }
}
