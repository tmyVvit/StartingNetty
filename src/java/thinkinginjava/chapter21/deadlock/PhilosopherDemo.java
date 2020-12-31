package thinkinginjava.chapter21.deadlock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 经典哲学家问题
public class PhilosopherDemo {

    public static void main(String[] args) throws InterruptedException {
        deadLock();
    }

    /**
     *  死锁发生条件
     *   1. 互斥条件。线程使用的资源中至少有一项是不能共享的
     *   2. 至少有一个线程持有资源并且等待获取一个当前被另外一个线程持有的资源
     *   3. 资源不能被线程抢占，线程必须把释放资源当作普通事件
     *   4. 必须有循环等待
     * 只需要破坏上面的一个条件，就可以有效防止死锁的发生
     *
     * @throws InterruptedException
     */
    public static void deadLock() throws InterruptedException{
        int ponder = 0, size = 5;
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[size];
        for (int i = 0; i < size; i++) {
            chopsticks[i] = new Chopstick(i+1);
        }
        for (int i = 0; i < size; i++) {
            exec.execute(new Philosopher(i+1, ponder, chopsticks[i], chopsticks[(i+1)%size]));
        }
        TimeUnit.SECONDS.sleep(100);
        exec.shutdownNow();
    }

    // 通过破坏上面的第四个条件防止死锁
    public static void fixed() throws InterruptedException {
        int ponder = 0, size = 5;
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[size];
        for (int i = 0; i < size; i++) {
            chopsticks[i] = new Chopstick(i+1);
        }

        for (int i = 0; i < size-1; i++) {
            exec.execute(new Philosopher(i+1, ponder, chopsticks[i], chopsticks[(i+1)%size]));
        }
        // 使最后一个哲学家先拿左边的筷子，破坏循环等待
        exec.execute(new Philosopher(size, ponder, chopsticks[0], chopsticks[size-1]));
        TimeUnit.SECONDS.sleep(100);
        exec.shutdownNow();
    }
}

class Philosopher implements Runnable {

    private final Chopstick left, right;
    private final int id, ponderFactor;
    private final Random random = new Random(47);
    private void pause() throws InterruptedException {
        if (ponderFactor == 0) return ;
        TimeUnit.MILLISECONDS.sleep(ponderFactor * 200L);
    }
    public Philosopher(int id, int ponderFactor, Chopstick left, Chopstick right) {
        this.id = id;
        this.ponderFactor = ponderFactor;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(this + " is thinking");
                pause();
                System.out.println(this + " grabbing right " + right);
                right.take();
                System.out.println(this + " grabbing left " + left);
                left.take();
                System.out.println(this + " eating");
                TimeUnit.MILLISECONDS.sleep(1000);
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            System.out.println(this + " exiting via interrupt");
        }
    }

    @Override
    public String toString() {
        return "Philosopher " + id;
    }
}

class Chopstick {
    private boolean taken = false;
    private final int id;
    public Chopstick(int id) {
        this.id = id;
    }
    public synchronized void take() throws InterruptedException {
        while (taken)
            wait();
        taken = true;
    }
    public synchronized void drop() throws InterruptedException {
        taken = false;
        notifyAll();
    }
    public String toString()  {
        return "Chopstick " + id;
    }
}
