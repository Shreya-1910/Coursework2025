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

    public ClearRow getClearRow() {
        return clearRow;
    }

    public BoardViewData getViewData() {
        return boardViewData;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
