package org.blueberry.spaceinvaders.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.net.URL;
import java.util.ResourceBundle;



public class ManualViewController implements Initializable {

    @FXML
    private WebView manualView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        WebEngine engine = manualView.getEngine();
       engine.load(getClass().getResource("/manual/manual.html").toExternalForm());
    }

    @FXML
    private void goToScreenWelcomeView(ActionEvent event){
        SpaceInvaders.setScreen("WelcomeView");
    }
}
