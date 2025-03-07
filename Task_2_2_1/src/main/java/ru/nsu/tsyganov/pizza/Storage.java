package ru.nsu.tsyganov.pizza;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Storage {
    private Queue<Order> storage = new LinkedList<>();
    private int capacity;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void storePizza(Order order) {
        while (storage.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.add(order);
        notifyAll();
    }

    public synchronized List<Order> takePizzas(int count) {
        while (storage.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Order> orders = new LinkedList<>();
        for (int i = 0; i < count && !storage.isEmpty(); i++) {
            orders.add(storage.poll());
        }
        notifyAll();
        return orders;
    }
}
