package homework.bedarev.task_02;
import org.junit.Test;

import java.util.Map;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

public class TestTask2 {
    private PrintInTask2 printInTask2 = mock(PrintInTask2.class);

    class ClassTwo {
        private String Name;
        private Integer Count;
        public String getName() {
            return Name;
        }

        public Integer getCount() {
            return Count;
        }

        public void setName(String name) {
            Name = name;
        }

        public void setCount(Integer count) {
            Count = count;
        }
    }

    public class ClassOne {
        private String Name;
        private Integer Count;

        public String getName() {
            return Name;
        }

        public Integer getCount() {
            return Count;
        }

        public void setName(String name) {
            Name = name;
        }

        public void setCount(Integer count) {
            Count = count;
        }
    }

    public class ClassBadFields {
        private Integer Name;
        private String Count;

        public Integer getName() {
            return Name;
        }

        public String getCount() {
            return Count;
        }

        public void setName(Integer name) {
            Name = name;
        }

        public void setCount(String count) {
            Count = count;
        }
    }

    private ClassOne classOne = new ClassOne();
    private ClassTwo classTwo = new ClassTwo();
    private ClassBadFields classBadFields = new ClassBadFields();

    @Test
    public void findGetter() throws Exception {
        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier(printInTask2);
        reflectionFieldCopier.findGetter(classOne.getClass(), classOne);
        verify(printInTask2).printMsg("Found 2 getters:");
    }

    @Test
    public void findSetter() throws Exception {
        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier(printInTask2);
        Map<String, Class[]> setterMap;
        setterMap = reflectionFieldCopier.findSetters(classOne.getClass(), classOne);
        assertTrue(setterMap
                        .keySet().contains("Name"));
    }

    @Test
    public void testCopy() {
        classOne.setCount(250);
        classOne.setName("Ford");

        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier(printInTask2);
        reflectionFieldCopier.copy(classOne, classTwo);
        verify(printInTask2,times(1)).printMsg("Found getter getName");
        verify(printInTask2,times(1)).printMsg("Found getter getCount");
        assertEquals(classTwo.getCount().longValue(),250);
        assertEquals(classTwo.getName(),"Ford");
    }

    @Test
    public void wrongClass() {
        classOne.setCount(250);
        classOne.setName("Ford");
        classBadFields.setCount("Wrong");
        classBadFields.setName(0);
        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier(printInTask2);
        reflectionFieldCopier.copy(classOne, classBadFields);
        assertEquals(classBadFields.getName().longValue(),0);
        assertEquals(classBadFields.getCount(),"Wrong");

    }

}
