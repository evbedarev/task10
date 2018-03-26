package homework.bedarev.task_02;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Start {
    public static void main(String[] args) throws Exception{
        ClassOne classOne = new ClassOne();
        classOne.setCount(25);
        classOne.setName("Huy");
        Map<String, Map<Type, Object>> returnedValue;
        Map<Type, Object> valueMap;
        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier();

        //GETTER
        returnedValue = reflectionFieldCopier.findGetter(classOne.getClass(), classOne);
        if (returnedValue.size() > 0) {
            for (Map.Entry entry: returnedValue.entrySet()) {
                System.out.println("Name method: " + entry.getKey());
                valueMap = (Map<Type, Object>) entry.getValue();
                valueMap.entrySet()
                        .stream()
                        .forEach(e -> {
                            System.out.println(e.getValue());
                            setSetter(classOne,entry.getKey().toString(), e.getKey().getClass(), e.getValue());
                            System.out.println(e.getValue());
                        });

            }
        }

    }

    /*
    *   @param to - Object to copy
    *   @param nameMethod - getter method
    *   @param typeParam - type
    *
     */

    private static void setSetter(Object to, String nameMethod, Class typeParam, Object valueToSet) {
        //SETTER
        try {
            Map<String, Class> setter;
            ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier();
            Class toClazz = to.getClass();
            setter = reflectionFieldCopier.findSetter(toClazz, to);
            for (Map.Entry entry : setter.entrySet()) {
                if (typeParam == entry.getValue()) {
                    Method method = toClazz.getMethod("set" + nameMethod,new Class[]{String.class});
                    method.invoke(to, typeParam.cast(valueToSet));
                }
                System.out.println("Setter method name: " + entry.getKey());
                System.out.println("Setter type is: " + entry.getValue().toString());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
