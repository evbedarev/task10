package homework.bedarev.task_03.serialize;

import homework.bedarev.task_03.CachedResult;

import java.io.*;

public class SerializeToFile implements Serialize {

    @Override
    public CachedResult desirializeResult (String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            CachedResult cachedResult = new CachedResult();
            cachedResult.readExternal(in);
            return cachedResult;
        } catch (ClassNotFoundException | IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
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
