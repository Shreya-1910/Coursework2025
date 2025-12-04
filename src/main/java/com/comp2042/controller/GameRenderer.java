package com.comp2042.controller;

import com.comp2042.view.BoardViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.List;

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

    public void positionBrickPanel(BoardViewData brick) {
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
    }

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

    public void clearGhost() {
        if (displayMatrix == null) return;

        for (int i = 2; i < displayMatrix.length; i++) {
            for (int j = 0; j < displayMatrix[i].length; j++) {
                displayMatrix[i][j].setOpacity(1.0);
            }
        }
    }

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

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

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

    public static int getGarbageBlockValue() {
        return GARBAGE_BLOCK_VALUE;
    }
}