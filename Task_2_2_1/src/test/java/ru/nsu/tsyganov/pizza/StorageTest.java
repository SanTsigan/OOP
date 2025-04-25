package ru.nsu.tsyganov.pizza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StorageTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(5); // Склад вместимостью 5 пицц
    }

    @Test
    void testStorePizza() {
        Order order = new Order(1);
        storage.storePizza(order);
        assertEquals(1, storage.takePizzas(1).size(), "Пицца должна быть добавлена на склад");
    }

    @Test
    void testTakePizzas() {
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        storage.storePizza(order1);
        storage.storePizza(order2);

        assertEquals(2, storage.takePizzas(2).size(), "Должны быть взяты 2 пиццы");
    }

    @Test
    void testTakePizzasWhenEmpty() {
        assertTrue(storage.takePizzas(1).isEmpty(), "Склад пуст, пицц нет");
    }

    @Test
    void testStorePizzaWhenFull() throws InterruptedException {
        // Заполняем склад
        for (int i = 1; i <= 5; i++) {
            storage.storePizza(new Order(i));
        }

        // Пытаемся добавить ещё одну пиццу (склад полон)
        Thread thread = new Thread(() -> storage.storePizza(new Order(6)));
        thread.start();
        Thread.sleep(100); // Даём время для блокировки

        assertEquals(5, storage.takePizzas(5).size(), "Склад должен быть полон");
    }
}