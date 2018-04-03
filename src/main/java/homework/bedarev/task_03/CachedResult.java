package homework.bedarev.task_03;

import java.io.Serializable;
import java.lang.reflect.Method;

public class CachedResult implements Serializable {
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

    public void setCachedObject(Method cachedObject) {
        this.cachedObject = cachedObject;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setTypeArgs(Class[] typeArgs) {
        this.typeArgs = typeArgs;
    }

    public Object[] getArgs() {

        return args;
    }

    public Class[] getTypeArgs() {
        return typeArgs;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
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
}
