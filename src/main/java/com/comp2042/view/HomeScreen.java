package com.comp2042.view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeScreen {

    private static final String FONT_PATH = "/digital.ttf";

    public static Scene create(Stage stage, Runnable startGameCallback) {
        StackPane root = new StackPane();

        try {
            Image backgroundImage = new Image(HomeScreen.class.getResourceAsStream("/Back.jpg"));
            ImageView backgroundView = new ImageView(backgroundImage);
            backgroundView.fitWidthProperty().bind(root.widthProperty());
            backgroundView.fitHeightProperty().bind(root.heightProperty());
            backgroundView.setPreserveRatio(false);
            root.getChildren().add(backgroundView);
        } catch (Exception e) {
            System.err.println("Could not load background image: " + e.getMessage());
            root.setStyle("-fx-background-color: linear-gradient(to bottom, #7A00FF, #001DFF);");
        }

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);

        final Font gameFont;
        Font tempFont = Font.loadFont(HomeScreen.class.getResourceAsStream(FONT_PATH), 50);
        if (tempFont == null) {
            System.err.println("Font not found! Using default system font.");
            tempFont = Font.font("Arial", 50);
        }
        gameFont = tempFont;

        Text title = new Text("TETRIS");
        title.setFill(Color.WHITE);
        title.fontProperty().bind(Bindings.createObjectBinding(() -> {
            double windowWidth = root.widthProperty().get();
            double fontSize = 50 * (1 + (windowWidth - 300) * 0.0005);
            fontSize = Math.min(fontSize, 80);
            fontSize = Math.max(fontSize, 50);
            return Font.font(gameFont.getName(), fontSize);
        }, root.widthProperty()));

        Button startButton = new Button("Start Game");
        startButton.getStyleClass().add("ipad-dark-grey");
        startButton.setFont(Font.font(gameFont.getName(), 18));
        startButton.setPrefWidth(200);
        startButton.setPrefHeight(50);
        startButton.setOnAction(e -> startGameCallback.run());

        Button instructionsButton = new Button("Instructions");
        instructionsButton.getStyleClass().add("ipad-dark-grey");
        instructionsButton.setFont(Font.font(gameFont.getName(), 18));
        instructionsButton.setPrefWidth(200);
        instructionsButton.setPrefHeight(50);
        instructionsButton.setOnAction(e -> {

            Scene instructions = InstructionsScreen.create(stage, () -> {
                Scene home = create(stage, startGameCallback);
                stage.setScene(home);
            });
            stage.setScene(instructions);
        });

        content.getChildren().addAll(title, startButton, instructionsButton);
        root.getChildren().add(content);

        Scene scene = new Scene(root, 400, 600);


        scene.getStylesheets().add(HomeScreen.class.getResource("/window_style.css").toExternalForm());

        stage.setResizable(true);
        return scene;
    }
}
