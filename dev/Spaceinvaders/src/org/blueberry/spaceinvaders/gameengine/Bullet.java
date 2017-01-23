package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.blueberry.spaceinvaders.SpaceInvaders;
import static org.blueberry.spaceinvaders.gameengine.Direction.*;


/**
 * Stellt das Projektil des Spielers und der Invaders dar
 */
public class Bullet extends ImageView implements ISprite {

    private Timeline timeLine;

    /**
     * Konstruktor für das Projektil
     * @param image Bild des Projektils
     * @param positionX X-Position des Projektils
     * @param positionY Y-Position des Projektils
     */
    public Bullet(Image image, int positionX, int positionY){
        this.setX(positionX);
        this.setY(positionY);
        this.setImage(image);
        this.setCache(true);
        this.setPreserveRatio(true);

        timeLine = new Timeline();
    }

    /**
     * Getter-Methode für das Timeline-Objekt
     * @return Timeline
     */
    public Timeline getTimeLine(){
        return timeLine;
    }

    /**
     * Implementiert die Bewegung des Projektils
     * @param direction die Bewegungsrichtung
     */
    @Override
    public void move(Direction direction){
        int duration = Integer.parseInt(SpaceInvaders.getSettings("ship.shoot.duration"));
        int yEndPosition = direction == DOWN ? Integer.parseInt(SpaceInvaders.getSettings("invader.shoot.end.y"))  : Integer.parseInt(SpaceInvaders.getSettings("ship.shoot.end.y"));

        KeyValue keyValue = new KeyValue(this.yProperty(), yEndPosition);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), keyValue);

        timeLine.getKeyFrames().add(keyFrame);

        Game.getInstance().getAllActiveTimeLines().add(timeLine);
        timeLine.play();
    }
}
