package org.blueberry.spaceinvaders.interfaces;

import org.blueberry.spaceinvaders.gameengine.Bullet;

/**
 * Created by KK on 13.12.2016.
 */
public interface IGunSprite extends ISprite{
    void newBullet();
    void removeBullet();
    Bullet getBullet();
}
