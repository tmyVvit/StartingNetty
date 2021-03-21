package netty4coreprinciple.myproxy.dynamic;

import netty4coreprinciple.myproxy.api.Hello;
import netty4coreprinciple.myproxy.api.IHello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyDynamicProxy {
    private Object target;
    public MyDynamicProxy(Object _target) {
        target = _target;
    }

    public Object getProxyInstance() {
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("Proxy start");
                Object result = method.invoke(target, args);
                System.out.println("Proxy end");
                return result;
            }
        });
    }

    public static void main(String[] args) {
        IHello hello = (IHello) new MyDynamicProxy(new Hello()).getProxyInstance();
        int res = hello.sayHello();
        System.out.println(res);
    }
}
