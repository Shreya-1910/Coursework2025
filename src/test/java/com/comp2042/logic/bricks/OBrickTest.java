package com.comp2042.logic.bricks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OBrickTest {

    private OBrick oBrick;

    @BeforeEach
    void setUp() {
        oBrick = new OBrick();
    }

    @AfterEach
    void tearDown() {
        // No resources to clean up
    }

    @Test
    void getShapeMatrix() {
        List<int[][]> shapes = oBrick.getShapeMatrix();

        // O brick should have only 1 rotation state
        assertEquals(1, shapes.size(), "OBrick should have 1 rotation state");

        // Each matrix should be 4x4
        int[][] matrix = shapes.get(0);
        assertEquals(4, matrix.length, "Matrix should have 4 rows");
        for (int[] row : matrix) {
            assertEquals(4, row.length, "Each row should have 4 columns");
        }

        matrix[1][1] = 99;
        List<int[][]> shapes2 = oBrick.getShapeMatrix();
        assertNotEquals(99, shapes2.get(0)[1][1], "Original matrix should not be affected by external modification");
    }
}
