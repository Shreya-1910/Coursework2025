package com.comp2042.model;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.view.BoardViewData;

public interface Board {

    boolean moveBrickDown();

    boolean moveBrickLeft();

    boolean moveBrickRight();

    boolean rotateLeftBrick();

    boolean createNewBrick();

    int[][] getBoardMatrix();

    BoardViewData getViewData();

    void mergeBrickToBackground();

    ClearRow clearRows();

    Score getScore();

    void newGame();

    void holdBrick();

    Brick getHeldBrick();

    int[][] getHeldShape();
}
