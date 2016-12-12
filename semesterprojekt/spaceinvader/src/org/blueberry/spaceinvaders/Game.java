package org.blueberry.spaceinvaders;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KK on 09.12.2016.
 */
public class Game {

    private static Game ourInstance = new Game();

    public static Game getInstance() {
        return ourInstance;
    }



    private Map<String, Image> imageAssets = new HashMap<String, Image>();
    private Map<String, AudioClip> audioAssets = new HashMap<String, AudioClip>();
    private InvaderGroup invaderGroup;
    private AnchorPane display;
    private Ship ship;



    public void loadAssets(String theme){

        imageAssets.put("invader1a", new Image(theme + "/graphics/invader1a.png"));
        imageAssets.put("invader1b", new Image(theme + "/graphics/invader1b.png"));
        imageAssets.put("invader2a", new Image(theme + "/graphics/invader2a.png"));
        imageAssets.put("invader2b", new Image(theme + "/graphics/invader2b.png"));
        imageAssets.put("invader3a", new Image(theme + "/graphics/invader3a.png"));
        imageAssets.put("invader3b", new Image(theme + "/graphics/invader3b.png"));

        imageAssets.put("shipBullet", new Image(theme + "/graphics/ship_bullet.png"));
        imageAssets.put("ship", new Image(theme + "/graphics/ship.png"));

        imageAssets.put("bunker1a", new Image(theme + "/graphics/bunker/8x8/1a.png"));
        imageAssets.put("bunker2a", new Image(theme + "/graphics/bunker/8x8/2a.png"));


        audioAssets.put("shipShoot", new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_shoot.wav").toExternalForm()));
        audioAssets.put("shipExplosion", new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_explosion.wav").toExternalForm()));
        audioAssets.put("invaderKilled", new AudioClip(getClass().getResource("/" + theme + "/sounds/invader_killed.wav").toExternalForm()));


    }

    public void addInvadersToPane(AnchorPane anchorPane, List<Invader> invaderList){

        for (Invader invader: invaderList){
            anchorPane.getChildren().add(invader);
        }
    }

    public Image getImageAsset(String key){
        return imageAssets.get(key);
    }

    public AudioClip getAudioAsset(String key){
        return audioAssets.get(key);
    }

    private Game(){
        loadAssets(SpaceInvaders.getSettings("game.standardtheme"));

    }

    public void constructGame(){
        createInvaderGroup();
        addInvadersToPane(display, invaderGroup.getInvaderList());
        ship = new Ship(getImageAsset("ship"));
        display.getChildren().add(ship);

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
                        tryShipShoot();
                        break;
                }
            }
        });
    }


    private void tryShipShoot(){

        if (ship.getBullet() == null){
            ship.newBullet();
            ship.getBullet().getTimeLine().setOnFinished(event -> {
                display.getChildren().remove(ship.getBullet());
                ship.removeBullet();
//                removeBullet(ship.getBullet());
                System.out.println("Schussanimation fertig");
            });
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


            display.getChildren().add(ship.getBullet());
            ship.shoot();
        }

    }


    public void setPane(AnchorPane pane){
        this.display = pane;
    }

    public void createInvaderGroup(){
        invaderGroup = InvaderGroup.getInstance();
        invaderGroup.createGroup(Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.x")), Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.y")));
    }


    public InvaderGroup getInvaderGroup(){
        return invaderGroup;
    }

    public void setTheme(String theme){
        loadAssets(theme);
        invaderGroup.createGroup(Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.x")), Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.y")));
    }

    public void play(){
        invaderGroup.move();
    }
}
