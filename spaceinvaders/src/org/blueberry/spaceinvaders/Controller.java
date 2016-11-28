package org.blueberry.spaceinvaders;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;


public class Controller {

    @FXML
    private AnchorPane display;

    @FXML
    private Label scoreLabel1;




    @FXML
    private void handleButtonAction(ActionEvent event){
        InvaderGroup.getInstance().setStepDuration(50);

    }


    private  Ship ship;



    @FXML
    public void initialize() {

        Border.getInstance().setBorder(0, 0, (int)display.getPrefWidth(), (int)display.getPrefHeight() - 150); //TODO width height

        ship = new Ship();
        display.getChildren().add(ship.getView());


        display.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        ship.move(-5 , 0);
                        break;
                    case RIGHT:
                        ship.move(5, 0);
                        break;
                }
            }
        });




        for (Sprite invader : InvaderGroup.getInstance().getInvaders()){
            display.getChildren().add(invader.getView());
        }

        InvaderGroup.getInstance().move();




    }

    public Controller(){
    }


}







