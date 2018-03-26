package homework.bedarev.task_02;

import homework.common.BeanFieldCopier;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ReflectionFieldCopier implements BeanFieldCopier {
    @Override
    public void copy(Object from, Object to) {
        Class classFrom = from.getClass();
    }

    public Map<String, Map<Type, Object>> findGetter(Class classFrom, Object from) throws Exception{
        Map<String, Map<Type, Object>> getterFrom = new HashMap<>();
        Method[] methods = classFrom.getMethods();
        for (Method method: methods) {
            if ((method.getName().startsWith("get")) && (!method.getName().equals("getClass"))) {
                Map<Type, Object> valueAndTypeFrom = new HashMap<>();
                System.out.println("Name method: " + method.getName());
                String nameMethodFrom = method.getName().substring(3);
                Object returnValue = method.invoke(from, null);
                valueAndTypeFrom.put(method.getReturnType(),returnValue);
                getterFrom.put(nameMethodFrom, valueAndTypeFrom);
            }
        }
        return  getterFrom;
    }

    public Map<String, Class[]> findSetter(Class classTo, Object to) throws Exception{
        Map<String, Class[]> setterTo = new HashMap<>();
        Method[] methods = classTo.getMethods();

        for (Method method: methods) {
            if (method.getName().startsWith("set")) {
                String nameMethodTo = method.getName().substring(3);
                Class[] typeSetter = method.getParameterTypes();
                setterTo.put(nameMethodTo, typeSetter);
            }
        }
        return setterTo;
    }



    private Type getMethodParameter(Method method) {
        Type[] types = method.getParameterTypes();
        if (types.length == 1) {
            return types[0];
        }
        return null;
    }

    private <T> boolean checkValues(Class<? extends T> from, Class<? super T> to) {
        return true;
    }



}
