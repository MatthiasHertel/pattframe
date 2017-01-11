package org.blueberry.spaceinvaders.gameengine;

/**
 * IGunSprite-Interface
 */
public interface IGunSprite extends ISprite {
    void newBullet();

    void removeBullet();

    Bullet getBullet();
}
