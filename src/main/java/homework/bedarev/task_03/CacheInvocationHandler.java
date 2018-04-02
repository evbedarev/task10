package homework.bedarev.task_03;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CacheInvocationHandler implements InvocationHandler {
    private Object obj;
    private Map<String, Cache> annotationParameters;
    private PrintTask3 printTask3;
    List<CachedResult> cachedResults = new ArrayList<>();

    public CacheInvocationHandler(Object f1,
                                  Map<String, Cache> annotationParameters,
                                  PrintTask3 printTask3){
        obj = f1;
        this.annotationParameters = annotationParameters;
        this.printTask3 = printTask3;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //verify annotations
        if (annotationParameters.containsKey(method.getName())) {
            return invokeMethodWithAnnotationMemory(method, args);
        }
        //check how cache this method
        //check is cached result of method
        //true ? return cached value : cache new result of method
        System.out.println("I void method and take args, and i cant take args");
        return method.invoke(obj, args);
    }

    private Object invokeMethodWithAnnotationMemory (Method method,
                                                    Object[] args) throws Throwable {

        List<CachedResult> localCachedResult = new ArrayList<>();
        CachedResult cachedResult;
        if (cachedResults != null) {
            localCachedResult = cachedResults.stream()
                    .filter( e -> e.getCachedObject().getName().equals(method.getName()))
                    .collect(Collectors.toList());
        }

        cachedResult = isEqualMethod(method, args, localCachedResult);


        if (cachedResult != null) {
            printTask3.printMessage("Return method from cache " + method.getName());
            return localCachedResult.iterator().next().getReturnValue();
        }

        if (cachedResult == null) {
            Object result = method.invoke(obj, args);
            Class[] typeArgs = method.getParameterTypes();
            Class returnType = method.getReturnType();

            cachedResults.add(new CachedResult(method, returnType, result, args, typeArgs));
            printTask3.printMessage("Save method name " + method.getName());
            return result;
        }
        return null;
    }

    private CachedResult isEqualMethod(Method method, Object[] args,  List<CachedResult> localCachedResult) {
        CachedResult result = null;
        for (CachedResult cachedResult: localCachedResult) {
            if (isEqualClasses(cachedResult.getTypeArgs(), method.getParameterTypes())) {
                result = cachedResult;
            }
        }
        return result;
    }

    private boolean isEqualClasses (Class[] fromCache, Class[] fromMethod) {
        List<Class> listFromCache = Arrays.asList(fromCache);
        List<Class> listFromMethod = Arrays.asList(fromMethod);
        for (Class clazz: listFromMethod) {
            if (!listFromCache.contains(clazz)) {
                return false;
            }
        }
        return true;
    }


}
