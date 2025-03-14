package ru.nsu.tsyganov.pizza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourierTest {
    private Storage storage;
    private Courier courier;

    @BeforeEach
    void setUp() {
        storage = new Storage(5);
        courier = new Courier(1, 3, storage); // Курьер с ID 1 и вместимостью 3 пиццы
    }

    @Test
    void testCourierDeliversOrders() throws InterruptedException {
        // Добавляем пиццы на склад
        storage.storePizza(new Order(1));
        storage.storePizza(new Order(2));

        Thread courierThread = new Thread(courier);
        courierThread.start();
        Thread.sleep(3000); // Ждём, пока курьер доставит пиццы

        assertTrue(storage.takePizzas(1).isEmpty(), "Пиццы должны быть доставлены");
    }
}