package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.controller.AssetController;
import java.util.*;
import static org.blueberry.spaceinvaders.gameengine.GameStatus.*;

import org.blueberry.spaceinvaders.gameengine.Direction;
import static org.blueberry.spaceinvaders.gameengine.Direction.*;


/**
 * Spiel, enthält die gesamte Spielelogik
 */
public class Game {

    /**
     * Konstruktor (privat), für Singleton
     */
    private static Game ourInstance;

    /**
     * Getter-Methode für die Spiel-Instanz
     * @return Spiel-Instanz
     */
    public static Game getInstance() {
        if (ourInstance == null) {
            ourInstance = new Game();
        }
        return ourInstance;
    }

    private AssetController assetController = AssetController.getInstance();
    private List<Timeline> allActiveTimeLines = new ArrayList<>();
    private InvaderGroup invaderGroup;
    private AnchorPane display;
    private Ship ship;
    private MysteryShip mysteryShip;
    private List<Shelter> shelterList;
    private boolean shipSelfMove = Boolean.parseBoolean(SpaceInvaders.getSettings("ship.move.self"));

    private Player player;
    private int currentInvaderBulletsCount = 0;
    private int invaderMaxCount;
    private int maxInvaderBulletsCount = Integer.parseInt(SpaceInvaders.getSettings("invader.shoots.parallel"));
    private ObjectProperty<GameStatus> gameStatus = new SimpleObjectProperty<>(PLAY);
    private IntegerProperty level = new SimpleIntegerProperty(1);

    private int invaderSpeed1 = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.1"));
    private int invaderSpeed2 = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.2"));
    private int invaderSpeed3 = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.3"));
    private int invaderMoveDuration = invaderSpeed1;

    private long invaderShootDelayMin = Long.parseLong(SpaceInvaders.getSettings("invader.shoots.delay.random.min"));
    private long invaderShootDelayMax = Long.parseLong(SpaceInvaders.getSettings("invader.shoots.delay.random.max"));

    private long mysteryShipDelayMin = Long.parseLong(SpaceInvaders.getSettings("mysteryship.delay.random.min"));
    private long mysteryShipDelayMax = Long.parseLong(SpaceInvaders.getSettings("mysteryship.delay.random.max"));

    private GameAnimationTimer gameAnimationTimer = new GameAnimationTimer();


    /**
     * Fügt die Invaders zur Anchorpane hinzu
     * @param anchorPane Entrynode in der View
     * @param invaderList Invaderliste
     */
    public void addInvadersToPane(AnchorPane anchorPane, List<Invader> invaderList) {

        for (Invader invader : invaderList) {
            anchorPane.getChildren().add(invader);
        }
    }

    /**
     * Fügt die Shelters (Schutzbunker) zur Anchorpane hinzu
     * @param anchorPane Entrynode in der View
     * @param shelter Schutzbunker für den Spielavatar
     */
    public void addShelterToPane(AnchorPane anchorPane, Shelter shelter) {

        for (ShelterPart shelterPart : shelter.getShelterParts()) {
            anchorPane.getChildren().add(shelterPart);
        }
    }
    /**
     * Löscht die Shelters (Schutzbunker) von der Anchorpane
     * @param anchorPane Entrynode in der View
     * @param shelter Schutzbunker für den Spielavatar
     */
    public void removeShelterFromPane(AnchorPane anchorPane, Shelter shelter) {

        for (ShelterPart shelterPart : shelter.getShelterParts()) {
            anchorPane.getChildren().remove(shelterPart);
        }
    }


    /**
     * Singleton: - lädt die Assets
     * erzeugt neue Instanz der Klasse Player
     */
    private Game() {
        player = new Player();
    }

