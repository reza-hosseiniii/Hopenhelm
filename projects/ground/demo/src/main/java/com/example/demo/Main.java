package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Group;

public class Main extends Application {

    private Game game;

    @Override
    public void start(Stage stage) {
        game = new Game(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
