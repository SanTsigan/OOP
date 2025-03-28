package ru.nsu.tsyganov.snake.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.tsyganov.snake.model.GameModel;
import ru.nsu.tsyganov.snake.model.Snake;
import ru.nsu.tsyganov.snake.model.Food;

import javafx.geometry.Point2D;

public class GameView {
    private Canvas canvas;
    private GraphicsContext gc;
    private double cellSize;

    public GameView(Canvas canvas, int width, int height) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.cellSize = Math.min(canvas.getWidth() / width, canvas.getHeight() / height);
    }

    public void render(GameModel model) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw grid
        gc.setStroke(Color.LIGHTGRAY);
        for (int i = 0; i <= model.getWidth(); i++) {
            gc.strokeLine(i * cellSize, 0, i * cellSize, model.getHeight() * cellSize);
        }
        for (int i = 0; i <= model.getHeight(); i++) {
            gc.strokeLine(0, i * cellSize, model.getWidth() * cellSize, i * cellSize);
        }

        // Draw snake
        gc.setFill(Color.GREEN);
        for (Point2D segment : model.getSnake().getBody()) {
            gc.fillRect(segment.getX() * cellSize, segment.getY() * cellSize, cellSize, cellSize);
        }

        // Draw head
        gc.setFill(Color.DARKGREEN);
        Point2D head = model.getSnake().getHead();
        gc.fillRect(head.getX() * cellSize, head.getY() * cellSize, cellSize, cellSize);

        // Draw food
        gc.setFill(Color.RED);
        for (Food food : model.getFoods()) {
            Point2D position = food.getPosition();
            gc.fillOval(position.getX() * cellSize, position.getY() * cellSize, cellSize, cellSize);
        }

        // Draw game over or win message
        if (model.isGameOver()) {
            drawCenteredText("Game Over!", Color.RED);
        } else if (model.isGameWon()) {
            drawCenteredText("You Win!", Color.GREEN);
        }
    }

    private void drawCenteredText(String text, Color color) {
        gc.setFill(color);
        gc.setFont(javafx.scene.text.Font.font(48));
        gc.fillText(text,
                (canvas.getWidth() - gc.getFont().getSize() * text.length() / 2) / 2,
                canvas.getHeight() / 2);
    }

    public double getCellSize() {
        return cellSize;
    }
}