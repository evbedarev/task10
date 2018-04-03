package homework.bedarev.task_03;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class CacheInvocationHandler implements InvocationHandler {
    private Object obj;
    private Map<String, Cache> annotationParameters;
    private PrintTask3 printTask3;
    private String addToCache;
    private String rootDirectory;
    private String filePathSerialize;
    SerializeAndFind serialize = new SerializeAndFind();

    List<CachedResult> cachedResults = new ArrayList<>();
    CheckEqualsMethods equalsMethods = new CheckEqualsMethods();

    public CacheInvocationHandler(Object f1,
                                  Map<String, Cache> annotationParameters,
                                  PrintTask3 printTask3,
                                  String rootDirectory){
        obj = f1;
        this.annotationParameters = annotationParameters;
        this.printTask3 = printTask3;
        this.rootDirectory = rootDirectory;
        this.rootDirectory = rootDirectory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean containsKey = annotationParameters.containsKey(method.getName());
        Cache cacheParam = annotationParameters.get(method.getName());
        String cacheType = cacheParam.cacheType();
        String fileNamePrefix = cacheParam.fileNamePrefix();

        if (!containsKey) {
            return method.invoke(obj, args);
        }

        if (fileNamePrefix.equals("")) {
            fileNamePrefix = method.getName();
        }

        if (cacheType.equals("MEMORY")) {
            addToCache = "MEMORY";
            return invokeMethodWithAnnotation(method, args, cachedResults);
        }

        if (cacheType.equals("FILE")) {
            addToCache = "FILE";
            filePathSerialize = rootDirectory + fileNamePrefix + ".dat";
            List<CachedResult> localCachedResult = new ArrayList<>();
            File fileSerialize = new File(rootDirectory + fileNamePrefix + ".dat");

            if (fileSerialize.exists()) {
                localCachedResult.add(serialize.desirializeFile(filePathSerialize));
            }

            return invokeMethodWithAnnotation(method, args, localCachedResult);
        }

        return method.invoke(obj, args);
    }

    private Object invokeMethodWithAnnotation(Method method,
                                              Object[] args,
                                              List<CachedResult> cachedResultList) throws Throwable {

        List<CachedResult> localCachedResult = new ArrayList<>();
        CachedResult cachedResult = null;

        if (cachedResultList != null) {
            localCachedResult = cachedResultList.stream()
                    .filter( e -> e.getMethodName().equals(method.getName()))
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

            addResultToCache(new CachedResult(method.getName(), returnType, result, args, typeArgs));
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
            System.out.println(cachedResult);
            serialize.serializeResult(filePathSerialize,cachedResult);
        }
    }
}
