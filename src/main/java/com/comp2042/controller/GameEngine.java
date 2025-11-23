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
 * row clearing, game over detection and new game creation.
 * Ghost piece calculations are also performed here so GUI stays display only.
 */
public class GameEngine {

    private final Board board;

    /**
     * Initializes the game engine with a board of the given size.
     * @param rows number of rows in the board
     * @param cols number of columns in the board
     */
    public GameEngine(int rows, int cols) {
        board = new SimpleBoard(rows, cols);
        board.createNewBrick();
    }

    /**
     * Moves the current brick down and handles merging, row clearing, and score updates.
     * @param source source of the event.
     * @return DownData containing cleared rows, updated view, and game over.
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
     * @return 2D integer array representing the board
     */
    public int[][] getBoardMatrix() {
        return board.getBoardMatrix();
    }

    /**
     * Returns the current view data for the board.
     * @return BoardViewData object
     */
    public BoardViewData getViewData() {
        return board.getViewData();
    }

    /**
     * Returns the Score object for the current game.
     * @return Score object
     */

    //score for gui
    public int getScore() {
        return board.getScore().scoreProperty().get();
    }
//full score data
    public Score getScoreObject() {
        return board.getScore();
    }

    public BoardViewData hold() {
        board.holdBrick();
        return board.getViewData();
    }

//HardDrop logic
    /**
     * Performs a hard drop. Move the brick down until it cannot move,
     * then merge it, clear rows, update score and spawn next brick.
     */
    public BoardViewData hardDrop() {

    while (board.moveBrickDown()) {

        }

        board.mergeBrickToBackground();

        // Hard drop points
        board.getScore().add(20);

        ClearRow clearRow = board.clearRows();
        if (clearRow.getLinesRemoved() > 0) {
            board.getScore().add(clearRow.getScoreBonus());
        }

        board.createNewBrick();

        return board.getViewData();
    }



    //Ghost brick logic
    /**
     * Calculates the ghost piece (the position where the current brick would land
     * if dropped straight down).
     * @return BoardViewData representing the ghost piece's final position
     */
    public BoardViewData getGhostPiece() {
        BoardViewData real = board.getViewData();

        int[][] shape = real.getBrickData();
        int ghostX = real.getxPosition();
        int ghostY = real.getyPosition();

        // Simulate falling until collision
        while (canPlaceAt(shape, ghostX, ghostY + 1)) {
            ghostY++;
        }

        // Return a ghost BoardViewData
        return new BoardViewData(shape, ghostX, ghostY, true); // true = ghost
    }

    public int[][] getHeldShape() {
        if (board == null) return null;
        return board.getHeldShape();
    }


    /**
     * Checks if a brick shape can be placed at a given board location.
     * Used internally for ghost and movement simulation.
     * @param shape the brick's 2D array
     * @param x     x-position on board
     * @param y     y-position on board
     * @return true if placement is valid
     */
    private boolean canPlaceAt(int[][] shape, int x, int y) {
        int[][] matrix = board.getBoardMatrix();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {

                if (shape[i][j] != 0) { // solid block
                    int boardY = y + i;
                    int boardX = x + j;

                    // Out of bounds
                    if (boardY < 0 || boardY >= matrix.length ||
                            boardX < 0 || boardX >= matrix[0].length) {
                        return false;
                    }

                    // Collision with background
                    if (matrix[boardY][boardX] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
