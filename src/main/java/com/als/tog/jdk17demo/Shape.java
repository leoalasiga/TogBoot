package com.als.tog.jdk17demo;

/**
 * 在这个示例中，Shape 是一个抽象类，并且使用 permits 关键字，明确允许哪些类继承该类。
 * Circle 和 Rectangle 是 Shape 的子类，并使用 final 关键字来表示它们是封闭类，
 * 不允许有其他子类继承它们。这种方式可以在编译时校验代码，并防止意外创建不受预期的子类。
 *
 */
public sealed abstract class Shape permits Circle, Rectangle {
    public abstract double calculateArea();
}



