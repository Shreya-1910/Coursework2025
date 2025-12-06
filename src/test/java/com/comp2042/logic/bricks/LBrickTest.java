package com.comp2042.logic.bricks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LBrickTest {

    private LBrick lBrick;

    @BeforeEach
    void setUp() {
        lBrick = new LBrick();
    }

    @AfterEach
    void tearDown() {
        // No external resources to clean up, so this can be empty
    }

    @Test
    void getShapeMatrix() {
        List<int[][]> shapes = lBrick.getShapeMatrix();

        // There should be 4 rotation matrices
        assertEquals(4, shapes.size(), "LBrick should have 4 rotation states");

        // Check that each matrix is 4x4
        for (int[][] matrix : shapes) {
            assertEquals(4, matrix.length, "Each matrix should have 4 rows");
            for (int[] row : matrix) {
                assertEquals(4, row.length, "Each row should have 4 columns");
            }
        }

        // Optional: verify deep copy (modifying returned shape should not affect original)
        int[][] firstShape = shapes.get(0);
        firstShape[1][1] = 99; // Modify the copy
        List<int[][]> shapes2 = lBrick.getShapeMatrix();
        assertNotEquals(99, shapes2.get(0)[1][1], "Original matrix should not be affected by external modifications");
    }
}
