package thinkinginjava.chapter21.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MoreBasicThreads {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for lift off");

        // CachedThreadPool为每个新任务都创建一个线程
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new LiftOff());
        }
        executorService.shutdown();

        // 使用有限的线程集来执行所提交的任务
        ExecutorService executorService1 = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            executorService1.execute(new LiftOff());
        }
        executorService1.shutdown();
    }
}
