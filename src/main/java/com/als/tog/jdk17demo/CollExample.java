package com.als.tog.jdk17demo;

import java.util.*;
import java.util.stream.Collectors;

public class CollExample {
    public static void main(String[] args) {
        List<String> list = List.of("apple", "banana", "orange");
        Set<Integer> set = Set.of(1, 2, 3, 4);
        Map<String, Integer> map = Map.of("apple", 1, "banana", 2, "orange", 3);

        //forEach() 方法：遍历集合

        list.forEach(name -> System.out.println(name));
        set.forEach(number -> System.out.println(number));


        //4. takeWhile() 方法和 dropWhile() 方法：根据条件截取集合
        List<Integer> list1 = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> takenList = list1.stream().takeWhile(number -> number < 5).collect(Collectors.toList());
        System.out.println(takenList);

        List<Integer> dropedList = list1.stream().dropWhile(number -> number < 5).collect(Collectors.toList());
        System.out.println(dropedList);

        //5. toArray(IntFunction<T[]>) 方法：返回集合中的所有元素到一个新数组中
        String[] array = list.toArray(String[]::new);
        System.out.println(Arrays.toString(array));
    }
}
