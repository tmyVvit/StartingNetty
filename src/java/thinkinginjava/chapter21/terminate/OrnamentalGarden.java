package thinkinginjava.chapter21.terminate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OrnamentalGarden {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();
        // awaitTermination方法等待每个任务结束，如果所有任务都在超市时间到达之前结束就返回true，否则返回false，表示不是所有任务都结束了
        if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS)) {
            System.out.println("Some tasks were not terminated");
        }
        System.out.println("Total: " + Entrance.getTotalCount());
        System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
    }
}

class Count {
    private int count = 0;
    private final Random rand = new Random(47);
    public synchronized int increment() {
        // 增加 temp 和 yield() 增加失败的可能
        // 如果去掉方法上的synchronized，那么很容易就会出现Count的计数和Entrance里面的计数不一致的情况
        int temp = count;
        if (rand.nextBoolean()) Thread.yield();
        return (count = ++temp);
    }

    public synchronized int value() {
        return count;
    }
}

class Entrance implements Runnable {
    // 静态的Count跟踪主计数值
    private static final Count count = new Count();
    private static final List<Entrance> entrances = new ArrayList<>();
    // 每个Entrance也维护一个自己的计数值
    private int number = 0;
    private final int id;
    private static volatile boolean canceled = false;
    public static void cancel() {
        canceled = true;
    }
    public Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++number;
            }
            System.out.println(this + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted");
            }
        }
        System.out.println("Stopping " + this);
    }

    public synchronized int getValue() {
        return number;
    }
    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }

    public static int getTotalCount() {
        return count.value();
    }

    public static int sumEntrances() {
        int sum = 0;
        for (Entrance e : entrances) {
            sum += e.getValue();
        }
        return sum;
    }
}