package com.comp2042.logic.bricks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IBrickTest {

    private IBrick iBrick;

    @BeforeEach
    void setUp() {
        iBrick = new IBrick();
    }

    @AfterEach
    void tearDown() {
        iBrick = null;
    }

    @Test
    void getShapeMatrix() {
        List<int[][]> shapes = iBrick.getShapeMatrix();

        // Check that there are exactly 2 orientations
        assertEquals(2, shapes.size(), "IBrick should have 2 rotation states");

        // Check that each matrix is 4x4
        for (int[][] matrix : shapes) {
            assertEquals(4, matrix.length, "Matrix height should be 4");
            for (int[] row : matrix) {
                assertEquals(4, row.length, "Matrix width should be 4");
            }
        }

        // Optional: Check the first orientation matches expected
        int[][] expectedFirst = {
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int[][] actualFirst = shapes.get(0);
        for (int r = 0; r < 4; r++) {
            assertArrayEquals(expectedFirst[r], actualFirst[r], "Row " + r + " does not match expected");
        }

        // Optional: Check the second orientation matches expected
        int[][] expectedSecond = {
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };
        int[][] actualSecond = shapes.get(1);
        for (int r = 0; r < 4; r++) {
            assertArrayEquals(expectedSecond[r], actualSecond[r], "Row " + r + " does not match expected");
        }
    }
}
