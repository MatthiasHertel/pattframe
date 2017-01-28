package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.controller.AssetController;
import static org.blueberry.spaceinvaders.gameengine.Direction.*;

/**
 * MysteryShip
 */
public class MysteryShip extends ImageView implements ISprite {

    private AssetController assetController = AssetController.getInstance();

    private int borderXSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xstart"));
    private int borderXEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xend"));

    private Timeline timeLine = new Timeline();
    private int value = Integer.parseInt(SpaceInvaders.getSettings("mysteryship.value"));

    /**
     * Konstruktor für ein MysteryShip
     * @param image Bildelement
     * @param direction die Bewedungsrichting
     */
    public MysteryShip(Image image, Direction direction) {

        this.setImage(image);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(Integer.parseInt(SpaceInvaders.getSettings("mysteryship.width")));

        int positionX = direction == LEFT ? borderXEend : borderXSstart;
        int positionY = Integer.parseInt(SpaceInvaders.getSettings("mysteryship.position.y"));

        this.setX(positionX);
        this.setY(positionY);
    }

    /**
     * Implementiert die Bewegung des MysteryShips
     * @param direction die Bewegungsrichtung
     */
    @Override
    public void move(Direction direction) {
        if (!(direction == RIGHT || direction == LEFT)) {
            System.out.println("Das MysteryShip kann sich nur nach links oder rechts bewegen. Bewegung (" + direction + ") abgebrochen");
            return;
        }

        int duration = Integer.parseInt(SpaceInvaders.getSettings("mysteryship.duration"));
        int xEndPosition = direction == RIGHT ? borderXEend + (int) this.getFitWidth() : borderXSstart - (int) this.getFitWidth();

        KeyValue keyValue = new KeyValue(this.xProperty(), xEndPosition);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), keyValue);

        timeLine.getKeyFrames().add(keyFrame);

        assetController.getAudioAsset("mystery").play();
        Game.getInstance().getAllActiveTimeLines().add(timeLine);
        timeLine.play();
    }

    /**
     * Getter-Methode für das Timeline-Objekt
     * @return Timeline
     */
    Timeline getTimeLine() {
        return timeLine;
    }

    /**
     * Getter-Methode für den Punkt-Wert, der dem Spieler gutgeschrieben wird
     * @return Punkt-Wert
     */
    int getValue() {
        return value;
    }
}
