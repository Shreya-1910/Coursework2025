package com.comp2042.model;

/**
 * This class represents information about the next shape in the game.
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * @param shape The matrix representation of the next shape.
     * @param position The position of the next shape in the queue.
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * @return A deep copy of the shape matrix.
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * @return the position of the next shape in the queue.
     */
    public int getPosition() {

        return position;
    }
}
