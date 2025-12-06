package com.comp2042.view;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * The NotificationPanel class represents a UI panel that displays a notification message on the screen.
 */
public class NotificationPanel extends BorderPane {

    /**
     * @param text The message to display on the panel.
     */
    public NotificationPanel(String text) {
        setMinHeight(200);
        setMinWidth(220);
        final Label score = new Label(text);
        score.getStyleClass().add("bonusStyle");
        final Effect glow = new Glow(0.6);
        score.setEffect(glow);
        score.setTextFill(Color.WHITE);
        setCenter(score);

    }

    /**
     * @param list The list of nodes that the notification panel will be removed from once the animation finishes.
     */
    public void showScore(ObservableList<Node> list) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        ft.setFromValue(1);
        ft.setToValue(0);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), this);
        tt.setByY(-40);
        ParallelTransition transition = new ParallelTransition(tt, ft);

        transition.setOnFinished(e -> list.remove(this));
        transition.play();
    }
}
