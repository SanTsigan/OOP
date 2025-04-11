package ru.nsu.tsyganov.snake.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import ru.nsu.tsyganov.snake.model.Actor;
import ru.nsu.tsyganov.snake.model.GameModel;
import ru.nsu.tsyganov.snake.model.Snake;
import ru.nsu.tsyganov.snake.view.GameView;
import ru.nsu.tsyganov.snake.model.Direction;

import java.util.LinkedList;
import java.util.List;

public class GameController {
    @FXML public Label scoreLabel;
    @FXML private Canvas gameCanvas;
    private GameModel model;
    private GameView view;
    private AnimationTimer gameLoop;
    private Snake playerSnake;

    @FXML
    public void initialize() {
        // Инициализация модели и представления
        model = new GameModel(20, 15);
        view = new GameView(gameCanvas, 20, 15);

        // Получаем управляемую змейку
        playerSnake = model.getPlayerSnake();

        // Настройка обработки ввода
        setupInputHandling();

        // Привязываем свойство счета к Label
        scoreLabel.textProperty().bind(
                Bindings.concat("Score: ")
                        .concat(model.scoreProperty())
                        .concat("/")
                        .concat(model.getTargetScore())
        );

        // Запуск игры
        startGame();
    }

    private void setupInputHandling() {
        // 1. Обработчик для сцены
        gameCanvas.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::handleKeyPress);
            }
        });

        // 2. Дополнительный обработчик для Canvas
        gameCanvas.setOnKeyPressed(this::handleKeyPress);

        // 3. Гарантированное получение фокуса
        gameCanvas.setFocusTraversable(true);
        Platform.runLater(() -> {
            gameCanvas.requestFocus();
            System.out.println("Canvas focused: " + gameCanvas.isFocused());
        });
    }

    private void handleKeyPress(KeyEvent event) {
        System.out.println("Key pressed: " + event.getCode()); // Отладочный вывод

        if (playerSnake == null) return;

        switch (event.getCode()) {
            case UP -> playerSnake.setDirection(Direction.UP);
            case DOWN -> playerSnake.setDirection(Direction.DOWN);
            case LEFT -> playerSnake.setDirection(Direction.LEFT);
            case RIGHT -> playerSnake.setDirection(Direction.RIGHT);
            case ESCAPE -> gameLoop.stop();
        }

        event.consume(); // Предотвращаем дальнейшую обработку
    }

    private void startGame() {
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) { // 100ms
                    model.update();
                    view.render(model.getActors(), model);
                    if (model.isGameOver() || model.isGameWon()) {
                        stop();
                    }
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }
}
