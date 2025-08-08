package com.example.app;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from Java!\n");
        if (args.length > 0) {
            System.out.println("Args:");
            for (int i = 0; i < args.length; i++) {
                System.out.printf("  [%d] %s%n", i, args[i]);
            }
        }
    }
}