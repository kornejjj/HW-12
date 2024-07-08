package Task_2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class FizzBuzzTest {

    static int NUMBER = 30;
    static Queue<String> toPrinting = new ConcurrentLinkedQueue<>();
    static CountDownLatch latch = new CountDownLatch(4);

    public static void main(String[] args) {

        NumberChecker B = new NumberChecker(toPrinting, NUMBER);
        NumberChecker C = new NumberChecker(toPrinting, NUMBER);
        NumberChecker D = new NumberChecker(toPrinting, NUMBER);

        B.start();
        C.start();
        D.start();

        Thread A = new Thread(() -> {
            if (fizz(NUMBER)){
                toPrinting.add("fizz");
            }else {
                toPrinting.add(String.valueOf(NUMBER));
            }
            latch.countDown();
        });
        A.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        toPrinting.forEach(System.out::println);
    }

    public static boolean fizz(int n){
        return n % 3 == 0;
    }
}






/* package Task_2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FizzBuzzTest {

    static int NUMBER = 30;
    static Queue<String> toPrinting = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {

        NumberChecker B = new NumberChecker(toPrinting, NUMBER);
        NumberChecker C = new NumberChecker(toPrinting, NUMBER);
        NumberChecker D = new NumberChecker(toPrinting, NUMBER);

        B.start();
        C.start();
        D.start();

        Thread A = new Thread(() -> {
            if (fizz(NUMBER)){
                toPrinting.add("fizz");
            }else {
                toPrinting.add(String.valueOf(NUMBER));
            }
        });
        A.start();


    }
    public static boolean fizz(int n){
        return n % 3 == 0;
    }
}*/
