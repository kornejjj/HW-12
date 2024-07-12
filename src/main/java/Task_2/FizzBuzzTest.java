package Task_2;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FizzBuzzTest {

    static Queue<String> toPrinting = new ConcurrentLinkedQueue<>();
    static final int LIMIT = 50;
    static AtomicInteger currentNumber = new AtomicInteger(1);

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(4);

        CountDownLatch finalLatch = latch;
        Thread fizzThread = new Thread(() -> {
            while (true) {
                int number = currentNumber.get();
                if (number > LIMIT) break;
                if (fizz(number) && currentNumber.compareAndSet(number, number + 1)) {
                    toPrinting.add("fizz");
                    finalLatch.countDown();
                }
            }
        });

        CountDownLatch finalLatch1 = latch;
        Thread buzzThread = new Thread(() -> {
            while (true) {
                int number = currentNumber.get();
                if (number > LIMIT) break;
                if (buzz(number) && currentNumber.compareAndSet(number, number + 1)) {
                    toPrinting.add("buzz");
                    finalLatch1.countDown();
                }
            }
        });

        CountDownLatch finalLatch2 = latch;
        Thread fizzBuzzThread = new Thread(() -> {
            while (true) {
                int number = currentNumber.get();
                if (number > LIMIT) break;
                if (fizzBuzz(number) && currentNumber.compareAndSet(number, number + 1)) {
                    toPrinting.add("fizzbuzz");
                    finalLatch2.countDown();
                }
            }
        });

        CountDownLatch finalLatch3 = latch;
        Thread numberThread = new Thread(() -> {
            while (true) {
                int number = currentNumber.get();
                if (number > LIMIT) break;
                if (!fizz(number) && !buzz(number) && !fizzBuzz(number) && currentNumber.compareAndSet(number, number + 1)) {
                    toPrinting.add(String.valueOf(number));
                    finalLatch3.countDown();
                }
            }
        });

        fizzThread.start();
        buzzThread.start();
        fizzBuzzThread.start();
        numberThread.start();

        while (currentNumber.get() <= LIMIT) {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            toPrinting.forEach(System.out::println);
            toPrinting.clear();
            latch = new CountDownLatch(4);
        }

        try {
            fizzThread.join();
            buzzThread.join();
            fizzBuzzThread.join();
            numberThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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


/*import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FizzBuzzTest {

    static Queue<String> toPrinting = new ConcurrentLinkedQueue<>();
    static final int LIMIT = 50;
    static AtomicInteger currentNumber = new AtomicInteger(1);

    public static void main(String[] args) {

        while (currentNumber.get() <= LIMIT) {
            CountDownLatch latch = new CountDownLatch(4);
            final int number = currentNumber.getAndIncrement();

            Thread fizzThread = new Thread(() -> {
                if (fizz(number)) {
                    toPrinting.add("fizz");
                }
                latch.countDown();
            });

            Thread buzzThread = new Thread(() -> {
                if (buzz(number)) {
                    toPrinting.add("buzz");
                }
                latch.countDown();
            });

            Thread fizzBuzzThread = new Thread(() -> {
                if (fizzBuzz(number)) {
                    toPrinting.add("fizzbuzz");
                }
                latch.countDown();
            });

            Thread numberThread = new Thread(() -> {
                if (!fizz(number) && !buzz(number) && !fizzBuzz(number)) {
                    toPrinting.add(String.valueOf(number));
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
}*/
