package org.blueberry.spaceinvaders.chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ChatViewController-Klasse
 */
public class ChatViewController implements Initializable {


    /**
     * Inizialisiert die Controller-Klasse.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
