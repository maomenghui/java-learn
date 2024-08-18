package com.stack;

/**
 * StackFrame 表示 JVM 栈中的一个栈帧。
 * 每个栈帧包含方法名称和局部变量数组。
 *
 * @author: Mao
 * @date: 2024/8/8 0:38
 * @Version: 1.0
 */
class StackFrame {
    String methodName; // 方法名称
    int[] localVariables; // 局部变量数组

    public StackFrame(String methodName, int localVariableCount) {
        this.methodName = methodName;
        this.localVariables = new int[localVariableCount];
    }

    @Override
    public String toString() {
        return "StackFrame{" +
                "methodName='" + methodName + '\'' +
                '}';
    }
}


