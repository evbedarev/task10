package homework.bedarev.task_02;

public class Main {
    public static void main(String[] args) {
        ClassOne classOne = new ClassOne();
        ClassTwo classTwo = new ClassTwo();

        classOne.setName("Madjo");
        classOne.setCount(22);

        ReflectionFieldCopier reflectionFieldCopier = new ReflectionFieldCopier();
        reflectionFieldCopier.copy(classOne, classTwo);

        System.out.println(classTwo.getCount());
        System.out.println(classTwo.getName());

    }
}
