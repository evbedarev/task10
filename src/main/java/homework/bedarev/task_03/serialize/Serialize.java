package homework.bedarev.task_03.serialize;

import homework.bedarev.task_03.CachedResult;

import java.io.IOException;

public interface Serialize {
    CachedResult desirializeResult (String filePath) throws ClassNotFoundException, IOException;
    void serializeResult (String filePath, CachedResult cachedResult) throws IOException;
}
