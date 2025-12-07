package com.comp2042.controller;

import com.comp2042.view.BoardViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.List;

/**
 * Handles the rendering of the game ui such as the drawing of the game board,
 * the current brick,the next 3 pieces,the hold piece and the ghost piece.
 */
public class GameRenderer {
    private static final int BRICK_SIZE = 20;
    private static final int GARBAGE_BLOCK_VALUE = 8;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;
    private final GridPane gamePanel;
    private final GridPane brickPanel;
    private final GridPane nextPiece1;
    private final GridPane nextPiece2;
    private final GridPane nextPiece3;
    private final GridPane holdPiece;

    /**
     * @param gamePanel the main panel where the game board is displayed
     * @param brickPanel the panel for displaying the current brick
     * @param nextPiece1 the panel for displaying the next piece in the game
     * @param nextPiece2 the panel for displaying the second next piece in the game
     * @param nextPiece3 the panel for displaying the third next piece in the game.
     * @param holdPiece the panel for displaying the held piece
     */
    public GameRenderer(GridPane gamePanel, GridPane brickPanel,
                        GridPane nextPiece1, GridPane nextPiece2, GridPane nextPiece3,
                        GridPane holdPiece) {
        this.gamePanel = gamePanel;
        this.brickPanel = brickPanel;
        this.nextPiece1 = nextPiece1;
        this.nextPiece2 = nextPiece2;
        this.nextPiece3 = nextPiece3;
        this.holdPiece = holdPiece;
    }

    /**
     * @param boardMatrix the 2D array representing the current state of the game board.
     */
    public void initDisplayMatrix(int[][] boardMatrix) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }
    }

    /**
     * @param brick the current {@link BoardViewData} object representing the brick
     */
    public void initBrickRectangles(BoardViewData brick) {
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
    }

    /**
     * @param brick the current {@link BoardViewData} object representing the brick
     */
    public void positionBrickPanel(BoardViewData brick) {
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
    }

    /**
     * @param i the integer value representing the brick type
     * @return a {@link Paint} object representing the fill color for the brick
     */
    public Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.ORANGE;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.BEIGE;
                break;
            case 7:
                returnPaint = Color.BURLYWOOD;
                break;
            case GARBAGE_BLOCK_VALUE:
                returnPaint = Color.BLACK;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }

    /**
     * clears ghost pieces by resetting its opacity to 1.
     */
    public void clearGhost() {
        if (displayMatrix == null) return;

        for (int i = 2; i < displayMatrix.length; i++) {
            for (int j = 0; j < displayMatrix[i].length; j++) {
                displayMatrix[i][j].setOpacity(1.0);
            }
        }
    }


    /**
     * Refreshes ghost piece.
     * @param ghost the {@link BoardViewData} object representing the ghost piece
     */
    public void refreshGhost(BoardViewData ghost) {
        if (ghost == null || !ghost.isGhost()) return;
        int[][] shape = ghost.getBrickData();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int boardY = ghost.getyPosition() + i;
                    int boardX = ghost.getxPosition() + j;
                    if (boardY >= 2 && boardY < displayMatrix.length &&
                            boardX >= 0 && boardX < displayMatrix[0].length) {
                        Rectangle r = displayMatrix[boardY][boardX];
                        r.setFill(Color.LIGHTGRAY);
                        r.setOpacity(0.30);
                    }
                }
            }
        }
    }

    /**
     * @param brick the {@link BoardViewData} object representing the current brick.
     * @param isPaused whether the game is paused.
     */
    public void refreshBrick(BoardViewData brick, boolean isPaused) {
        if (isPaused) return;

        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
            }
        }

        if (brick.getNextThreeData() != null) {
            drawPreview(brick.getNextThreeData().get(0), nextPiece1);
            drawPreview(brick.getNextThreeData().get(1), nextPiece2);
            drawPreview(brick.getNextThreeData().get(2), nextPiece3);
        }
    }

    /**
     * @param shape the shape data of the next piece
     * @param target the {@link GridPane} to display the next piece preview
     */
    private void drawPreview(int[][] shape, GridPane target) {
        target.getChildren().clear();
        if (shape == null) return;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                Rectangle r = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                r.setFill(getFillColor(shape[i][j]));
                target.add(r, j, i);
            }
        }
    }

    /**
     * @param board the 2D array representing the current state of the game board
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * @param color the integer value representing the brick's color
     * @param rectangle the {@link Rectangle} object whose properties will be updated
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    /**
     * @param heldShape a 2d array representing the shape of the held piece.
     */
    public void updateHoldPiece(int[][] heldShape) {
        holdPiece.getChildren().clear();
        if (heldShape == null) return;
        for (int i = 0; i < heldShape.length; i++) {
            for (int j = 0; j < heldShape[i].length; j++) {
                Rectangle r = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                r.setFill(getFillColor(heldShape[i][j]));
                holdPiece.add(r, j, i);
            }
        }
    }

    /**
     * @return the integer value represnting the garbage block.
     */
    public static int getGarbageBlockValue() {
        return GARBAGE_BLOCK_VALUE;
    }

    /**
     * @return a 2D array of {@link Rectangle} objects representing the current state of the game board.
     */
    public Rectangle[][] getDisplayMatrix() {
        return displayMatrix;
    }

    /**
     * @return a 2D array of {@link Rectangle} objects representing the current brick's shape.
     */
    public Rectangle[][] getRectangles() {
        return rectangles;
    }


}