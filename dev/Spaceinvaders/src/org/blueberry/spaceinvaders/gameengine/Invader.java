package org.blueberry.spaceinvaders.gameengine;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.controller.AssetController;

/**
 * Invader
 */
public class Invader extends ImageView implements IGunSprite {

    private AssetController assetController = AssetController.getInstance();

    private int value;
    private int width;
    private int height;
    private Image image1;
    private Image image2;

    private Bullet bullet;

    private int moveXPixels = Integer.parseInt(SpaceInvaders.getSettings("invader.move.xpixel"));
    private int moveYPixels = Integer.parseInt(SpaceInvaders.getSettings("invader.move.ypixel"));

    /**
     * Konstruktor für einen Invader
     * @param image1 Invader-Bewegungsanimationszustand 1
     * @param image2 Invader-Bewegungsanimationszustand 2
     * @param positionX X-Position
     * @param positionY Y-Position
     * @param value Punkte-Wert
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
     * Wechselt den Bewegungsanimationszustand
     */
    private void changeView() {
        this.setImage(this.getImage() == image1 ? image2 : image1);
    }

    /**
     * Implementiert die Bewegung des Invaders
     * @param direction die Bewegungsrichtung
     */
    @Override
    public void move(Direction direction) {

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
     * Setzt einen Invade-Schuss ab
     */
    void shoot() {
        //Game.getInstance().getAudioAsset("invaderShoot").play(); TODO: invaderShootsound?
        bullet.move(Direction.DOWN);
    }

    /**
     * Erzeugt ein neues Projektil
     */
    @Override
    public void newBullet() {
        int bulletPositionX = (int) (getX() + width / 2);
        int bulletPositionY = (int) getY();
        bullet = new Bullet(assetController.getImageAsset("invaderBullet"), bulletPositionX, bulletPositionY);
    }

    /**
     * Getter-Methode für das Projektil
     * @return Projektil
     */
    @Override
    public Bullet getBullet() {
        return bullet;
    }

    /**
     * Entfernt das Projektil
     */
    @Override
    public void removeBullet() {
        bullet = null;
    }

    /**
     * Getter-Methode für den Punkte-Wert
     * @return Punkte-Wert
     */
    public int getValue() {
        return value;
    }

    /**
     * Getter-Methode für die Invader-Breite
     * @return Breite
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter-Methode für die Invader-Höhe
     * @return Höhe
     */
    public int getHeight() {
        return height;
    }
}
