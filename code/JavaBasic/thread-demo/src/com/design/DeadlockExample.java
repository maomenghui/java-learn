package com.design;

/**
 * 死锁案例
 * 由于 Thread-1 持有 resource1 的锁而 Thread-2 持有 resource2 的锁，两个线程互相等待对方释放资源，从而进入了死锁状态，无法继续执行
 * @author: Mao
 * @date: 2024/8/8 19:45
 * @Version: 1.0
 */
public class DeadlockExample {

    public static void main2(String[] args) {
        // 创建两个资源对象
        final Object resource1 = new Object();
        final Object resource2 = new Object();

        // 创建第一个线程
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: locked resource 1");

                // 试图获取资源2的锁
                try {
                    // 暂停一会儿，确保另一个线程已经锁定资源2
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread 1: waiting for resource 2");
                synchronized (resource2) {
                    System.out.println("Thread 1: locked resource 2");
                }
            }
        });

        // 创建第二个线程
        Thread thread2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2: locked resource 2");

                // 试图获取资源1的锁
                try {
                    // 暂停一会儿，确保另一个线程已经锁定资源1
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread 2: waiting for resource 1");
                synchronized (resource1) {
                    System.out.println("Thread 2: locked resource 1");
                }
            }
        });

        // 启动两个线程
        thread1.start();
        thread2.start();
    }

    /**
     * 现在两个线程都会首先锁定 resource1，然后锁定 resource2。由于 resource1 被一个线程持有后，
     * 另一个线程必须等待锁定它，因此不会发生死锁。
     *
     * @Author Mao
     * @@Date  2024/8/8 20:22
     * @Version:    1.0     
     **/
    public static void main(String[] args) {
        // 创建两个资源对象
        final Object resource1 = new Object();
        final Object resource2 = new Object();

        // 创建第一个线程
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: locked resource 1");

                // 试图获取资源2的锁
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) {
                    System.out.println("Thread 1: locked resource 2");
                }
            }
        });

        // 创建第二个线程
        Thread thread2 = new Thread(() -> {
            // 改变锁的顺序，先锁定 resource1，然后再锁定 resource2
            synchronized (resource1) {  // 注意这里的锁顺序与 thread1 保持一致
                System.out.println("Thread 2: locked resource 1");

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) {
                    System.out.println("Thread 2: locked resource 2");
                }
            }
        });

        // 启动两个线程
        thread1.start();
        thread2.start();
    }
}

