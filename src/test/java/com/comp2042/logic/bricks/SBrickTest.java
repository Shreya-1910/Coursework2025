package com.comp2042.logic.bricks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SBrickTest {

    private SBrick sBrick;

    @BeforeEach
    void setUp() {
        sBrick = new SBrick();
    }

    @AfterEach
    void tearDown() {
        // No resources to clean up
    }

    @Test
    void getShapeMatrix() {
        List<int[][]> shapes = sBrick.getShapeMatrix();

        // SBrick should have 2 rotation states
        assertEquals(2, shapes.size(), "SBrick should have 2 rotation states");

        // Each matrix should be 4x4
        for (int[][] matrix : shapes) {
            assertEquals(4, matrix.length, "Matrix should have 4 rows");
            for (int[] row : matrix) {
                assertEquals(4, row.length, "Each row should have 4 columns");
            }
        }

        // Verify deep copy
        int[][] firstShape = shapes.get(0);
        firstShape[1][1] = 99; // Modify the copy
        List<int[][]> shapes2 = sBrick.getShapeMatrix();
        assertNotEquals(99, shapes2.get(0)[1][1], "Original matrix should not be affected by external modification");
    }
}
