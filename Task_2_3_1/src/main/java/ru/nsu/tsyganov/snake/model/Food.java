package ru.nsu.tsyganov.snake.model;

import javafx.geometry.Point2D;

public class Food implements Actor{
    private final Point2D position;
    private volatile boolean eaten = false;

    public Food(Point2D position) {
        this.position = position;
    }

    @Override
    public void update(GameModel model) {
        // Еда не требует обновления
    }

    @Override
    public boolean isAlive() {
        return !eaten;
    }

    public Point2D getPosition() {
        return position;
    }

    public void markAsEaten() {
        eaten = true;
    }
}
