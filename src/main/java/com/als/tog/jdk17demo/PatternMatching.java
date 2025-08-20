package com.als.tog.jdk17demo;

public class PatternMatching {
    public static void main(String[] args) {
        Object obj = "Hello";

// 传统写法
        if (obj instanceof String) {

            String s = (String) obj;
            System.out.println(s.length());
        }

// 模式匹配写法
        if (obj instanceof String s) {

            System.out.println(s.length()); // 直接使用s，无需强制转换
        }

    }
}
