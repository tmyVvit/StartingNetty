package thinkinginjava.chapter21.other;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SemaphoreDemo {
    final static int SIZE = 25;
    public static void main(String[] args) throws Exception {
        final Pool<Fat> pool = new Pool<>(Fat.class, SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new CheckOutTask<>(pool));
        }
        System.out.println("All checkoutTasks created");
        List<Fat> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            Fat f = pool.checkOut();
            System.out.print(i + ": main() thread checked out ");
            f.operation();
            list.add(f);
        }
        Future<?> future = exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    pool.checkOut();
                } catch (InterruptedException e) {
                    System.out.println("checkout interrupt");
                }
            }
        });
        TimeUnit.SECONDS.sleep(2);
        future.cancel(true);
        System.out.println("Checking in objects in " + list);
        for (Fat f : list) {
            pool.checkIn(f);
        }
        for (Fat f : list) {
            pool.checkIn(f);
        }
        exec.shutdown();
    }
}

class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<>();
    private volatile boolean[] checkedOut;
    private Semaphore semaphore;
    public Pool(Class<T> objClass, int size) {
        this.size = size;
        checkedOut = new boolean[size];
        semaphore = new Semaphore(size, true);
        for (int i = 0; i < size; i++) {
            try {
                items.add(objClass.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public T checkOut() throws InterruptedException {
        semaphore.acquire();
        return getItem();
    }

    public void checkIn(T item) {
        if (releaseItem(item)) {
            semaphore.release();
        }
    }

    private synchronized T getItem() {
        for (int i = 0; i < size; i++) {
            if (!checkedOut[i]) {
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        return null;
    }

    private synchronized boolean releaseItem(T item) {
        int index = items.indexOf(item);
        if (index == -1) return false;
        if (checkedOut[index]) {
            checkedOut[index] = false;
            return true;
        }
        return false;
    }
}

class Fat {
    private volatile double d; // set volatile to prevent optimization
    private static int counter = 0;
    private final int id = counter++;
    public Fat() {
        // expensive
        for (int i = 0; i < 10000; i++) {
            d += (Math.PI + Math.E) / i;
        }
    }
    public void operation() {
        System.out.println(this);
    }
    public String toString() {
        return "Fat id: " + id;
    }
}

class CheckOutTask<T> implements Runnable {
    private static int count = 0;
    private final int id = count++;
    private final Pool<T> pool;
    public CheckOutTask(Pool<T> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + " checked out " + item);
            TimeUnit.SECONDS.sleep(1);
            pool.checkIn(item);
            System.out.println(this + " checked in " + item);
        } catch (InterruptedException e) {
            System.out.println(this + " off via interrupt");
        }
    }

    @Override
    public String toString() {
        return "CheckoutTask " + id;
    }
}