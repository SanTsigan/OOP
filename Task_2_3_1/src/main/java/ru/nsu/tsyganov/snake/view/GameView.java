package ru.nsu.tsyganov.snake.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ru.nsu.tsyganov.snake.model.Actor;
import ru.nsu.tsyganov.snake.model.Snake;
import ru.nsu.tsyganov.snake.model.Food;
import ru.nsu.tsyganov.snake.model.GameModel;

import javafx.geometry.Point2D;

import java.util.LinkedList;
import java.util.List;

public class GameView {
    private final Canvas canvas;
    private final double cellSize;
    private final Text textHelper = new Text();
    private static final Font UI_FONT = Font.font("Arial", 24);
    private static final Font MESSAGE_FONT = Font.font("Arial", FontWeight.BOLD, 48);

    public GameView(Canvas canvas, int gridWidth, int gridHeight) {
        this.canvas = canvas;
        this.cellSize = Math.min(
                canvas.getWidth() / gridWidth,
                canvas.getHeight() / gridHeight
        );
    }

    public void render(List<Actor> actors, GameModel model) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 1. Рисуем игровые объекты
        drawActors(gc, actors);

        // 3. Рисуем сообщения о конце игры
        if (model.isGameOver()) {
            drawCenteredMessage(gc, "GAME OVER", Color.RED);
        } else if (model.isGameWon()) {
            drawCenteredMessage(gc, "YOU WON!", Color.GREEN);
        }
    }

    private void drawActors(GraphicsContext gc, List<Actor> actors) {
        actors.forEach(actor -> {
            if (actor instanceof Snake) {
                drawSnake((Snake) actor);
            } else if (actor instanceof Food) {
                drawFood((Food) actor);
            }
        });
    }

    private void drawUI(GraphicsContext gc, GameModel model) {
        gc.setFont(UI_FONT);
        gc.setFill(Color.BLACK);
        String scoreText = "Score: " + model.getScore() + "/" + model.getTargetScore();
        gc.fillText(scoreText, 20, 30);
    }

    private void drawCenteredMessage(GraphicsContext gc, String text, Color color) {
        gc.setFont(MESSAGE_FONT);
        gc.setFill(color);

        double textWidth = computeTextWidth(text, MESSAGE_FONT);
        double x = (canvas.getWidth() - textWidth) / 2;
        double y = canvas.getHeight() / 2;

        gc.fillText(text, x, y);
    }

    private double computeTextWidth(String text, Font font) {
        textHelper.setText(text);
        textHelper.setFont(font);
        return textHelper.getLayoutBounds().getWidth();
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.LIGHTGRAY);
        for (int i = 0; i <= canvas.getWidth() / cellSize; i++) {
            gc.strokeLine(i * cellSize, 0, i * cellSize, canvas.getHeight());
        }
        for (int i = 0; i <= canvas.getHeight() / cellSize; i++) {
            gc.strokeLine(0, i * cellSize, canvas.getWidth(), i * cellSize);
        }
    }

    private void drawActor(Actor actor) {
        if (actor instanceof Snake) {
            drawSnake((Snake) actor);
        } else if (actor instanceof Food) {
            drawFood((Food) actor);
        }
    }

    private void drawSnake(Snake snake) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        snake.getBody().forEach(segment -> {
            gc.fillRect(
                    segment.getX() * cellSize,
                    segment.getY() * cellSize,
                    cellSize, cellSize
            );
        });

        // Выделяем голову
        gc.setFill(Color.DARKGREEN);
        Point2D head = snake.getHead();
        gc.fillRect(
                head.getX() * cellSize,
                head.getY() * cellSize,
                cellSize, cellSize
        );
    }

    private void drawFood(Food food) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        Point2D pos = food.getPosition();
        gc.fillOval(
                pos.getX() * cellSize,
                pos.getY() * cellSize,
                cellSize, cellSize
        );
    }
}