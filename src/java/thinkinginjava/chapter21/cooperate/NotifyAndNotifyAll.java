package thinkinginjava.chapter21.cooperate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NotifyAndNotifyAll {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Task());
        }
        exec.execute(new Task2());
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean prod = true;
            @Override
            public void run() {
                if (prod) {
                    System.out.println("notify");
                    Task.blocker.prod();
                    prod = false;
                } else {
                    System.out.println("notify all");
                    Task.blocker.prodAll();
                    prod = true;
                }
            }
        }, 400, 400); // run every 0.4 seconds
        TimeUnit.SECONDS.sleep(5);
        timer.cancel();
        System.out.println("Timer canceled");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("Task2.blocker.prodAll");
        Task2.blocker.prodAll();
        System.out.println("shutting down");
        exec.shutdownNow();
    }
}

class Blocker {
    synchronized void waitingCall() {
        try {
            while (!Thread.interrupted()) {
                wait();
                System.out.println(Thread.currentThread() + " ");
            }
        } catch (InterruptedException e) {
            System.out.println("Blocker exit with interrupt");
        }
    }
    synchronized void prod() {
        // 注意方法上的synchronized， 将只唤醒在等待这个特定锁的线程
        notify();
    }
    synchronized void prodAll() {
        // 注意方法上的synchronized， 将只唤醒在等待这个特定锁的线程
        notifyAll();
    }
}

class Task implements Runnable {
    static Blocker blocker = new Blocker();
    @Override
    public void run() {
        blocker.waitingCall();
    }
}

class Task2 implements Runnable {
    static Blocker blocker = new Blocker();
    @Override
    public void run() {
        blocker.waitingCall();
    }
}
