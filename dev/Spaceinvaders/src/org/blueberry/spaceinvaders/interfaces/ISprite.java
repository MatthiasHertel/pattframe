package org.blueberry.spaceinvaders.interfaces;

import org.blueberry.spaceinvaders.gameengine.InvaderGroup;

/**
 * Created by KK on 13.12.2016.
 */
public interface ISprite {
    void move(InvaderGroup.MoveDirection direction);
}
