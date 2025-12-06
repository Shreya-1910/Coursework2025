package com.comp2042.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class represents the player's score in the game.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * @return The IntegerProperty representing the current score.
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * @param i The value to add to the current score.
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * Resets to 0 when game is restarted.
     */
    public void reset() {
        score.setValue(0);
    }

    /**
     * @return The current score.
     */
    public int get() {
        return score.get();
    }
}
