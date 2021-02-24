package terry.practice;

public class ThreadLocalTest {
    /**
     *  ThreadLocal 其实是将value保存进一个ThreadLocalMap里面的，这个map是定义在Thread中的
     *  ThreadLocal是key，存进去的对象是value
     *
     *  这样就可以保证在这个线程结束的时候，ThreadLocal里的值也都可以被释放掉
     *  一个线程只需要管理自己线程的私有变量，可以保证map不会太大
     */
    private static final  ThreadLocal<Node> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Node node = new Node("node1");
                threadLocal.set(node);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1:" + threadLocal.get());
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Node node = new Node("node2");
                threadLocal.set(node);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2: " + threadLocal.get());
            }
        });
        thread1.start();
        thread2.start();
    }

    private static class Node {
        String name;

        Node(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
