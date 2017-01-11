package org.blueberry.spaceinvaders.gameengine;

import org.blueberry.spaceinvaders.gameengine.InvaderGroup.MoveDirection;

/**
 * ISprite-Interface
 */
public interface ISprite {
    void move(MoveDirection direction);
}
