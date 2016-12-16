package org.blueberry.spaceinvaders.gameengine;

import org.blueberry.spaceinvaders.gameengine.InvaderGroup.MoveDirection;

/**
 * Created by KK on 13.12.2016.
 */
public interface ISprite {
    void move(MoveDirection direction);
}
