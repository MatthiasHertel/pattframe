package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.util.*;

import static org.blueberry.spaceinvaders.gameengine.Game.GameStatus.*;

import org.blueberry.spaceinvaders.gameengine.InvaderGroup.MoveDirection;

import static org.blueberry.spaceinvaders.gameengine.InvaderGroup.MoveDirection.*;

/**
 * Game-Klasse
 */
public class Game {

    /**
     * ourInstance
     */
    private static Game ourInstance;

    /**
     * Game
     * @return
     */
    public static Game getInstance() {
        if (ourInstance == null) {
            ourInstance = new Game();
        }
        return ourInstance;
    }

    private Map<String, Image> imageAssets = new HashMap<String, Image>();
    private Map<String, AudioClip> audioAssets = new HashMap<String, AudioClip>();
    private List<Timeline> allActiveTimeLines = new ArrayList<>();
    private InvaderGroup invaderGroup;
    private AnchorPane display;
    private Ship ship;
    private MysteryShip mysteryShip;
    private List<Shelter> shelterList;
    private boolean shipSelfMove = Boolean.parseBoolean(SpaceInvaders.getSettings("ship.move.self"));

    private Player player;
    private int currentInvaderBulletsCount = 0;
    private int maxInvaderBulletsCount = Integer.parseInt(SpaceInvaders.getSettings("invader.shoots.parallel"));
    private ObjectProperty<GameStatus> gameStatus = new SimpleObjectProperty<>(PLAY);
    private IntegerProperty level = new SimpleIntegerProperty(1);

    private int invaderMoveDuration = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.1"));

    private long invaderShootDelayMin = Long.parseLong(SpaceInvaders.getSettings("invader.shoots.delay.random.min"));
    private long invaderShootDelayMax = Long.parseLong(SpaceInvaders.getSettings("invader.shoots.delay.random.max"));

    private long mysteryShipDelayMin = Long.parseLong(SpaceInvaders.getSettings("mysteryship.delay.random.min"));
    private long mysteryShipDelayMax = Long.parseLong(SpaceInvaders.getSettings("mysteryship.delay.random.max"));

    private GameAnimationTimer gameAnimationTimer = new GameAnimationTimer();

    private Label gameStatusLabel = new Label(); //TODO: wieder entfernen nur temporär

