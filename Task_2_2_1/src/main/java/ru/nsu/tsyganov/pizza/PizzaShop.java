package ru.nsu.tsyganov.pizza;

import java.util.LinkedList;
import java.util.Queue;

public class PizzaShop {
    private static Queue<Order> orderQueue = new LinkedList<>();
    private static boolean isOpen = true;

    public static synchronized Order getNextOrder() {
        return isOpen || !orderQueue.isEmpty() ? orderQueue.poll() : null;
    }

    public static void main(String[] args) {
        Storage storage = new Storage(10); // Склад на 10 пицц

        // Создаем пекарей
        for (int i = 1; i <= 3; i++) {
            new Thread(new Baker(i, 2, storage)).start(); // 3 пекаря с разной скоростью
        }

        // Создаем курьеров
        for (int i = 1; i <= 2; i++) {
            new Thread(new Courier(i, 3, storage)).start(); // 2 курьера с разной вместимостью
        }

        // Поступление заказов
        for (int i = 1; i <= 20; i++) {
            orderQueue.add(new Order(i));
            System.out.println("Order " + i + " has been placed.");
            try {
                Thread.sleep(1000); // Имитация времени между заказами
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Закрываем пиццерию
        isOpen = false;
    }
}