package com.comp2042.view;

import com.comp2042.model.MatrixOperations;

public final class BoardViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;


    private final boolean ghost;

    /**
     * This is used for normal bricks.
     */
    public BoardViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
        this.ghost = false;  // default
    }

    /**
     * New for ghost pieces.
     * nextBrickData is left null because ghosts do not need a next brick.
     */
    public BoardViewData(int[][] brickData, int xPosition, int yPosition, boolean ghost) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.ghost = ghost;
        this.nextBrickData = null; // ghost does not show next brick
    }

    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }

    /**
     * Returns true if this BoardViewData represents a ghost piece.
     */
    public boolean isGhost() {
        return ghost;
    }
}
