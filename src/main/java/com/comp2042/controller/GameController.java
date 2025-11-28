package com.comp2042.controller;

import com.comp2042.events.MoveEvent;
import com.comp2042.model.Score;
import com.comp2042.view.BoardViewData;
import com.comp2042.model.DownData;


/**
 * Listens to input events, delegates game logic to GameEngine
 * and updates the GUI accordingly.
 */
public class GameController implements InputEventListener {

    private final GameEngine engine;
    private final GuiController gui;

    /**
     * Initializes a new GameController with the given GUI controller.
     * @param gui the GUI controller to interact with
     */
    public GameController(GuiController gui) {
        this.gui = gui;
        engine = new GameEngine(25, 10);

        gui.setEventListener(this);
        gui.initGameView(engine.getBoardMatrix(), engine.getViewData());
        gui.bindScore(engine.getScoreObject().scoreProperty());
    }


    @Override
    public DownData onDownEvent(MoveEvent event) {
        DownData data = engine.moveDown(event.getEventSource());
        gui.refreshGameBackground(engine.getBoardMatrix());
        if (data.isGameOver()) gui.gameOver();
        return data;
    }

    @Override
    public BoardViewData onLeftEvent(MoveEvent event) {
        return engine.moveLeft();
    }

    @Override
    public BoardViewData onRightEvent(MoveEvent event) {
        return engine.moveRight();
    }

    @Override
    public BoardViewData onRotateEvent(MoveEvent event) {
        return engine.rotate();
    }

    @Override
    public void createNewGame() {
        engine.newGame();
        gui.refreshGameBackground(engine.getBoardMatrix());
    }
    //GhostPiece method added
    @Override
    public BoardViewData getGhostPiece() {
        return engine.getGhostPiece();
    }
//returns score
    @Override
    public int getScore() {
        return engine.getScore();
    }

    @Override
    public int getTotalLinesCleared() {
        return engine.getTotalLinesCleared();
    }

//drops brick and updates board
    @Override
    public BoardViewData onHardDropEvent(MoveEvent event) {
        return engine.hardDrop();
    }


    @Override
    public BoardViewData onHoldEvent(MoveEvent event) {
        BoardViewData data = engine.hold();

        // Send hold shape to GUI
        gui.updateHoldPiece(engine.getHeldShape());

        return data;
    }



}
