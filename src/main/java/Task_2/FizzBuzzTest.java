package Task_2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

public class FizzBuzzTest {

    static Queue<String> toPrinting = new ConcurrentLinkedQueue<>();
    static final int LIMIT = 50;

    public static void main(String[] args) {

        for (int number = 1; number <= LIMIT; number++) {
            final int currentNumber = number;
            CountDownLatch latch = new CountDownLatch(4);

            Thread fizzThread = new Thread(() -> {
                if (fizz(currentNumber)) {
                    toPrinting.add("fizz");
                }
                latch.countDown();
            });

            Thread buzzThread = new Thread(() -> {
                if (buzz(currentNumber)) {
                    toPrinting.add("buzz");
                }
                latch.countDown();
            });

            Thread fizzBuzzThread = new Thread(() -> {
                if (fizzBuzz(currentNumber)) {
                    toPrinting.add("fizzbuzz");
                }
                latch.countDown();
            });

            Thread numberThread = new Thread(() -> {
                if (!fizz(currentNumber) && !buzz(currentNumber) && !fizzBuzz(currentNumber)) {
                    toPrinting.add(String.valueOf(currentNumber));
                }
                latch.countDown();
            });

            fizzThread.start();
            buzzThread.start();
            fizzBuzzThread.start();
            numberThread.start();

            try {
                latch.await();
            } catch (InterruptedException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }

            toPrinting.forEach(System.out::println);
            toPrinting.clear();
        }
    }

    public static boolean fizz(int n) {
        return n % 3 == 0 && n % 5 != 0;
    }

    public static boolean buzz(int n) {
        return n % 5 == 0 && n % 3 != 0;
    }

    public static boolean fizzBuzz(int n) {
        return n % 3 == 0 && n % 5 == 0;
    }
}

