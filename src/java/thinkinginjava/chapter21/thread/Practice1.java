package thinkinginjava.chapter21.thread;

public class Practice1 {
    private static int id = 1;
    static class Run implements Runnable {
        private final int localId = id++;
        @Override
        public void run() {
            System.out.println("Thread " + localId + " start------");
            for (int i = 0; i < 3; i++) {
                System.out.println("Thread " + localId + ": " + i);
                Thread.yield();
            }
            System.out.println("Thread " + localId + " end------");
        }
    };
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Run()).start();
        }
    }
}
