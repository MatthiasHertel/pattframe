package org.blueberry.spaceinvaders.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ManualViewController-Klasse
 */
public class ManualViewController implements Initializable {

    @FXML
    Button button;
    @FXML
    private WebView manualView;

    /**
     * Inizialisiert die Controller-Klasse.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WebEngine engine = manualView.getEngine();
        engine.load(getClass().getResource("/manual/manual.html").toExternalForm());

        Platform.runLater(() -> button.requestFocus());
    }

    /**
     * Wechselt zur Welcome-View
     * @param event
     */
    @FXML
    private void goToScreenWelcomeView(ActionEvent event) {
        SpaceInvaders.setScreen("WelcomeView");
    }
}
