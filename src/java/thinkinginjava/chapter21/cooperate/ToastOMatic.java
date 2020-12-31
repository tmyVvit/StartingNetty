package thinkinginjava.chapter21.cooperate;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

// BlockingQueue内部是同步的，不需要我们显式地进行同步管理
public class ToastOMatic {
    public static void main(String[] args) throws InterruptedException {
        ToastQueue dryQueue = new ToastQueue(), butteredQueue = new ToastQueue(), finishQueue = new ToastQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(1, dryQueue));
        exec.execute(new Toaster(2, dryQueue));
        exec.execute(new Toaster(3, dryQueue));
        exec.execute(new Toaster(4, dryQueue));
        exec.execute(new Butter(1, dryQueue, butteredQueue));
        exec.execute(new Butter(2, dryQueue, butteredQueue));
        exec.execute(new Butter(3, dryQueue, butteredQueue));
        exec.execute(new Jammer(1, butteredQueue, finishQueue));
        exec.execute(new Jammer(2, butteredQueue, finishQueue));
        exec.execute(new Eater(1, finishQueue));

        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}

class Toast {
    public enum Status {DRY, BUTTERED, JAMMED}
    private Status status = Status.DRY;
    private final String id;
    public Toast(String id) {
        this.id = id;
    }
    public void butter() {
        if (status != Status.DRY) {
            System.out.println(this + " not valid in butter()");
        }
        status = Status.BUTTERED;
    }
    public void jam() {
        if (status != Status.BUTTERED) {
            System.out.println(this + " not valid in jam()");
        }
        status = Status.JAMMED;
    }
    public String getStatus() {
        return status.name();
    }
    public String toString() {
        return "Toast " + id + ": " + status.name();
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {}

class Toaster implements Runnable {

    private final ToastQueue dryQueue;
    private int count = 0;
    private final int id;
    private final Random random = new Random(47);
    public Toaster(int id, ToastQueue queue) {
        dryQueue = queue;
        this.id = id;
    }
    public String toString() {
        return "Toaster " + id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(500));
                Toast t = new Toast(++count + " by " + this);
                System.out.println(t + " made");
                dryQueue.put(t);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }
}

class Butter implements Runnable {
    private final ToastQueue dryQueue, butteredQueue;
    private final int id;
    public Butter(int id, ToastQueue dryQueue, ToastQueue butteredQueue) {
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
        this.id = id;
    }
    public String toString() {
        return "Butter " + id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast t = dryQueue.take();
                t.butter();
                System.out.println(this + " " + t);
                butteredQueue.put(t);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }
}

class Jammer implements Runnable {
    private final ToastQueue butteredQueue, finishQueue;
    private final int id;
    public Jammer(int id, ToastQueue butteredQueue, ToastQueue finishQueue) {
        this.id = id;
        this.butteredQueue = butteredQueue;
        this.finishQueue = finishQueue;
    }

    public String toString() {
        return "Jammer " + id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = butteredQueue.take();
                toast.jam();
                System.out.println("Jam " + toast);
                finishQueue.put(toast);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }
}

class Eater implements Runnable {

    private final ToastQueue finishQueue;
    private final int id;
    public Eater(int id, ToastQueue finishQueue) {
        this.id = id;
        this.finishQueue = finishQueue;
    }

    public String toString() {
        return "Eater " + id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = finishQueue.take();
                System.out.println(this + " " + toast);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }
}