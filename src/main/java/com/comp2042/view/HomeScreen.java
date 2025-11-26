package com.comp2042.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class HomeScreen {

    public static Scene create(Stage stage, Runnable startGameCallback) {

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #7A00FF, #001DFF);");


        Text title = new Text("TETRIS");
        title.setFill(Color.WHITE);
        title.setFont(Font.font(50));

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-font-size: 18px;");
        startButton.setOnAction(e -> startGameCallback.run());

        Button instructionsButton = new Button("Instructions");
        instructionsButton.setStyle("-fx-font-size: 18px;");
        instructionsButton.setOnAction(e -> {
            Scene instructions = InstructionsScreen.create(stage, () -> {
                Scene home = create(stage, startGameCallback);
                stage.setScene(home);
            });
            stage.setScene(instructions);
        });

        root.getChildren().addAll(title, startButton, instructionsButton);

        return new Scene(root, 300, 510);
    }
}
