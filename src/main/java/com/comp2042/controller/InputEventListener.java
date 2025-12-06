package com.comp2042.controller;

import com.comp2042.model.DownData;
import com.comp2042.events.MoveEvent;
import com.comp2042.view.BoardViewData;

/**
 * Interface for handling user input events in the game such as
 * moving the piece down,rotating it,moving it left,right,hard drop and hold.
 */
public interface InputEventListener {

    /**
     * @param event The move event triggered when the piece moves down.
     * @return A {@link DownData} object containing information about the move.
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * @param event The move event triggered when the piece moves left.
     * @return A {@link BoardViewData} object representing the new position of the piece.
     */
    BoardViewData onLeftEvent(MoveEvent event);

    /**
     * @param event The move event triggered when the piece moves right.
     * @return A {@link BoardViewData} object representing the new position of the piece.
     */
    BoardViewData onRightEvent(MoveEvent event);

    /**
     * @param event The move event triggered when the piece is rotated.
     * @return A {@link BoardViewData} object representing the rotated piece.
     */
    BoardViewData onRotateEvent(MoveEvent event);

    /**
     * @return  A {@link BoardViewData} object representing the ghost piece.
     */
    BoardViewData getGhostPiece();

    /**
     * @param event The move event triggered when a hard drop occurs.
     * @return  A {@link BoardViewData} object representing the final position after the hard drop.
     */
    BoardViewData onHardDropEvent(MoveEvent event);

    /**
     * @return The current score.
     */
    int getScore();

    /**
     *Initializes a new game by resetting the game state and board.
     */
    void createNewGame();

    /**
     * @param event The move event triggered when the hold action occurs.
     * @return  A {@link BoardViewData} object representing the held piece.
     */
    BoardViewData onHoldEvent(MoveEvent event);

    /**
     * @return the total number of lines cleared.
     */
    int getTotalLinesCleared();

    /**
     * @return the current level.
     */
    int getLevel();
}
