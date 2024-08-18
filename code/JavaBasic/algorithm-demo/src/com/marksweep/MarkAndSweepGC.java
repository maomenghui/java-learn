package src.com.marksweep;

/**
 * 模拟垃圾回收标记清除算法
 *  思路分为2步
 *  1 标记没有引用的对象
 *  2 回收没有引用的对象
 * @author: Mao
 * @date: 2024/8/18 0:33
 * @Version: 1.0
 */

import java.util.ArrayList;
import java.util.List;

// 模拟对象
class Node {
    int value;
    boolean marked = false;
    List<Node> references = new ArrayList<>();

    Node(int value) {
        this.value = value;
    }
}

public class MarkAndSweepGC {

    public static void main(String[] args) {
        // 模拟堆内存
        List<Node> heap = new ArrayList<>();

        // 创建对象并添加到堆
        Node root = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        root.references.add(node2);
        node2.references.add(node3);

        heap.add(root);
        heap.add(node2);
        heap.add(node3);
        heap.add(node4); // node4 没有被引用，应该被清除

        // 执行标记清除算法
        MarkAndSweepGC gc = new MarkAndSweepGC();
        gc.mark(root); // 标记可达对象
        gc.sweep(heap); // 清除不可达对象

        // 输出结果，查看哪些对象还存在
        for (Node node : heap) {
            System.out.println("Node value: " + node.value);
        }
    }

    // 标记阶段
    public void mark(Node root) {
        if (root == null || root.marked) {
            return;
        }
        root.marked = true;
        for (Node ref : root.references) {
            mark(ref);
        }
    }

    // 清除阶段
    public void sweep(List<Node> heap) {
        heap.removeIf(node -> !node.marked); // 移除未标记的对象（回收内存）
        for (Node node : heap) {
            node.marked = false; // 重置标记
        }
    }
}

