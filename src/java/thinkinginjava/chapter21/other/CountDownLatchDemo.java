package thinkinginjava.chapter21.other;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// CountDownLatch用来同步一个或多个线程，强制它们等待有其它线程执行的一组操作完成
public class CountDownLatchDemo {
    static final int SIZE = 100;
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(SIZE);
        for (int i = 0; i < 10; i++)
            exec.execute(new WaitingTask(latch));
        for (int i = 0; i < SIZE; i++)
            exec.execute(new TaskPortion(latch));
        System.out.println("Launched all tasks");
        exec.shutdown();
    }
}

class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random random = new Random(47);
    private final CountDownLatch latch;
    public TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            doWork();
            // 每次调用countDown()方法就会减少一次计数
            latch.countDown();
        } catch (InterruptedException ignored) {}
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
        System.out.println(this + " complete");
    }

    @Override
    public String toString() {
        return String.format("%1$-3d", id);
    }
}

class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;
    public WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d", id);
    }

    @Override
    public void run() {
        try {
            // 将自己挂起，直到计数减少到0
            latch.await();
            System.out.println("Latch barrier passed for " + this);
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }
}
