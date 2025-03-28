module ru.nsu.tsyganov.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens ru.nsu.tsyganov.snake.model to javafx.base;
    opens com.example.snake.controller to javafx.fxml;

    exports ru.nsu.tsyganov.snake;
    exports ru.nsu.tsyganov.snake.model;
    exports ru.nsu.tsyganov.snake.controller;
    exports ru.nsu.tsyganov.snake.view;
}