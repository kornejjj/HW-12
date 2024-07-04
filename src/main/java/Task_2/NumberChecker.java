package Task_2;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class NumberChecker extends Thread {

    private static int number;
    private static int maxNumber;
    private Queue<String> toPrinting;
    private AtomicBoolean processed = new AtomicBoolean(true);
    private int semaphore = 1;

    public NumberChecker(Queue<String> toPrinting, int maxNumber) {
        this.toPrinting = toPrinting;
        this.maxNumber = maxNumber;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void fizz() {
        semaphore = 3;
        processed.set(false);
    }

    public void buzz() {
        semaphore = 5;
        processed.set(false);
    }

    public void fizzbuzz() {
        semaphore = 15;
        processed.set(false);
    }

    public void number() {
        semaphore = 0;
        processed.set(false);
        while (toPrinting.size() != 0) {
            System.out.println(toPrinting.poll());
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
            if (processed.get()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            } else {
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
                    if (toPrinting.size() != 0) {
                        System.out.println(toPrinting.poll());
                    }
                    break;
                }
            }
        }
    }
}