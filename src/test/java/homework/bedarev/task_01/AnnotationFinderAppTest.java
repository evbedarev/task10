package homework.bedarev.task_01;

import homework.bedarev.task_01.FindAnnotation;
import homework.bedarev.task_01.Print;
import org.junit.Test;
import static org.mockito.Mockito.*;


public class AnnotationFinderAppTest {
    private Print print = mock(Print.class);
    private String PATH_TO_FILE = "./src/main/java/homework/common/entity";


    @Test
    public void TestFileExists() throws Exception {
        FindAnnotation findAnnotation = new FindAnnotation(PATH_TO_FILE + "/www", print);
        findAnnotation.findFile();
        verify(print,times(1)).printMsg("No files found in directory: ./src/main/java/homework/common/entity/www");
    }

    @Test
    public void TestFindAnnotation() throws Exception {
        FindAnnotation findAnnotation = new FindAnnotation(PATH_TO_FILE, print);
        findAnnotation.findFile();
        verify(print,times(1)).printMsg("Class name: TeslaModelHydro");
        verify(print,times(1)).printMsg("Version: 9001");
        verify(print,times(1)).printMsg("Name of method with ExperimentalFeature :fuelWithWater");
        verify(print,times(1)).printMsg("Name of Field with ExperimentalFeature :codename");
    }
}
