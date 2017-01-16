package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.blueberry.spaceinvaders.SpaceInvaders;

/**
 * WelcomeViewController-Klasse
 */
public class WelcomeViewController implements Initializable {

    /**
     * Inizialisiert die Controller-Klasse.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Wechselt zur Game-View.
     * @param event
     */
    @FXML
    private void goToScreenGameplayView(ActionEvent event) {
        SpaceInvaders.setScreen("GameplayView");
    }

    /**
     * Wechselt zur Highscore-View.
     * @param event
     */
    @FXML
    private void goToScreenHighscoreView(ActionEvent event) {
        SpaceInvaders.setScreen("HighscoreView");
    }

    /**
     * Wechselt zur Manual-View.
     * @param event
     */
    @FXML
    private void goToScreenManualView(ActionEvent event) {
        SpaceInvaders.setScreen("ManualView");
    }

    /**
     * Wechselt zur Chat-View.
     * @param event
     */
    @FXML
    private void goToScreenChatView(ActionEvent event) { SpaceInvaders.setScreen("ChatView"); }

    /**
     * Schließt das Spiel.
     */
    @FXML
    private void closeGame() {
        Platform.exit();
        System.exit(0);
    }
}
