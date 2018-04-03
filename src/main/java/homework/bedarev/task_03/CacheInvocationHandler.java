package homework.bedarev.task_03;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class CacheInvocationHandler implements InvocationHandler {
    private Object obj;
    private Map<String, Cache> annotationParameters;
    private PrintTask3 printTask3;
    private String addToCache;
    List<CachedResult> cachedResults = new ArrayList<>();
    CheckEqualsMethods equalsMethods = new CheckEqualsMethods();

    public CacheInvocationHandler(Object f1,
                                  Map<String, Cache> annotationParameters,
                                  PrintTask3 printTask3){
        obj = f1;
        this.annotationParameters = annotationParameters;
        this.printTask3 = printTask3;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean containsKey = annotationParameters.containsKey(method.getName());
        String cacheType = annotationParameters.get(method.getName()).cacheType();

        if ((containsKey) && (cacheType.equals("MEMORY"))) {
            addToCache = "MEMORY";
            return invokeMethodWithAnnotationMemory(method, args, cachedResults);
        }

        if ((containsKey) && (cacheType.equals("FILE"))) {
            addToCache = "FILE";
            System.out.println("File");
        }
        return method.invoke(obj, args);
    }

    private Object invokeMethodWithAnnotationMemory (Method method,
                                                     Object[] args,
                                                     List<CachedResult> cachedResultList) throws Throwable {

        List<CachedResult> localCachedResult = new ArrayList<>();
        CachedResult cachedResult = null;

        if (cachedResultList != null) {
            localCachedResult = cachedResultList.stream()
                    .filter( e -> e.getCachedObject().getName().equals(method.getName()))
                    .collect(Collectors.toList());
            cachedResult = equalsMethods.isEqualMethod(method, args, localCachedResult);
        }

        if (cachedResult != null) {
            printTask3.printMessage("Return method from cache " + method.getName());
            return localCachedResult.iterator().next().getReturnValue();
        }

        if (cachedResult == null) {
            Object result = method.invoke(obj, args);
            Class[] typeArgs = method.getParameterTypes();
            Class returnType = method.getReturnType();
            addResultToCache(new CachedResult(method, returnType, result, args, typeArgs));
            printTask3.printMessage("Save method name " + method.getName());
            return result;
        }
        return null;
    }

    private void addResultToCache (CachedResult cachedResult) {
        if (addToCache.equals("MEMORY")) {
            cachedResults.add(cachedResult);
        }

        if (addToCache.equals("FILE")) {
            SerializeAndFind serialize = new SerializeAndFind();
            serialize.serializeResult("./",cachedResult);
        }
    }

    private Object invokeMethodWithAnnotationFile (Method method, Object[] args, String path) {
        List<CachedResult> cachedResults = new ArrayList<>();
        return cachedResults;
    }
}
