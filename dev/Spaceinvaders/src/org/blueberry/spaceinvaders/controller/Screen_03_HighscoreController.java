package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.blueberry.spaceinvaders.interfaces.ControlledScreen;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.gameengine.Game;
import org.blueberry.spaceinvaders.highscore.Highscore;

public class Screen_03_HighscoreController implements Initializable, ControlledScreen {

    ScreensController myController;

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
    
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       myController.setScreen(SpaceInvaders.screen1ID);
    }
    
    @FXML
    private void goToScreen2(ActionEvent event){
       myController.setScreen(SpaceInvaders.screen2ID);
    }

}
