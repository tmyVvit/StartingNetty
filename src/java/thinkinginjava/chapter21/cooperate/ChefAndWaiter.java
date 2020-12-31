package thinkinginjava.chapter21.cooperate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//一个生产者与消费者的例子
// 厨师和服务员，服务员必须等待厨师准备好膳食，
// 当厨师准备好时，就会通知服务员，然后服务员上菜，继续等待

// 出了使用wait()/notify()，也可以使用显式的锁Lock以及允许线程挂起的基本类Condition来实现 await()/signalAll()
//        Lock lock = new ReentrantLock();
//        Condition condition = lock.newCondition();
//        condition.await(); condition.signal(); condition.signalAll();
public class ChefAndWaiter {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        restaurant.start();
    }
}

class Meal {
    private final int mealNum;
    public Meal(int num) {
        mealNum = num;
    }

    @Override
    public String toString() {
        return "Meal: " + mealNum;
    }
}

class Chef implements Runnable {
    private final Restaurant restaurant;
    private int mealCount = 0;
    public Meal meal;
    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (meal != null) {
                        // 服务员还没有拿走膳食，厨师等待
                        wait();
                    }
                }
                if (++mealCount > 10) {
                    System.out.println("no food");
                    restaurant.stopNow();
                    return ;
                }
                // 模拟厨师做菜时间
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.print("Order up ");
                synchronized (restaurant.waiter) {
                    meal = new Meal(mealCount);
                    System.out.println("chef prepared meal: " + meal);
                    // 已经做好膳食，提醒服务员来拿
                    restaurant.waiter.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Chef out with interrupt");
        }
    }
}

class Waiter implements Runnable {
    private final Restaurant restaurant;
    public Waiter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.chef.meal == null) {
                        // 厨师还没做好膳食，服务员等待
                        wait();
                    }
                }
                System.out.println("waiter get meal: " + restaurant.chef.meal);
                synchronized (restaurant.chef) {
                    // 服务员拿走膳食，提醒厨师继续做菜
                    restaurant.chef.meal = null;
                    restaurant.chef.notifyAll();
                }
                // 模拟服务员送餐
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Waiter out with interrupt");
        }
    }
}

class Restaurant {
    final Chef chef;
    final Waiter waiter;
    private final ExecutorService executorService;
    public Restaurant() {
        this.chef = new Chef(this);
        this.waiter = new Waiter(this);
        executorService = Executors.newCachedThreadPool();
    }
    public void start() {
        executorService.execute(chef);
        executorService.execute(waiter);
    }

    public void stopNow() {
        executorService.shutdownNow();
    }
}
