package org.blueberry.spaceinvaders.gameengine;

/**
 * Interface für Spieleelemente, die schießen können
 */
public interface IGunSprite extends ISprite {
    /**
     * Fügt dem Spieleelement ein Projektil hinzu
     */
    void newBullet();

    /**
     * Entfernt das Projektil des Spielelements
     */
    void removeBullet();

    /**
     * Getter-Methode für ein Projektil
     * @return Projektil
     */
    Bullet getBullet();
}
