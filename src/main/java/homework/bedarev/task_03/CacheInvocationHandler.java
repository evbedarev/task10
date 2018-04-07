package homework.bedarev.task_03;

import homework.bedarev.task_03.serialize.Serialize;
import homework.bedarev.task_03.serialize.SerializeToFile;
import homework.bedarev.task_03.serialize.SerializeToZip;

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
    private Class[] identityBy;
    private List<CachedResult> cachedResults = new ArrayList<>();
    private Serialize serializeTo;
    private int maxListSize;

    public CacheInvocationHandler(Object obj,
                                  Map<String, Cache> annotationParameters,
                                  PrintTask3 printTask3,
                                  String rootDirectory){
        this.obj = obj;
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
        identityBy = cacheParam.identityBy();
        maxListSize = cacheParam.maxListSize();
        boolean zip = cacheParam.zip();
        String fileNamePrefix = cacheParam.fileNamePrefix();

        checkArgsForNull(args, method.getName());
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

        if (cacheType.equals("FILE") && !zip) {
            String filePath = rootDirectory + fileNamePrefix + ".dat";
            List<CachedResult> localCachedResult = readResultFromFile(new SerializeToFile(), filePath);
            return invokeMethodWithAnnotation(method, args, localCachedResult);
        }

        if (cacheType.equals("FILE") && zip) {
            String filePath = rootDirectory + fileNamePrefix + ".zip";
            List<CachedResult> localCachedResult = readResultFromFile(new SerializeToZip(), filePath);
            return invokeMethodWithAnnotation(method, args, localCachedResult);
        }
        return method.invoke(obj, args);
    }

    private List<CachedResult> readResultFromFile (Serialize serializeTo, String filePath) throws Throwable {
        this.serializeTo = serializeTo;
        addToCache = "FILE";
        filePathSerialize = filePath;
        List<CachedResult> localCachedResult = new ArrayList<>();

        if (new File(filePathSerialize).exists()) {
            localCachedResult.add(serializeTo.desirializeResult(filePathSerialize));
        }
        return localCachedResult;
    }

    private Object invokeMethodWithAnnotation(Method method,
                                              Object[] args,
                                              List<CachedResult> cachedResultList) throws Throwable {
        CheckEqualsMethods equalsMethods = new CheckEqualsMethods(printTask3);
        List<CachedResult> localCachedResult;

        //Try process exception instead return null
        try {
            localCachedResult = cachedResultList.stream()
                    .filter(e -> e.getMethodName().equals(method.getName()))
                    .collect(Collectors.toList());
            return equalsMethods.isEqualMethod(method, args, localCachedResult, identityBy).getReturnValue();
        } catch (NoSuchElementException exception) {
            Object result = isList(method.invoke(obj, args));
            isResultNull(result, method.getName());
            Class[] typeArgs = method.getParameterTypes();
            Class returnType = method.getReturnType();
            addResultToCache(new CachedResult(method.getName(), returnType, result, args, typeArgs));
            printTask3.printMessage("Save method name " + method.getName());
            return result;
        }
    }

    private void addResultToCache (CachedResult cachedResult) throws Throwable {
        if (addToCache.equals("MEMORY")) {
            cachedResults.add(cachedResult);
        }
        if (addToCache.equals("FILE")) {
            serializeTo.serializeResult(filePathSerialize,cachedResult);
        }
    }

    private void checkArgsForNull (Object[] args, String methodName) {
        Arrays.asList(args)
                .forEach( e -> {
                    if (e == null) {
                        throw new NullPointerException("Find Null in arguments of method " + methodName);
                    }
                });
    }

    private void isResultNull (Object result, String methodName) {
        if (result == null) {
            throw new NullPointerException("Can't cache result, because method " +
                    methodName + " return null");
        }
    }

    private Object isList(Object result) {
        if (result != null && result instanceof List && maxListSize != 0) {
            return ((List) result).stream()
                    .limit(maxListSize)
                    .collect(Collectors.toList());
        }
        return result;
    }


}
