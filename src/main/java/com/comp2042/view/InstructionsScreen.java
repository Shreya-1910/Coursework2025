package com.comp2042.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InstructionsScreen {

    public static Scene create(Stage stage, Runnable startGameCallback) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #0a001a;");
        try {
            Image bgImage = new Image(InstructionsScreen.class.getResource("/Back.jpg").toExternalForm());
            ImageView background = new ImageView(bgImage);

            background.setPreserveRatio(false); // Stretch to fill
            background.fitWidthProperty().bind(root.widthProperty());
            background.fitHeightProperty().bind(root.heightProperty());

            root.getChildren().add(background);

        } catch (Exception e) {
            System.out.println("Background image not found, using gradient fallback");
            root.setStyle("-fx-background-color: linear-gradient(to bottom, #8a2be2, #ff00ff, #ff1493);");
        }

        VBox mainContent = new VBox(15);
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setPadding(new Insets(30, 60, 30, 60));
        mainContent.setMaxSize(1000, 700);

        // Title with neon glow effect
        Label title = new Label("HOW TO PLAY");
        title.setStyle("-fx-font-size: 48px; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: 900; " +
                "-fx-font-family: 'Impact', 'Arial Black', sans-serif; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 105, 180, 0.8), 0, 0, 0, 3); " +
                "-fx-padding: 0 0 20 0;");

        HBox mainContainer = new HBox(50);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(10, 0, 30, 0));

        VBox controlsColumn = new VBox(10);
        controlsColumn.setAlignment(Pos.TOP_CENTER);
        controlsColumn.setPadding(new Insets(15, 30, 15, 30));
        controlsColumn.setMinWidth(400);

        Label controlsTitle = new Label("CONTROLS");
        controlsTitle.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: #ff99ff; " +
                "-fx-font-weight: bold; " +
                "-fx-font-family: 'Arial', sans-serif; " +
                "-fx-padding: 0 0 20 0; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 153, 255, 0.5), 5, 0, 0, 2);");

        VBox controlsBox = new VBox(10);
        controlsBox.setPadding(new Insets(0, 0, 0, 10));

        addControlPoint(controlsBox, "← / A : Move Left");
        addControlPoint(controlsBox, "→ / D : Move Right");
        addControlPoint(controlsBox, "↓ / S : Move Down");
        addControlPoint(controlsBox, "↑ / W : Rotate");
        addControlPoint(controlsBox, "SPACE : Hard Drop");
        addControlPoint(controlsBox, "P : Pause / Unpause");
        addControlPoint(controlsBox, "N : New Game");

        controlsColumn.getChildren().addAll(controlsTitle, controlsBox);

        VBox gameplayColumn = new VBox(10);
        gameplayColumn.setAlignment(Pos.TOP_CENTER);
        gameplayColumn.setPadding(new Insets(15, 30, 15, 30));
        gameplayColumn.setMinWidth(400);

        Label gameplayTitle = new Label("GAMEPLAY");
        gameplayTitle.setStyle("-fx-font-size: 32px; " +
                "-fx-text-fill: #ff99ff; " +
                "-fx-font-weight: bold; " +
                "-fx-font-family: 'Arial', sans-serif; " +
                "-fx-padding: 0 0 20 0; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 153, 255, 0.5), 5, 0, 0, 2);");

        VBox gameplayBox = new VBox(10);
        gameplayBox.setPadding(new Insets(0, 0, 0, 10));

        addGameplayPoint(gameplayBox, "• 50 points for each line cleared");
        addGameplayPoint(gameplayBox, "• 20 bonus points for hard drop");
        addGameplayPoint(gameplayBox, "• Clear 5 lines to advance to next level");
        addGameplayPoint(gameplayBox, "• Speed increases with each level");
        addGameplayPoint(gameplayBox, "• Level 3+: Random garbage blocks spawn");
        addGameplayPoint(gameplayBox, "• React quickly to survive!");
        gameplayColumn.getChildren().addAll(gameplayTitle, gameplayBox);

        mainContainer.getChildren().addAll(controlsColumn, gameplayColumn);

        Button back = new Button("BACK TO MENU");
        back.setStyle("-fx-font-size: 22px; " +
                "-fx-padding: 12px 45px; " +
                "-fx-background-color: rgba(153, 50, 204, 0.8); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-family: 'Arial', sans-serif; " +
                "-fx-background-radius: 30; " +
                "-fx-border-radius: 30; " +
                "-fx-border-color: #ff66ff; " +
                "-fx-border-width: 2; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 102, 255, 0.5), 10, 0, 0, 3);");

        back.setOnMouseEntered(e -> {
            back.setStyle("-fx-font-size: 22px; " +
                    "-fx-padding: 12px 45px; " +
                    "-fx-background-color: rgba(186, 85, 211, 0.9); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Arial', sans-serif; " +
                    "-fx-background-radius: 30; " +
                    "-fx-border-radius: 30; " +
                    "-fx-border-color: #ff99ff; " +
                    "-fx-border-width: 2; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(255, 153, 255, 0.7), 15, 0, 0, 5);");
        });

        back.setOnMouseExited(e -> {
            back.setStyle("-fx-font-size: 22px; " +
                    "-fx-padding: 12px 45px; " +
                    "-fx-background-color: rgba(153, 50, 204, 0.8); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Arial', sans-serif; " +
                    "-fx-background-radius: 30; " +
                    "-fx-border-radius: 30; " +
                    "-fx-border-color: #ff66ff; " +
                    "-fx-border-width: 2; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(255, 102, 255, 0.5), 10, 0, 0, 3);");
        });

        // Button action
        back.setOnAction(e -> stage.setScene(HomeScreen.create(stage, startGameCallback)));

        mainContent.getChildren().addAll(title, mainContainer, back);

        root.getChildren().add(mainContent);


        Scene scene = new Scene(root, 1100, 750);

        applyTextShadows(mainContent);

        return scene;
    }

    private static void addControlPoint(VBox box, String text) {
        HBox pointBox = new HBox(10);
        pointBox.setAlignment(Pos.CENTER_LEFT);

        Label point = new Label(text);
        point.setStyle("-fx-font-size: 19px; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: 500; " +
                "-fx-wrap-text: true; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 3, 0, 1, 1);");

        pointBox.getChildren().add(point);
        box.getChildren().add(pointBox);
    }
    private static void addGameplayPoint(VBox box, String text) {
        HBox pointBox = new HBox(10);
        pointBox.setAlignment(Pos.CENTER_LEFT);

        Label point = new Label(text);
        point.setStyle("-fx-font-size: 19px; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: 500; " +
                "-fx-wrap-text: true; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 3, 0, 1, 1);");

        pointBox.getChildren().add(point);
        box.getChildren().add(pointBox);
    }

    private static void applyTextShadows(Pane container) {
        for (javafx.scene.Node node : container.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                String currentStyle = label.getStyle();
                if (!currentStyle.contains("-fx-effect")) {
                    label.setStyle(currentStyle + " -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 3, 0, 1, 1);");
                }
            } else if (node instanceof Pane) {
                applyTextShadowsToChildren((Pane) node);
            }
        }
    }

    private static void applyTextShadowsToChildren(Pane pane) {
        for (javafx.scene.Node node : pane.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                String currentStyle = label.getStyle();
                if (!currentStyle.contains("-fx-effect")) {
                    label.setStyle(currentStyle + " -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 3, 0, 1, 1);");
                }
            } else if (node instanceof Pane) {
                applyTextShadowsToChildren((Pane) node);
            }
        }
    }
}
