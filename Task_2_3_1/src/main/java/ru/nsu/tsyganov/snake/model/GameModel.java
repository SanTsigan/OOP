package ru.nsu.tsyganov.snake.model;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameModel {
    private final ActorSystem actorSystem;
    private final int width;
    private final int height;
    private final Random random = new Random();
    private boolean gameOver = false;
    private boolean gameWon = false;
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final int targetLength = 100;

    public GameModel(int width, int height) {
        this.width = width;
        this.height = height;
        this.actorSystem = new ActorSystem(this);
        initializeGame();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    private void initializeGame() {
        Point2D startPosition = new Point2D(width / 2, height / 2);
        actorSystem.addActor(new Snake(startPosition));
        spawnFood(3);
    }

    public void update() {
        if (gameOver || gameWon) return;

        checkFoodCollisions();

        actorSystem.update();
        checkGameConditions();
        maintainFood();
    }

    private void checkFoodCollisions() {
        List<Snake> snakes = actorSystem.getActorsOfType(Snake.class);
        List<Food> foods = actorSystem.getActorsOfType(Food.class);

        for (Snake snake : snakes) {
            Point2D head = snake.getHead();

            // Ищем еду, которую касается голова змейки
            for (Food food : foods) {
                if (head.equals(food.getPosition())) {
                    food.markAsEaten();
                    snake.grow();
                    score.set(score.get() + 1);// Увеличиваем змейку
                }
            }
        }
    }

    private void checkGameConditions() {
        // Проверка проигрыша (все змейки мертвы)
        boolean snakesAlive = actorSystem.getActorsOfType(Snake.class)
                .stream()
                .anyMatch(Snake::isAlive);

        gameOver = !snakesAlive;

        // Проверка победы (достигнут целевой счет)
        gameWon = score.get() >= targetLength;
    }

    private void maintainFood() {
        if (actorSystem.getActorsOfType(Food.class).size() < 3) {
            spawnFood(1);
        }
    }

    private void spawnFood(int count) {
        for (int i = 0; i < count; i++) {
            Point2D position;
            do {
                position = new Point2D(random.nextInt(width), random.nextInt(height));
            } while (isPositionOccupied(position));
            actorSystem.addActor(new Food(position));
        }
    }

    private boolean isPositionOccupied(Point2D position) {
        return actorSystem.getActorsOfType(Snake.class).stream()
                .flatMap(snake -> snake.getBody().stream())
                .anyMatch(p -> p.equals(position));
    }

    public Snake getPlayerSnake() {
        return actorSystem.getActors().stream()
                .filter(a -> a instanceof Snake)
                .map(a -> (Snake) a)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Snake not found"));
    }

    // Геттеры и сеттеры
    public List<Actor> getActors() { return actorSystem.getActors(); }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon() { return gameWon; }

    public void reset() {
        gameOver = false;
        gameWon = false;
        actorSystem.clear();
        initializeGame();
    }

    public int getScore() {
        return score.get();
    }

    public int getTargetScore() {
        return targetLength;
    }
}
