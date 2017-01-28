package org.blueberry.spaceinvaders.gameengine;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.controller.AssetController;

import static org.blueberry.spaceinvaders.gameengine.Direction.*;

/**
 * Spielfigur
 */
public class Ship extends ImageView implements IGunSprite {

    private AssetController assetController = AssetController.getInstance();
    private int borderXEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xend"));

    private Bullet bullet;
    private Direction moveDirection = Direction.NONE;

    /**
     * Konstruktor für die Spielfigur
     * @param image Spielfigurelement
     */
    public Ship(Image image) {

        this.setImage(image);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(Integer.parseInt(SpaceInvaders.getSettings("ship.width")));

        int borderXSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xstart"));
        int positionX = borderXSstart + (borderXEend - borderXSstart) / 2 - (int) this.getFitWidth() / 2;

        this.setX(positionX);
        this.setY(Integer.parseInt(SpaceInvaders.getSettings("ship.position.y")));
    }

    /**
     * Implementiert die Bewegung der Spielfigur
     * @param direction die Bewegungsrichtung
     */
    @Override
    public void move(Direction direction) {
        if (Game.getInstance().getGameStatus() != GameStatus.PLAY || direction == Direction.NONE) {
            return;
        }
        int positionX = (int) getX();
        int x = 0;

        if (direction == Direction.RIGHT) {
            x = Integer.parseInt(SpaceInvaders.getSettings("ship.move.step"));
        } else if (direction == Direction.LEFT) {
            x = Integer.parseInt(SpaceInvaders.getSettings("ship.move.step")) * -1;
        }

        if (positionX + x + getFitWidth() < borderXEend && positionX + x > 0) {
            positionX += x;
            this.setX(positionX);
        }
    }

    /**
     * Setzt einen Schiffs-Schuss ab
     */
    void shoot() {
        assetController.getAudioAsset("shipShoot").play();
        bullet.move(UP);
    }

    /**
     * Setter-Methode für die Bewegungsrichtung
     * @param direction die Bewegungsrichtung
     */
    public void setMoveDirection(Direction direction) {
        this.moveDirection = direction;
    }

    /**
     * Getter-Methode für die Bewegungsrichtung
     * @return die Bewegungsrichtung
     */
    Direction getMoveDirection() {
        return this.moveDirection;
    }

    /**
     * Erzeugt ein neues Projektil
     */
    @Override
    public void newBullet() {
        int bulletPositionX = (int) (getX() + Integer.parseInt(SpaceInvaders.getSettings("ship.width")) / 2);
        int bulletPositionY = (int) getY();
        bullet = new Bullet(assetController.getImageAsset("shipBullet"), bulletPositionX, bulletPositionY);
    }

    /**
     * Entfernt das Projektil
     */
    @Override
    public void removeBullet() {
        bullet = null;
    }

    /**
     * Getter-Methode für das Projektil
     * @return Projektil
     */
    @Override
    public Bullet getBullet() {
        return bullet;
    }
}
