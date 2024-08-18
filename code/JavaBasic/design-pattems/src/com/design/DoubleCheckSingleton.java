package com.design;

/**
 * volatile 标识对象在多线程中状态可见
 * synchronized 给对象加锁，保证多线程下安全
 *
 * @author: Mao
 * @date: 2024/8/13 12:43
 * @Version: 1.0
 */
public class DoubleCheckSingleton {
    public static volatile DoubleCheckSingleton singleton = null;

    private DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getSingleton() {
        if (singleton == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (singleton == null) {
                    singleton = new DoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }
}
