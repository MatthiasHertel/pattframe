package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.blueberry.spaceinvaders.SpaceInvaders;

/**
 * Bullet
 */
public class Bullet extends ImageView implements ISprite {

    private Timeline timeLine;

    /**
     * Bullet
     * @param image
     * @param positionX
     * @param positionY
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
     * getTimeLine
     * @return
     */
    public Timeline getTimeLine(){
        return timeLine;
    }

    /**
     * move
     * @param direction
     */
    @Override
    public void move(InvaderGroup.MoveDirection direction){
        int duration = Integer.parseInt(SpaceInvaders.getSettings("ship.shoot.duration"));
        int yEndPosition = direction == InvaderGroup.MoveDirection.DOWN ? Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend")) + 150 : Integer.parseInt(SpaceInvaders.getSettings("ship.shoot.end.y"));

        KeyValue keyValue = new KeyValue(this.yProperty(), yEndPosition);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), keyValue);

        timeLine.getKeyFrames().add(keyFrame);

        Game.getInstance().getAllActiveTimeLines().add(timeLine);
        timeLine.play();
    }
}
