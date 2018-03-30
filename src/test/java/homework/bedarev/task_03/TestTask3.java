package homework.bedarev.task_03;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TestTask3 {
    PrintTask3 printTask3 = mock(PrintTask3.class);

    interface ITest {
        public String testMethod(String someString);
    }

    class TestClass implements ITest {
        PrintTask3 printTask3;

        public TestClass(PrintTask3 printTask3) {
            this.printTask3 = printTask3;
        }

        @Cache(cacheType = "FILE", fileNamePrefix = "data", zip = true, identityBy = {String.class, double.class})
        @Override
        public String testMethod(String someString) {

            return someString;
        }
    }

    @Test
    public void testCacheProxy() {
        TestClass testClass = new TestClass(printTask3);
        CacheProxy cacheProxy = new CacheProxy(printTask3);
        ITest testClassProxy = (ITest) cacheProxy.cache(testClass);
//        System.out.println(testClassProxy.testMethod("Im Test Method"));
        verify(printTask3,times(1)).printMessage("testMethod");
//        testClassProxy.testMethod("qwe");
//        verify(test).testMethod("qwe");
//        cacheProxy.readAnnotations(testClass);
        verify(printTask3).printMessage("@homework.bedarev.task_03.Cache(zip=true, maxCountList=0, " +
                "fileNamePrefix=data, cacheType=FILE, identityBy=[class java.lang.String, double])");
    }

}
