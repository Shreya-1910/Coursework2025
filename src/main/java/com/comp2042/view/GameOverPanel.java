package com.comp2042.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


/**
 * he GameOverPanel class represents the UI panel displayed when the game is over.
 */
public class GameOverPanel extends BorderPane {

    /**
     * Constructs a new game over panel.
     */
    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }

}
