package homework.bedarev.task_01;

public class AnnotationFinderApp {

    public static void main(String[] args) throws Exception {
        Print print = new Print();
        FindAnnotation findAnnotation = new FindAnnotation("./src/main/java/homework/common/entity", print);
        findAnnotation.findFile();
    }
}