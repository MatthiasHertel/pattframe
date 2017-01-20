package org.blueberry.spaceinvaders.gameengine;

import org.blueberry.spaceinvaders.gameengine.InvaderGroup.MoveDirection;

/**
 * Interface für Spielelemente
 */
public interface ISprite {
    /**
     * Bewegung des Spielelements
     * @param direction die Bewegungsrichtung
     */
    void move(MoveDirection direction);
}
