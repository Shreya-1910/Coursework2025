package com.comp2042.model;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.view.BoardViewData;

/**
 * Interface representing the game board for a Tetris.It defines the key operations for manipulating the board,
 * including moving bricks, rotating them, merging them with the background,
 * clearing rows, and tracking game state such as score and held bricks.
 */
public interface Board {

    /**
     * @return true if the brick was successfully moved down, false otherwise
     */
    boolean moveBrickDown();

    /**
     * @return true if the brick was successfully moved left, false otherwise
     */
    boolean moveBrickLeft();

    /**
     * @return true if the brick was successfully moved right, false otherwise
     */
    boolean moveBrickRight();

    /**
     * @return true if the brick successfully rotated, false otherwise
     */
    boolean rotateLeftBrick();

    /**
     * @return true if a new brick was successfully created, false otherwise
     */
    boolean createNewBrick();

    /**
     * @return A 2D array of integers representing the game board.
     */
    int[][] getBoardMatrix();

    /**
     * @return A {@link BoardViewData} object representing the visual state of the board.
     */
    BoardViewData getViewData();

    /**
     * Merges the current brick into the game board background, effectively locking it into place.
     */
    void mergeBrickToBackground();

    /**
     * @return A {@link ClearRow} object representing the rows that were cleared.
     */
    ClearRow clearRows();

    /**
     * @return A {@link Score} object representing the player's score.
     */
    Score getScore();

    /**
     * Starts a new game, resetting the board and other game state.
     */
    void newGame();

    /**
     * Holds the current brick, swapping it with the held brick if there is one.
     * The held brick can be used later during the game.
     */
    void holdBrick();

    /**
     * @return A 2D integer array representing the shape of the held brick.
     */
    int[][] getHeldShape();

    /**
     * @param matrix A 2D integer array representing the new state of the board.
     */
    void setBoardMatrix(int[][] matrix);
}
