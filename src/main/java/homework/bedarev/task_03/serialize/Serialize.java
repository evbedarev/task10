package homework.bedarev.task_03.serialize;

import homework.bedarev.task_03.CachedResult;

public interface Serialize {
    CachedResult desirializeResult (String filePath);
    boolean serializeResult (String filePath, CachedResult cachedResult);
}
