package com.comp2042.controller;

import com.comp2042.events.EventSource;
import com.comp2042.events.EventType;
import com.comp2042.events.MoveEvent;
import com.comp2042.model.DownData;
import com.comp2042.view.BoardViewData;
import com.comp2042.view.GameOverPanel;
import com.comp2042.view.NotificationPanel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import com.comp2042.model.HighScore;

/**
 * GUI Controller that handles rendering and user input.
 * Includes ghost piece rendering.
 */
public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    @FXML
    private Label scoreLabel;

    // next 3 preview panels
    @FXML
    private GridPane nextPiece1;

    @FXML
    private GridPane nextPiece2;

    @FXML
    private GridPane nextPiece3;

    @FXML
    private Label highScoreLabel;

    @FXML private GridPane holdPiece;

    @FXML
    private Label linesClearedLabel;

    private Rectangle[][] displayMatrix;
    private InputEventListener eventListener;
    private Rectangle[][] rectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        //update high score
        highScoreLabel.setText("High Score: " + HighScore.load());

        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                // allow pause/unpause
                if (keyEvent.getCode() == KeyCode.P) {
                    togglePause();
                    keyEvent.consume();
                    return;
                }

                //  Stop all movement if paused or game over
                if (isPause.getValue() || isGameOver.getValue()) {
                    return;
                }

                // left
                if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                    clearGhost();
                    refreshGhost(eventListener.getGhostPiece());
                    refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                    keyEvent.consume();
                }

                // right
                if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                    clearGhost();
                    refreshGhost(eventListener.getGhostPiece());
                    refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                    keyEvent.consume();
                }

                // rotate
                if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                    clearGhost();
                    refreshGhost(eventListener.getGhostPiece());
                    refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                    keyEvent.consume();
                }

                // down
                if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                    moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                    keyEvent.consume();
                }

                // new game
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }

                // hard drop
                 if (keyEvent.getCode() == KeyCode.SPACE) {
                    int before = eventListener.getScore();

                    BoardViewData data = eventListener.onHardDropEvent(
                            new MoveEvent(EventType.HARDDROP, EventSource.USER)
                    );

                    int after = eventListener.getScore();
                    int gained = after - before;

                    if (gained > 0) {
                        NotificationPanel np = new NotificationPanel("+" + gained);
                        // reset
                        np.setTranslateY(0);
                        np.setOpacity(1.0);
                        np.setVisible(true);

                        groupNotification.getChildren().add(np);
                        np.showScore(groupNotification.getChildren());
                    }

                    // Update lines cleared
                    linesClearedLabel.setText("Lines cleared: " + eventListener.getTotalLinesCleared());

                    clearGhost();
                    refreshGhost(eventListener.getGhostPiece());
                    refreshBrick(data);

                    keyEvent.consume();
                }

                // Hold press 'C'
                if (keyEvent.getCode() == KeyCode.C) {
                    // clear and refresh ghost first
                    clearGhost();
                    refreshGhost(eventListener.getGhostPiece());
                    refreshBrick(eventListener.onHoldEvent(new MoveEvent(EventType.HOLD, EventSource.USER)));
                    keyEvent.consume();
                }

                highScoreLabel.setText("High Score: " + HighScore.load());

            }
        });

        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    private void togglePause() {
        if (isPause.get()) {
            timeLine.play();
            isPause.set(false);
        } else {
            timeLine.pause();
            isPause.set(true);
        }
    }

    public void initGameView(int[][] boardMatrix, BoardViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.BEIGE;
                break;
            case 7:
                returnPaint = Color.BURLYWOOD;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }

    private void refreshBrick(BoardViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
            if (brick.getNextThreeData() != null) {
                drawPreview(brick.getNextThreeData().get(0), nextPiece1);
                drawPreview(brick.getNextThreeData().get(1), nextPiece2);
                drawPreview(brick.getNextThreeData().get(2), nextPiece3);
            }
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {

                int lines = downData.getClearRow().getLinesRemoved();
                int points = lines * 50;

                NotificationPanel notificationPanel = new NotificationPanel("+" + points);
                notificationPanel.setTranslateY(0);
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }

            clearGhost();
            refreshGhost(eventListener.getGhostPiece());
            refreshBrick(downData.getViewData());
            linesClearedLabel.setText("Lines cleared: " + eventListener.getTotalLinesCleared());

        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }
    // Binds score label to game score
    public void bindScore(IntegerProperty scoreProperty) {
        scoreLabel.textProperty().bind(scoreProperty.asString("Score: %d"));
    }

    public void gameOver() {
        timeLine.stop();
        isGameOver.setValue(Boolean.TRUE);

        int finalScore = eventListener.getScore();
        HighScore.saveIfHigher(finalScore);
        highScoreLabel.setText("High Score: " + HighScore.load());

        gameOverPanel.setVisible(true);
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
        refreshGameBackground(eventListener.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.THREAD)).getViewData().getBrickData());updateHighScoreLabel();
        linesClearedLabel.setText("Lines: 0");
    }

    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }

    private void updateHighScoreLabel() {
        highScoreLabel.setText("High Score: " + HighScore.load());
    }

    //  draws a preview brick
    private void drawPreview(int[][] shape, GridPane target) {
        target.getChildren().clear();

        if (shape == null) return;

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                Rectangle r = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                r.setFill(getFillColor(shape[i][j]));

                target.add(r, j, i);
            }
        }
    }

    // Ghost piece methods

    /**Removes previous ghost from board*/
    private void clearGhost() {
        if (displayMatrix == null) return;

        for (int i = 2; i < displayMatrix.length; i++) {
            for (int j = 0; j < displayMatrix[i].length; j++) {
                displayMatrix[i][j].setOpacity(1.0);
            }
        }
    }

    public void updateHoldPiece(int[][] heldShape) {
        holdPiece.getChildren().clear();

        if (heldShape == null) {
            return; // nothing to draw
        }

        for (int i = 0; i < heldShape.length; i++) {
            for (int j = 0; j < heldShape[i].length; j++) {

                Rectangle r = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                r.setFill(getFillColor(heldShape[i][j]));

                holdPiece.add(r, j, i);
            }
        }
    }


    /**Draws the ghost piece on the background grid. */
    public void refreshGhost(BoardViewData ghost) {
        if (ghost == null || !ghost.isGhost()) return;

        int[][] shape = ghost.getBrickData();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {

                    int boardY = ghost.getyPosition() + i;
                    int boardX = ghost.getxPosition() + j;

                    if (boardY >= 2 && boardY < displayMatrix.length &&
                            boardX >= 0 && boardX < displayMatrix[0].length) {

                        Rectangle r = displayMatrix[boardY][boardX];
                        r.setFill(Color.LIGHTGRAY);
                        r.setOpacity(0.30);
                    }
                }
            }
        }
    }
}
