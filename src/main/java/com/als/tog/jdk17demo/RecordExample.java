package com.als.tog.jdk17demo;

/**
 * 代码说明： 在这个示例中，我们定义了一个名为 Person 的 Record 类，它有两个字段：name 和 age。
 * Record 类会自动生成一个带有这些字段的构造函数、getter 方法和 equals、hashCode 和 toString 方法。
 * • 我们在 main 方法中创建了一个 Person 对象，并使用 name() 和 age() 方法获取其名称和年龄信息，然后将其打印出来。
 * • 使用 Record 类，我们可以更轻松地定义简单的数据类，而不需要手动编写大量的构造函数和 getter 方法。
 * 这可以使我们的代码更加简洁、清晰，并且更易于阅读和维护。
 */
public class RecordExample {
    public static void main(String[] args) {
        Person person = new Person("John", 30);

        System.out.println("Name: " + person.name());
        System.out.println("Age: " + person.age());
    }
}

