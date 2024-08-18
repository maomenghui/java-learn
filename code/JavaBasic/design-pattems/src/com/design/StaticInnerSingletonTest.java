package com.design;

/**
 * TODO
 *
 * @author: Mao
 * @date: 2024/8/13 12:30
 * @Version: 1.0
 */
public class StaticInnerSingletonTest implements Runnable{
    StaticInnerSingleton singleton = null;
    public StaticInnerSingletonTest(StaticInnerSingleton singleton) {
        this.singleton = singleton;
    }

    public static void main(String[] args) {
        StaticInnerSingleton singleton1 = StaticInnerSingleton.getInstance();
        StaticInnerSingleton singleton2 = StaticInnerSingleton.getInstance();
        StaticInnerSingleton singleton3 = StaticInnerSingleton.getInstance();

        Thread t1 = new Thread(new StaticInnerSingletonTest(singleton1));
        Thread t2 = new Thread(new StaticInnerSingletonTest(singleton2));
        Thread t3 = new Thread(new StaticInnerSingletonTest(singleton3));
        t1.start();
        t2.start();
        t3.start();
    }

    @Override
    public void run() {
        System.out.println(singleton);
    }
}
