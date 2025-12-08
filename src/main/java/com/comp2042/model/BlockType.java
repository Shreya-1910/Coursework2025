package com.comp2042.model;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * This enum acts as a central mapping between game logic values and UI colours,
 * improving readability and maintainability.
 */
public enum BlockType {
    EMPTY(0, Color.TRANSPARENT),
    I(1, Color.AQUA),
    L(2, Color.ORANGE),
    S(3, Color.DARKGREEN),
    O(4, Color.YELLOW),
    Z(5, Color.RED),
    J(6, Color.BEIGE),
    T(7, Color.BURLYWOOD),
    GARBAGE(8, Color.BLACK);

    private final int value;
    private final Paint color;

    /**
     * @param value the numeric grid value of the block.
     * @param color the visual colour of the block.
     */
    BlockType(int value, Paint color) {
        this.value = value;
        this.color = color;
    }

    /**
     * @param value the numeric value from the game grid.
     * @return the matching {@link Paint} colour, or {@link Color#WHITE} if no match is found
     */
    public static Paint fromValue(int value) {
        for (BlockType type : values()) {
            if (type.value == value) {
                return type.color;
            }
        }
        return Color.WHITE;
    }
}
