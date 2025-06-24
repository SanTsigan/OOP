package ru.nsu.tsyganov.snake.model;

public interface Actor {
    void update(GameModel model);
    boolean isAlive();
    default boolean isThreadSafe() { return false; }
}
