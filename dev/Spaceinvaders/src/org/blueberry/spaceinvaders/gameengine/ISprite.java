package org.blueberry.spaceinvaders.gameengine;

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
