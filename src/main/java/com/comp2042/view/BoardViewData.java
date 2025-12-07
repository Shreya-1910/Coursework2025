package com.comp2042.view;

import com.comp2042.model.MatrixOperations;
import java.util.List;
import java.util.ArrayList;


/**
 * Represents the view data for the game board, including the current brick,
 * its position, the next brick, the next three upcoming bricks, and the held brick.
 */
public final class BoardViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;

    // store next 3 bricks
    private final List<int[][]> nextThreeData;

    //store held piece
    private final int[][] holdBrickData;

    private final boolean ghost;


    /**
     * @param brickData The current brick's shape
     * @param xPosition The X position of the current brick on the board.
     * @param yPosition The Y position of the current brick on the board.
     * @param nextBrickData The shape of the next brick
     * @param nextThreeData List of the shapes of the next three bricks.
     * @param holdBrickData The shape of the held brick, or null if no brick is held.
     */
    public BoardViewData(int[][] brickData, int xPosition, int yPosition,
                         int[][] nextBrickData,
                         List<int[][]> nextThreeData,
                         int[][] holdBrickData
    ) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;

        // store copies of next 3 bricks
        this.nextThreeData = copyList(nextThreeData);
        this.holdBrickData = holdBrickData == null ? null : MatrixOperations.copy(holdBrickData);
        this.ghost = false;  //default
    }


    /**
     * @param brickData The shape of the ghost piece.
     * @param xPosition The X position of the ghost piece on the board.
     * @param yPosition The Y position of the ghost piece on the board.
     * @param ghost Boolean flag to indicate this is a ghost piece.
     */
    public BoardViewData(int[][] brickData, int xPosition, int yPosition, boolean ghost) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.ghost = ghost;
        this.nextBrickData = null; // ghost does not show next brick
        this.nextThreeData = null;
        this.holdBrickData = null;
    }


    /**
     * @return A 2D array representing the current brick's shape.
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * @return The X position of the current brick.
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * @return The Y position of the current brick.
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * @return the next brick's shape.
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    /**
     * Returns true if this BoardViewData represents a ghost piece.
     */
    public boolean isGhost() {
        return ghost;
    }

    /**
     * @param list The list of 2D arrays (int[][]) to be copied.
     * @return A new list containing deep copies of each 2D array in the input list.If the input list is null,
     * returns null.
     */
    private List<int[][]> copyList(List<int[][]> list) {
        if (list == null) return null;

        List<int[][]> result = new ArrayList<>();
        for (int[][] m : list) {
            result.add(MatrixOperations.copy(m));
        }
        return result;
    }

    /**
     * @return A list of 2D arrays representing the shapes of the next three upcoming bricks.
     * This list may be null if there are no upcoming bricks.
     */
    public List<int[][]> getNextThreeData() {
        return nextThreeData;
    }


    /**
     * @return A 2D array representing the shape of the held brick, or null if no brick is held.
     */
    public int[][] getHoldBrickData() {
        return holdBrickData == null ? null : MatrixOperations.copy(holdBrickData);
    }
}
