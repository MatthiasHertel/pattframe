package org.blueberry.spaceinvaders.gameengine;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.blueberry.spaceinvaders.SpaceInvaders;

/**
 * Ship-Klasse
 */
public class Ship extends ImageView implements IGunSprite {

    private int borderXSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xstart"));
    private int borderXEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xend"));
    private int borderYEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"));

    private Bullet bullet;
    private InvaderGroup.MoveDirection moveDirection = InvaderGroup.MoveDirection.NONE;

    /**
     * Ship
     * @param image
     */
    public Ship(Image image) {

        this.setImage(image);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(Integer.parseInt(SpaceInvaders.getSettings("ship.width")));

        int positionX = borderXSstart + (borderXEend - borderXSstart) / 2 - (int) this.getFitWidth() / 2;
        int positionY = borderYEend + 50;

        this.setX(positionX);
        this.setY(positionY);
    }

    /**
     * move
     * @param direction
     */
    @Override
    public void move(InvaderGroup.MoveDirection direction) {
        if (Game.getInstance().getGameStatus() != Game.GameStatus.PLAY || direction == InvaderGroup.MoveDirection.NONE) {
            return;
        }
        int positionX = (int) getX();
        int x = 0;

        if (direction == InvaderGroup.MoveDirection.RIGHT) {
            x = Integer.parseInt(SpaceInvaders.getSettings("ship.move.step"));
        } else if (direction == InvaderGroup.MoveDirection.LEFT) {
            x = Integer.parseInt(SpaceInvaders.getSettings("ship.move.step")) * -1;
        }

        if (positionX + x + getFitWidth() < borderXEend && positionX + x > 0) {
            positionX += x;
            this.setX(positionX);
        }
    }

    /**
     * shoot
     */
    public void shoot() {
        Game.getInstance().getAudioAsset("shipShoot").play();
        bullet.move(InvaderGroup.MoveDirection.UP);

        System.out.println("SchiffSchuss");
    }

    /**
     * setMoveDirection
     * @param direction
     */
    public void setMoveDirection(InvaderGroup.MoveDirection direction) {
        this.moveDirection = direction;
    }

    /**
     * getMoveDirection
     * @return
     */
    public InvaderGroup.MoveDirection getMoveDirection() {
        return this.moveDirection;
    }

    /**
     * newBullet
     */
    @Override
    public void newBullet() {
        int bulletPositionX = (int) (getX() + Integer.parseInt(SpaceInvaders.getSettings("ship.width")) / 2);
//        int bulletPositionY = (int)getY()-30;
        int bulletPositionY = (int) getY();
        bullet = new Bullet(Game.getInstance().getImageAsset("shipBullet"), bulletPositionX, bulletPositionY);
    }

    /**
     * removeBullet
     */
    @Override
    public void removeBullet() {
        bullet = null;
    }

    /**
     * getBullet
     * @return
     */
    @Override
    public Bullet getBullet() {
        return bullet;
    }
}
