package org.blueberry.spaceinvaders.gameengine;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.blueberry.spaceinvaders.SpaceInvaders;

/**
 * Invader-Klasse
 */
public class Invader extends ImageView implements IGunSprite {

    private int value;
    private int width;
    private int height;
    private Image image1;
    private Image image2;

    private Bullet bullet;

    private int moveXPixels = Integer.parseInt(SpaceInvaders.getSettings("invader.move.xpixel"));
    private int moveYPixels = Integer.parseInt(SpaceInvaders.getSettings("invader.move.ypixel"));

    /**
     * Invader
     * @param image1
     * @param image2
     * @param positionX
     * @param positionY
     * @param value
     */
    public Invader(Image image1, Image image2, int positionX, int positionY, int value) {

        this.value = value;

        this.image1 = image1;
        this.image2 = image2;

        this.setX(positionX);
        this.setY(positionY);
        this.setImage(this.image1);
        this.setCache(true);
        this.setPreserveRatio(true);

        int invaderHeigthSuggestion = Integer.parseInt(SpaceInvaders.getSettings("invader.height"));
        int invaderWidthSuggestion = Integer.parseInt(SpaceInvaders.getSettings("invader.widht"));
        if (invaderHeigthSuggestion > 0) {
            this.setFitHeight(invaderHeigthSuggestion);
            this.height = invaderHeigthSuggestion;
            this.width = (int) this.getLayoutBounds().getWidth();
        } else if (invaderWidthSuggestion > 0) {
            this.setFitWidth(invaderWidthSuggestion);
            this.width = invaderWidthSuggestion;
            this.height = (int) this.getLayoutBounds().getHeight();
        }
    }

    /**
     * changeView
     */
    private void changeView() {
        this.setImage(this.getImage() == image1 ? image2 : image1);
    }

    /**
     * move
     * @param direction
     */
    @Override
    public void move(InvaderGroup.MoveDirection direction) {

        switch (direction) {
            case DOWN:
                this.setY(this.getY() + moveYPixels);
                break;
            case RIGHT:
                this.setX(this.getX() + moveXPixels);
                break;
            case LEFT:
                this.setX(this.getX() + moveXPixels * -1);
        }

        changeView();
    }

    /**
     * shoot
     */
    public void shoot() {
        //Game.getInstance().getAudioAsset("invaderShoot").play(); TODO: invaderShootsound
        bullet.move(InvaderGroup.MoveDirection.DOWN);
        System.out.println("InvaderSchuss");
    }

    /**
     * newBullet
     */
    @Override
    public void newBullet() {
        int bulletPositionX = (int) (getX() + width / 2);
        int bulletPositionY = (int) getY();
        bullet = new Bullet(Game.getInstance().getImageAsset("invaderBullet"), bulletPositionX, bulletPositionY);
    }

    /**
     * getBullet
     * @return
     */
    @Override
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * removeBullet
     */
    @Override
    public void removeBullet() {
        bullet = null;
    }

    /**
     * getValue
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * getWidth
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * getHeight
     * @return
     */
    public int getHeight() {
        return height;
    }
}
