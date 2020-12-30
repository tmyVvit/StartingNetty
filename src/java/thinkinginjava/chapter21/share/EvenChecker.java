package thinkinginjava.chapter21.share;

import thinkinginjava.chapter21.thread.DaemonThreadDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable{
    private IntGenerator generator;
    private final int id;
    public EvenChecker(IntGenerator generator, int id) {
        this.generator = generator;
        this.id = id;
    }
    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            if (val%2 != 0) {
                System.out.println(val + " not even!");
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator generator, int count) {
        // 防止线程一直执行占用资源，设置为后台线程
        ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadDemo.DaemonThreadFactory());
        for (int i = 0; i < count; i++) {
            exec.execute(new EvenChecker(generator, i));
        }
        exec.shutdown();
    }

    public static void test(IntGenerator generator) {
        test(generator, 10);
    }
}
