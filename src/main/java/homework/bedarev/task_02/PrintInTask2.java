package homework.bedarev.task_02;

public class PrintInTask2 {
    boolean debug;

    public PrintInTask2() {
        debug = false;
    }

    public PrintInTask2(boolean debug) {
        this.debug = debug;
    }

    public void printMsg(String message) {
        if (debug) {
            System.out.println(message);
        }
    }
}