    /**
     * Konstruiert den Spielplatz für alle Elemente
     * bindet die Spiellogikrelevanten Elemente (Score, Leben) an die View
     * listener fuer Key interaktionen des Spielers
     * @param pane Entrynode in der View
     */
    public void constructGame(AnchorPane pane) {
        this.display = pane;
        createInvaderGroup();

        ship = new Ship(assetController.getImageAsset("ship"));
        display.getChildren().add(ship);

        createShelter();

        display.setFocusTraversable(true);

        // TODO: refactore in GameplayViewController oder gameinteractioncontroller
        display.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        ship.setMoveDirection(LEFT);
                        break;
                    case RIGHT:
                        ship.setMoveDirection(RIGHT);
                        break;
                    case X:
                        tryShipShoot();
                        break;
                    case SPACE:
                        tryShipShoot();
                        break;
                    case ESCAPE:
                        stop();
                        SpaceInvaders.setScreen("WelcomeView");
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
                    ship.setMoveDirection(Direction.NONE);
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

    /**
     * Erzeugt die Bunker (mit Aufräumen)
     */
    private void createShelter() {

        if(shelterList != null){
            for (Shelter shelter : shelterList){
                removeShelterFromPane(display, shelter);
            }
        }

        shelterList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            shelterList.add(new Shelter(208 + i * 176, Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"))));
            addShelterToPane(display, shelterList.get(i));
        }
    }

    /**
     * setzt alle Timelines auf Pause
     * @param timeLines
     */
    private void pauseActiveTimeLines(List<Timeline> timeLines) {
        timeLines.forEach(Timeline::pause);
//        System.out.println("Anzahl aktiver TimeLines: " + timeLines.size());
    }

    /**
     * setzt alle Timelines auf Play
     * @param timeLines
     */
    private void playActiveTimeLines(List<Timeline> timeLines) {
        timeLines.forEach(Timeline::play);
    }

    /**
     * Getter-Methode für alle Timelines
     * @return Timeline-Liste
     */
    public List<Timeline> getAllActiveTimeLines() {
        return this.allActiveTimeLines;
    }

    /**
     * Entfernt Projektil vom Spielelement und von der View
     * @param sprite
     */
    private void removeBullet(IGunSprite sprite) {
        if (sprite instanceof Invader) {
            currentInvaderBulletsCount--;
        }
//        System.out.println("RemoveBulletAnfang Anzahl aktiver TimeLines: " + allActiveTimeLines.size());
        sprite.getBullet().getTimeLine().stop();
        allActiveTimeLines.remove(sprite.getBullet().getTimeLine());
        display.getChildren().remove(sprite.getBullet());
        sprite.removeBullet();
//        System.out.println("RemoveBulletEndeAnzahl aktiver TimeLines: " + allActiveTimeLines.size());
    }

    /**
     * Entfernt Invader vom Spiel
     * @param invader
     */
    private void removeInvader(Invader invader) {
        display.getChildren().remove(invader);
        invaderGroup.removeInvader(invader);
    }

    /**
     * Entfernt Sprite vom Spiel(TODO: vereinheitliche mit anderen remove Methoden DRY: removeInvader,removeBullet  )
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
     * steuert Eigenschussfrequenz , falls noch kein Schuss aktiv ist , schiesse sonst nicht
     */
    private void tryShipShoot() {

        if (ship.getBullet() == null && gameStatus.get() == PLAY) {
            ship.newBullet();
            ship.getBullet().getTimeLine().setOnFinished(event -> {
                removeBullet(ship);
//                System.out.println("Schussanimation fertig");
            });

            display.getChildren().add(ship.getBullet());
            ship.shoot();
        }
    }

    /**
     * steuert Invaderschussfrequenz , falls noch kein Schuss aktiv ist , schiesse sonst nicht
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
     * erzeugt Invadergruppe im Spiel und fügt sie zur Pane hinzu
     */
    public void createInvaderGroup() {
        invaderGroup = InvaderGroup.getInstance();

        int posX = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.x"));
        int posY = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"));

        invaderGroup.createGroup(posX, posY);
        invaderMaxCount = invaderGroup.getInvaderList().size();
        addInvadersToPane(display, invaderGroup.getInvaderList());
    }


    /**
     * Getter-Methode für Invadergroup
     * @return InvaderGroup
     */
    public InvaderGroup getInvaderGroup() {
        return invaderGroup;
    }


    /**
     * Kollisionsdetektion Eigenprojektil <-> Invader
     * @param bullet Projektil
     * @param invaders Invader
     * @return null oder den getroffenen Invader
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
                        assetController.getAudioAsset("invaderKilled").play();
                        removeBullet(ship);

                        player.setScore(player.getScore() + collisionedInvader.getValue());
                        removeInvader(collisionedInvader);

                        //Geschwindigkeit in Abhängigkeit von der Invaderanzahl setzen
                        if(invaderGroup.getInvaderList().size() < invaderMaxCount / 3 ){
                            invaderMoveDuration = invaderSpeed3;
                        }
                        else if(invaderGroup.getInvaderList().size() < invaderMaxCount * 2/3 ){
                            invaderMoveDuration = invaderSpeed2;
                        }
                    }
                    if (invaderGroup.getInvaderList().size() == 0) {
                        gameStatus.set(WON);
                    }
                }

                // hat die schiffskanone das MysteryShip getroffen
                if (ship.getBullet() != null && mysteryShip != null) {
                    if (ship.getBullet().intersects(mysteryShip.getLayoutBounds())) {
                        assetController.getAudioAsset("mysteryKilled").play();
                        player.setScore(player.getScore() + mysteryShip.getValue());
                        removeBullet(ship);
                        removeMysteryShip();
                    }
                }

                // hat die schiffskanone den Bunker getroffen
                bunkerWurdeGetroffen:
                if (ship.getBullet() != null) {
                    for (Shelter shelter : shelterList) {
                        if(ship.getBullet().intersects(shelter.getLayoutBounds().getMinX(), shelter.getLayoutBounds().getMinY(), shelter.getLayoutBounds().getWidth(), shelter.getLayoutBounds().getHeight())) {
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
                }

                // hat ein invader den Bunker getroffen

                bunkerWurdeGetroffen2:
                for (Invader invader : invaderGroup.getInvaderList()) {
                    if (invader.getBullet() != null) {
                        for (Shelter shelter : shelterList) {
                            if(invader.getBullet().intersects(shelter.getLayoutBounds().getMinX(), shelter.getLayoutBounds().getMinY(), shelter.getLayoutBounds().getWidth(), shelter.getLayoutBounds().getHeight())) {

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
                }


                // hat ein Invader das ship getroffen
                for (Invader invader : invaderGroup.getInvaderList()) {
                    if (invader.getBullet() != null) {
                        if (ship.intersects(invader.getBullet().getLayoutBounds())) {
                            System.out.println("Ship getroffen");
                            assetController.getAudioAsset("shipExplosion").play();
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

                    Direction randomDirection = random.nextInt(2) == 0 ? RIGHT : LEFT;
                    mysteryShip = new MysteryShip(assetController.getImageAsset("mysteryShip"), randomDirection);
                    mysteryShip.getTimeLine().setOnFinished(event -> removeMysteryShip());
                    display.getChildren().add(mysteryShip);
                    mysteryShip.move(randomDirection);
                }
            }
        }
    }

    /**
     * Entfernt Geheimschiff
     */
    private void removeMysteryShip() {
        if(mysteryShip == null) return;

        mysteryShip.getTimeLine().stop();
        allActiveTimeLines.remove(mysteryShip.getTimeLine());
        display.getChildren().remove(mysteryShip);
        mysteryShip = null;
//        System.out.println("MysteryShip entfernt");
    }

    /**
     * startet das Spiel
     */
    public void play() {
        //GameAnimationTimer gameAnimationTimer = new GameAnimationTimer();
        gameAnimationTimer.start();

    }

    /**
     * stoppt das Spiel
     */
    public void stop() {
        allActiveTimeLines.forEach(Timeline::stop);
        this.gameAnimationTimer.stop();
    }

    /**
     * setzt das spiel zurück
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
        invaderMoveDuration = invaderSpeed1;
        this.setGameStatus(PLAY);

    }


    /**
     * Property Level
     * @return level
     */
    public IntegerProperty levelProperty() {
        return level;
    }

    /**
     * Getter-Methode
     * @return Spielstatus
     */
    public final GameStatus getGameStatus() { return gameStatus.get(); }

    /**
     * Setter-Methode
     * @param status Spielstatus
     */
    public final void setGameStatus(GameStatus status) {
        gameStatusProperty().set(status);
    }

    /**
     * Property Spielstatus
     * @return Spielstatus
     */
    public final ObjectProperty<GameStatus> gameStatusProperty() {
        return gameStatus;
    }

    /**
     * Getter-Methode Spieler
     * @return Spieler
     */
    public Player getPlayer() {
        return player;
    }

}
