package Task_2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FizzBuzzTest {

    static int NUMBER = 30;
    static Queue<String> toPrinting = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {

        NumberChecker A = new NumberChecker(toPrinting, NUMBER);
        NumberChecker B = new NumberChecker(toPrinting, NUMBER);
        NumberChecker C = new NumberChecker(toPrinting, NUMBER);
        NumberChecker D = new NumberChecker(toPrinting, NUMBER);
        A.start();
        B.start();
        C.start();
        D.start();

        for (int i = 1; i <= NUMBER; i++) {
            A.setNumber(i);
            A.fizz();
            B.buzz();
            C.fizzbuzz();
            D.number();
            while (true) {
                if (A.isProcessed() && B.isProcessed() && C.isProcessed() && D.isProcessed()) {
                    break;
                }
            }
        }
    }
}
