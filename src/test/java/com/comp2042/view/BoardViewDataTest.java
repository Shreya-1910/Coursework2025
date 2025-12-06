package com.comp2042.view;

import com.comp2042.model.MatrixOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardViewDataTest {

    private int[][] brickData;
    private int[][] nextBrickData;
    private List<int[][]> nextThreeData;
    private int[][] holdBrickData;
    private BoardViewData boardViewData;

    @BeforeEach
    void setUp() {
        // Setup example data
        brickData = new int[][] {
                {1, 1},
                {1, 0}
        };

        nextBrickData = new int[][] {
                {2, 2},
                {0, 2}
        };

        nextThreeData = Arrays.asList(
                new int[][]{{3, 3}, {3, 0}},
                new int[][]{{4, 4}, {0, 4}},
                new int[][]{{5, 5}, {0, 5}}
        );

        holdBrickData = new int[][] {
                {6, 6},
                {0, 6}
        };

        // Initialize a BoardViewData object
        boardViewData = new BoardViewData(brickData, 0, 0, nextBrickData, nextThreeData, holdBrickData);
    }

    @Test
    void testConstructorAndGetters() {
        // Test the brick data
        assertNotNull(boardViewData.getBrickData());
        assertEquals(2, boardViewData.getBrickData().length);
        assertEquals(2, boardViewData.getBrickData()[0].length);

        // Test position
        assertEquals(0, boardViewData.getxPosition());
        assertEquals(0, boardViewData.getyPosition());

        // Test next brick data
        assertNotNull(boardViewData.getNextBrickData());
        assertEquals(2, boardViewData.getNextBrickData().length);
        assertEquals(2, boardViewData.getNextBrickData()[0].length);

        // Test next 3 bricks
        assertNotNull(boardViewData.getNextThreeData());
        assertEquals(3, boardViewData.getNextThreeData().size());
        assertNotNull(boardViewData.getNextThreeData().get(0));
        assertEquals(2, boardViewData.getNextThreeData().get(0).length);

        // Test hold brick data
        assertNotNull(boardViewData.getHoldBrickData());
        assertEquals(2, boardViewData.getHoldBrickData().length);
        assertEquals(2, boardViewData.getHoldBrickData()[0].length);

        // Check ghost piece (for constructor with ghost)
        BoardViewData ghostData = new BoardViewData(brickData, 1, 1, true);
        assertTrue(ghostData.isGhost());
    }

    @Test
    void testNullFields() {
        // Test edge case for null nextThreeData and null holdBrickData
        BoardViewData nullData = new BoardViewData(brickData, 0, 0, null, null, null);

        assertNull(nullData.getNextThreeData());
        assertNull(nullData.getHoldBrickData());
    }

    @Test
    void testCopyList() {
        // Test the copyList method in BoardViewData's constructor
        List<int[][]> copiedList = boardViewData.getNextThreeData();
        assertNotNull(copiedList);
        assertEquals(3, copiedList.size());
        assertNotSame(nextThreeData, copiedList); // Ensure it's a deep copy

        // Modify the original list and ensure copied list remains unchanged
        nextThreeData.get(0)[0][0] = 99;
        assertNotEquals(99, copiedList.get(0)[0][0]);
    }
}
