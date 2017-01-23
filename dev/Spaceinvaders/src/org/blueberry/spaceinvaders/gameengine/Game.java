package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.controller.AssetController;
import java.util.*;

import static org.blueberry.spaceinvaders.gameengine.Direction.LEFT;
import static org.blueberry.spaceinvaders.gameengine.Direction.RIGHT;
import static org.blueberry.spaceinvaders.gameengine.GameStatus.*;

/**
 * Spiel, enthält die gesamte Spielelogik
 */
public class Game {

    private AssetController assetController = AssetController.getInstance();
    private List<Timeline> allActiveTimeLines = new ArrayList<>();
    private InvaderGroup invaderGroup;
    private AnchorPane display;
    private Ship ship = new Ship(assetController.getImageAsset("ship"));
    private MysteryShip mysteryShip;
    private List<Shelter> shelterList;

    private Player player;
    private int currentInvaderBulletsCount = 0;
    private int maxInvaderBulletsCount = Integer.parseInt(SpaceInvaders.getSettings("invader.shoots.parallel"));
    private int invaderSpeed1 = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.1"));
    private int invaderSpeed2 = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.2"));
    private int invaderSpeed3 = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.3"));
    private int invaderMoveDuration = invaderSpeed1;

    private int invaderMaxCount;
    private ObjectProperty<GameStatus> gameStatus = new SimpleObjectProperty<>(PLAY);
    private IntegerProperty level = new SimpleIntegerProperty(1);

    private GameAnimationTimer gameAnimationTimer;

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

    /**
     * Konstruktor für das Spiel Singleton
     * erzeugt einen neuen Spieler
     */
    private Game() {
        player = new Player();
    }

    /**
     * Fügt ein Sprite zur Anchorpane hinzu
     * @param sprite ImageView, die der AnchorPane hinzugefügt wird
     */
    private void addSpriteToPane(ISprite sprite){
        if( sprite instanceof Shelter){
            Shelter shelter = (Shelter) sprite;
            for (ShelterPart shelterPart : shelter.getShelterParts()) {
                display.getChildren().add(shelterPart);
            }
            return;
        }
        display.getChildren().add((ImageView)sprite);
    }


