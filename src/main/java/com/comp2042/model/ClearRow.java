package com.comp2042.model;

/**
 *Represents the result of clearing rows on the game board.
 * This class encapsulates information about the number of rows that were cleared,
 * the new state of the board after the rows were cleared, and the score bonus awarded for clearing the rows.
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;

    /**
     * @param linesRemoved The number of lines that were removed from the board.
     * @param newMatrix The new state of the game board after the rows were cleared.
     * @param scoreBonus The score bonus earned for clearing the rows.
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * @return The number of lines removed from the board.
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * @return  a deep of the new game board.
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * @return the score bonus for clearing the rows.
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
