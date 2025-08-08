package com.example.app;

public class App {
    public static void main(String[] args) {
        String name = args.length > 0 ? String.join(" ", args) : "World";
        System.out.printf("Hello, %s!%n", name);
    }
}