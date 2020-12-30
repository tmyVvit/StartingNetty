package thinkinginjava.chapter21.share;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EvenGenerator extends IntGenerator {
    private int currentVal = 0;
    @Override
    public int next() {
        // 线程不安全的！！！
        ++currentVal;
        ++currentVal;
        return currentVal;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
        while (true) {
        // control - c 退出程序
        }
    }
}

class SynchronizedEvenGenerator extends IntGenerator {
    private int currentVal = 0;
    @Override
    public synchronized int next() {
        ++currentVal;
        ++currentVal;
        return currentVal;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
        while (true) {
            // control - c 退出程序
        }
    }
}


class MutexEvenGenerator extends IntGenerator {
    private int currentVal = 0;
    private final Lock lock = new ReentrantLock();
    @Override
    public synchronized int next() {
        lock.lock();
        try {
            ++currentVal;
            ++currentVal;
            return currentVal;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
        while (true) {
            // control - c 退出程序
        }
    }
}