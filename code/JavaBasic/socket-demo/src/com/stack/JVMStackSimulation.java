package com.stack;

/**
 * JVMStackSimulation 通过方法调用和栈帧管理模拟 JVM 栈的行为。
 *
 * @author: Mao
 * @date: 2024/8/8 0:40
 * @Version: 1.0
 */
public class JVMStackSimulation {

    public static void main(String[] args) {
        JVMStack jvmStack = new JVMStack();
        mainMethod(jvmStack);
    }

    /**
     * 模拟 main 方法，它调用 methodA。
     *
     * @param jvmStack 模拟的 JVM 栈。
     */
    public static void mainMethod(JVMStack jvmStack) {
        StackFrame mainFrame = new StackFrame("main", 1);
        jvmStack.push(mainFrame); // 将 main 方法的栈帧压入栈

        methodA(jvmStack); // 调用 methodA 方法

        jvmStack.pop(); // 弹出 main 方法的栈帧
        jvmStack.printStack(); // 打印当前栈状态
    }

    /**
     * 模拟 methodA 方法，它调用 methodB。
     *
     * @param jvmStack 模拟的 JVM 栈。
     */
    public static void methodA(JVMStack jvmStack) {
        StackFrame methodAFrame = new StackFrame("methodA", 1);
        jvmStack.push(methodAFrame); // 将 methodA 方法的栈帧压入栈

        methodB(jvmStack); // 调用 methodB 方法

        jvmStack.pop(); // 弹出 methodA 方法的栈帧
        jvmStack.printStack(); // 打印当前栈状态
    }

    /**
     * 模拟 methodB 方法，这是最内层的方法调用。
     *
     * @param jvmStack 模拟的 JVM 栈。
     */
    public static void methodB(JVMStack jvmStack) {
        StackFrame methodBFrame = new StackFrame("methodB", 1);
        jvmStack.push(methodBFrame); // 将 methodB 方法的栈帧压入栈

        // 模拟方法体执行

        jvmStack.pop(); // 弹出 methodB 方法的栈帧
        jvmStack.printStack(); // 打印当前栈状态
    }
}
