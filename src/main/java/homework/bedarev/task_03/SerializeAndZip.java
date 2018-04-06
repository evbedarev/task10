package homework.bedarev.task_03;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SerializeAndZip {

    public CachedResult desirializeFile (String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            CachedResult cachedResult = new CachedResult();
            cachedResult.readExternal(in);
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

    public CachedResult desirializeFileZip (String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(
                new GZIPInputStream(new FileInputStream(filePath))
        )) {
            CachedResult cachedResult = new CachedResult();
            cachedResult.readExternal(in);
            return cachedResult;
        } catch (ClassNotFoundException | IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public boolean serializeResultZip (String filePath, CachedResult cachedResult) {

        try (ObjectOutputStream out = new ObjectOutputStream(
                new GZIPOutputStream(
                        new FileOutputStream(filePath)))) {

            cachedResult.writeExternal(out);
            out.close();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

}
