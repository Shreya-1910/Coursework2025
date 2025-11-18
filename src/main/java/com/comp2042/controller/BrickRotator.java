package com.comp2042.controller;

import com.comp2042.model.NextShapeInfo;
import com.comp2042.logic.bricks.Brick;

/**
 * Manages rotation logic for bricks by tracking current shape
 * Provides access to next and current rotated bricks.
 */
public class BrickRotator {
    /**
     * Brick whose shape is managed by this rotator.
     */

    private Brick brick;
    /**Index of brick's current rotation*/
    private int currentShape = 0;

    /**
     * Calculates and returns the next rotated shape of the brick.
     * The rotation index increases by 1 and goes back to 0 when final shape is reached.
     * @return a {@link NextShapeInfo} object containing next shape
     */

    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Return current rotation of the brick.
     * @return a 2d integer array representing the current shape.
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Set brick's current rotation.
     * @param currentShape the shape index to set as current.
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Assigns brick to be managed and resets.
     * @param brick the brick to be rotated.
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }


}
