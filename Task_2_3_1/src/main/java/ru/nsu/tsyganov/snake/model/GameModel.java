package ru.nsu.tsyganov.snake.model;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private Snake snake;
    private List<Food> foods;
    private int width;
    private int height;
    private int foodCount;
    private int targetLength;
    private boolean gameOver;
    private boolean gameWon;
    private Random random;

    public GameModel(int width, int height, int foodCount, int targetLength) {
        this.width = width;
        this.height = height;
        this.foodCount = foodCount;
        this.targetLength = targetLength;
        this.random = new Random();

        resetGame();
    }

    public void resetGame() {
        Point2D startPosition = new Point2D((double) width / 2, (double) height / 2);
        snake = new Snake(startPosition);
        foods = new ArrayList<>();
        gameOver = false;
        gameWon = false;

        for (int i = 0; i < foodCount; i++) {
            spawnFood();
        }
    }

    public void update() {
        if (gameOver || gameWon) return;

        snake.move();

        // Check collisions with walls
        Point2D head = snake.getHead();
        if (head.getX() < 0 || head.getX() >= width ||
                head.getY() < 0 || head.getY() >= height) {
            gameOver = true;
            return;
        }

        // Check collision with self
        if (snake.collidesWithSelf()) {
            gameOver = true;
            return;
        }

        // Check collision with food
        Food foodToRemove = null;
        for (Food food : foods) {
            if (food.getPosition().equals(head)) {
                snake.grow();
                foodToRemove = food;
                break;
            }
        }

        if (foodToRemove != null) {
            foods.remove(foodToRemove);
            spawnFood();
        }

        // Check win condition
        if (snake.getBody().size() >= targetLength) {
            gameWon = true;
        }
    }

    private void spawnFood() {
        Point2D position = null;
        Point2D finalPosition = position;
        do {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            position = new Point2D(x, y);
        } while (snake.collidesWith(position) ||
                foods.stream().anyMatch(f -> f.getPosition().equals(finalPosition)));

        foods.add(new Food(position));
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
