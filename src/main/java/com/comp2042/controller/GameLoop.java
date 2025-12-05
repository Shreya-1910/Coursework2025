package com.comp2042.controller;

import com.comp2042.events.MoveEvent;
import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Handles main game loop timing and triggers moveDown events.
 */
public class GameLoop {

    private Timeline timeline;
    private final GuiController guiController;
    private final int initialSpeed;
    private int currentSpeed;

    public GameLoop(GuiController guiController, int initialSpeed) {
        this.guiController = guiController;
        this.initialSpeed = initialSpeed;
        this.currentSpeed = initialSpeed;
    }

    /**
     * Starts the game loop with a default speed.
     */
    public void start() {
        stop();
        timeline = new Timeline(new KeyFrame(
                Duration.millis(currentSpeed),
                ae -> tick()
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Stops the game loop.
     */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear();
        }
    }

    /**
     * Pauses the game loop.
     */
    public void pause() {
        if (timeline != null) timeline.pause();
    }

    /**
     * Resumes the game loop if paused.
     */
    public void resume() {
        if (timeline != null) timeline.play();
    }

    /**
     * Adjusts the speed of the game loop.
     */
    public void setSpeed(int speedMillis) {
        currentSpeed = speedMillis;
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(currentSpeed),
                    ae -> tick()
            ));
            timeline.play();
        }
    }

    /**
     * The tick method triggers a DOWN move event on the GuiController.
     */
    private void tick() {
        guiController.moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD));
    }

    /**
     * Returns whether the loop is currently running.
     */
    public boolean isRunning() {
        return timeline != null && timeline.getStatus() == Timeline.Status.RUNNING;
    }
}
