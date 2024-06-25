package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Enemy {
    private Rectangle enemy;

    public Enemy(double x, double y, double width, double height, String imagePath) {
        enemy = new Rectangle(x, y, width, height);
        Image bgEnemy = new Image(imagePath);
        ImagePattern enemyImagePattern = new ImagePattern(bgEnemy);
        enemy.setFill(enemyImagePattern);
    }

    public Rectangle getEnemy() {
        return enemy;
    }
}
