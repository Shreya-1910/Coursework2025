package com.comp2042.logic.bricks;

import java.util.List;

/**
 * Interface representing a game brick. It defines the method all bricks in the game should
 * implement to provide their shape data.
 */
public interface Brick {

    /**
     * @return A list of 2D integer matrices representing the shape of the brick in different rotations.
     */
    List<int[][]> getShapeMatrix();
}
