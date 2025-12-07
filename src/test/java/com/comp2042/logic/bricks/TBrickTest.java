package com.comp2042.logic.bricks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TBrickTest {

    private TBrick tBrick;

    @BeforeEach
    void setUp() {
        tBrick = new TBrick();
    }

    @AfterEach
    void tearDown() {
        // No resources to clean up
    }

    @Test
    void getShapeMatrix() {
        List<int[][]> shapes = tBrick.getShapeMatrix();

        // TBrick should have 4 rotation states
        assertEquals(4, shapes.size(), "TBrick should have 4 rotation states");

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
        List<int[][]> shapes2 = tBrick.getShapeMatrix();
        assertNotEquals(99, shapes2.get(0)[1][1], "Original matrix should not be affected by external modification");
    }
}
