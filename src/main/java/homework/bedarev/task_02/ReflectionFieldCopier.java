package homework.bedarev.task_02;

import homework.common.BeanFieldCopier;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionFieldCopier implements BeanFieldCopier {
    @Override
    public void copy(Object from, Object to) {
        Class classFrom = from.getClass();
    }

    private void findGetter(Class classFrom, Object from) throws Exception{
        Map<String, Map<Type, Object>> nameFrom = new HashMap<>();
        Map<Type, Object> valueAndTypeFrom = new HashMap<>();
        Method[] methods = classFrom.getMethods();

        for (Method method: methods) {
            if (method.getName().startsWith("get")) {
                String nameMethodFrom = method.getName().substring(3);
                Object returnValue = method.invoke(from, null);
                valueAndTypeFrom.put(getMethodParameter(method),returnValue);
                nameFrom.put(nameMethodFrom, valueAndTypeFrom);
            }
        }
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
