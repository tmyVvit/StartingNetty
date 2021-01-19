package netty4coreprinciple.rpc.api;

public interface IRpcCalculateService {
    int add(int a, int b);
    int sub(int a, int b);
    int mult(int a, int b);
    int div(int a, int b);
}
