package thinkinginjava.chapter21.cooperate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WaxOMatic {
    public static void main(String[] args) throws Exception{
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new WaxOff(car));
        exec.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        // shutdownNow()会调用所有由它控制的线程的interrupt()
        exec.shutdownNow();
    }
}

class Car {
    private boolean waxOn = false;
    public synchronized void waxed() {
        waxOn = true;
        notifyAll();
    }
    public synchronized void buffed() {
        waxOn = false;
        notifyAll();
    }
    public synchronized void waitingForWaxing() throws InterruptedException {
        while (!waxOn) {
            // wait()会使线程挂起，并释放锁
            wait();
        }
    }
    public synchronized void waitingForBuffing() throws InterruptedException {
        while (waxOn) {
            wait();
        }
    }
}

class WaxOn implements Runnable {
    private final Car car;
    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println("Wax On!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitingForBuffing();
            }
        } catch (InterruptedException e) {
            System.out.println("Existing via interrupt");
        }
        System.out.println("Ending Wax On task");
    }
}

class WaxOff implements Runnable {
    private Car car;
    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.waitingForWaxing();
                System.out.println("Wax Off!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed();
            }
        } catch (InterruptedException e) {
            System.out.println("Existing via interrupt");
        }
        System.out.println("Ending Wax Off task");
    }
}
