package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.MoveEvent;
import com.comp2042.model.DownData;
import com.comp2042.view.BoardViewData;
import com.comp2042.view.GameOverPanel;
import com.comp2042.view.NotificationPanel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Refactored GUI Controller using GameLoop.
 */
public class GuiController implements Initializable {

    @FXML private GridPane gamePanel;
    @FXML private Group groupNotification;
    @FXML private GridPane brickPanel;
    @FXML private GameOverPanel gameOverPanel;
    @FXML private Label scoreLabel;
    @FXML private GridPane nextPiece1;
    @FXML private GridPane nextPiece2;
    @FXML private GridPane nextPiece3;
    @FXML private Label highScoreLabel;
    @FXML private GridPane holdPiece;
    @FXML private Label linesClearedLabel;
    @FXML private Label levelLabel;
    @FXML private ToggleButton pauseButton;
    @FXML private Label garbageInfoLabel;

    private InputEventListener eventListener;
    private GameLoop gameLoop;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();
    private int currentLevel = 1;
    private final int linesPerLevel = 5;

    private InputHandler inputHandler;
    private HighScoreManager highScoreManager;
    private GameRenderer gameRenderer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();

        highScoreManager = new HighScoreManager(highScoreLabel);
        gameRenderer = new GameRenderer(gamePanel, brickPanel, nextPiece1, nextPiece2, nextPiece3, holdPiece);
        inputHandler = new InputHandler(isPause, isGameOver, this, null);

        highScoreManager.updateHighScoreDisplay();
        initializeGarbageInfo();

        gamePanel.setOnKeyPressed(this::handleKeyPress);

        gameOverPanel.setVisible(false);

        Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    private void handleKeyPress(KeyEvent keyEvent) {
        if (eventListener == null) return;
        inputHandler.handleKeyPress(keyEvent);
        highScoreManager.updateHighScoreDisplay();
    }

    private void initializeGarbageInfo() {
        if (garbageInfoLabel != null) {
            garbageInfoLabel.setText("Garbage: Off");
            garbageInfoLabel.setTextFill(Color.YELLOW);
        }
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
        inputHandler = new InputHandler(isPause, isGameOver, this, eventListener);
    }

    public void initGameView(int[][] boardMatrix, BoardViewData brick) {
        gameRenderer.initDisplayMatrix(boardMatrix);
        gameRenderer.initBrickRectangles(brick);
        gameRenderer.positionBrickPanel(brick);

        gameLoop = new GameLoop(this, 400); // 400ms initial speed
        gameLoop.start();
    }

    public void moveDown(MoveEvent event) {
        if (!isPause.getValue()) {
            DownData downData = eventListener.onDownEvent(event);

            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                int lines = downData.getClearRow().getLinesRemoved();
                int points = lines * 50;
                NotificationPanel notificationPanel = new NotificationPanel("+" + points);
                notificationPanel.setTranslateY(0);
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
                updateLevelAndSpeed();
            }

            gameRenderer.clearGhost();
            gameRenderer.refreshGhost(eventListener.getGhostPiece());
            gameRenderer.refreshBrick(downData.getViewData(), isPause.get());
            linesClearedLabel.setText("Lines cleared: " + eventListener.getTotalLinesCleared());
            updateGarbageInfo();
        }
        gamePanel.requestFocus();
    }

    private void updateLevelAndSpeed() {
        int totalLines = eventListener.getTotalLinesCleared();
        currentLevel = totalLines / linesPerLevel + 1;
        if (levelLabel != null) levelLabel.setText("Level: " + currentLevel);

        int newSpeed = Math.max(80, 400 - (currentLevel - 1) * 50);
        if (gameLoop != null) gameLoop.setSpeed(newSpeed);

        updateGarbageInfo();
    }

    private void updateGarbageInfo() {
        if (garbageInfoLabel != null && eventListener != null) {
            if (eventListener.getLevel() >= 3) {
                garbageInfoLabel.setText("Garbage: ON");
                garbageInfoLabel.setTextFill(Color.RED);
            } else {
                garbageInfoLabel.setText("Garbage: Off");
                garbageInfoLabel.setTextFill(Color.YELLOW);
            }
        }
    }

    void hardDrop() {
        int before = eventListener.getScore();
        BoardViewData data = eventListener.onHardDropEvent(new MoveEvent(EventType.HARDDROP, EventSource.USER));
        int after = eventListener.getScore();
        int gained = after - before;

        if (gained > 0) {
            NotificationPanel np = new NotificationPanel("+" + gained);
            np.setTranslateY(0);
            np.setOpacity(1.0);
            np.setVisible(true);
            groupNotification.getChildren().add(np);
            np.showScore(groupNotification.getChildren());
        }

        linesClearedLabel.setText("Lines cleared: " + eventListener.getTotalLinesCleared());
        gameRenderer.clearGhost();
        gameRenderer.refreshGhost(eventListener.getGhostPiece());
        gameRenderer.refreshBrick(data, isPause.get());
        updateLevelAndSpeed();
        updateGarbageInfo();
    }
    void togglePause() {
        if (isPause.get()) {
            gameLoop.resume();
            isPause.set(false);
            pauseButton.setSelected(false);
            pauseButton.setText("Pause");
        } else {
            gameLoop.pause();
            isPause.set(true);
            pauseButton.setSelected(true);
            pauseButton.setText("Resume");
        }
    }

    @FXML
    private void pauseGame(ActionEvent event) {
        togglePause();
    }
    @FXML
    public void newGame(ActionEvent actionEvent) {
        resetGameState();
        clearGameElements();
        eventListener.createNewGame();
        resetGameLogic();
        gameLoop.start();
        resetUI();
        gamePanel.requestFocus();
    }

    private void resetGameState() {
        if (gameLoop != null) gameLoop.stop();
        gameOverPanel.setVisible(false);
        isPause.setValue(false);
        isGameOver.setValue(false);
        linesClearedLabel.setText("Lines: 0");
        levelLabel.setText("Level: 1");
        currentLevel = 1;
    }

    private void resetUI() {
        highScoreManager.updateHighScoreDisplay();
        updateGarbageInfo();
        gamePanel.requestFocus();

        if (pauseButton != null) {
            pauseButton.setSelected(false);
            pauseButton.setText("Pause");
        }
    }

    private void clearGameElements() {
        holdPiece.getChildren().clear();
        groupNotification.getChildren().clear();
    }

    private void resetGameLogic() {
        gameRenderer.refreshGameBackground(eventListener.onDownEvent(
                new MoveEvent(EventType.DOWN, EventSource.THREAD)
        ).getViewData().getBrickData());
    }

    public void gameOver() {
        if (gameLoop != null) gameLoop.stop();
        isGameOver.set(true);

        int finalScore = eventListener.getScore();
        highScoreManager.saveIfHigher(finalScore);
        gameOverPanel.setVisible(true);
    }

    void clearGhost() {
        gameRenderer.clearGhost();
    }

    void refreshGhost(BoardViewData ghost) {
        gameRenderer.refreshGhost(ghost);
    }

    void refreshBrick(BoardViewData brick) {
        gameRenderer.refreshBrick(brick, isPause.get());
    }

    public void updateHoldPiece(int[][] heldShape) {
        gameRenderer.updateHoldPiece(heldShape);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public static int getGarbageBlockValue() {
        return GameRenderer.getGarbageBlockValue();
    }

    public void refreshGameBackground(int[][] board) {
        gameRenderer.refreshGameBackground(board);
    }

    public void bindScore(IntegerProperty scoreProperty) {
        scoreLabel.textProperty().bind(scoreProperty.asString("Score: %d"));
    }
}
