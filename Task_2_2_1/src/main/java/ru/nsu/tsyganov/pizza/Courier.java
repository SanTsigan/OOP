package ru.nsu.tsyganov.pizza;

import java.util.List;

public class Courier implements Runnable {
    private int courierId;
    private int trunkCapacity; // Вместимость багажника
    private Storage storage;

    public Courier(int courierId, int trunkCapacity, Storage storage) {
        this.courierId = courierId;
        this.trunkCapacity = trunkCapacity;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            List<Order> orders = storage.takePizzas(trunkCapacity);
            if (orders.isEmpty()) break; // Если пицц больше нет, завершаем работу

            System.out.println("Courier " + courierId + " is delivering orders: " + orders);
            try {
                Thread.sleep(2000); // Имитация времени доставки
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Courier " + courierId + " has delivered orders: " + orders);
            for (Order order : orders) {
                PizzaShop.orderCompleted(); // Уменьшаем счётчик активных заказов
            }
        }
    }
}