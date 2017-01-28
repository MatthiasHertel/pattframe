package org.blueberry.spaceinvaders.gameengine;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.blueberry.spaceinvaders.SpaceInvaders;

/**
 * Spieler
 */
public class Player {

    //    scoreProperty ------------------------------------------------------------------------------------------------
    private IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Property für Punkte
     * @return Punkte-Wert
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Setter-Methode für den Punkte-Wert
     * @param value Punkte-Wert
     */
    void setScore(int value) {
        score.set(value);
    }

    /**
     * Getter-Methode für den Punkte-Wert
     * @return Punkte-Wert
     */
    public int getScore() {
        return score.get();
    }

    //    livesProperty ------------------------------------------------------------------------------------------------
    private IntegerProperty lives = new SimpleIntegerProperty(Integer.parseInt(SpaceInvaders.getSettings("player.lives")));

    /**
     * Property für die Spieler-Leben
     * @return Spieler-Leben
     */
    public IntegerProperty livesProperty() {
        return lives;
    }

    /**
     * Setter-Methode für die Spieler-Leben
     * @param value Spieler-Leben
     */
    void setlives(int value) {
        lives.set(value);
    }

    /**
     * Getter-Methode für die Spieler-Leben
     * @return Spieler-Leben
     */
    int getlives() {
        return lives.get();
    }
}
