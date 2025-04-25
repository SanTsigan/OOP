package ru.nsu.tsyganov.pizza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BakerTest {
    private Storage storage;
    private Baker baker;

    @BeforeEach
    void setUp() {
        storage = new Storage(5);
        baker = new Baker(1, 2, storage); // Пекарь с ID 1 и скоростью 2 секунды
    }

    @Test
    void testBakerPreparesOrder() throws InterruptedException {
        Order order = new Order(1);
        PizzaShop.getOrderQueue().add(order);

        Thread bakerThread = new Thread(baker);
        bakerThread.start();
        Thread.sleep(3000); // Ждём, пока пекарь приготовит пиццу

        assertFalse(storage.takePizzas(1).isEmpty(), "Пицца должна быть приготовлена и добавлена на склад");
    }
}