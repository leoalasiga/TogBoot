package com.als.tog.jdk17demo;

import java.util.List;
import java.util.ArrayList;

/** 这个示例中，我们使用了 ZGC 垃圾回收器来回收 list 对象占用的内存。我们在代码中使用了
 *  System.gc() 方法来手动触发垃圾回收器。注意，在实际应用中，我们通常不需要手动触发垃圾回收器，
 *  因为 JVM 会自动进行垃圾回收操作。
 *  ZGC 垃圾回收器具有可伸缩性和低延迟的特点，可以在处理大型、高并发应用程序时提供更好的性能和吞吐量。
 *  除了 ZGC，Java 17 中还引入了 Shenandoah 垃圾回收器，它也具有类似的高性能和低延迟的特点。
 **/
public class GarbageCollectorExample {
    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }

        System.out.println("List size: " + list.size()); // List size: 1000000


        System.gc(); // 调用垃圾回收器

        System.out.println("List size after GC: " + list.size()); //List size after GC: 1000000

        /**
         * 什么 list.size() 在 GC 前后 没有变化？
         * 因为：
         *
         * ❗ list 对象在 GC 时 仍然被引用（reachable），所以 不会被回收！
         *
         * 关键概念：垃圾回收只回收“不可达对象”
         * JVM 的垃圾回收器只会回收那些 程序无法再访问的对象（即“垃圾”）。
         *
         * 在您的代码中：
         *
         * list 是一个 局部变量，位于 main 方法的栈帧中。
         * 在 System.gc() 调用时，list 仍然在作用域内，且后续代码还要使用它（打印 size）。
         * 所以 list 是 可达的（reachable），JVM 绝对不会把它当作垃圾回收！
         *
         * 误解	                                    正确理解
         * “调用 System.gc() 会回收所有对象”	    ❌ System.gc() 只是建议JVM进行GC，且只回收不可达对象
         * “GC 会清空集合”	                    ❌ GC 回收的是对象本身，不是清空内容
         * “调用 System.gc() 后 list 应该变小”	❌ size() 是逻辑数据量，与 GC 无关
         *
         * System.gc() 不保证立即执行
         * System.gc() 只是向 JVM 建议执行垃圾回收。
         * JVM 可能忽略这个请求，尤其是在生产环境中（可通过 -XX:+DisableExplicitGC 禁用）。
         * 即使执行，GC 的时机和范围也由 JVM 自己决定。
         *
         *
         * 您可以添加 JVM 参数来打印 GC 日志：

         * java -Xlog:gc GCDemo
         * 或更详细的：
         *
         * java -Xlog:gc* GCDemo
         * 输出示例：
         ** [0.123s][info][gc] GC(0) Pause Young (G1 Evacuation Pause) 10M->2M(16M) 5.6ms
         * 这会显示堆内存变化，帮助您理解 GC 实际行为。
         */

    }
}
