/**
 * This package contains classes and interfaces that represent the core data and state of the Tetris game.
 * <p>
 * It defines the game board, scoring, and other data structures required to maintain
 * the current state of the game and perform operations on it.
 * <ul>
 *     <li>{@link com.comp2042.model.Board} - Interface representing a generic game board.</li>
 *     <li>{@link com.comp2042.model.ClearRow} - Handles the logic for clearing completed rows and updating the board.</li>
 *     <li>{@link com.comp2042.model.DownData} - Represents information about downward movement of bricks.</li>
 *     <li>{@link com.comp2042.model.HighScore} - Stores and manages high score information.</li>
 *     <li>{@link com.comp2042.model.MatrixOperations} - Provides utility methods for operating on the board matrix.</li>
 *     <li>{@link com.comp2042.model.NextShapeInfo} - Contains information about the next brick shape and its rotation.</li>
 *     <li>{@link com.comp2042.model.Score} - Tracks the player's current score and score updates.</li>
 *     <li>{@link com.comp2042.model.SimpleBoard} -  implementation of Board, representing the game board.</li>
 *     <li>{@link com.comp2042.model.BlockType} - Enum representing all Tetris block types, used for rendering, game logic, and color mapping.</li>
 *
 * </ul>
 * <p>
 * This package is the foundation of the game's state management, storing and manipulating
 * all data necessary for gameplay, scoring, and board updates.
 */
package com.comp2042.model;
