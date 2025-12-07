/**
 * This package contains classes and interfaces related to event handling in the Tetris game.
 * <p>
 * It defines the framework for generating, representing, and processing game events,
 * allowing different parts of the game to communicate in a decoupled manner.
 * <ul>
 *     <li>{@link com.comp2042.events.EventSource} - Interface for objects that can generate events.</li>
 *     <li>{@link com.comp2042.events.EventType} - Interface representing types of events that can occur.</li>
 *     <li>{@link com.comp2042.events.MoveEvent} - Represents a move action in the game, such as moving or rotating a brick.</li>
 * </ul>
 * <p>
 * This package provides the foundation for handling user input, game state changes,
 * and other actions in a structured and modular way.
 *
 * @author Shreya
 * @version 1.0
 */
package com.comp2042.events;
