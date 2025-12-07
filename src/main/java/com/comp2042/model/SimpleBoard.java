package com.comp2042.model;

import com.comp2042.controller.BrickRotator;
import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.view.BoardViewData;
import java.awt.Point;
import java.util.List;

/**
 * The SimpleBoard class represents the game board for Tetris.
 */
public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;

    // Hold
    private Brick heldBrick = null;          // stores the currently held brick
    private boolean hasHeldThisTurn = false; // prevents double hold until new brick spawns

    /**
     * @param width The width of the board
     * @param height The height of the board.
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    /**
     * @return true if the brick move down and false if it collided.
     */
    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * @return true if the brick moved left and false if it collided.
     */
    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * @return true if the brick moved right and false if it collided.
     */
    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * @return true if the brick was rotated successfully and false if it collided.
     */
    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    /**
     * @return true if new brick was placed and false if it was blocked.
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(4, 2); //Changed y value so that game doesn't end mid screen
        // allows hold after new piece

        hasHeldThisTurn = false;


        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(),
                (int) currentOffset.getX(), (int) currentOffset.getY());
    }


    // hold feature

    /**
     * Holds current brick. Prevents holding more than once per turn.
     */
    public void holdBrick() {
        if (hasHeldThisTurn)
            return; // prevent holding twice

        Brick current = brickRotator.getBrick();

        if (heldBrick == null) {
            heldBrick = current;
            createNewBrick();
        } else {
            // Swap the current brick with the held one
            Brick temp = heldBrick;
            heldBrick = current;
            brickRotator.setBrick(temp);
            currentOffset = new Point(4, 2);
        }

        hasHeldThisTurn = true;
    }

    /**
     * @return The held brick or null if none is held.
     */
    public Brick getHeldBrick() {
        return heldBrick;
    }

    /**
     * @return A 2d array representing the current state of the game board.
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     * @return A BoardViewData object containing information about the current board state.
     */
    @Override
    public BoardViewData getViewData() {

        // get the next three bricks from brick generator
        List<int[][]> nextThreeShapes = brickGenerator.getNextThreeShapes();

        return new BoardViewData(
                brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY(),
                brickGenerator.getNextBrick().getShapeMatrix().get(0),
                brickGenerator.getNextThreeShapes(),
                heldBrick == null ? null : heldBrick.getShapeMatrix().get(0)  // hold display
        );
    }

    /**
     * Merges the current brick into the game board.
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix,
                brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY());
    }

    /**
     * @return A ClearRow object containing the number of cleared rows and the updated board matrix.
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;
    }

    /**
     * @return The Score object representing the current score.
     */
    @Override
    public Score getScore() {
        return score;
    }

    /**
     * Resets the game state for new game.
     */
    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        heldBrick = null;         // reset hold on new game
        hasHeldThisTurn = false;
        createNewBrick();
    }

    /**
     * @return A 2D array representing the shape of the held brick, or null if no brick is held.
     */
    @Override
    public int[][] getHeldShape() {
        return heldBrick == null ? null : heldBrick.getShapeMatrix().get(0);
    }

    /**
     * @param newMatrix The new game board matrix to set.
     */
    @Override
    public void setBoardMatrix(int[][] newMatrix) {
        this.currentGameMatrix = new int[newMatrix.length][];
        for (int i = 0; i < newMatrix.length; i++) {
            this.currentGameMatrix[i] = newMatrix[i].clone();
        }
    }
}
