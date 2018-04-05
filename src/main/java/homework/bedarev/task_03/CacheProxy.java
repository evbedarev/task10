package homework.bedarev.task_03;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy {
    PrintTask3 printTask3;
    String rootFolderPath;

    public CacheProxy(PrintTask3 printTask3, String rootFolderPath) {
        this.printTask3 = printTask3;
        this.rootFolderPath = rootFolderPath;
    }

    public Object cache(Object service) {
        File rootDir = new File(rootFolderPath);

        if (!rootDir.exists()) {
            System.out.println("Please verify root directory. No such directory...");
        }

        InvocationHandler handler = new CacheInvocationHandler(service,
                readAnnotations(service),
                printTask3, rootFolderPath);

        if (rootDir.exists()) {
            Object objectProxy = Proxy.newProxyInstance(service.getClass().getClassLoader(),
                    service.getClass().getInterfaces(),
                    handler);

            Arrays.stream(objectProxy
                    .getClass()
                    .getMethods())
                    .forEach(e -> printTask3.printMessage(e.getName()));

            return objectProxy;
        }
        return null; //Bad, refactor
    }

    public Map<String, Cache> readAnnotations (Object service) {
        Map<String, Cache> annotationParameters = new HashMap<>();
        Class classOfService = service.getClass();
        Method[] methods = classOfService.getMethods();
        Arrays.stream(methods)
                .forEach(e -> {
                    if (e.isAnnotationPresent(Cache.class)) {
                        Cache annotation = e.getAnnotation(Cache.class);
                        printTask3.printMessage("Find value of fileNamePrefix: " + annotation.fileNamePrefix());
                        annotationParameters.put(e.getName(), annotation);
                    }
                });
        return annotationParameters;
    }
}
