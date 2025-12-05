package com.comp2042.controller;

import com.comp2042.model.HighScore;
import javafx.scene.control.Label;

public class HighScoreManager {
    private final Label highScoreLabel;

    public HighScoreManager(Label highScoreLabel) {
        this.highScoreLabel = highScoreLabel;
    }

    public void updateHighScoreDisplay() {
        highScoreLabel.setText("High Score: " + HighScore.load());
    }

    public void saveIfHigher(int score) {
        HighScore.saveIfHigher(score);
        updateHighScoreDisplay();
    }
}