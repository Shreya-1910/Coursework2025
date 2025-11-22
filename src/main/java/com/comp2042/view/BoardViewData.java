package com.comp2042.view;

import com.comp2042.model.MatrixOperations;
import java.util.List;
import java.util.ArrayList;


public final class BoardViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    //store next 3 bricks
    private final List<int[][]> nextThreeData;


    private final boolean ghost;

    /**
     * This is used for normal bricks.
     */
    public BoardViewData(int[][] brickData, int xPosition, int yPosition,
                         int[][] nextBrickData,
                         List<int[][]> nextThreeData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;

        //store copies of next 3 bricks
        this.nextThreeData = copyList(nextThreeData);

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
        this.nextThreeData = null;
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

    private List<int[][]> copyList(List<int[][]> list) {
        if (list == null) return null;

        List<int[][]> result = new ArrayList<>();
        for (int[][] m : list) {
            result.add(MatrixOperations.copy(m));
        }
        return result;
    }
    //returns next 3 bricks
    public List<int[][]> getNextThreeData() {
        return nextThreeData;
    }
}