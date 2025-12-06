package com.comp2042.controller;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import com.comp2042.model.NextShapeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrickRotatorTest {

    private BrickRotator brickRotator;
    private Brick mockBrick;

    @BeforeEach
    void setUp() {
        brickRotator = new BrickRotator();
        mockBrick = new Brick() {
            @Override
            public java.util.List<int[][]> getShapeMatrix() {
                return java.util.List.of(
                        new int[][]{{1, 1}, {1, 0}}, // Shape 0
                        new int[][]{{1, 0}, {1, 1}}, // Shape 1
                        new int[][]{{0, 1}, {1, 1}}  // Shape 2
                );
            }
        };
        brickRotator.setBrick(mockBrick); // Assign the brick to the rotator
    }

    @Test
    void testGetCurrentShapeInitial() {
        // Test the initial shape (rotation index should be 0)
        int[][] currentShape = brickRotator.getCurrentShape();
        int[][] expectedShape = mockBrick.getShapeMatrix().get(0);
        assertArrayEquals(expectedShape, currentShape, "The initial shape should match the first shape in the matrix.");
    }

    @Test
    void testGetNextShape() {
        // Test that nextShape returns the correct shape when currentShape is 0
        NextShapeInfo nextShapeInfo = brickRotator.getNextShape();
        int[][] nextShape = nextShapeInfo.getShape();
        int[][] expectedNextShape = mockBrick.getShapeMatrix().get(1);
        assertArrayEquals(expectedNextShape, nextShape, "Next shape should be the second shape in the matrix.");
    }

    @Test
    void testRotationWrapAround() {
        // Test rotation wraparound
        // Set current shape to the last shape
        brickRotator.setCurrentShape(2);  // Shape index 2
        int[][] currentShape = brickRotator.getCurrentShape();
        int[][] expectedShape = mockBrick.getShapeMatrix().get(2);
        assertArrayEquals(expectedShape, currentShape, "The current shape should be the third shape in the matrix.");

        // Call getNextShape, it should wrap around to index 0
        NextShapeInfo nextShapeInfo = brickRotator.getNextShape();
        int[][] nextShape = nextShapeInfo.getShape();
        expectedShape = mockBrick.getShapeMatrix().get(0);
        assertArrayEquals(expectedShape, nextShape, "Next shape should wrap around to the first shape in the matrix.");
    }

    @Test
    void testSetCurrentShape() {
        // Test setting a specific shape index
        brickRotator.setCurrentShape(1); // Set to shape index 1
        int[][] currentShape = brickRotator.getCurrentShape();
        int[][] expectedShape = mockBrick.getShapeMatrix().get(1);
        assertArrayEquals(expectedShape, currentShape, "The current shape should match the shape at index 1.");
    }

    @Test
    void testGetBrick() {
        // Ensure the brick assigned to the rotator is the correct one
        Brick retrievedBrick = brickRotator.getBrick();
        assertNotNull(retrievedBrick, "The brick should not be null.");
        assertEquals(mockBrick, retrievedBrick, "The brick retrieved from the rotator should match the mock brick.");
    }

    @Test
    void testSetBrick() {
        // Test setting a new brick
        Brick newMockBrick = new Brick() {
            @Override
            public java.util.List<int[][]> getShapeMatrix() {
                return java.util.List.of(
                        new int[][]{{0, 1}, {0, 1}}, // Shape 0
                        new int[][]{{1, 1}, {1, 0}}  // Shape 1
                );
            }
        };
        brickRotator.setBrick(newMockBrick);

        // Verify the new brick is set and check initial shape
        int[][] currentShape = brickRotator.getCurrentShape();
        int[][] expectedShape = newMockBrick.getShapeMatrix().get(0);
        assertArrayEquals(expectedShape, currentShape, "The current shape of the new brick should match the first shape.");
    }
}
