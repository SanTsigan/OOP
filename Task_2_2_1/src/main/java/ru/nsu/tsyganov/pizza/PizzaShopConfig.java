package ru.nsu.tsyganov.pizza;

import java.util.List;

public class PizzaShopConfig {
    private int storageCapacity;
    private List<BakerConfig> bakers;
    private List<CourierConfig> couriers;
    private int simulationDuration; // Время работы пиццерии в секундах

    // Геттеры и сеттеры
    public int getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(int storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public List<BakerConfig> getBakers() {
        return bakers;
    }

    public void setBakers(List<BakerConfig> bakers) {
        this.bakers = bakers;
    }

    public List<CourierConfig> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<CourierConfig> couriers) {
        this.couriers = couriers;
    }

    public int getSimulationDuration() {
        return simulationDuration;
    }

    public void setSimulationDuration(int simulationDuration) {
        this.simulationDuration = simulationDuration;
    }
}

class BakerConfig {
    private int id;
    private int cookingSpeed; // Время приготовления одной пиццы в секундах

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCookingSpeed() {
        return cookingSpeed;
    }

    public void setCookingSpeed(int cookingSpeed) {
        this.cookingSpeed = cookingSpeed;
    }
}

class CourierConfig {
    private int id;
    private int trunkCapacity; // Вместимость багажника (количество пицц)

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrunkCapacity() {
        return trunkCapacity;
    }

    public void setTrunkCapacity(int trunkCapacity) {
        this.trunkCapacity = trunkCapacity;
    }
}
