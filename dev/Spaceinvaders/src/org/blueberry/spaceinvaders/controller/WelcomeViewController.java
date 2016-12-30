package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.blueberry.spaceinvaders.SpaceInvaders;

public class WelcomeViewController implements Initializable{

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToScreenGameplayView(ActionEvent event){
       SpaceInvaders.setScreen("GameplayView");
    }
    
    @FXML
    private void goToScreenHighscoreView(ActionEvent event){
       SpaceInvaders.setScreen("HighscoreView");
    }

    @FXML
    private void goToScreenManualView(ActionEvent event){
        SpaceInvaders.setScreen("ManualView");
    }

    @FXML
    private void closeGame(){
        Platform.exit();
        System.exit(0);
    }
}
