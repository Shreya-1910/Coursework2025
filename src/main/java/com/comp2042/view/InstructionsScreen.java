package com.comp2042.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class InstructionsScreen {

    public static Scene create(Stage stage, Runnable startGameCallback) {

        Label title = new Label("How to Play");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label instructions = new Label(
                "Controls:\n\n" +
                        "← / A : Move Left\n" +
                        "→ / D : Move Right\n" +
                        "↓ / S : Move Down Faster\n" +
                        "↑ / W : Rotate\n" +
                        "P : Pause / Unpause\n" +
                        "N : New Game\n\n" +
                        "Goal:\n" +
                        "Clear horizontal lines.\n" +
                        "Don't let bricks reach the top!"
        );
        instructions.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        instructions.setWrapText(true);

        Button back = new Button("Back");
        back.setStyle("-fx-font-size: 20px; -fx-padding: 10px 30px;");

        // Call HomeScreen.create(stage, startGameCallback)
        back.setOnAction(e -> {
            stage.setScene(HomeScreen.create(stage, startGameCallback));
        });

        VBox layout = new VBox(20, title, instructions, back);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color:#0033cc ;");

        return new Scene(layout, 300, 510);
    }
}
