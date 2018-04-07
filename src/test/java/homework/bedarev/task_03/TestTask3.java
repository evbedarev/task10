package homework.bedarev.task_03;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestTask3 {
    private PrintTask3 printTask3 = mock(PrintTask3.class);
    private TestClass testClass;
    private ITest testClassProxy;
    private CacheProxy cacheProxy;
    private static final String FILE_SERIALIZE = "data";
    private static final String ROOT_PATH = "./";
    private static final String FILE_IDENTITY_METHOD = "method";
    private static final String FILE_ZIP = "ziptest";
    private static final String FILE_LIST = "listTest";

    interface ITest {
        String testMethod(String someString, Integer someNum);
        String testMethodSerialize(String someString, Integer someNum);
        String testMethodIdentityMethod(String someString, Integer someNum);
        Integer testMethodNullResult(Integer firstInt, Integer secondInt);
        String testMethodZip(String someString, Integer someNum);
        List<String> testMethodList(String element);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void createClasses() {
        testClass = new TestClass(printTask3);
        cacheProxy = new CacheProxy(printTask3, ROOT_PATH);
        testClassProxy = (ITest) cacheProxy.cache(testClass);
    }

    @After
    public void deleteFileAfterTest() {
        File file = new File(ROOT_PATH + FILE_SERIALIZE + ".dat");
        file.delete();
        file = new File(ROOT_PATH + FILE_IDENTITY_METHOD + ".dat");
        file.delete();
        file = new File (ROOT_PATH + FILE_ZIP + ".zip");
        file.delete();
        file = new File (ROOT_PATH + FILE_LIST + ".zip");
        file.delete();
    }

    class TestClass implements ITest {
        PrintTask3 printTask3;

        public TestClass(PrintTask3 printTask3) {
            this.printTask3 = printTask3;
        }

        @Cache(cacheType = "MEMORY",
                fileNamePrefix = "data",
                zip = false,
                identityBy = {String.class, Integer.class})

        @Override
        public String testMethod(String someString, Integer someNum) {
            return someString;
        }

        @Cache(cacheType = "FILE",
                fileNamePrefix = FILE_SERIALIZE,
                zip = false,
                identityBy = {String.class, Integer.class})

        @Override
        public String testMethodSerialize(String someString, Integer someNum) {
            return someString;
        }

        @Cache(cacheType = "FILE",
                fileNamePrefix = FILE_IDENTITY_METHOD,
                zip = false,
                identityBy = {String.class})

        @Override
        public String testMethodIdentityMethod(String someString, Integer someNum) {
            return someString;
        }

        @Cache(cacheType = "FILE",
                fileNamePrefix = "1234",
                zip = false,
                identityBy = {Integer.class})

        @Override
        public Integer testMethodNullResult(Integer firstInt, Integer secondInt) {
            return null;
        }

        @Cache(cacheType = "FILE",
                fileNamePrefix = FILE_ZIP,
                zip = true,
                identityBy = {})

        @Override
        public String testMethodZip(String someString, Integer someNum) {
            return someString;
        }

        @Cache(cacheType = "FILE",
                fileNamePrefix = FILE_LIST,
                zip = true,
                identityBy = {},
                maxListSize = 10)

        @Override
        public List<String> testMethodList(String element) {
            List<String> testList = new ArrayList<>();
            for (int i=0; i < 100; i++) {
                testList.add(element + "__" + i);
            }
            return testList;
        }
    }

    @Test
    public void testCacheProxyMemory() {
        testClassProxy.testMethod("qweqwe", 25);
        testClassProxy.testMethod("qweqwe", 33);
        testClassProxy.testMethod("qweqwe", 33);
        verify(printTask3,times(2)).printMessage("Save method name testMethod");
        verify(printTask3,times(1)).printMessage("testMethod");
        verify(printTask3,times(2)).printMessage("Find value of fileNamePrefix: data");
        verify(printTask3).printMessage("Return method from cache testMethod");
    }

    @Test
    public void testCacheProxySerialize() {
        testClassProxy.testMethodSerialize("qweqwe", 55);
        testClassProxy.testMethodSerialize("qweqwe", 55);
        testClassProxy.testMethodSerialize("qweqwe", 44);
        verify(printTask3).printMessage("Return method from cache testMethodSerialize");
        verify(printTask3, times(2)).printMessage("Save method name testMethodSerialize");
        assertTrue(new File(ROOT_PATH + FILE_SERIALIZE + ".dat").exists());
    }

    @Test
    public void testSelectorValues () {
        testClassProxy.testMethodSerialize("qweqwe", 55);
        testClassProxy.testMethodSerialize("qweqwe", 55);
        verify(printTask3,times(2))
                .printMessage("One of identity method is Class: class java.lang.String");
        verify(printTask3,times(2))
                .printMessage("One of identity method is Class: class java.lang.Integer");
    }

    @Test
    public void testIdentityMethod () {
        testClassProxy.testMethodIdentityMethod("first", 22);
        testClassProxy.testMethodIdentityMethod("first", 55);
        verify(printTask3).printMessage("Return method from cache testMethodIdentityMethod");
        assertTrue(new File(ROOT_PATH + FILE_IDENTITY_METHOD + ".dat").exists());
    }

    @Test
    public void nullInArgs() {
        exception.expect(NullPointerException.class);
        testClassProxy.testMethodIdentityMethod(null, 22);
    }

    @Test
    public void nullInResult() {
        exception.expect(NullPointerException.class);
        testClassProxy.testMethodNullResult(1, 1);
    }

    @Test
    public void testZip() {
        testClassProxy.testMethodZip("zip", 22);
        assertTrue(new File("./ziptest.zip").exists());
        testClassProxy.testMethodZip("zip", 22);
    }

    @Test
    public void testList() {
        List<String> testList = testClassProxy.testMethodList("hellow");
        assertTrue(testList.size() == 10);
    }
}
