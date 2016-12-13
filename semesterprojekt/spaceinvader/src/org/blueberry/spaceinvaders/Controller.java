package org.blueberry.spaceinvaders;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;



public class Controller {

    @FXML
    private AnchorPane display;

    @FXML
    private Label scoreLabel1;

    @FXML
    private Label livesLabel;




    @FXML
    private void handleButtonAction(ActionEvent event){
//        InvaderGroup.getInstance().setStepDuration(50);

    }





    @FXML
    public void initialize() {

        Game game = Game.getInstance();
//        game.setTheme("theme1"); //optional we

        game.setPane(display);
        game.constructGame();

        scoreLabel1.textProperty().bind(game.getPlayer().scoreProperty().asString());
        livesLabel.textProperty().bind(game.getPlayer().livesProperty().asString());

        game.play();


    }

}







