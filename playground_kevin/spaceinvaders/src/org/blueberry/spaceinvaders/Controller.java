package org.blueberry.spaceinvaders;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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


    private Ship ship;
    private Player player;


    @FXML
    public void initialize() {

        Border.getInstance().setBorder(0, 0, (int)display.getPrefWidth(), (int)display.getPrefHeight() - 150); //TODO width height

        ship = new Ship();
        player = new Player();
        display.getChildren().add(ship.getView());

        scoreLabel1.textProperty().bind(player.scoreProperty().asString());


        display.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        ship.move(-10 , 0);
                        break;
                    case RIGHT:
                        ship.move(10, 0);
                        break;
                    case SPACE:
                        tryShoot();
                        break;
                }
            }
        });




        for (ISprite invader : InvaderGroup.getInstance().getInvaders()){
            display.getChildren().add(invader.getView());
        }

        InvaderGroup.getInstance().move();

    }

    private void removeBullet(Bullet bullet){
        if(bullet != null) {
            display.getChildren().remove(bullet.getView());
        }
        if(bullet != null) {
            ship.removeBullet();
        }
    }

    private void removeInvader(IGunSprite invader){
        display.getChildren().remove(invader.getView());
        InvaderGroup.getInstance().removeInvader(invader);
    }

    private void tryShoot(){

        if (ship.getBullet() == null){
            ship.newBullet();
            ship.getBullet().getTimeLine().setOnFinished(event -> {
//                display.getChildren().remove(ship.getBullet().getView());
//                ship.removeBullet();
                removeBullet(ship.getBullet());
                System.out.println("Schussanimation fertig");
            });

            ship.getBullet().getTimeLine().currentTimeProperty().addListener((observable, oldValue, newValue) -> {
//            ship.getBullet().getView().yProperty().addListener((observable1, oldValue1, newValue1) -> {


                System.out.println("Bulletycheck middle: " + ship.getBullet().getYMiddle());
                System.out.println("Bulletycheck posx: " + ship.getBullet().getView().getY());
                IGunSprite colInvader = ship.getBullet().collisionedInvader(InvaderGroup.getInstance().getInvaders());


                if (colInvader != null) {
                    removeBullet(ship.getBullet());

                    player.setScore(player.getScore() + ((Invader)colInvader).getValue());
                    removeInvader(colInvader);


                    System.out.println("Invader getroffen" + colInvader.getPositionX() + "  " + colInvader.getPositionY());

                    colInvader = null;
                }
//                System.out.println(ship.getBullet().collisionDetected(InvaderGroup.getInstance().getInvaders()));
//                System.out.println(ship.getBullet().getTimeLine().getCurrentTime());

            });


            display.getChildren().add(ship.getBullet().getView());
            ship.shoot();
        }

    }


    public Controller(){
    }


}







