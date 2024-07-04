package Task_1;

public class Task_1 {

    static long start = System.currentTimeMillis();
    public static void main(String[] args) {

        Thread timer = new Thread(() -> {
            while (timeIsOut()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Від моменту запуску програми минуло: " + (System.currentTimeMillis() - start) / 1000 + " сек.");
            }
        });

        Thread pager = new Thread(() -> {
            while (timeIsOut()) {
                try {
                    Thread.sleep(5050);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Минуло 5 секунд");
            }
        });

        timer.start();
        pager.start();

    }
    private static boolean timeIsOut() {
        return (System.currentTimeMillis() - start) < 60000;
    }
}
