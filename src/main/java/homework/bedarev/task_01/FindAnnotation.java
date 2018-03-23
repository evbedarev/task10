package homework.bedarev.task_01;

import homework.common.annotation.ExperimentalFeature;
import homework.common.annotation.Prototype;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class FindAnnotation {
    private String rootPath;
    public Print print;

    public FindAnnotation(String rootPath, Print print) {
        this.rootPath = rootPath;
        this.print = print;
    }

    public void findFile() throws Exception {
        final String pathPkg = rootPath.split("/java/")[1]
                .replace("/",".") + ".";
        System.out.println(this.getClass().getPackage().getName());
        File javaClass = new File (rootPath);
        File[] files = javaClass.listFiles();
        if (files != null) {
            Arrays.stream(files)
                    .filter(elm -> elm.getName().contains(".java"))
                    .forEach(elm -> {
                        findAnnotationClass(pathPkg
                                + elm.getName().split(".java")[0]);
                    });
        } else {
            print.printMsg("No files found in directory: " + rootPath);
        }
    }

    private void findAnnotationClass(String nameClass) {
        try {
            Class soughtClass = Class.forName(nameClass);
            Annotation[] annotations = soughtClass.getAnnotations();
            for (Annotation annotation: annotations) {
                if (annotation instanceof Prototype) {
                    Prototype prototype = (Prototype) annotation;
                    print.printMsg("Class name: " + soughtClass.getSimpleName());
                    print.printMsg("Version: " + prototype.version());
                    findMethods(soughtClass);
                    findField(soughtClass);
                }
            }
        } catch (ClassNotFoundException exception) {
            print.printMsg("Not found class in file, verify class in file exists.");
            exception.printStackTrace();
        }
    }

    private void findField(Class clazz) {
        iterateFields(clazz.getFields(), print);
        iterateFields(clazz.getDeclaredFields(), print);
    }

    private void iterateFields(Field[] fields, Print print) {
        for (Field field: fields) {
            field.setAccessible(true);
            findAnnotationsField(field);
        }
    }

    private void findAnnotationsField(Field field) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation: annotations) {
            if (annotation instanceof ExperimentalFeature) {
                print.printMsg("Name of Field with ExperimentalFeature :" + field.getName());
            }
        }
    }

    private void findMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method: methods) {
            findAnnotationsInMethod(method);
        }
    }

    private void findAnnotationsInMethod(Method method){
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation: annotations) {
            if (annotation instanceof ExperimentalFeature) {
                print.printMsg("Name of method with ExperimentalFeature :" + method.getName());
            }
        }
    }
}
