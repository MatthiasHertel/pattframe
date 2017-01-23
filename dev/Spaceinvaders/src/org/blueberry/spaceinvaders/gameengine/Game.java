package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.AnchorPane;

import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.controller.AssetController;
import java.util.*;

import static org.blueberry.spaceinvaders.gameengine.GameStatus.*;

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

    private Player player;
    private int currentInvaderBulletsCount = 0;
    private int maxInvaderBulletsCount = Integer.parseInt(SpaceInvaders.getSettings("invader.shoots.parallel"));
    private ObjectProperty<GameStatus> gameStatus = new SimpleObjectProperty<>(PLAY);
    private IntegerProperty level = new SimpleIntegerProperty(1);

    private GameAnimationTimer gameAnimationTimer;


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
     * Setzt das MysteryShip und fügt es zur Anchorpane hinzu
     * @param mysteryShip Geheimschiff
     */
    public void setMysteryShip(MysteryShip mysteryShip) {
        this.mysteryShip = mysteryShip;
        display.getChildren().add(mysteryShip);
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
    public void removeBullet(IGunSprite sprite) {
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
    public void removeInvader(Invader invader) {
        display.getChildren().remove(invader);
        invaderGroup.removeInvader(invader);
    }

    /**
     * Entfernt Sprite vom Spiel(TODO: vereinheitliche mit anderen remove Methoden DRY: removeInvader,removeBullet  )
     * @param sprite
     */
    public void removeSprite(ISprite sprite) {
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
    public void tryShipShoot() {

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
            shootInvader.getBullet().getTimeLine().setOnFinished(event -> {
                removeBullet(shootInvader);
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
    public Invader detectCollisionedInvader(Bullet bullet, List<Invader> invaders) {
        for (Invader invader : invaders) {
            if (bullet.intersects(invader.getLayoutBounds())) {
                return invader;
            }
        }
        return null;
    }


    /**
     * Entfernt Geheimschiff
     */
    public void removeMysteryShip() {
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
        gameAnimationTimer = new GameAnimationTimer();
        gameAnimationTimer.start();
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
        level.set(level.get() < 10 ? level.get() + 1 : 1);

        removeMysteryShip();

        createInvaderGroup();
        createShelter();

        setGameStatus(PLAY);
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
     * Getter-Methode für alle Schutzbunker
     * @return ShelterList
     */
    public List<Shelter> getShelterList() {
        return shelterList;
    }

}
