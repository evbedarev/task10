package homework.bedarev.task_03;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;

public class CachedResult implements Externalizable {
    private Method cachedObject;
    private Class<?> returnType;
    private Object returnValue;
    private Object[] args;
    private Class[] typeArgs;

    public CachedResult(Method cachedObject, Class<?> returnType, Object returnValue, Object[] args, Class[] typeArgs) {
        this.cachedObject = cachedObject;
        this.returnType = returnType;
        this.returnValue = returnValue;
        this.args = args;
        this.typeArgs = typeArgs;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class[] getTypeArgs() {
        return typeArgs;
    }


    public Method getCachedObject() {
        return cachedObject;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(cachedObject);
        out.writeObject(returnType);
        out.writeObject(returnValue);
        out.writeObject(args);
        out.writeObject(typeArgs);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        cachedObject = (Method) in.readObject();
        returnType = (Class<?>) in.readObject();
        returnValue = in.readObject();
        args = (Object[]) in.readObject();
        typeArgs = (Class[]) in.readObject();
    }

}
