package com.comp2042.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NextShapeInfoTest {

    private int[][] shape;
    private int position;
    private NextShapeInfo nextShapeInfo;

    @BeforeEach
    void setUp() {
        // Initialize the shape array and position
        shape = new int[][]{
                {1, 1, 0},
                {0, 1, 1}
        };
        position = 2;
        nextShapeInfo = new NextShapeInfo(shape, position);
    }

    @Test
    void testConstructor() {
        // Verify that the constructor correctly initializes the shape and position
        assertArrayEquals(shape, nextShapeInfo.getShape(), "Shape should be initialized correctly.");
        assertEquals(position, nextShapeInfo.getPosition(), "Position should be initialized correctly.");
    }


    @Test
    void testGetPosition() {
        // Verify that the position is correctly returned
        assertEquals(position, nextShapeInfo.getPosition(), "The position should be returned correctly.");
    }
}
