package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.gameengine.InvaderGroup.MoveDirection;
import static org.blueberry.spaceinvaders.gameengine.InvaderGroup.MoveDirection.*;


/**
 * Created by KK on 19.12.2016.
 */
public class MysteryShip extends ImageView implements ISprite{

    private int borderXSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xstart"));
    private int borderXEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xend"));
    private int borderYSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.ystart"));

    private Timeline timeLine = new Timeline();
    private int value = Integer.parseInt(SpaceInvaders.getSettings("mysteryship.value"));
    private MoveDirection moveDirection;

    public MysteryShip(Image image, MoveDirection direction){

        this.moveDirection = direction;

        this.setImage(image);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(Integer.parseInt(SpaceInvaders.getSettings("mysteryship.width")));

        int positionX = direction == LEFT ? borderXEend : borderXSstart;
        int positionY = borderYSstart + 50;

        this.setX(positionX);
        this.setY(positionY);
    }

    @Override
    public void move(MoveDirection direction) {
        if (! (direction == RIGHT || direction == LEFT)) {
            System.out.println("Das MysteryShip kann sich nur nach links oder rechts bewegen. Bewegung (" + direction + ") abgebrochen");
            return;
        }

        int duration = Integer.parseInt(SpaceInvaders.getSettings("mysteryship.duration"));
        int xEndPosition = direction == RIGHT ? borderXEend + (int)this.getFitWidth() : borderXSstart - (int)this.getFitWidth();

        KeyValue keyValue = new KeyValue(this.xProperty(), xEndPosition);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), keyValue);

        timeLine.getKeyFrames().add(keyFrame);

        Game.getInstance().getAudioAsset("mystery").play();
        Game.getInstance().getAllActiveTimeLines().add(timeLine);
        timeLine.play();

    }

    public Timeline getTimeLine(){
        return timeLine;
    }

    public int getValue(){
        return value;
    }

}
