package homework.bedarev.task_03.serialize;

import homework.bedarev.task_03.CachedResult;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SerializeToZip implements Serialize {

    @Override
    public CachedResult desirializeResult (String filePath) throws ClassNotFoundException, IOException {
        try (ObjectInputStream in = new ObjectInputStream(
                new GZIPInputStream(new FileInputStream(filePath))
        )) {
            CachedResult cachedResult = new CachedResult();
            cachedResult.readExternal(in);
            return cachedResult;
        } catch (ClassNotFoundException exception) {
            throw new ClassNotFoundException("Could not find Class in file: " + filePath
                    + " during desirialization");
        } catch (IOException exception) {
            throw new IOException("Error while reading file " + filePath + " during desirialization");
        }
    }

    @Override
    public void serializeResult (String filePath, CachedResult cachedResult) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new GZIPOutputStream(
                        new FileOutputStream(filePath)))) {

            cachedResult.writeExternal(out);
            out.close();
        } catch (IOException exception) {
            throw new IOException("Error while writing file " + filePath + " during sirialization");
        }
    }
}
