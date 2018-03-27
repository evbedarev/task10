package homework.bedarev.task_02;

import homework.common.BeanFieldCopier;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionFieldCopier implements BeanFieldCopier {
    Print print = new Print();

    @Override
    public void copy(Object from, Object to) {
        Class classFrom = from.getClass();
        Map<String, Map<Class<?>, Object>> returnedValueGet;
        Map<Class<?>, Object> valueMap;

        try {
            returnedValueGet = findGetter(classFrom, from);
            if (returnedValueGet.size() > 0) {
                for (Map.Entry<String, Map<Class<?>, Object>> entry: returnedValueGet.entrySet()) {
                    valueMap = entry.getValue();
                    valueMap.entrySet()
                            .stream()
                            .forEach(e -> {
                                setValue(e.getValue(),e.getKey(),entry.getKey(), to);
                            });
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Map<String, Map<Class<?>, Object>> findGetter(Class classFrom, Object from) throws Exception{
        Map<String, Map<Class<?>, Object>> getterFrom = new HashMap<>();
        Method[] methods = classFrom.getMethods();
        for (Method method: methods) {
            if ((method.getName().startsWith("get")) && (!method.getName().equals("getClass"))) {
                Map<Class<?>, Object> valueAndTypeFrom = new HashMap<>();
                String nameMethodFrom = method.getName().substring(3);
                Object returnValue = method.invoke(from, null);
                valueAndTypeFrom.put(method.getReturnType(),returnValue);
                getterFrom.put(nameMethodFrom, valueAndTypeFrom);
            }
        }
        print.printMsg("Found " + getterFrom.size() + " getters");
        return  getterFrom;
    }

    private void setValue (Object valueToSet,Class<?> typeParam, String nameMethod, Object to) {
        try {
            Map<String, Class[]> setterMap = findSetter(to.getClass(), to);
            setterMap.entrySet()
                    .removeIf( e -> !e.getKey().equals(nameMethod));

            Class<?> clazz = setterMap
                    .entrySet()
                    .iterator()
                    .next()
                    .getValue()[0];

            if (clazz == typeParam) {
                setValueToSetter(to, setterMap, valueToSet);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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

    private static void setValueToSetter(Object objectTo,
                                         Map<String, Class[]> mapNameAndClass,
                                         Object valueToSet) throws Exception{

        for (Map.Entry<String, Class[]> entry: mapNameAndClass.entrySet()) {
            Class ClassTypeParameter = entry.getValue()[0];
            Method method = objectTo.getClass().getMethod("set" + entry.getKey(), entry.getValue());
            method.invoke(objectTo, ClassTypeParameter.cast(valueToSet));
        }
    }
}
