package com.comp2042.controller;

import com.comp2042.events.MoveEvent;
import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Handles main game loop timing and triggers moveDown events.
 * Ensures speed resets immediately when starting a new game.
 */
public class GameLoop {

    private Timeline timeline;
    private final GuiController guiController;
    private final int initialSpeed;
    private int currentSpeed;

    /**
     * @param guiController the GUI controller to interact with.
     * @param initialSpeed  the initial speed of the game loop in milliseconds.
     */
    public GameLoop(GuiController guiController, int initialSpeed) {
        this.guiController = guiController;
        this.initialSpeed = initialSpeed;
        this.currentSpeed = initialSpeed;
    }

    /**
     * Starts the game loop for a new game.
     * Resets speed immediately and initializes the timeline.
     */
    public void start() {
        currentSpeed = initialSpeed;// Reset speed immediately
        createTimeline();
    }

    /**
     * Stops the game loop and clears timeline resources.
     */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline = null;
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
     * Updates the speed of the game loop mid-game.
     *
     * @param speedMillis the new speed in milliseconds.
     */
    public void setSpeed(int speedMillis) {
        currentSpeed = speedMillis;
        if (timeline != null) {
            createTimeline();
        }
    }

    /**
     * Creates the timeline with the current speed and starts it.
     */
    private void createTimeline() {
        stop(); // Stop any existing timeline
        timeline = new Timeline(new KeyFrame(
                Duration.millis(currentSpeed),
                ae -> tick()
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Called on every tick of the timeline to move the piece down.
     */
    private void tick() {
        guiController.moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD));
    }

    /**
     * @return true if the game loop is currently running.
     */
    public boolean isRunning() {
        return timeline != null && timeline.getStatus() == Timeline.Status.RUNNING;
    }
}
