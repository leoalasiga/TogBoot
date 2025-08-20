package com.als.tog.jdk17demo;

public final class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }


    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}
