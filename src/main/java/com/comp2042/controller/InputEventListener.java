package com.comp2042.controller;

import com.comp2042.model.DownData;
import com.comp2042.events.MoveEvent;
import com.comp2042.view.BoardViewData;

public interface InputEventListener {

    DownData onDownEvent(MoveEvent event);

    BoardViewData onLeftEvent(MoveEvent event);

    BoardViewData onRightEvent(MoveEvent event);

    BoardViewData onRotateEvent(MoveEvent event);

    BoardViewData getGhostPiece();

    BoardViewData onHardDropEvent(MoveEvent event);

    int getScore();

    void createNewGame();
}
