package netty4coreprinciple.myproxy.api;

import netty4coreprinciple.myproxy.api.IHello;

public class Hello implements IHello {
    @Override
    public int sayHello() {
        System.out.println("Say hello!");
        return 1;
    }
}