    /**
     * loadAssets
     * @param theme
     */
    public void loadAssets(String theme) {

        imageAssets.put("invader1a", new Image(theme + "/graphics/invader1a.png"));
        imageAssets.put("invader1b", new Image(theme + "/graphics/invader1b.png"));
        imageAssets.put("invader2a", new Image(theme + "/graphics/invader2a.png"));
        imageAssets.put("invader2b", new Image(theme + "/graphics/invader2b.png"));
        imageAssets.put("invader3a", new Image(theme + "/graphics/invader3a.png"));
        imageAssets.put("invader3b", new Image(theme + "/graphics/invader3b.png"));

        imageAssets.put("shelter1a", new Image(theme + "/graphics/shelter1a.png"));
        imageAssets.put("shelter1b", new Image(theme + "/graphics/shelter1b.png"));
        imageAssets.put("shelter1c", new Image(theme + "/graphics/shelter1c.png"));
        imageAssets.put("shelter1d", new Image(theme + "/graphics/shelter1d.png"));
        imageAssets.put("shelter1e", new Image(theme + "/graphics/shelter1e.png"));
        imageAssets.put("shelter1f", new Image(theme + "/graphics/shelter1f.png"));
        imageAssets.put("shelter2a", new Image(theme + "/graphics/shelter2a.png"));
        imageAssets.put("shelter2b", new Image(theme + "/graphics/shelter2b.png"));
        imageAssets.put("shelter2c", new Image(theme + "/graphics/shelter2c.png"));

        imageAssets.put("invaderBullet", new Image(theme + "/graphics/invader_bullet.png"));

        imageAssets.put("shipBullet", new Image(theme + "/graphics/ship_bullet.png"));
        imageAssets.put("ship", new Image(theme + "/graphics/ship.png"));

        imageAssets.put("mysteryShip", new Image(theme + "/graphics/mystery_ship.png"));

        audioAssets.put("shipShoot", new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_shoot.wav").toExternalForm()));
        audioAssets.put("shipExplosion", new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_explosion.wav").toExternalForm()));
        audioAssets.put("invaderKilled", new AudioClip(getClass().getResource("/" + theme + "/sounds/invader_killed.wav").toExternalForm()));
        audioAssets.put("mystery", new AudioClip(getClass().getResource("/" + theme + "/sounds/mystery.wav").toExternalForm()));
        audioAssets.put("mysteryKilled", new AudioClip(getClass().getResource("/" + theme + "/sounds/mystery_killed.wav").toExternalForm()));


    }

    /**
     * addInvadersToPane
     * @param anchorPane
     * @param invaderList
     */
    public void addInvadersToPane(AnchorPane anchorPane, List<Invader> invaderList) {

        for (Invader invader : invaderList) {
            anchorPane.getChildren().add(invader);
        }
    }

    /**
     * addShelterToPane
     * @param anchorPane
     * @param shelter
     */
    public void addShelterToPane(AnchorPane anchorPane, Shelter shelter) {

        for (ShelterPart shelterPart : shelter.getShelterParts()) {
            anchorPane.getChildren().add(shelterPart);
        }
    }

    public void removeShelterFromPane(AnchorPane anchorPane, Shelter shelter) {

        for (ShelterPart shelterPart : shelter.getShelterParts()) {
            anchorPane.getChildren().remove(shelterPart);
        }
    }

    /**
     * getImageAsset
     * @param key
     * @return
     */
    public Image getImageAsset(String key) {
        return imageAssets.get(key);
    }

    /**
     * getAudioAsset
     * @param key
     * @return
     */
    public AudioClip getAudioAsset(String key) {
        return audioAssets.get(key);
    }

    /**
     * Game
     */
    private Game() {
        loadAssets(SpaceInvaders.getSettings("game.standardtheme"));
        player = new Player();
    }

    /**
     * constructGame
     * @param pane
     */
    public void constructGame(AnchorPane pane) {
        this.display = pane;
        createInvaderGroup();

        ship = new Ship(getImageAsset("ship"));
        display.getChildren().add(ship);

        createShelter();

        gameStatusLabel.textProperty().bind(gameStatus.asString()); //TODO: raus damit
        display.getChildren().add(gameStatusLabel); //TODO: raus damit

        display.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        ship.setMoveDirection(InvaderGroup.MoveDirection.LEFT);
                        break;
                    case RIGHT:
                        ship.setMoveDirection(InvaderGroup.MoveDirection.RIGHT);
                        break;
                    case X:
                        tryShipShoot();
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

        display.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (shipSelfMove) {
                    return;
                }
                if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
                    ship.setMoveDirection(InvaderGroup.MoveDirection.NONE);
                }
            }
        });

        gameStatus.addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case PLAY:
                    playActiveTimeLines(allActiveTimeLines);
                    break;
                case PAUSE:
                    pauseActiveTimeLines(allActiveTimeLines);
                    break;
                case GAMEOVER:
                    stop();
                    SpaceInvaders.setScreen("HighscoreView"); //TODO: Übergang so mit GAMEOVER und dann in den Screen
                    break;
                case WON:
                    nextLevel();
                    break;
            }
        });

        player.livesProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Player Lives Changed: " + newValue);
            if (newValue.intValue() == 0) {
                gameStatus.set(GAMEOVER);
            }
        });
    }

    private void createShelter() {

        if(shelterList != null){
            for (Shelter shelter : shelterList){
                removeShelterFromPane(display, shelter);
            }
        }

        shelterList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            shelterList.add(new Shelter(100 + i * 170, Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"))));
            addShelterToPane(display, shelterList.get(i));
        }
    }

    /**
     * pauseActiveTimeLines
     * @param timeLines
     */
    private void pauseActiveTimeLines(List<Timeline> timeLines) {
        timeLines.forEach(Timeline::pause);
        System.out.println("Anzahl aktiver TimeLines: " + timeLines.size());
    }

    /**
     * playActiveTimeLines
     * @param timeLines
     */
    private void playActiveTimeLines(List<Timeline> timeLines) {
        timeLines.forEach(Timeline::play);
    }

    /**
     * getAllActiveTimeLines
     * @return
     */
    public List<Timeline> getAllActiveTimeLines() {
        return this.allActiveTimeLines;
    }

    /**
     * removeBullet
     * @param sprite
     */
    private void removeBullet(IGunSprite sprite) {
        if (sprite instanceof Invader) {
            currentInvaderBulletsCount--;
        }
        System.out.println("RemoveBulletAnfang Anzahl aktiver TimeLines: " + allActiveTimeLines.size());
        sprite.getBullet().getTimeLine().stop();
        allActiveTimeLines.remove(sprite.getBullet().getTimeLine());
        display.getChildren().remove(sprite.getBullet());
        sprite.removeBullet();
        System.out.println("RemoveBulletEndeAnzahl aktiver TimeLines: " + allActiveTimeLines.size());
    }

    /**
     * removeInvader
     * @param invader
     */
    private void removeInvader(Invader invader) {
        display.getChildren().remove(invader);
        invaderGroup.removeInvader(invader);
    }

    /**
     * removeSprite
     * @param sprite
     */
    private void removeSprite(ISprite sprite) {
        if (sprite instanceof ShelterPart) {
            System.out.println("instance of = shelterpart");
            display.getChildren().remove(sprite);
            for (Shelter shelter : shelterList) {
                shelter.getShelterParts().remove(sprite);
            }
        }
    }

    /**
     * tryShipShoot
     */
    private void tryShipShoot() {

        if (ship.getBullet() == null && gameStatus.get() == PLAY) {
            ship.newBullet();
            ship.getBullet().getTimeLine().setOnFinished(event -> {
                removeBullet(ship);
                System.out.println("Schussanimation fertig");
            });

            display.getChildren().add(ship.getBullet());
            ship.shoot();
        }
    }

    /**
     * tryInvaderShoot
     */
    private void tryInvaderShoot() {

        if (currentInvaderBulletsCount < maxInvaderBulletsCount && currentInvaderBulletsCount < invaderGroup.getInvaderList().size()) {

            Random random = new Random();
            int randomInt = random.nextInt(invaderGroup.getInvaderList().size());

            // einen Invader bekommen, der momentan nicht schießt
            Invader tempInvader = invaderGroup.getInvaderList().get(randomInt);
            while (tempInvader.getBullet() != null) {
                randomInt = random.nextInt(invaderGroup.getInvaderList().size());
                tempInvader = invaderGroup.getInvaderList().get(randomInt);
            }

            currentInvaderBulletsCount++;
            final Invader shootInvader = tempInvader;

            shootInvader.newBullet();
            shootInvader.getBullet().getTimeLine().setOnFinished(event -> {
                removeBullet(shootInvader);
//                currentInvaderBulletsCount--;
            });

            display.getChildren().add(shootInvader.getBullet());
            shootInvader.shoot();
        }

    }

    /**
     * createInvaderGroup
     */
    public void createInvaderGroup() {
        invaderGroup = InvaderGroup.getInstance();

        int posX = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.x"));
        int posY = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"));

        invaderGroup.createGroup(posX, posY);
        addInvadersToPane(display, invaderGroup.getInvaderList());
    }


    /**
     * getInvaderGroup
     * @return
     */
    public InvaderGroup getInvaderGroup() {
        return invaderGroup;
    }

    /**
     * setTheme
     * @param theme
     */
    public void setTheme(String theme) {
        loadAssets(theme);
        invaderGroup.createGroup(Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.x")), Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.y")));
    }

    /**
     * play
     */
    public void play() {
        //GameAnimationTimer gameAnimationTimer = new GameAnimationTimer();
        gameAnimationTimer.start();

    }

    /**
     * detectCollisionedInvader
     * @param bullet
     * @param invaders
     * @return
     */
    private Invader detectCollisionedInvader(Bullet bullet, List<Invader> invaders) {
        for (Invader invader : invaders) {
            if (bullet.intersects(invader.getLayoutBounds())) {
                return invader;
            }
        }
        return null;
    }

    /**
     * GameAnimationTimer
     */
    public class GameAnimationTimer extends AnimationTimer {

        long invaderMoveLastTime = System.nanoTime();
        long invaderShootLastTime = System.nanoTime();
        long mysteryShipLastTime = System.nanoTime();
        Random random = new Random();

        /**
         * handle
         * @param now
         */
        @Override
        public void handle(long now) {

            if (gameStatus.get() == PLAY) {

                // ship bewegen
                ship.move(ship.getMoveDirection());

                //InvaderGroup bewegen (Zeitinterval application.properties: invader.move.speed.1)
                if (now > invaderMoveLastTime + invaderMoveDuration * 1000000) {
                    invaderMoveLastTime = now;
                    invaderGroup.move();
                }


                //Invaderschuss absetzen
                if (now > invaderShootLastTime + ((long) (random.nextDouble() * invaderShootDelayMax) + invaderShootDelayMin) * 1000000L) {
                    invaderShootLastTime = now;
                    tryInvaderShoot();
                }

                // hat die schiffskanone einen invader getroffen
                if (ship.getBullet() != null) {
                    Invader collisionedInvader = detectCollisionedInvader(ship.getBullet(), invaderGroup.getInvaderList());
                    if (collisionedInvader != null) {
                        System.out.println("Invader getroffen");
                        getAudioAsset("invaderKilled").play();
                        removeBullet(ship);

                        player.setScore(player.getScore() + collisionedInvader.getValue());
                        removeInvader(collisionedInvader);
                    }
                    if (invaderGroup.getInvaderList().size() == 0) {
                        gameStatus.set(WON);
                    }
                }

                // hat die schiffskanone das MysteryShip getroffen
                if (ship.getBullet() != null && mysteryShip != null) {
                    if (ship.getBullet().intersects(mysteryShip.getLayoutBounds())) {
                        getAudioAsset("mysteryKilled").play();
                        player.setScore(player.getScore() + mysteryShip.getValue());
                        removeBullet(ship);
                        removeMysteryShip();
                    }
                }

                // hat die schiffskanone den Bunker getroffen
                bunkerWurdeGetroffen:
                if (ship.getBullet() != null) {
                    for (Shelter shelter : shelterList) {
                        for (ShelterPart shelterPart : shelter.getShelterParts()) {
                            if (ship.getBullet().intersects(shelterPart.getLayoutBounds())) {
                                removeBullet(ship);
                                shelterPart.damagedFromBottom();
                                if (shelterPart.getState() == 0) {
                                    removeSprite(shelterPart);
                                }
                                break bunkerWurdeGetroffen;
                            }
                        }
                    }
                }

                // hat ein invader den Bunker getroffen

                bunkerWurdeGetroffen2:
                for (Invader invader : invaderGroup.getInvaderList()) {
                    if (invader.getBullet() != null) {
                        for (Shelter shelter : shelterList) {
                            for (ShelterPart shelterPart : shelter.getShelterParts()) {
                                if (invader.getBullet().intersects(shelterPart.getLayoutBounds())) {
                                    removeBullet(invader);
                                    shelterPart.damagedFromTop();
                                    if (shelterPart.getState() == 0) {
                                        removeSprite(shelterPart);
                                    }
                                    break bunkerWurdeGetroffen2;
                                }

                            }
                        }
                    }
                }


                // hat ein Invader das ship getroffen
                for (Invader invader : invaderGroup.getInvaderList()) {
                    if (invader.getBullet() != null) {
                        if (ship.intersects(invader.getBullet().getLayoutBounds())) {
                            System.out.println("Ship getroffen");
                            getAudioAsset("shipExplosion").play();
                            player.setlives(player.getlives() - 1);
                            removeBullet(invader);
                            break;
                        }
                    }
                }


                // MysteryShip losschicken
                if (now > mysteryShipLastTime + ((long) (random.nextDouble() * mysteryShipDelayMax) + mysteryShipDelayMin) * 1000000L) {
                    if (mysteryShip != null) {
                        return;
                    }
                    mysteryShipLastTime = now;

                    MoveDirection randomDirection = random.nextInt(2) == 0 ? RIGHT : LEFT;
                    mysteryShip = new MysteryShip(getImageAsset("mysteryShip"), randomDirection);
                    mysteryShip.getTimeLine().setOnFinished(event -> removeMysteryShip());
                    display.getChildren().add(mysteryShip);
                    mysteryShip.move(randomDirection);
                }
            }
        }
    }

    /**
     * removeMysteryShip
     */
    private void removeMysteryShip() {
        if(mysteryShip == null) return;

        mysteryShip.getTimeLine().stop();
        allActiveTimeLines.remove(mysteryShip.getTimeLine());
        display.getChildren().remove(mysteryShip);
        mysteryShip = null;
        System.out.println("MysteryShip entfernt");
    }

    /**
     * stop
     */
    public void stop() {
        allActiveTimeLines.forEach(Timeline::stop);
        this.gameAnimationTimer.stop();
    }

    /**
     * reset
     */
    public static void reset() {
        ourInstance = null;
    }

    public void nextLevel(){
        if (level.get() >= 10){
            level.set(0);
        }
        level.set(level.get() + 1);


        removeMysteryShip();

        createInvaderGroup();
        createShelter();

        this.setGameStatus(PLAY);

    }

    public IntegerProperty levelProperty() {
        return level;
    }

    /**
     * getGameStatus
     * @return
     */
    public final GameStatus getGameStatus() {
        return gameStatus.get();
    }

    /**
     * setGameStatus
     * @param status
     */
    public final void setGameStatus(GameStatus status) {
        gameStatusProperty().set(status);
    }

    /**
     * gameStatusProperty
     * @return
     */
    public final ObjectProperty<GameStatus> gameStatusProperty() {
        return gameStatus;
    }

    /**
     * getPlayer
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * GameStatus
     */
    public enum GameStatus {
        PLAY,
        PAUSE,
        WON,
        GAMEOVER;
    }
}
