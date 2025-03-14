package ru.nsu.tsyganov.pizza;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class PizzaShop {
    private static Queue<Order> orderQueue = new LinkedList<>();
    private static boolean isOpen = true;
    private static AtomicInteger activeOrders = new AtomicInteger(0); // Счётчик активных заказов

    public static synchronized Order getNextOrder() {
        return isOpen || !orderQueue.isEmpty() ? orderQueue.poll() : null;
    }

    public static void main(String[] args) {
        // Загрузка конфигурации
        PizzaShopConfig config = ConfigLoader.loadConfig("config.json");
        if (config == null) {
            System.out.println("Failed to load configuration.");
            return;
        }

        Storage storage = new Storage(config.getStorageCapacity());

        // Создаем пекарей
        for (BakerConfig bakerConfig : config.getBakers()) {
            new Thread(new Baker(bakerConfig.getId(), bakerConfig.getCookingSpeed(), storage)).start();
        }

        // Создаем курьеров
        for (CourierConfig courierConfig : config.getCouriers()) {
            new Thread(new Courier(courierConfig.getId(), courierConfig.getTrunkCapacity(), storage)).start();
        }

        // Поступление заказов
        new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                if (!isOpen) break; // Если пиццерия закрыта, прекращаем приём заказов
                orderQueue.add(new Order(i));
                activeOrders.incrementAndGet(); // Увеличиваем счётчик активных заказов
                System.out.println("Order " + i + " has been placed.");
                try {
                    Thread.sleep(1000); // Имитация времени между заказами
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isOpen = false; // Останавливаем приём заказов
            System.out.println("Pizza shop is no longer accepting new orders.");
        }).start();

        // Закрываем пиццерию через указанное время
        try {
            Thread.sleep(config.getSimulationDuration() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isOpen = false; // Останавливаем приём заказов

        // Ждём завершения всех оставшихся заказов
        while (activeOrders.get() > 0) {
            try {
                Thread.sleep(1000); // Проверяем каждую секунду
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All orders have been completed. Pizza shop is now closed.");
    }

    public static void orderCompleted() {
        activeOrders.decrementAndGet(); // Уменьшаем счётчик активных заказов
    }
}