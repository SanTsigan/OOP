package ru.nsu.tsyganov.pizza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PizzaShopTest {
    @BeforeEach
    void setUp() {
        PizzaShop.getOrderQueue().clear(); // Очищаем очередь заказов перед каждым тестом
    }

    @Test
    void testOrderQueue() {
        Order order = new Order(1);
        PizzaShop.getOrderQueue().add(order);

        assertEquals(1, PizzaShop.getOrderQueue().size(), "Заказ должен быть добавлен в очередь");
    }
}