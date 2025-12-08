package com.comp2042.controller;

import com.comp2042.view.BoardViewData;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameRendererTest {

    private GridPane gamePanel;
    private GridPane brickPanel;
    private GridPane nextPiece1;
    private GridPane nextPiece2;
    private GridPane nextPiece3;
    private GridPane holdPiece;
    private GameRenderer gameRenderer;

    @BeforeEach
    void setUp() {
        gamePanel = new GridPane();
        brickPanel = new GridPane();
        nextPiece1 = new GridPane();
        nextPiece2 = new GridPane();
        nextPiece3 = new GridPane();
        holdPiece = new GridPane();

        gameRenderer = new GameRenderer(
                gamePanel, brickPanel, nextPiece1, nextPiece2, nextPiece3, holdPiece
        );
    }

    @Test
    void testConstructor() {
        assertNotNull(gameRenderer);
    }

    @Test
    void testInitDisplayMatrix() {
        int[][] boardMatrix = new int[22][10];
        gameRenderer.initDisplayMatrix(boardMatrix);

        Rectangle[][] displayMatrix = gameRenderer.getDisplayMatrix();
        assertNotNull(displayMatrix);
        assertEquals(boardMatrix.length, displayMatrix.length);
        assertEquals(boardMatrix[0].length, displayMatrix[0].length);

        // Check that rectangles are created starting from row index 2
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                assertNotNull(displayMatrix[i][j]);
                assertEquals(Color.TRANSPARENT, displayMatrix[i][j].getFill());
            }
        }

        // Rows 0 and 1 should remain null
        assertNull(displayMatrix[0][0]);
        assertNull(displayMatrix[1][0]);
    }

    @Test
    void testGetFillColor_WithEnum() {
        assertEquals(Color.TRANSPARENT, gameRenderer.getFillColor(0));
        assertEquals(Color.AQUA, gameRenderer.getFillColor(1));
        assertEquals(Color.ORANGE, gameRenderer.getFillColor(2));
        assertEquals(Color.DARKGREEN, gameRenderer.getFillColor(3));
        assertEquals(Color.YELLOW, gameRenderer.getFillColor(4));
        assertEquals(Color.RED, gameRenderer.getFillColor(5));
        assertEquals(Color.BEIGE, gameRenderer.getFillColor(6));
        assertEquals(Color.BURLYWOOD, gameRenderer.getFillColor(7));
        assertEquals(Color.BLACK, gameRenderer.getFillColor(8)); // Garbage block
        assertEquals(Color.WHITE, gameRenderer.getFillColor(99)); // default
        assertEquals(Color.WHITE, gameRenderer.getFillColor(-1)); // default
    }

    @Test
    void testClearGhost() {
        int[][] boardMatrix = new int[22][10];
        gameRenderer.initDisplayMatrix(boardMatrix);
        Rectangle[][] displayMatrix = gameRenderer.getDisplayMatrix();

        // Set opacity to 0.5
        for (int i = 2; i < displayMatrix.length; i++) {
            for (int j = 0; j < displayMatrix[i].length; j++) {
                displayMatrix[i][j].setOpacity(0.5);
            }
        }

        gameRenderer.clearGhost();

        // Verify opacity reset
        for (int i = 2; i < displayMatrix.length; i++) {
            for (int j = 0; j < displayMatrix[i].length; j++) {
                assertEquals(1.0, displayMatrix[i][j].getOpacity(), 0.001);
            }
        }
    }

    @Test
    void testRefreshGameBackground() {
        int[][] boardMatrix = new int[22][10];
        gameRenderer.initDisplayMatrix(boardMatrix);

        int[][] gameBoard = new int[22][10];
        gameBoard[2][0] = 1; // AQUA
        gameBoard[3][1] = 2; // ORANGE
        gameBoard[4][2] = 8; // GARBAGE (BLACK)

        gameRenderer.refreshGameBackground(gameBoard);

        Rectangle[][] displayMatrix = gameRenderer.getDisplayMatrix();
        assertEquals(Color.AQUA, displayMatrix[2][0].getFill());
        assertEquals(Color.ORANGE, displayMatrix[3][1].getFill());
        assertEquals(Color.BLACK, displayMatrix[4][2].getFill());

        // Check arc settings
        assertEquals(9, displayMatrix[2][0].getArcHeight(), 0.001);
        assertEquals(9, displayMatrix[2][0].getArcWidth(), 0.001);
    }

    @Test
    void testUpdateHoldPiece() {
        int[][] heldShape = {
                {0, 3, 0},
                {3, 3, 3}
        };

        gameRenderer.updateHoldPiece(heldShape);
        assertEquals(heldShape.length * heldShape[0].length, holdPiece.getChildren().size());
    }

    @Test
    void testUpdateHoldPiece_Null() {
        gameRenderer.updateHoldPiece(null);
        assertEquals(0, holdPiece.getChildren().size());
    }

    @Test
    void testGetGarbageBlockValue() {
        assertEquals(8, GameRenderer.getGarbageBlockValue());
        assertEquals(Color.BLACK, gameRenderer.getFillColor(GameRenderer.getGarbageBlockValue()));
    }

    @Test
    void testGetDisplayMatrix() {
        assertNull(gameRenderer.getDisplayMatrix()); // not initialized yet

        int[][] boardMatrix = new int[22][10];
        gameRenderer.initDisplayMatrix(boardMatrix);

        assertNotNull(gameRenderer.getDisplayMatrix());
        assertEquals(22, gameRenderer.getDisplayMatrix().length);
        assertEquals(10, gameRenderer.getDisplayMatrix()[0].length);
    }
}
