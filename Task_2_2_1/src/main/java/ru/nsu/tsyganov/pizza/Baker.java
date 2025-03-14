package ru.nsu.tsyganov.pizza;

public class Baker implements Runnable {
    private int bakerId;
    private int cookingSpeed; // Время приготовления одной пиццы
    private Storage storage;

    public Baker(int bakerId, int cookingSpeed, Storage storage) {
        this.bakerId = bakerId;
        this.cookingSpeed = cookingSpeed;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            Order order = PizzaShop.getNextOrder();
            if (order == null) break; // Если заказов больше нет, завершаем работу

            System.out.println("Order " + order.getOrderId() + " is being prepared by Baker " + bakerId);
            try {
                Thread.sleep(cookingSpeed * 1000); // Имитация времени приготовления
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            storage.storePizza(order);
            System.out.println("Order " + order.getOrderId() + " is ready and stored by Baker " + bakerId);
        }
    }
}