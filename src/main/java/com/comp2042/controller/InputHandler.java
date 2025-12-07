package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.MoveEvent;
import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**Handles user input events (key presses) for controlling the game.
 * Responsible for responding to key events that control the game's state.
 * Depending on the state (paused, game over), it manages movement, rotation,
 * and other actions for the game.*/
public class InputHandler {
    private final BooleanProperty isPause;
    private final BooleanProperty isGameOver;
    private final GuiController controller;
    private final InputEventListener eventListener;

    /**
     * @param isPause A BooleanProperty indicating whether the game is paused.
     * @param isGameOver A BooleanProperty indicating whether the game is over.
     * @param controller The GuiController that manages the game interface and logic.
     * @param eventListener The listener that handles the specific game events (e.g., movement, rotation).
     */
    public InputHandler(BooleanProperty isPause, BooleanProperty isGameOver,
                        GuiController controller, InputEventListener eventListener) {
        this.isPause = isPause;
        this.isGameOver = isGameOver;
        this.controller = controller;
        this.eventListener = eventListener;
    }

    /**
     * @param keyEvent The key event triggered by the user.
     */
    public void handleKeyPress(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        if (code == KeyCode.N) {
            controller.newGame(null);
            keyEvent.consume();
            return;
        }

        if (code == KeyCode.P && !isGameOver.get()) {
            controller.togglePause();
            keyEvent.consume();
            return;
        }

        if (isPause.get() || isGameOver.get()) {
            return;
        }

        // Handle game controls
        switch (code) {
            case LEFT:
            case A:
                handleLeft();
                break;

            case RIGHT:
            case D:
                handleRight();
                break;

            case UP:
            case W:
                handleRotate();
                break;

            case DOWN:
            case S:
                controller.moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                break;

            case SPACE:
                controller.hardDrop();
                break;

            case C:
                handleHold();
                break;
        }

        keyEvent.consume();
    }

    /**
     * Handles the left movement event.
     * Clears the ghost piece and updates the game state for a left move.
     */
    private void handleLeft() {
        controller.clearGhost();
        controller.refreshGhost(eventListener.getGhostPiece());
        controller.refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
    }

    /**
     * Handles the right movement event.
     * Clears the ghost piece and updates the game state for a right move.
     */
    private void handleRight() {
        controller.clearGhost();
        controller.refreshGhost(eventListener.getGhostPiece());
        controller.refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
    }

    /**
     * Handles rotation.
     *Clears the ghost piece and updates the game state for a rotation.
     */
    private void handleRotate() {
        controller.clearGhost();
        controller.refreshGhost(eventListener.getGhostPiece());
        controller.refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
    }

    /**
     * Handles hold.
     * Clears the ghost piece and updates the game state for hold.
     */
    private void handleHold() {
        controller.clearGhost();
        controller.refreshGhost(eventListener.getGhostPiece());
        controller.refreshBrick(eventListener.onHoldEvent(new MoveEvent(EventType.HOLD, EventSource.USER)));
    }
}