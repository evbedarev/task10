package homework.bedarev.task_03;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class CheckEqualsMethods {
    private PrintTask3 printTask3;

    public CheckEqualsMethods(PrintTask3 printTask3) {
        this.printTask3 = printTask3;
    }

    public CachedResult isEqualMethod(Method method, Object[] args,
                                      List<CachedResult> localCachedResult,
                                      Class[] identityBy) throws NoSuchElementException {
        for (CachedResult cachedResult: localCachedResult) {
            boolean equalsMethods = false;
            if (isEqualArgsTypes(cachedResult.getTypeArgs(), method.getParameterTypes())) {
                List<StorageValue> valuesFromMethod = convertToList(method.getParameterTypes(),
                        args, identityBy);
                List<StorageValue> valuesFromCache = convertToList(cachedResult.getTypeArgs(),
                        cachedResult.getArgs(), identityBy);
                equalsMethods = isEqualArgsValues(valuesFromMethod, valuesFromCache);
            }

            if (equalsMethods) {
                printTask3.printMessage("Return method from cache " + method.getName());
                return cachedResult;
            }
        }
        throw new NoSuchElementException("Nothing to return in method isEqualMethod");
    }

    private List<StorageValue> convertToList (Class[] clazz, Object[] args, Class[] identityBy) {
        List<StorageValue> storageValueList = new ArrayList<>();
        for (int index=0; index < args.length; index++) {
            storageValueList.add(new StorageValue(clazz[index],args[index]));
        }

        if (identityBy.length > 0) {
            return selectorValues(identityBy, storageValueList);
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

    private List<StorageValue> selectorValues (Class[] clazz,
                                               List<StorageValue> listValues) {
        List<StorageValue> resultList = new ArrayList<>();
        for (int i=0; i < clazz.length; i++) {
            int value = i;
            listValues
                    .stream()
                    .filter( e -> e.getClazz().equals(clazz[value]))
                    .forEach(e -> {
                        printTask3.printMessage("One of identity method is Class: " + e.getClazz().toString());
                        resultList.add(e);
                    });
        }
        return resultList;
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
