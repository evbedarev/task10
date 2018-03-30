package homework.bedarev.task_03;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CacheProxy {
    PrintTask3 printTask3;

    public CacheProxy(PrintTask3 printTask3) {
        this.printTask3 = printTask3;
    }

    public Object cache(Object service) {
        Object objectProxy = Proxy.newProxyInstance(service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new SomeInvocationHandler(service));

        Arrays.stream(objectProxy
                .getClass()
                .getMethods())
                .forEach(e -> printTask3.printMessage(e.getName()));

        readAnnotations(service);

        return objectProxy;
    }

    public void readAnnotations(Object service) {
        Class classOfService = service.getClass();
        Method[] methods = classOfService.getMethods();
        Arrays.stream(methods)
                .forEach(e -> {
                    Annotation[] annotations = e.getAnnotations();
                    Arrays.stream(annotations)
                            .forEach(a -> {
                                printTask3.printMessage(a.toString());
                                System.out.println("Found annotations: " + a.toString());
                            });
                });
    }
}
