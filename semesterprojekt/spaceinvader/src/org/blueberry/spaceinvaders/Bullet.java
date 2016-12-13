package org.blueberry.spaceinvaders;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static org.blueberry.spaceinvaders.InvaderGroup.MoveDirection;


/**
 * Created by KK on 12.12.2016.
 */
public class Bullet extends ImageView implements ISprite{

    private Timeline timeLine;

    public Bullet(Image image, int positionX, int positionY){
        this.setX(positionX);
        this.setY(positionY);
        this.setImage(image);
        this.setCache(true);
        this.setPreserveRatio(true);

        timeLine = new Timeline();

    }

    public Timeline getTimeLine(){
        return timeLine;
    }

    @Override
    public void move(MoveDirection direction){
        int duration = Integer.parseInt(SpaceInvaders.getSettings("ship.shoot.duration"));
        int yEndPosition = direction == MoveDirection.DOWN ? Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend")) + 150 : Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.ystart"));

        KeyValue keyValue = new KeyValue(this.yProperty(), yEndPosition);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), keyValue);

        timeLine.getKeyFrames().add(keyFrame);

        Game.getInstance().getAllActiveTimeLines().add(timeLine);
        timeLine.play();

    }
}
