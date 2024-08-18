package com.design;

/**
 * 单例模式
 *
 * @author: Mao
 * @date: 2024/8/13 12:28
 * @Version: 1.0
 */
public class Singleton {
    public static Singleton singleton = new Singleton();

    private Singleton(){}

    public static Singleton getSingleton() {
        return singleton;
    }

}
