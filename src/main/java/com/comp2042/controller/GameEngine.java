package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.model.Board;
import com.comp2042.model.ClearRow;
import com.comp2042.model.DownData;
import com.comp2042.model.Score;
import com.comp2042.model.SimpleBoard;
import com.comp2042.view.BoardViewData;

/**
 * Handles all the game logic including brick movement, rotations, scoring,
 * row clearing, and new game creation. This class does not interact with the GUI.
 */
public class GameEngine {

    private final Board board;

    /**
     * Initializes the game engine with a board of the given size.
     *
     * @param rows number of rows in the board
     * @param cols number of columns in the board
     */
    public GameEngine(int rows, int cols) {
        board = new SimpleBoard(rows, cols);
        board.createNewBrick();
    }

    /**
     * Moves the current brick down and handles merging, row clearing, and score updates.
     *
     * @param source source of the event (user or system)
     * @return DownData containing cleared rows, updated view, and game over flag
     */
    public DownData moveDown(EventSource source) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        boolean gameOver = false;

        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                gameOver = true;
            }
        } else if (source == EventSource.USER) {
            board.getScore().add(1);
        }

        return new DownData(clearRow, board.getViewData(), gameOver);
    }

    /**
     * Moves the current brick to the left.
     *
     * @return updated BoardViewData
     */
    public BoardViewData moveLeft() {
        board.moveBrickLeft();
        return board.getViewData();
    }

    /**
     * Moves the current brick to the right.
     *
     * @return updated BoardViewData
     */
    public BoardViewData moveRight() {
        board.moveBrickRight();
        return board.getViewData();
    }

    /**
     * Rotates the current brick left.
     *
     * @return updated BoardViewData
     */
    public BoardViewData rotate() {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    /**
     * Resets the board and starts a new game.
     */
    public void newGame() {
        board.newGame();
    }

    /**
     * Returns the current board matrix.
     *
     * @return 2D integer array representing the board
     */
    public int[][] getBoardMatrix() {
        return board.getBoardMatrix();
    }

    /**
     * Returns the current view data for the board.
     *
     * @return BoardViewData object
     */
    public BoardViewData getViewData() {
        return board.getViewData();
    }

    /**
     * Returns the Score object for the current game.
     *
     * @return Score object
     */
    public Score getScore() {
        return board.getScore();
    }
}
