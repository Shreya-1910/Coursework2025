package com.comp2042.logic.bricks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JBrickTest {

    private JBrick jBrick;

    @BeforeEach
    void setUp() {
        jBrick = new JBrick();
    }

    @AfterEach
    void tearDown() {
        jBrick = null;
    }

    @Test
    void getShapeMatrix() {
        List<int[][]> shapes = jBrick.getShapeMatrix();

        // Check that there are exactly 4 orientations
        assertEquals(4, shapes.size(), "JBrick should have 4 rotation states");

        // Check that each matrix is 4x4
        for (int i = 0; i < shapes.size(); i++) {
            int[][] matrix = shapes.get(i);
            assertEquals(4, matrix.length, "Matrix height should be 4");
            for (int[] row : matrix) {
                assertEquals(4, row.length, "Matrix width should be 4");
            }
        }

        int[][] expectedFirst = {
                {0, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0}
        };
        int[][] actualFirst = shapes.get(0);
        for (int r = 0; r < 4; r++) {
            assertArrayEquals(expectedFirst[r], actualFirst[r], "Row " + r + " does not match expected");
        }
    }
}
