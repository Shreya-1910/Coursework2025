package com.comp2042.events;

/**
 * Enum represents the source of an event in the game.
 */
public enum EventSource {
    /**
     * Indicates that the event was triggered by the user
     */
    USER,
    /**
     *Indicates that the event was triggered by the game loop or background thread
     */
 THREAD
}
