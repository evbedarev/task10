package homework.bedarev.task_03;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;

public class CachedResult implements Externalizable {
    private String methodName;
    private Class<?> returnType;
    private Object returnValue;
    private Object[] args;
    private Class[] typeArgs;

    public CachedResult() {
    }

    public CachedResult(String methodName, Class<?> returnType, Object returnValue, Object[] args, Class[] typeArgs) {
        this.returnType = returnType;
        this.returnValue = returnValue;
        this.args = args;
        this.typeArgs = typeArgs;
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getTypeArgs() {
        return typeArgs;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(methodName);
        out.writeObject(returnType);
        out.writeObject(returnValue);
        out.writeObject(args);
        out.writeObject(typeArgs);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        methodName = (String) in.readObject();
        returnType = (Class<?>) in.readObject();
        returnValue = in.readObject();
        args = (Object[]) in.readObject();
        typeArgs = (Class[]) in.readObject();
    }

}
