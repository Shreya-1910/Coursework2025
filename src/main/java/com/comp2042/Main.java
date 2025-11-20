package com.comp2042;

import com.comp2042.controller.GameController;
import com.comp2042.controller.GuiController;
import com.comp2042.view.HomeScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Show home screen first
        Scene home = HomeScreen.create(primaryStage, () -> startGame(primaryStage));

        primaryStage.setTitle("TetrisJFX");
        primaryStage.setScene(home);
        primaryStage.show();
    }

    /**
     * Loads the real game screen (gameLayout.fxml).
     */
    private void startGame(Stage primaryStage) {
        try {
            URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
            FXMLLoader loader = new FXMLLoader(location);

            Parent root = loader.load();
            GuiController gui = loader.getController();

            // Create game controller AFTER the GUI is loaded
            new GameController(gui);

            primaryStage.setScene(new Scene(root, 300, 510));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
