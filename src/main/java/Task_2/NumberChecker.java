package Task_2;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class NumberChecker extends Thread {

    private static int number;
    private static int maxNumber;
    private Queue<String> toPrinting;
    private AtomicBoolean processed = new AtomicBoolean(true);
    private int semaphore = 1;
    private final Object monitor = new Object();

    public NumberChecker(Queue<String> toPrinting, int maxNumber) {
        this.toPrinting = toPrinting;
        this.maxNumber = maxNumber;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void fizz() {
        synchronized (monitor) {
            semaphore = 3;
            processed.set(false);
            monitor.notifyAll();
        }
    }

    public void buzz() {
        synchronized (monitor) {
            semaphore = 5;
            processed.set(false);
            monitor.notifyAll();
        }
    }

    public void fizzbuzz() {
        synchronized (monitor) {
            semaphore = 15;
            processed.set(false);
            monitor.notifyAll();
        }
    }

    public void number() {
        synchronized (monitor) {
            semaphore = 0;
            processed.set(false);
            monitor.notifyAll();
            while (!toPrinting.isEmpty()) {
                System.out.println(toPrinting.poll());
            }
        }
    }

    private void answerWriter(String answer){
        toPrinting.add(answer);
    }

    public boolean isProcessed(){
        return processed.get();
    }

    @Override
    public void run() {
        int iterationCounter = 0;
        while (true) {
            synchronized (monitor) {
                while (processed.get()) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                iterationCounter++;
                if (semaphore == 3 && number % 3 == 0 && number % 5 != 0) {
                    answerWriter("fizz");
                }
                if (semaphore == 5 && number % 5 == 0 && number % 3 != 0) {
                    answerWriter("buzz");
                }
                if (semaphore == 15 && number % 15 == 0) {
                    answerWriter("fizzbuzz");
                }
                if (semaphore == 0 && number % 3 != 0 && number % 5 != 0) {
                    answerWriter(String.valueOf(number));
                }
                processed.set(true);
                semaphore = 1;

                if (iterationCounter == maxNumber) {
                    if (!toPrinting.isEmpty()) {
                        System.out.println(toPrinting.poll());
                    }
                    break;
                }
            }
        }
    }
}
