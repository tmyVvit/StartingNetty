package thinkinginjava.chapter21.cooperate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 在ChefAndWaiter中，因为没有一个缓冲区放置膳食，所以单个生产者个单个消费之需要互相等待
// 在这个例子中，我们增加一个缓冲区，并且有多个厨师多个服务员，厨师将做好的食物放进缓冲区，而服务员直接从缓冲区拿去食物
public class ChefAndWaiter2 {
    public static void main(String[] args) throws InterruptedException {
        MealHolder mealHolder = new MealHolder(10);
        NewRestaurant newRestaurant = new NewRestaurant(mealHolder);
        NewChef chef1 = new NewChef(1, newRestaurant);
        NewChef chef2 = new NewChef(2, newRestaurant);
        NewChef chef3 = new NewChef(3, newRestaurant);
        NewWaiter waiter1 = new NewWaiter(1, mealHolder);
        NewWaiter waiter2 = new NewWaiter(2, mealHolder);
        NewWaiter waiter3 = new NewWaiter(3, mealHolder);
        newRestaurant.addTasks(chef1, chef2, chef3, waiter1, waiter2, waiter3);
        newRestaurant.start();
        TimeUnit.SECONDS.sleep(5);
        newRestaurant.stop();
    }
}

// MealHolder 专门用来放置做好的食物
// 厨师做好食物后直接放进这里，服务员也直接从这里拿食物

// 其实我们也可以在MealHolder中实现同步与唤醒等操作，但是在这个代码中并没有采用这种方式
class MealHolder {
    private final List<NewMeal> meals;
    private final int maxMeal;
    public MealHolder(int count) {
        maxMeal = count;
        meals = new ArrayList<>();
    }
    public boolean full() {
        return meals.size() == maxMeal;
    }
    public boolean putMeal(NewMeal meal) {
        if (!full()) {
            meals.add(meal);
            return true;
        }
        return false;
    }
    public NewMeal getMeal() throws InterruptedException {
        NewMeal meal = null;
        if (!meals.isEmpty()) {
            meal = meals.remove(0);
        }
        return meal;
    }

    public boolean isEmpty() {
        return meals.isEmpty();
    }

    public String statue() {
        StringBuilder sb = new StringBuilder();
        sb.append("Meal Holder have ").append(meals.size()).append(" meals left.");
        for (NewMeal meal : meals) {
            sb.append("\n").append(meal);
        }
        return sb.toString();
    }
}

class NewMeal {
    private final String name;
    public NewMeal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Meal: " + name;
    }
}
class NewChef implements Runnable {
    private int mealCount = 0;
    private final NewRestaurant restaurant;
    private final int id;
    public NewChef(int id, NewRestaurant restaurant) {
        this.restaurant = restaurant;
        this.id = id;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (restaurant.mealHolder) {
                    // 如果mealHolder已经满了，则不需要再做
                    while (restaurant.mealHolder.full()) {
                        restaurant.mealHolder.wait();
                    }
                }
                NewMeal meal = new NewMeal((++mealCount) + " by " + this);
                TimeUnit.MILLISECONDS.sleep(200);
                synchronized (restaurant.mealHolder) {
                    while (restaurant.mealHolder.full()) {
                        restaurant.mealHolder.wait();
                    }
                    if (restaurant.mealHolder.putMeal(meal)) {
                        System.out.println(this + " put " + meal);
                    } else {
                        // 不可能放不进去
                        System.out.println(meal + " not put to meal holder, meal holder full");
                    }
                    restaurant.mealHolder.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + " out with interrupt");
        }
    }
    public String toString() {
        return "Chef " + id;
    }
}
class NewWaiter implements Runnable {
    private final MealHolder holder;
    private final int id;
    public NewWaiter(int id, MealHolder holder) {
        this.holder = holder;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (holder) {
                    while (holder.isEmpty()) {
                        holder.wait();
                    }
                    NewMeal meal = holder.getMeal();
                    System.out.println(this + " get " + meal);
                    holder.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " out with interrupt");
        }
    }

    public String toString() {
        return "Waiter " + id;
    }
}

class NewRestaurant {
    final MealHolder mealHolder;
    private final ExecutorService exec;
    List<Runnable> tasks = new ArrayList<>();
    public NewRestaurant (MealHolder holder) {
        mealHolder = holder;
        exec = Executors.newCachedThreadPool();
    }
    public void addTasks(Runnable... task) {
        tasks.addAll(Arrays.asList(task));
    }
    public void start() {
        for (Runnable task : tasks) {
            exec.execute(task);
        }
        tasks.clear();
    }
    public void stop() {
        exec.shutdownNow();
        System.out.println(mealHolder.statue());
    }
}