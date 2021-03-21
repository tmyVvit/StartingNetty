package terry.practice.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerformanceTest {
    static int MAX = 100000;
    public static void main(String[] args) {
        List<Long> list = new ArrayList<>(MAX);
        Random random = new Random();
        for (int i = 0; i < MAX; i++) {
            list.add((long) random.nextInt(100));
        }
        System.out.println("start normal loop:");
        long start = System.currentTimeMillis();
        long max = 0;
        for (int i = 0; i < list.size(); i++) {
            if (max < list.get(i)) {
                max = list.get(i);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("normal loop end, max : %d, total cost: %04f seconds",max, (end - start)/1000.0));
        System.out.println();
        System.out.println("start normal loop:");
        start = System.currentTimeMillis();
        max = 0;
        max = list.stream().reduce((a, b) -> {
            if (a < b) {
                a = b;
            }
            return a;
        }).get();
        end = System.currentTimeMillis();
        System.out.println(String.format("normal loop end, max : %d, total cost: %04f seconds",max, (end - start)/1000.0));
    }
}
