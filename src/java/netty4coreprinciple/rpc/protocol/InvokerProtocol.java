package netty4coreprinciple.rpc.protocol;

import java.io.Serializable;

public class InvokerProtocol implements Serializable {
    private String className;
    private String methodName;
    private Class<?>[] paramClass;
    private Object[] params;

    public InvokerProtocol(){}
    public InvokerProtocol(String className, String methodName, Class<?>[] paramClass, Object[] params) {
        this.className = className;
        this.methodName = methodName;
        this.paramClass = paramClass;
        this.params = params;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class<?>[] paramClass) {
        this.paramClass = paramClass;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
