package ru.nsu.tsyganov.pizza;

import java.util.List;

public class Courier implements Runnable {
    private int courierId;
    private int trunkCapacity;
    private Storage storage;

    public Courier(int courierId, int trunkCapacity, Storage storage) {
        this.courierId = courierId;
        this.trunkCapacity = trunkCapacity;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (PizzaShop.isRunning() || !storage.isEmpty()) { //флажок про пекарей
            List<Order> orders = storage.takePizzas(trunkCapacity);
            if (orders.isEmpty()) {
                try {
                    Thread.sleep(1000); // Ждём новых пицц
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                continue;
            }

            System.out.println("Courier " + courierId + " is delivering orders: " + orders);
            try {
                Thread.sleep(2000); // Имитация времени доставки
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            System.out.println("Courier " + courierId + " has delivered orders: " + orders);
            for (Order order : orders) {
                PizzaShop.orderCompleted();
            }
        }
        System.out.println("Courier " + courierId + " has finished work.");
    }
}