package com.stack;

import java.util.Stack;

/**
 * JVMStack 模拟 JVM 的栈结构。
 * 提供方法来压入和弹出栈帧，并打印当前栈的状态。
 *
 * @author: Mao
 * @date: 2024/8/8 0:40
 * @Version: 1.0
 */
class JVMStack {
    private Stack<com.stack.StackFrame> stack; // 栈结构用于保存栈帧

    public JVMStack() {
        this.stack = new Stack<>();
    }

    /**
     * 将栈帧压入 JVM 栈。
     * @param frame 要压入的栈帧。
     */
    public void push(StackFrame frame) {
        stack.push(frame);
        System.out.println("压入: " + frame);
    }

    /**
     * 从 JVM 栈中弹出栈帧。
     * @return 被弹出的栈帧。
     */
    public StackFrame pop() {
        StackFrame frame = stack.pop();
        System.out.println("弹出: " + frame);
        return frame;
    }

    /**
     * 查看栈顶的栈帧但不弹出。
     * @return 栈顶的栈帧。
     */
    public StackFrame peek() {
        return stack.peek();
    }

    /**
     * 检查 JVM 栈是否为空。
     * @return 如果栈为空则返回 true，否则返回 false。
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * 打印 JVM 栈的当前状态。
     */
    public void printStack() {
        System.out.println("当前栈状态:");
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.println(stack.get(i));
        }
    }
}

