package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.MoveEvent;
import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
    private final BooleanProperty isPause;
    private final BooleanProperty isGameOver;
    private final GuiController controller;
    private final InputEventListener eventListener;

    public InputHandler(BooleanProperty isPause, BooleanProperty isGameOver,
                        GuiController controller, InputEventListener eventListener) {
        this.isPause = isPause;
        this.isGameOver = isGameOver;
        this.controller = controller;
        this.eventListener = eventListener;
    }

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

    private void handleLeft() {
        controller.clearGhost();
        controller.refreshGhost(eventListener.getGhostPiece());
        controller.refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
    }

    private void handleRight() {
        controller.clearGhost();
        controller.refreshGhost(eventListener.getGhostPiece());
        controller.refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
    }

    private void handleRotate() {
        controller.clearGhost();
        controller.refreshGhost(eventListener.getGhostPiece());
        controller.refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
    }

    private void handleHold() {
        controller.clearGhost();
        controller.refreshGhost(eventListener.getGhostPiece());
        controller.refreshBrick(eventListener.onHoldEvent(new MoveEvent(EventType.HOLD, EventSource.USER)));
    }
}