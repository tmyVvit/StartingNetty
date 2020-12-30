package thinkinginjava.chapter21.thread;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

// 后台线程
// 当所有非后台线程结束时，程序也就终止了，这时会杀死所有后台线程，后台线程会突然终止（即时运行在try-catch-finally时，也不会执行finally中的代码，而是直接终止）
// 后台线程创建的线程也是后台线程
public class DaemonThreadDemo {
    public static class DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(@NotNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for (int i = 0; i < 5; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread() + " started");
                        TimeUnit.MILLISECONDS.sleep(200);
                        System.out.println(Thread.currentThread() + " recovered");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException ignored){}
        exec.shutdown();
    }
}
