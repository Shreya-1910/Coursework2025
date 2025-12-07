package com.comp2042.controller;

import com.comp2042.events.MoveEvent;
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

    /**
     * delegates the move action to the GameEngine and updates the game display.
     * @param event the move event containing the source of the move action
     * @return a {@link DownData} object containing data about the move including whether the game is over.
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        DownData data = engine.moveDown(event.getEventSource());
        updateGameDisplay();
        if (data.isGameOver()) gui.gameOver();
        return data;
    }

    /**
     * @param event delegates the move action to the GameEngine and updates the game display.
     * @return a {@link BoardViewData} object representing the updated game board after the move.
     */
    @Override
    public BoardViewData onLeftEvent(MoveEvent event) {
        return updateBoardViewData(engine::moveLeft); // Using method reference
    }

    /**
     * @param event the move event containing the source of the move action
     * @return a {@link BoardViewData} object representing the updated game board after the move
     */
    @Override
    public BoardViewData onRightEvent(MoveEvent event) {
        return updateBoardViewData(engine::moveRight); // Using method reference
    }

    /**
     * @param event event the move event containing the source of the rotation action
     * @return a {@link BoardViewData} object representing the updated game board after the rotation
     */
    @Override
    public BoardViewData onRotateEvent(MoveEvent event) {
        return updateBoardViewData(engine::rotate); // Using method reference
    }

    /**
     * Starts a new game by resetting the GameEngine and updating the display.
     */
    @Override
    public void createNewGame() {
        engine.newGame();
        updateGameDisplay();
    }

    /**
     * @return a {@link BoardViewData} object representing the ghost piece on the board
     */
    @Override
    public BoardViewData getGhostPiece() {
        return engine.getGhostPiece();
    }

    /**
     * @return the current score.
     */
    @Override
    public int getScore() {
        return engine.getScore();
    }

    /**
     * @return the total lines cleared.
     */
    @Override
    public int getTotalLinesCleared() {
        return engine.getTotalLinesCleared();
    }

    /**
     * @param event  the move event containing the source of the hard drop action
     * @return a {@link BoardViewData} object representing the updated game board after the hard drop.
     */
    @Override
    public BoardViewData onHardDropEvent(MoveEvent event) {
        return updateBoardViewData(engine::hardDrop); // Using method reference
    }

    /**
     * @param event  event the move event containing the source of the hold action.
     * @return a {@link BoardViewData} object representing the updated game board after the hold.
     */
    @Override
    public BoardViewData onHoldEvent(MoveEvent event) {
        BoardViewData data = engine.hold();
        gui.updateHoldPiece(engine.getHeldShape());
        return data;
    }

    /**
     * @return the current level of the game.
     */
    @Override
    public int getLevel() {
        return engine.getLevel();
    }

    /**
     * A helper method to centralize the board update logic.
     * This reduces repetition across move/rotate/hold/hard drop methods.
     */
    private BoardViewData updateBoardViewData(Runnable action) {
        action.run(); // Perform the game action
        return engine.getViewData(); // Return the updated board view data
    }

    /**
     * A helper method to update the GUI with the latest game state.
     */
    private void updateGameDisplay() {
        gui.refreshGameBackground(engine.getBoardMatrix());
    }
}
