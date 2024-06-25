// finaly version

package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Game {
    private Pane heartPane;
    private Pane boxPane;
    private ArrayList<Rectangle> rectangles;
    private static final double SHIFT_AMOUNT = 50;
    private Button runBtn;
    private Button attackBtn;
    private boolean flag = false;
    private int damageNum = 0;
    private boolean isJmping = false;
    private Rectangle enemyBox;
    private Player player;

    public Game(Stage stage) {
        boxPane = new Pane();
        rectangles = new ArrayList<>();

        // Set background
        Rectangle backGround = new Rectangle(500, 700);
        Image bgImage = new Image("file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/background.png");
        ImagePattern imagePattern = new ImagePattern(bgImage);
        backGround.setFill(imagePattern);

        // Create player
        player = new Player(195, 250, 60, 100, "file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/Knight.gif");

        // Create hearts
        Rectangle hearts;
        heartPane = new Pane();
        for (int i = 0; i < 3; i++) {
            hearts = new Rectangle((i * 50), 0, 50, 50);
            Image bgHeart = new Image("file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/heart.png");
            ImagePattern heartImagePattern = new ImagePattern(bgHeart);
            hearts.setFill(heartImagePattern);
            heartPane.getChildren().add(hearts);
        }

        // Create ground
        int width = 0;
        Rectangle ground;
        Group root = new Group();
        for (int i = 0; i < 10; i++) {
            ground = new Rectangle(width + (i * 50), 350, 50, 50);
            Image groundBgImage = new Image("file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/brick.png");
            ImagePattern groundImagePattern = new ImagePattern(groundBgImage);
            ground.setFill(groundImagePattern);
            boxPane.getChildren().add(ground);
            rectangles.add(ground);
        }

        // Create run button
        runBtn = new Button("");
        runBtn.setPrefWidth(100);
        runBtn.setPrefHeight(100);
        runBtn.setLayoutX(100);
        runBtn.setLayoutY(600);

        // Set run button image
        ImageView imageView = new ImageView(new Image("file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/run.png"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        runBtn.setGraphic(imageView);

        // Run button event
        runBtn.setOnMouseClicked(event -> {
            if (boxPane.getChildren().indexOf(enemyBox) < 5)
                flag = false;

            runBtn.setDisable(true);

            if (!isJmping) {
                jumpPlayer();
            }

            int randomInt = (int) (Math.random() * 3);

            if (randomInt < 2) {
                addBox();
            } else if (randomInt == 2) {
                if (!flag) {
                    addEnemy();
                } else {
                    addBox();
                }
            }

            rectangles.remove(0);
            boxPane.getChildren().remove(0);

            if (boxPane.getChildren().indexOf(enemyBox) == 4) {
                damageNum++;
                heartPane.getChildren().remove(3 - damageNum);
            }

            if (damageNum == 3) {
                gameOverScreen();
            }
        });

        // Create attack button
        attackBtn = new Button("");
        attackBtn.setPrefWidth(100);
        attackBtn.setPrefHeight(100);
        attackBtn.setLayoutX(300);
        attackBtn.setLayoutY(600);

        // Set attack button image
        ImageView imageVieww = new ImageView(new Image("file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/attack.gif"));
        imageVieww.setFitWidth(100);
        imageVieww.setFitHeight(100);
        attackBtn.setGraphic(imageVieww);

        // Attack button event
        attackBtn.setOnMouseClicked(event -> {
            if (boxPane.getChildren().indexOf(enemyBox) == 5 || boxPane.getChildren().indexOf(enemyBox) == 4)
                attackButton();
        });

        // Show screen
        Group group = new Group(backGround, heartPane, player.getPlayer(), root, boxPane, runBtn, attackBtn);
        Scene scene = new Scene(group, 500, 700);
        stage.setTitle("Hoppenhelm!");
        stage.setScene(scene);
        stage.show();
    }

    // Making player movable
    public void jumpPlayer() {
        double originalY = player.getPlayer().getLayoutY();
        double newY = originalY - 80;  // move player up 50px

        // Upper animation
        Timeline moveTimeline = new Timeline(
                new KeyFrame(Duration.millis(125), // animation time
                        new KeyValue(player.getPlayer().layoutYProperty(), newY))
        );

        // Downer animation
        Timeline returnTimeline = new Timeline(
                new KeyFrame(Duration.millis(125), // animation time
                        new KeyValue(player.getPlayer().layoutYProperty(), originalY))
        );

        // Making continues animation
        SequentialTransition sequentialTransition = new SequentialTransition(moveTimeline, returnTimeline);
        sequentialTransition.setOnFinished(event -> isJmping = false);
        isJmping = true;
        sequentialTransition.play();
    }

    // Add box
    public void addBox() {
        // Create a new rectangle with specific properties
        Rectangle standardBox = new Rectangle(500, 350, 50, 50);
        Image newBox = new Image("file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/brick1.png");
        ImagePattern newBoxImagePattern = new ImagePattern(newBox);
        standardBox.setFill(newBoxImagePattern);

        // Add the new rectangle to the pane and list
        boxPane.getChildren().add(standardBox);
        rectangles.add(standardBox);

        // Shift existing rectangles to the left
        for (Rectangle rect : rectangles) {
            rect.setLayoutX(rect.getLayoutX() - SHIFT_AMOUNT);
        }
        runBtn.setDisable(false);
    }

    // Add enemy
    public void addEnemy() {
        flag = true;

        // Create a new enemy
        enemyBox = new Rectangle(418, 210, 160, 140);
        Image newBox = new Image("file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/enemy.gif");
        ImagePattern newBoxImagePattern = new ImagePattern(newBox);
        enemyBox.setFill(newBoxImagePattern);

        Rectangle enemyGroundBox = new Rectangle(500, 350, 50, 50);
        Image enemyGround = new Image("file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/brick1.png");
        ImagePattern enemyGroundImagePattern = new ImagePattern(enemyGround);
        enemyGroundBox.setFill(enemyGroundImagePattern);

        // Add the new rectangle to the pane and list
        boxPane.getChildren().add(enemyBox);
        rectangles.add(enemyBox);
        boxPane.getChildren().add(enemyGroundBox);
        rectangles.add(enemyGroundBox);

        // Shift existing rectangles to the left
        for (Rectangle rect : rectangles) {
            rect.setLayoutX(rect.getLayoutX() - SHIFT_AMOUNT);
        }

        // Disable run button
        runBtn.setDisable(false);
    }

    // Attack button event
    public void attackButton() {
        if (boxPane.getChildren().indexOf(enemyBox) == 5) {
            boxPane.getChildren().remove(5);
            flag = false;
        } else if (boxPane.getChildren().indexOf(enemyBox) == 4) {
            boxPane.getChildren().remove(4);
            flag = false;
        }
    }

    // Show gameOver screen & stop the game
    public void gameOverScreen() {
        // Make gameOver screen
        Rectangle gameOver = new Rectangle(500, 700);
        Image gameOverBg = new Image("file:///C:/Users/select/Desktop/projects/ground/demo/src/main/resources/images/gameover.gif");
        ImagePattern gameOverImagePattern = new ImagePattern(gameOverBg);
        gameOver.setFill(gameOverImagePattern);

        // Show gameOver screen & stop the game
        Group GameOver = new Group(gameOver);
        Scene gameOverScreen = new Scene(GameOver, 500, 700);
        Stage stage = new Stage();
        stage.setTitle("GameOver!");
        stage.setScene(gameOverScreen);
        stage.show();
        runBtn.setDisable(true);
        attackBtn.setDisable(true);
    }
}
