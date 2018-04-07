package homework.bedarev.task_03;

public class PrintTask3 {
    boolean debug;

    public PrintTask3(boolean debug) {
        this.debug = debug;
    }

    public PrintTask3() {
    }

    public void printMessage(String message) {
        if (debug) {
            System.out.println(message);
        }
    }
}
