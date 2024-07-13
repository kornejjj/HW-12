package Task_2;


import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class FizzBuzz {
    private int n;
    private int current = 1;
    private final Object lock = new Object();
    private final Queue<String> queue = new LinkedBlockingQueue<>();

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz() {
        synchronized (lock) {
            while (current <= n) {
                while (current <= n && (current % 3 != 0 || current % 5 == 0)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (current > n) {
                    break;
                }
//                System.out.print("fizz, ");
                queue.add("fizz");
                current++;
                lock.notifyAll();
            }
        }
    }

    public void buzz() {
        synchronized (lock) {
            while (current <= n) {
                while (current <= n && (current % 5 != 0 || current % 3 == 0)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (current > n) {
                    break;
                }
//                System.out.print("buzz, ");
                queue.add("buzz");
                current++;
                lock.notifyAll();
            }
        }
    }

    public void fizzbuzz() {
        synchronized (lock) {
            while (current <= n) {
                while (current <= n && (current % 3 != 0 || current % 5 != 0)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (current > n) {
                    break;
                }
//                System.out.print("fizzbuzz, ");
                queue.add("fizzbuzz");
                current++;
                lock.notifyAll();
            }
        }
    }

    public void number() {
        synchronized (lock) {
            while (current <= n) {
                while (current <= n && (current % 3 == 0 || current % 5 == 0) && queue.isEmpty()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (current > n && queue.isEmpty()) {
                    break;
                }
                String item = queue.poll();
                if (item == null) {
                    System.out.println(current + ", ");
                    current++;
                } else {
                    System.out.println(item + ", ");
                }
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzz fizzBuzz = new FizzBuzz(n);

        Thread threadA = new Thread(fizzBuzz::fizz);
        Thread threadB = new Thread(fizzBuzz::buzz);
        Thread threadC = new Thread(fizzBuzz::fizzbuzz);
        Thread threadD = new Thread(fizzBuzz::number);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
