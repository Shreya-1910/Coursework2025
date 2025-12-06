package com.comp2042.events;

/**
 *Enum represents the different types of events in the game.
 */
public enum EventType {
    /**
     * Represents a move event where the piece moves down.
     */
    DOWN,
    /**
     * Represents a move event where the piece moves left.
     */
 LEFT,
    /**
     *Represents a move event where the piece moves right.
     */
 RIGHT,
    /**
     * Represents a rotate event where piece is rotated.
     */
 ROTATE,
    /**
     * Represents a hard drop event where piece is hard dropped.
     */
 HARDDROP,
    /**
     *Represents a hold event where current piece is held.
     */
 HOLD
}
