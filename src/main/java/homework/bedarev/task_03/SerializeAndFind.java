package homework.bedarev.task_03;

import java.io.*;

public class SerializeAndFind {

    public CachedResult desirializeFile (String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            CachedResult cachedResult = (CachedResult) in.readObject();
            return cachedResult;
        } catch (ClassNotFoundException | IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public boolean serializeResult (String filePath, CachedResult cachedResult) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            cachedResult.writeExternal(out);
            out.close();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
