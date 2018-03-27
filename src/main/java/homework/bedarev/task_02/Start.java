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
        setSetter(classOne,
                reflectionFieldCopier.findSetter(classOne.getClass(), classOne),
                "MADJO",
                "Name");

        System.out.println(classOne.getName());

        //GETTER
//        returnedValue = reflectionFieldCopier.findGetter(classOne.getClass(), classOne);
//        if (returnedValue.size() > 0) {
//            for (Map.Entry entry: returnedValue.entrySet()) {
//                System.out.println("Name method: " + entry.getKey());
//                valueMap = (Map<Type, Object>) entry.getValue();
//                valueMap.entrySet()
//                        .stream()
//                        .forEach(e -> {
//                            System.out.println(e.getValue());
//                        });
//            }
//        }
//        setSetterTest();
    }

    /*
    *   @param ObjectTo - Object to copy
    *   @param mapNameAndClass - String name of method and type parameter;
    *   @param valueToSet - object of value to set into setter
    *   @param nameMethod - name of method without 'set'
    *
     */

    private static void setSetter(Object objectTo,
                                  Map<String, Class[]> mapNameAndClass,
                                  Object valueToSet,
                                  String nameMethod) throws Exception{

        for (Map.Entry<String, Class[]> entry: mapNameAndClass.entrySet()) {
            if (nameMethod.equals(entry.getKey())) {
                Class ClassTypeParameter = entry.getValue()[0];
                Method method = objectTo.getClass().getMethod("set" + entry.getKey(), entry.getValue());
                method.invoke(objectTo, ClassTypeParameter.cast(valueToSet));
            }
        }
    }

    //SETTER
    private static void setSetterTest() throws Exception{
        ClassOne classOne = new ClassOne();
        classOne.setCount(25);
        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier();
        classOne.setName("Huy");
        Map<String, Class[]> returnedValue;

        returnedValue = reflectionFieldCopier.findSetter(classOne.getClass(), classOne);
        for (Map.Entry<String, Class[]> entry: returnedValue.entrySet()) {
            Method method = classOne.getClass().getMethod("set" + entry.getKey(), entry.getValue());
            if (entry.getValue()[0] == String.class) {
                method.invoke(classOne, entry.getValue()[0].cast("MMM"));
            }
            if (entry.getValue()[0] == Integer.class) {
                method.invoke(classOne, entry.getValue()[0].cast(11));
            }
        }
        System.out.println("Count: " + classOne.getCount());
        System.out.println("Name: " + classOne.getName());
        //SETTER
    }


}
