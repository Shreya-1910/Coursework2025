package com.comp2042.controller;

import com.comp2042.model.HighScore;
import javafx.scene.control.Label;

/**
 *This class handles updating the high score label and saving a new high score
 */
public class HighScoreManager {
    private final Label highScoreLabel;

    /**
     * @param highScoreLabel The label where the high score will be displayed.
     */
    public HighScoreManager(Label highScoreLabel) {
        this.highScoreLabel = highScoreLabel;
    }

    /**
     * Updates the high score label to show the current high score.
     */
    public void updateHighScoreDisplay() {
        highScoreLabel.setText("High Score: " + HighScore.load());
    }

    /**
     * @param score The current score to compare with the high score.
     */
    public void saveIfHigher(int score) {
        HighScore.saveIfHigher(score);
        updateHighScoreDisplay();
    }
}