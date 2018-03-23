package homework.bedarev.task_01;

public class AnnotationFinderApp {

    public static void main(String[] args) throws Exception {
        Print print = new Print();
        FindAnnotation findAnnotation = new FindAnnotation("./src/main/java/homework/common/entity", print);
        findAnnotation.findFile();
    }
}


//        for (Class clazz: parameterTypes) {
//            if (clazz.getSimpleName().equals("String")) {
//                fields.add(" ");
//            }
//            if (clazz.getSimpleName().equals("int")) {
//                fields.add(1);
//            }
//        }

//        Object[] objFields = fields.toArray();
//        Object newObject = constructor.newInstance(objFields);