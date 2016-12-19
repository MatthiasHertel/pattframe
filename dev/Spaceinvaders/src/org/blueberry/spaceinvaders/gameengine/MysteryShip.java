package org.blueberry.spaceinvaders.gameengine;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public void move(InvaderGroup.MoveDirection direction) {

    }


}
