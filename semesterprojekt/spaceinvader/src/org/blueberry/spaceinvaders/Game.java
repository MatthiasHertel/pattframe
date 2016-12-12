package org.blueberry.spaceinvaders;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.blueberry.spaceinvaders.Game.GameStatus.*;
import static org.blueberry.spaceinvaders.InvaderGroup.MoveDirection.*;


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
    private List<Timeline> allActiveTimeLines = new ArrayList<>();
    private InvaderGroup invaderGroup;
    private AnchorPane display;
    private Ship ship;
    private ObjectProperty<GameStatus> gameStatus = new SimpleObjectProperty<>(PLAY);
//    private ObjectProperty<GameStatus> gameStatus = new SimpleObjectProperty<>(GameStatus.PAUSE);

    private int invaderMoveDuration = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.3"));



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
                        ship.move(LEFT);
                        break;
                    case RIGHT:
                        ship.move(RIGHT);
                        break;
                    case SPACE:
                        tryShipShoot();
                        break;
                    case P:
                        gameStatus.set(gameStatus.get() == PLAY ? PAUSE : PLAY);
                        break;
                }

            }
        });

        gameStatus.addListener((observable, oldValue, newValue) -> {
            switch (newValue){
                case PLAY:
                    playActiveTimeLines(allActiveTimeLines);
                    break;
                case PAUSE:
                    pauseActiveTimeLines(allActiveTimeLines);
                    break;
                case GAMEOVER : display.getChildren().add(new Label("GameOver!!"));
                break;

            }

            display.getChildren().add(new Label(newValue.toString()));
        });
    }

    private void pauseActiveTimeLines(List<Timeline> timeLines){
        timeLines.forEach(Timeline::pause);
        System.out.println("Anzahl aktiver TimeLines: " + timeLines.size());
    }

    private void playActiveTimeLines(List<Timeline> timeLines){
        timeLines.forEach(Timeline::play);
    }

    public List<Timeline> getAllActiveTimeLines(){
        return this.allActiveTimeLines;
    }

    private void removeBullet(Bullet bullet){
        allActiveTimeLines.remove(bullet.getTimeLine());
        display.getChildren().remove(bullet);
        ship.removeBullet();
    }

    private void tryShipShoot(){

        if (ship.getBullet() == null && gameStatus.get() == PLAY){
            ship.newBullet();
            ship.getBullet().getTimeLine().setOnFinished(event -> {
                removeBullet(ship.getBullet());
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

        GameAnimationTimer gameAnimationTimer = new GameAnimationTimer();
        gameAnimationTimer.start();

    }


    public class GameAnimationTimer extends AnimationTimer {

        long lastTime = System.nanoTime();

        @Override
        public void handle(long now) {

            if (gameStatus.get() == PLAY) {

                if (now > lastTime + invaderMoveDuration * 1000000) {
                    lastTime = now;

                    invaderGroup.move();

                }

//                System.out.println("LastTime: " + lastTime);

            }
        }
    }




    public final GameStatus getGameStatus() {
        return gameStatus.get();
    }

    public final void setGameStatus(GameStatus status) {
        gameStatusProperty().set(status);
    }

    public final ObjectProperty<GameStatus> gameStatusProperty() {
        return gameStatus;
    }



    public enum GameStatus{
        PLAY,
        PAUSE,
        GAMEOVER;
    }


}
