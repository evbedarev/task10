package homework.bedarev.task_03;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TestTask3 {
    PrintTask3 printTask3 = mock(PrintTask3.class);

    interface ITest {
        public String testMethod(String someString, Integer someNum);
        public String testMethodSerialize(String someString, Integer someNum);
    }

    class TestClass implements ITest {
        PrintTask3 printTask3;

        public TestClass(PrintTask3 printTask3) {
            this.printTask3 = printTask3;
        }

        @Cache(cacheType = "MEMORY", fileNamePrefix = "data", zip = false, identityBy = {String.class, double.class})
        @Override
        public String testMethod(String someString, Integer someNum) {
            System.out.println("Calling method testMethod");
            return someString;
        }

        @Cache(cacheType = "FILE", fileNamePrefix = "data", zip = false, identityBy = {String.class, double.class})
        @Override
        public String testMethodSerialize(String someString, Integer someNum) {
            System.out.println("Calling method testMethodSerialize");
            return someString;
        }

    }

    @Test
    public void testCacheProxyMemory() {
        TestClass testClass = new TestClass(printTask3);
        CacheProxy cacheProxy = new CacheProxy(printTask3, "./");
        ITest testClassProxy = (ITest) cacheProxy.cache(testClass);
        verify(printTask3,times(1)).printMessage("testMethod");
        verify(printTask3,times(2)).printMessage("Find value of fileNamePrefix: data");
        testClassProxy.testMethod("qweqwe", 25);
        verify(printTask3).printMessage("Save method name testMethod");
        testClassProxy.testMethod("qweqwe", 33);
        verify(printTask3,times(2)).printMessage("Save method name testMethod");
        testClassProxy.testMethod("qweqwe", 33);
        verify(printTask3).printMessage("Return method from cache testMethod");
    }
//
    @Test
    public void testCacheProxyFile() {
        TestClass testClass = new TestClass(printTask3);
        CacheProxy cacheProxy = new CacheProxy(printTask3, "./");
        ITest testClassProxy = (ITest) cacheProxy.cache(testClass);
        testClassProxy.testMethodSerialize("qweqwe", 55);
        testClassProxy.testMethodSerialize("qweqwe", 55);
        verify(printTask3).printMessage("Return method from cache testMethodSerialize");
    }
}
