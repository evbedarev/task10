package homework.bedarev.task_03;

import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class CheckEqualsMethods {

    public CachedResult isEqualMethod(Method method, Object[] args, List<CachedResult> localCachedResult) {
        CachedResult methodFromCache = null;
        boolean equalsMethods = false;

        for (CachedResult cachedResult: localCachedResult) {
            if (isEqualArgsTypes(cachedResult.getTypeArgs(), method.getParameterTypes())) {
                methodFromCache = cachedResult;
                equalsMethods = isEqualArgsValues(method, methodFromCache, args);
            }

            if (equalsMethods == true) {
                return methodFromCache;
            }
        }
        return  null;
    }

    private boolean isEqualArgsValues(Method method,
                                      CachedResult methodFromCache,
                                      Object[] args) {

        for (int i = 0; i < args.length; i++) {
            Class clazz = method.getParameterTypes()[i];
            System.out.println(clazz.toString());
            Object argFromCache = methodFromCache.getArgs()[i];
            if (!clazz.cast(args[i]).equals(clazz.cast(argFromCache))) {
                System.out.println("Exit from cicle");
                return false;
            }
        }
        return true;
    }

    private boolean isEqualArgsTypes(Class[] fromCache, Class[] fromMethod) {
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
