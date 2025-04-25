module ru.nsu.tsyganov.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens ru.nsu.tsyganov.snake.model to javafx.base;
    opens ru.nsu.tsyganov.snake.controller to javafx.fxml;
    opens ru.nsu.tsyganov.snake.view to javafx.controls;

    exports ru.nsu.tsyganov.snake;
    exports ru.nsu.tsyganov.snake.model;
    exports ru.nsu.tsyganov.snake.controller;
    exports ru.nsu.tsyganov.snake.view;
}