package com.design;

/**
 * 静态内部类，JVM 在创建静态内部类的时候确保线程安全的
 *
 * @author: Mao
 * @date: 2024/8/13 13:59
 * @Version: 1.0
 */
public class StaticInnerSingleton {
    private StaticInnerSingleton(){}

    private static class Singleton {
        public static StaticInnerSingleton singleton = new StaticInnerSingleton();
    }

    public static StaticInnerSingleton getInstance() {
        return Singleton.singleton;
    }
}
