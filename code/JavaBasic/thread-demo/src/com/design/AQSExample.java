package com.design;

/** 独占锁 案例
 * 内部类 Sync：这是一个自定义同步器，继承 AQS，并实现了 tryAcquire 和 tryRelease 方法来管理锁的获取和释放。
 * lock() 方法：通过调用 sync.acquire(1) 来获取锁，这会尝试修改 state，并将当前线程设置为锁的持有者。
 * unlock() 方法：通过 sync.release(1) 来释放锁，这会将 state 置为 0，并清除当前锁的持有者。
 * isLocked() 方法：判断当前锁是否被持有。
 *
 * @author: Mao
 * @date: 2024/8/14 19:01
 * @Version: 1.0
 */
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

class MyLock {
    // 内部类，继承 AQS，实现独占锁的功能
    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            // 判断 state 是否为 0，为 0 则可以获取锁
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            // 释放锁，将 state 置为 0
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            // 判断锁是否被当前线程持有
            return getState() == 1 && getExclusiveOwnerThread() == Thread.currentThread();
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }
}

public class AQSExample {
    public static void main(String[] args) {
        MyLock lock = new MyLock();

        Runnable task = () -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " acquired the lock.");
                // 模拟一些操作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " released the lock.");
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
    }
}

