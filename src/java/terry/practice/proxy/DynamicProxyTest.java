package terry.practice.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    interface IHello {
        void sayHello();
    }

    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }

    static class DynamicProxy implements InvocationHandler {

        Object originObject;

        Object bind(Object originObject) {
            this.originObject = originObject;
            return Proxy.newProxyInstance(originObject.getClass().getClassLoader(), originObject.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Dynamic Proxy Start");
            Object res = method.invoke(originObject, args);
            System.out.println("Dynamic Proxy end");
            return res;
        }
    }

    public static void main(String[] args) {
        IHello hello = (IHello) new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }

}
