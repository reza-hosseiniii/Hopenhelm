package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Player {
    private Rectangle player;

    public Player(double x, double y, double width, double height, String imagePath) {
        player = new Rectangle(x, y, width, height);
        Image bgPlayer = new Image(imagePath);
        ImagePattern playerImagePattern = new ImagePattern(bgPlayer);
        player.setFill(playerImagePattern);
    }

    public Rectangle getPlayer() {
        return player;
    }
}
