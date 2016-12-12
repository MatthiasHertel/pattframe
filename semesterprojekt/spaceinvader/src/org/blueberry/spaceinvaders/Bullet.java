package org.blueberry.spaceinvaders;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by KK on 12.12.2016.
 */
public class Bullet extends ImageView {

    private Timeline timeLine;

    public Bullet(Image image, int positionX, int positionY){
        this.setX(positionX);
        this.setY(positionY);
        this.setImage(image);
        this.setCache(true);
        this.setPreserveRatio(true);

        int duration = Integer.parseInt(SpaceInvaders.getSettings("ship.shoot.duration"));

        KeyValue keyValue = new KeyValue(this.yProperty(), Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.ystart")));
        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), keyValue);
        timeLine = new Timeline();
        timeLine.getKeyFrames().add(keyFrame);

        timeLine.play();


    }

    public Timeline getTimeLine(){
        return timeLine;
    }
}
