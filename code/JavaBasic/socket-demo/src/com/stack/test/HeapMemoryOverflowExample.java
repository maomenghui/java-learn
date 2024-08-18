package com.stack.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出场景
 * 堆最大内存    生成 Dump 文件                  Dump 文件位置
 * -Xmx1m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\Users\56359\Desktop\Java
 *  可以使用 JVisual JConsole 这两个jdk自带工具调试，JVisual 可以打开 Dump文件进行分析异常，可以看到具体溢出原因，溢出代码位置
 * @author: Mao
 * @date: 2024/8/9 22:52
 * @Version: 1.0
 */
public class HeapMemoryOverflowExample extends Thread {
    @Override
    public void run() {
        // 创建一个ArrayList来存储对象
        List<String> list = new ArrayList<String>();
        // 无限循环向列表中添加对象
        try {
            while (true) {
                list.add(new String("11122"));
                sleep(1);
            }
        }catch (OutOfMemoryError | InterruptedException error) {
            error.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HeapMemoryOverflowExample().start();
    }
}
