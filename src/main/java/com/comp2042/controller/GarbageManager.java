package com.comp2042.controller;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;


/**
 *Manages garbage brick behavior in the game.
 *Tracks whether garbage bricks should appear based on the level and updates the UI label accordingly.
 */
public class GarbageManager {

    private static final int GARBAGE_THRESHOLD_LEVEL = 3; // Level when garbage starts
    private final Label garbageInfoLabel;
    private boolean garbageActive = false;

    /**
     * @param garbageInfoLabel The label in the UI to show garbage status
     */
    public GarbageManager(Label garbageInfoLabel) {
        this.garbageInfoLabel = garbageInfoLabel;
        initializeGarbageInfo();
    }

    /**
     * Initializes the garbage info label to default state off.
     */
    private void initializeGarbageInfo() {
        if (garbageInfoLabel != null) {
            garbageInfoLabel.setText("Garbage: Off");
            garbageInfoLabel.setTextFill(Color.YELLOW);
        }
    }

    /**
     * Updates the garbage status based on current level
     * @param currentLevel The current game level
     */
    public void updateGarbageStatus(int currentLevel) {
        if (garbageInfoLabel == null) return;

        if (currentLevel >= GARBAGE_THRESHOLD_LEVEL) {
            garbageActive = true;
            garbageInfoLabel.setText("Garbage Brick: ON");
            garbageInfoLabel.setTextFill(Color.RED);
        } else {
            garbageActive = false;
            garbageInfoLabel.setText("Garbage Brick: Off");
            garbageInfoLabel.setTextFill(Color.YELLOW);
        }
    }

    /**
     * Checks whether garbage bricks are active
     * @return true if garbage bricks should appear
     */
    public boolean isGarbageActive() {
        return garbageActive;
    }
}
