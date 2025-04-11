package ru.nsu.tsyganov.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 500);

        // Даем фокус сцене
        scene.setOnMouseClicked(e -> scene.getRoot().requestFocus());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Змейка");
        primaryStage.show();

        // Фокус на корневой элемент
        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}