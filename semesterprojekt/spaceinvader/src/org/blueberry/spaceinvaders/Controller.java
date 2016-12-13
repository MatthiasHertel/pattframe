package org.blueberry.spaceinvaders;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;


public class Controller {

    @FXML
    private AnchorPane display;

    @FXML
    private Label scoreLabel1;




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


//        addInvadersToPane(display, (game.getInvaderGroup().getInvaderList()));

        game.play();


//        display.getChildren().add(new ImageView(game.getInvader1a()));

    }
//
//    private void removeBullet(Bullet bullet){
//        if(bullet != null) {
//            display.getChildren().remove(bullet.getView());
//        }
//        if(bullet != null) {
//            ship.removeBullet();
//        }
//    }
//
//    private void removeInvader(IGunSprite invader){
//        display.getChildren().remove(invader.getView());
//        InvaderGroup.getInstance().removeInvader(invader);
//    }
//
//    private void tryShoot(){
//
//        if (ship.getBullet() == null){
//            ship.newBullet();
//            ship.getBullet().getTimeLine().setOnFinished(event -> {
////                display.getChildren().remove(ship.getBullet().getView());
////                ship.removeBullet();
//                removeBullet(ship.getBullet());
//                System.out.println("Schussanimation fertig");
//            });
//
////            ship.getBullet().getTimeLine().currentTimeProperty().addListener((observable, oldValue, newValue) -> {
//            ship.getBullet().getView().yProperty().addListener((observable1, oldValue1, newValue1) -> {
//
//                IGunSprite colInvader = ship.getBullet().collisionedInvader(InvaderGroup.getInstance().getInvaders());
//
//                if (colInvader != null) {
//                    removeBullet(ship.getBullet());
//
//                    player.setScore(player.getScore() + ((Invader)colInvader).getValue());
//                    removeInvader(colInvader);
//
//
//                    System.out.println("Invader getroffen " + colInvader.getPositionX() + "  " + colInvader.getPositionY());
//
//                    colInvader = null;
//                }
//            });
//
//
//            display.getChildren().add(ship.getBullet().getView());
//            ship.shoot();
//        }
//
//    }
//
//
//    public Controller(){
//    }
//

}







