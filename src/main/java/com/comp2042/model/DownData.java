package com.comp2042.model;

import com.comp2042.view.BoardViewData;

/**
 * Represents the result of moving a brick down, including
 * cleared rows, updated board view, and game-over status.
 */
public final class DownData {

    private final ClearRow clearRow;
    private final BoardViewData boardViewData;
    private final boolean gameOver;

    /**
     * Creates a DownData object.
     * @param clearRow cleared rows information
     * @param boardViewData current board view
     * @param gameOver true if the game is over, false otherwise
     */
    public DownData(ClearRow clearRow, BoardViewData boardViewData, boolean gameOver) {
        this.clearRow = clearRow;
        this.boardViewData = boardViewData;
        this.gameOver = gameOver;
    }

    /**
     * @return A {@link ClearRow} object containing the number of rows cleared and the updated board state.
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * @return A {@link BoardViewData} object representing the current visual state of the game board.
     */
    public BoardViewData getViewData() {
        return boardViewData;
    }

    /**
     * @return true if the game is over, otherwise false.
     */
    public boolean isGameOver() {
        return gameOver;
    }
}
