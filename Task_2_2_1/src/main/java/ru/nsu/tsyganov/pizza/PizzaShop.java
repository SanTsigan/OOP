package ru.nsu.tsyganov.pizza;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class PizzaShop {
    private static Queue<Order> orderQueue = new LinkedList<>();
    private static volatile boolean isRunning = true;
    private static AtomicInteger activeOrders = new AtomicInteger(0); // Счётчик активных заказов

    public static synchronized Order getNextOrder() { //должен блокировать пока running и empty
        // Блокируем поток, пока isRunning и очередь пуста
        while (isRunning && orderQueue.isEmpty()) {
            try {
                PizzaShop.class.wait(); // Ожидаем появления нового заказа
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return orderQueue.poll(); // Возвращаем заказ, если он есть
    }

    public static synchronized void addOrder(Order order) {
        orderQueue.add(order);
        activeOrders.incrementAndGet();
        PizzaShop.class.notifyAll(); // Уведомляем ожидающие потоки о новом заказе
    }

    public static synchronized void stop() {
        isRunning = false;
        PizzaShop.class.notifyAll(); // Уведомляем все потоки о завершении работы
    }

    public static boolean isRunning() {
        return isRunning;
    }

    public static void orderCompleted() {
        activeOrders.decrementAndGet(); // Уменьшаем счётчик активных заказов
    }

    public static Queue<Order> getOrderQueue() {
        return orderQueue;
    }

    public static void main(String[] args) throws InterruptedException {
        PizzaShopConfig config =
                ConfigLoader.loadConfig(PizzaShop.class.getClassLoader().
                        getResource("config.json").getPath());
        if (config == null) {
            System.out.println("Failed to load configuration.");
            return;
        }

        Storage storage = new Storage(config.getStorageCapacity());

        // Создаем пекарей
        List<Thread> bakerThreads = new ArrayList<>();
        for (BakerConfig bakerConfig : config.getBakers()) {
            Thread bakerThread = new Thread(new Baker(bakerConfig.getId(), bakerConfig.getCookingSpeed(), storage));
            bakerThread.start();
            bakerThreads.add(bakerThread);
        }

        // Создаем курьеров
        List<Thread> courierThreads = new ArrayList<>();
        for (CourierConfig courierConfig : config.getCouriers()) {
            Thread courierThread = new Thread(new Courier(courierConfig.getId(), courierConfig.getTrunkCapacity(), storage));
            courierThread.start();
            courierThreads.add(courierThread);
        }

        // Поступление заказов
        new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                if (!isRunning) break;
                orderQueue.add(new Order(i));
                activeOrders.incrementAndGet();
                System.out.println("Order " + i + " has been placed.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isRunning = false;
            System.out.println("Pizza shop is no longer accepting new orders.");
        }).start();

        // Закрываем пиццерию через указанное время
        try {
            Thread.sleep(config.getSimulationDuration() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isRunning = false;

        // Ждём завершения всех оставшихся заказов
        while (activeOrders.get() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Завершаем все потоки
        for (Thread bakerThread : bakerThreads) {
            bakerThread.interrupt();
            bakerThread.join();
        }
        for (Thread courierThread : courierThreads) {
            courierThread.interrupt();
            courierThread.join();
        }

        System.out.println("All orders have been completed. Pizza shop is now closed.");
    }
}