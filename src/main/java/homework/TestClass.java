package homework;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class TestClass {
    public static void main(String[] args) throws Exception{

        Class soughtClass = Class.forName("homework.common.entity.TeslaModel3");
//        Class soughtClass = Class.forName("./src/main/java/homework/common/entity/TeslaModel3");
        System.out.println(soughtClass.getPackage());


        Constructor constructors = soughtClass.getConstructor(new Class[]{String.class, int.class});
        for (Class par: constructors.getParameterTypes()) {
            System.out.println(par.toString());
        }

        Object createdObj = constructors.newInstance("Tesla", 10);
        System.out.println(createdObj.getClass().toString());

        Field modifiersField = createdObj.getClass().getDeclaredField("number");
        modifiersField.setAccessible(true);
        System.out.println(modifiersField.getModifiers());
        modifiersField.set(createdObj,"changed");
        System.out.println(modifiersField.get(createdObj));



        for (Field field:soughtClass.getFields()) {
            System.out.println(field.toString());
        }

        System.out.println("========Methods========");
        Method[] methods = soughtClass.getMethods();
        for (Method meth: methods) {
//            if (meth.getName().contains("set")) {
//                Method method = soughtClass.getDeclaredMethod("setNumber", new Class[]{String.class});
//                method.invoke(createdObj,"Hellow");
//            }
            findReflectable(meth);
        }

//        System.out.println("========Annotations class======");
//        Annotation[] annotations = soughtClass.getAnnotations();
//        for (Annotation annotation: annotations) {
//            if(annotation instanceof Reflectable) {
//                Reflectable mAnnotation = (Reflectable) annotation;
//                System.out.println(mAnnotation.name());
//                System.out.println(mAnnotation.value());
//            }
//        }

//        System.out.println("========Annotations Parameter======");
//        Method method =
//                soughtClass.getMethod("setNumber", new Class[]{String.class});
//        Annotation[][] paramAnnotations = method.getParameterAnnotations();
//        Class[] paramTypes = method.getParameterTypes();
//
//        int i = 0;
//        for (Annotation[] annotationss: paramAnnotations) {
//            Class paramType = paramTypes[i++];
//
//            for (Annotation annotatio: annotationss) {
//                if (annotatio instanceof Reflectable) {
//                    Reflectable refAnn = (Reflectable) annotatio;
//                    System.out.println("param: " + paramType.getName());
//                    System.out.println("name: " + refAnn.name());
//                    System.out.println("value: " + refAnn.value());
//                }
//            }
//        }
    }

    private static void findReflectable(Method method) {
        System.out.println("========Annotations Method========");
        Annotation[] methAnnotation = method.getAnnotations();
        for (Annotation methodA: methAnnotation) {
            if (methodA instanceof Reflectable) {
                Reflectable refAnnMethod = (Reflectable) methodA;
                System.out.println("method: " + method.getName());
                System.out.println("name: " + refAnnMethod.name());
                System.out.println("value: " + refAnnMethod.value());
            }
        }
    }
}
