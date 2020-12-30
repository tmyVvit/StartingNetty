package thinkinginjava.chapter18.nio;

import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

import static thinkinginjava.chapter18.nio.GetChannel.PATH;

/**
 *   文件加锁
 *   文件所对其他的操作系统进程是可见的，因为Java的文件加锁直接映射到了本地操作系统的加锁工具
 */
public class FileLocking {
    public static void main(String[] args) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(PATH + "data")) {
            // tryLock()方法是非阻塞的，如果不能获得锁，则会直接返回
            // lock()方法是阻塞的，它要阻塞直至获得锁
            // 也可以tryLock(long position, long size, boolean shared) 或者lock(long position, long size, boolean shared)来对文件一部分上锁
            // shared指定是否是共享锁
            FileLock lock = fos.getChannel().tryLock();
            if (lock != null) {
                System.out.println("Lock File");
                TimeUnit.SECONDS.sleep(1);
                lock.release();
                System.out.println("Release lock");
            }
        }
    }
}
