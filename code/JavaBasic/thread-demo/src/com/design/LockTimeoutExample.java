package com.design;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
/**
 * 锁超时案例
 *
 * @author: Mao
 * @date: 2024/8/8 20:02
 * @Version: 1.0
 */
public class LockTimeoutExample {

    // 定义两个可重入锁，用于模拟两个资源
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        // 创建 LockTimeoutExample 类的实例
        LockTimeoutExample example = new LockTimeoutExample();

        // 创建两个线程，分别执行 task1 和 task2 方法
        Thread thread1 = new Thread(example::task1, "Thread-1");
        Thread thread2 = new Thread(example::task2, "Thread-2");

        // 启动线程
        thread1.start();
        thread2.start();
    }

    // 第一个任务，尝试按顺序获取锁 lock1 和 lock2
    public void task1() {
        try {
            // 尝试获取 lock1 的锁，等待时间为 1 秒
            if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " locked lock1");

                try {
                    // 模拟一些工作，暂停 500 毫秒
                    Thread.sleep(500);

                    // 尝试获取 lock2 的锁，等待时间为 1 秒
                    if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " locked lock2");
                            // 执行任务的关键部分，已经持有两个锁
                        } finally {
                            // 确保在使用完 lock2 后释放锁
                            lock2.unlock();
                        }
                    } else {
                        // 如果在 1 秒内未能获取 lock2 的锁，输出超时信息
                        System.out.println(Thread.currentThread().getName() + " could not lock lock2, timeout!");
                    }
                } finally {
                    // 确保在使用完 lock1 后释放锁
                    lock1.unlock();
                }
            } else {
                // 如果在 1 秒内未能获取 lock1 的锁，输出超时信息
                System.out.println(Thread.currentThread().getName() + " could not lock lock1, timeout!");
            }
        } catch (InterruptedException e) {
            // 捕获线程中断异常
            e.printStackTrace();
        }
    }

    // 第二个任务，尝试按顺序获取锁 lock2 和 lock1
    public void task2() {
        try {
            // 尝试获取 lock2 的锁，等待时间为 1 秒
            if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " locked lock2");

                try {
                    // 模拟一些工作，暂停 500 毫秒
                    Thread.sleep(500);

                    // 尝试获取 lock1 的锁，等待时间为 1 秒
                    if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " locked lock1");
                            // 执行任务的关键部分，已经持有两个锁
                        } finally {
                            // 确保在使用完 lock1 后释放锁
                            lock1.unlock();
                        }
                    } else {
                        // 如果在 1 秒内未能获取 lock1 的锁，输出超时信息
                        System.out.println(Thread.currentThread().getName() + " could not lock lock1, timeout!");
                    }
                } finally {
                    // 确保在使用完 lock2 后释放锁
                    lock2.unlock();
                }
            } else {
                // 如果在 1 秒内未能获取 lock2 的锁，输出超时信息
                System.out.println(Thread.currentThread().getName() + " could not lock lock2, timeout!");
            }
        } catch (InterruptedException e) {
            // 捕获线程中断异常
            e.printStackTrace();
        }
    }
}


