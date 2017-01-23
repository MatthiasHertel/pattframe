package org.blueberry.spaceinvaders.gameengine;

import org.blueberry.spaceinvaders.gameengine.Direction;

/**
 * Interface für Spielelemente
 */
public interface ISprite {
    /**
     * Bewegung des Spielelements
     * @param direction die Bewegungsrichtung
     */
    void move(Direction direction);
}
