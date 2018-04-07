package homework.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TetsOPAth {
    public static void main(String[] args) throws IOException{
        Path path = Paths.get("./src");
        File dir = new File(new File(".").getAbsolutePath());
        System.out.println(dir.getCanonicalFile());
    }
}
