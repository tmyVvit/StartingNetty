package netty4coreprinciple.myproxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import netty4coreprinciple.myproxy.api.Hello;
import netty4coreprinciple.myproxy.api.IHello;

import java.lang.reflect.Method;

public class MyCglibProxy implements MethodInterceptor {
    private Object target;
    public MyCglibProxy(Object _target) {
        target = _target;
    }

    public Object getInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib proxy start");
        Object res = method.invoke(target, objects);
        System.out.println("cglib proxy end");
        return res;
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        Hello proxy = (Hello) new MyCglibProxy(hello).getInstance();
        int res = proxy.sayHello();
        System.out.println(res);
    }
}
