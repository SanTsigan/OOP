package ru.nsu.tsyganov.snake.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import ru.nsu.tsyganov.snake.model.Direction;
import ru.nsu.tsyganov.snake.model.GameModel;
import ru.nsu.tsyganov.snake.view.GameView;

public class GameController {
    @FXML private Canvas gameCanvas;
    @FXML private Button startButton;
    @FXML private Label scoreLabel;

    private GameModel model;
    private GameView view;
    private javafx.animation.AnimationTimer gameLoop;

    public void initialize() {
        int width = 20;
        int height = 15;
        int foodCount = 3;
        int targetLength = 10;

        model = new GameModel(width, height, foodCount, targetLength);
        view = new GameView(gameCanvas, width, height);

        gameLoop = new javafx.animation.AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) { // 100ms delay
                    model.update();
                    view.render(model);
                    updateScore();

                    if (model.isGameOver() || model.isGameWon()) {
                        stop();
                    }

                    lastUpdate = now;
                }
            }
        };
    }

    @FXML
    private void handleStartButton() {
        model.resetGame();
        gameLoop.start();
        gameCanvas.requestFocus();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                model.getSnake().setDirection(Direction.UP);
                break;
            case DOWN:
                model.getSnake().setDirection(Direction.DOWN);
                break;
            case LEFT:
                model.getSnake().setDirection(Direction.LEFT);
                break;
            case RIGHT:
                model.getSnake().setDirection(Direction.RIGHT);
                break;
            default:
                break;
        }
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + (model.getSnake().getBody().size() - 1));
    }
}
