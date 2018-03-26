package homework.bedarev.task_02;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Start {
    public static void main(String[] args) throws Exception{
//        ClassOne classOne = new ClassOne();
//        classOne.setCount(25);
//        classOne.setName("Huy");
//        Map<String, Map<Type, Object>> returnedValue;
//        Map<Type, Object> valueMap;
//        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier();

//        //GETTER
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
        setSetter();

    }

    /*
    *   @param to - Object to copy
    *   @param nameMethod - getter method
    *   @param typeParam - type
    *
     */

    private static void setSetter() throws Exception{
        ClassOne classOne = new ClassOne();
        classOne.setCount(25);
        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier();
        classOne.setName("Huy");
        Map<String, Class[]> returnedValue;

        returnedValue = reflectionFieldCopier.findSetter(classOne.getClass(), classOne);
        for (Map.Entry<String, Class[]> entry: returnedValue.entrySet()) {
            Method method = classOne.getClass().getMethod("set" + entry.getKey(), entry.getValue());
            if (entry.getValue()[0] == String.class) {
                method.invoke(classOne, entry.getValue()[0].cast("1"));
            }
            if (entry.getValue()[0] == Integer.class) {
                method.invoke(classOne, entry.getValue()[0].cast(11));
            }
        }
        System.out.println(classOne.getCount());
        System.out.println(classOne.getName());
        //SETTER

    }
}
