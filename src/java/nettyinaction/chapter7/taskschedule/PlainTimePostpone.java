package nettyinaction.chapter7.taskschedule;

import java.util.concurrent.*;

public class PlainTimePostpone {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
    public void executeAfterSeconds(int second, String taskName) {
        ScheduledFuture<?> future = executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(taskName +"executed "+ second + " seconds later");
            }
        }, second, TimeUnit.SECONDS);
    }

    public void release() {
        System.out.println("start shutdown");
        executor.shutdown();
        System.out.println("shutdown complete");
    }

    public static void main(String[] args) {
        PlainTimePostpone timePostpone = new PlainTimePostpone();
        timePostpone.executeAfterSeconds(4, "first task");
        timePostpone.executeAfterSeconds(3, "second task");
        timePostpone.executeAfterSeconds(2, "third task");
        timePostpone.executeAfterSeconds(1, "fourth task");
        timePostpone.release();
    }
}
