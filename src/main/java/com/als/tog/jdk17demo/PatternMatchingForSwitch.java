package com.als.tog.jdk17demo;

/**
 * 代码说明： 在这个示例中，我们首先定义了一个 Object 类型的变量 obj，它可能是一个字符串、整型数或其他类型的对象。
 * 接下来，我们使用了 switch 语句，并对 obj 进行了几个模式匹配：
 * • 如果 obj 是一个长度大于 5 的字符串，表达式 case String s && s.length() > 5 就会被匹配并执行相应的代码块。
 * • 如果 obj 是一个短字符串，表达式 case String s 会匹配并执行相应代码块。
 * • 如果 obj 是一个整型数，表达式 case Integer i 就会执行相应代码块。
 * • 如果 obj 不属于以上任何一种类型，就会执行默认代码块。
 */
public class PatternMatchingForSwitch {
    public static void main(String[] args) {
        Object obj = "hello";
        /**
         * java: patterns in switch statements 是预览功能，默认情况下禁用。
         *   （请使用 --enable-preview 以启用 patterns in switch statements）
         */
//        String result = switch (obj) {
//            case String s -> "String: " + s;  // 即使是 17，简单 case Type var 也需要 --enable-preview
//            case Integer i -> "Integer: " + i;
//            default -> "Unknown";
//        };
    }


}
