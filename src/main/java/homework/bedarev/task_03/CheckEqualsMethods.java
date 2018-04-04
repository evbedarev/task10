package homework.bedarev.task_03;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class CheckEqualsMethods {
    PrintTask3 printTask3;

    public CheckEqualsMethods(PrintTask3 printTask3) {
        this.printTask3 = printTask3;
    }

    public CachedResult isEqualMethod(Method method, Object[] args,
                                      List<CachedResult> localCachedResult,
                                      Class[] identityBy) throws NoSuchElementException {

        List<StorageValue> valuesFromMethod = convertToList(method.getParameterTypes(), args);

        for (CachedResult cachedResult: localCachedResult) {
            boolean equalsMethods = false;
            if (isEqualArgsTypes(cachedResult.getTypeArgs(), method.getParameterTypes())) {
                List<StorageValue> valuesFromCache = convertToList(cachedResult.getTypeArgs(), cachedResult.getArgs());
                equalsMethods = isEqualArgsValues(valuesFromMethod, valuesFromCache);
            }

            if (equalsMethods) {
                printTask3.printMessage("Return method from cache " + method.getName());
                return cachedResult;
            }
        }
        throw new NoSuchElementException("Nothing to return in method isEqualMethod");
    }

    private List<StorageValue> convertToList (Class[] clazz, Object[] args) {
        List<StorageValue> storageValueList = new ArrayList<>();
        for (int index=0; index < args.length; index++) {
            storageValueList.add(new StorageValue(clazz[index],args[index]));
        }
        return storageValueList;
    }

    private boolean isEqualArgsValues(List<StorageValue> valuesFromMethod,
                                      List<StorageValue> valuesFromCache) {

        for (StorageValue storageValue: valuesFromCache) {
            if (!valuesFromMethod.contains(storageValue)) {
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
