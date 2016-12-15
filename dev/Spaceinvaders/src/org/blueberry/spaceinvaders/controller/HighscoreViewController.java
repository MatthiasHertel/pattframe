package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.gameengine.Game;

public class HighscoreViewController implements Initializable{


    @FXML
    private Label punkte;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        String game = String.valueOf(Game.getInstance().getGameStatus());
//        System.out.println(game);
        punkte.textProperty().bind(Game.getInstance().getPlayer().scoreProperty().asString());

    }


    @FXML
    private void goToScreen1(ActionEvent event){
        SpaceInvaders.setScreen("WelcomeView");
    }
    
    @FXML
    private void goToScreen2(ActionEvent event){
        SpaceInvaders.setScreen("GameplayView");
    }

}
