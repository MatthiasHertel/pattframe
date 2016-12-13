package org.blueberry.spaceinvaders;

/**
 * Created by KK on 13.12.2016.
 */
public interface IGunSprite extends ISprite{
    void newBullet();
    void removeBullet();
    Bullet getBullet();
}
