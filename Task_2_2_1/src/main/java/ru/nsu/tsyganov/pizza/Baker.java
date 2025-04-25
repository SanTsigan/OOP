package ru.nsu.tsyganov.pizza;

public class Baker implements Runnable {
    private int bakerId;
    private int cookingSpeed;
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
            if (order == null) {
                break;
            }
                //break когда etNextOrder() блокирущи

            System.out.println("Order " + order.getOrderId() + " is being prepared by Baker " + bakerId);
            try {
                Thread.sleep(cookingSpeed * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            storage.storePizza(order);
            System.out.println("Order " + order.getOrderId() + " is ready and stored by Baker " + bakerId);
        }
        System.out.println("Baker " + bakerId + " has finished work.");
    }
}