    /**
     * Konstruiert den Spielplatz für alle Elemente
     * bindet die spiellogikrelevanten Elemente (Score, Leben) an die View
     * @param pane Entrynode in der View
     */
    public void constructGame(AnchorPane pane) {
        this.display = pane;
        createInvaderGroup();
        createShelters();

        addSpriteToPane(ship);

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
            if (newValue.intValue() == 0) {
                gameStatus.set(GAMEOVER);
            }
        });
    }

    /**
     * Erzeugt die Bunker
     */
    private void createShelters() {
        shelterList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            shelterList.add(new Shelter(208 + i * 176, Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"))));
            addSpriteToPane(shelterList.get(i));
        }
    }

    /**
     * Erzeugt und startet das MysteryShip
     */
    public void tryCreateMysteryShip(){

        if (mysteryShip != null) return;

        Random random = new Random();

        Direction randomDirection = random.nextInt(2) == 0 ? RIGHT : LEFT;
        mysteryShip = new MysteryShip(assetController.getImageAsset("mysteryShip"), randomDirection);

        mysteryShip.getTimeLine().setOnFinished(event -> {
            removeSprite(mysteryShip);
            mysteryShip = null;
        });

        addSpriteToPane(mysteryShip);
        mysteryShip.move(randomDirection);
    }

    /**
     * setzt alle Timelines auf Pause
     * @param timeLines
     */
    private void pauseActiveTimeLines(List<Timeline> timeLines) {
        timeLines.forEach(Timeline::pause);
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
    public void removeBullet(IGunSprite sprite) {
        if (sprite instanceof Invader) {
            currentInvaderBulletsCount--;
        }

        removeSprite(sprite.getBullet());
        sprite.removeBullet();
    }


    /**
     * Entfernt Sprite vom Spiel
     * @param sprite
     */
    public void removeSprite(ISprite sprite) {
        if (sprite == null) return;

        if (sprite instanceof ShelterPart) {
            System.out.println("instance of = shelterpart");
            for (Shelter shelter : shelterList) {
                shelter.getShelterParts().remove(sprite);
            }
        }

        else if (sprite instanceof Invader) {
            invaderGroup.removeInvader((Invader) sprite);
        }

        else if( sprite instanceof Bullet){
            Bullet bullet = (Bullet) sprite;
            bullet.getTimeLine().stop();
            allActiveTimeLines.remove(bullet.getTimeLine());
        }

        else if( sprite instanceof MysteryShip){
            Timeline timeline = ((MysteryShip) sprite).getTimeLine();
            timeline.stop();
            allActiveTimeLines.remove(timeline);
            display.getChildren().remove(sprite);
            mysteryShip = null;
            return;
        }

        else if (sprite instanceof Shelter){
            for (ShelterPart shelterPart : ((Shelter)sprite).getShelterParts()) {
                display.getChildren().remove(shelterPart);
            }
        }

        display.getChildren().remove(sprite);
    }


    /**
     * Testet, ob der Spieler das Geheimschiff getroffen hat
     * und behandelt dementsprechend
     */
    public void checkAndHandleMysteryShipCollision(){
        if (ship.getBullet() != null && mysteryShip != null) {
            if (ship.getBullet().intersects(mysteryShip.getLayoutBounds())) {
                assetController.getAudioAsset("mysteryKilled").play();
                player.setScore(player.getScore() + mysteryShip.getValue());
                removeBullet(ship);
                removeSprite(mysteryShip);
                mysteryShip = null;
            }
        }
    }

    /**
     * Testet, ob der Spieler einen Invader getroffen hat
     * und behandelt dementsprechend
     */
    public void checkAndHandleInvaderCollision() {
        if (ship.getBullet() != null) {
            Invader collisionedInvader = detectCollisionedInvader(ship.getBullet(), invaderGroup.getInvaderList());
            if (collisionedInvader != null) {
                assetController.getAudioAsset("invaderKilled").play();
                removeBullet(ship);

                player.setScore(player.getScore() + collisionedInvader.getValue());
               removeSprite(collisionedInvader);

                //Geschwindigkeit in Abhängigkeit von der Invaderanzahl setzen
                if(invaderGroup.getInvaderList().size() < invaderMaxCount / 3 ){
                    invaderMoveDuration = invaderSpeed3;
                }
                else if(invaderGroup.getInvaderList().size() < invaderMaxCount * 2/3 ){
                    invaderMoveDuration = invaderSpeed2;
                }
            }
            if (invaderGroup.getInvaderList().size() == 0) {
                setGameStatus(WON);
            }
        }
    }

    /**
     * Testet, ob der Spieler einen Bunker getroffen hat
     * und behandelt dementsprechend
     */
    public void checkAndHandleShipBulletShelterCollision() {
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
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Testet, ob ein Invader einen Bunker getroffen hat
     * und behandelt dementsprechend
     */
    public void checkAndHandleInvaderBulletShelterCollision() {
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
                                return;
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Testet, ob ein Invader den Spieler getroffen hat
     * und behandelt dementsprechend
     */
    public void checkAndHandleInvaderBulletShipCollision() {
        for (Invader invader : invaderGroup.getInvaderList()) {
            if (invader.getBullet() != null) {
                if (ship.intersects(invader.getBullet().getLayoutBounds())) {
                    assetController.getAudioAsset("shipExplosion").play();
                    player.setlives(player.getlives() - 1);
                    removeBullet(invader);
                    break;
                }
            }
        }
    }


    /**
     * steuert Eigenschussfrequenz , falls noch kein Schuss aktiv ist , schiesse sonst nicht
     */
    public void tryShipShoot() {

        if (ship.getBullet() == null && gameStatus.get() == PLAY) {
            ship.newBullet();
            ship.getBullet().getTimeLine().setOnFinished(event -> {
                removeBullet(ship);
            });

            display.getChildren().add(ship.getBullet());
            ship.shoot();
        }
    }

    /**
     * steuert Invaderschussfrequenz , falls noch kein Schuss aktiv ist , schiesse sonst nicht
     */
    public void tryInvaderShoot() {

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
            shootInvader.getBullet().getTimeLine().setOnFinished(event -> removeBullet(shootInvader));

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
        invaderGroup.getInvaderList().forEach(this::addSpriteToPane);
        invaderMaxCount = invaderGroup.getInvaderList().size();
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
    public Invader detectCollisionedInvader(Bullet bullet, List<Invader> invaders) {
        for (Invader invader : invaders) {
            if (bullet.intersects(invader.getLayoutBounds())) {
                return invader;
            }
        }
        return null;
    }

    /**
     * startet das Spiel
     */
    public void play() {
        gameAnimationTimer = new GameAnimationTimer();
        gameAnimationTimer.start();
        setGameStatus(PLAY);
    }

    /**
     * stoppt das Spiel
     */
    public void stop() {
        allActiveTimeLines.forEach(Timeline::stop);
        gameAnimationTimer.stop();
    }

    /**
     * setzt das spiel zurück
     */
    public static void reset() {
        ourInstance = null;
    }

    /**
     * initiiert das nächste Spiellevel
     */
    public void nextLevel(){
        stop();

        if(shelterList != null){
            shelterList.forEach(this::removeSprite);
        }
        removeSprite(mysteryShip);

        invaderMoveDuration = invaderSpeed1;

        level.set(level.get() < 10 ? level.get() + 1 : 1);

        createInvaderGroup();
        createShelters();

        play();
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

    /**
     * Getter-Methode Spieler-Schiff
     * @return Schiff
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Getter-Methode für die Zeit zwischen der Invaderbewegung
     * @return invaderMoveDuration
     */
    public int getInvaderMoveDuration(){
        return this.invaderMoveDuration;
    }

}
