/**
 * This package contains the main controller classes for the Tetris game.
 * <p>
 * The classes in this package are responsible for handling the game's core logic,
 * input, and interactions between the model and the view. Key responsibilities include:
 * <ul>
 *     <li>{@link com.comp2042.controller.GuiController} - Handles the user interface and input events.</li>
 *     <li>{@link com.comp2042.controller.GameController} - Manages the overall game state and logic flow.</li>
 *     <li>{@link com.comp2042.controller.GameRenderer} - Responsible for rendering the game board and bricks.</li>
 *     <li>{@link com.comp2042.controller.HighScoreManager} - Manages high scores and persistence.</li>
 *     <li>{@link com.comp2042.controller.GameLoop} - Controls the main game loop and timing.</li>
 *     <li>{@link com.comp2042.controller.GameEngine} - Encapsulates the core game mechanics and rules.</li>
 *     <li>{@link com.comp2042.controller.GarbageManager} - Handles garbage bricks and their activation logic.</li>
 *     <li>{@link com.comp2042.controller.InputEventListener} - Listens for and processes input events.</li>
 *     <li>{@link com.comp2042.controller.InputHandler} - Handles player input actions and translates them into game commands.</li>
 *     <li>{@link com.comp2042.controller.BrickRotator} - Manages brick rotation and shape transitions.</li>
 * </ul>
 * <p>
 * This package acts as the bridge between the model (game state) and the view (UI),
 * coordinating gameplay, user actions, and visual updates.
 */
package com.comp2042.controller;
