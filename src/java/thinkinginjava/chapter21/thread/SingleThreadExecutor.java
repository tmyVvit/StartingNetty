package thinkinginjava.chapter21.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {
    public static void main(String[] args) {
        // 如果向SingleThreadExecutor提交了多个任务，这些任务会排队，只有在前一个任务执行结束之后才会执行后一个任务
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}
