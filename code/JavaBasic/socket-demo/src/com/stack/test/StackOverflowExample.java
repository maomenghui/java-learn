package com.stack.test;

/**
 * TODO
 *
 * @author: Mao
 * @date: 2024/8/9 15:01
 * @Version: 1.0
 */
public class StackOverflowExample {

    public static void main(String[] args) {
        while (true) {
            testOverflow();
            System.out.println(11111);
        }
    }

    public static void testOverflow() {
        float a = 222222222222f;
        float b = 2222222222221f;
        float c = 2222222222223f;
    }
}
