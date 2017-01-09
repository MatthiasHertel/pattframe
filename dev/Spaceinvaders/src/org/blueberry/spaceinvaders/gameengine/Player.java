package org.blueberry.spaceinvaders.gameengine;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.blueberry.spaceinvaders.SpaceInvaders;

/**
 * Player-Klasse
 */
public class Player {

    //    scoreProperty ------------------------------------------------------------------------------------------------
    private IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * scoreProperty
     * @return
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * setScore
     * @param value
     */
    public void setScore(int value) {
        score.set(value);
    }

    /**
     * getScore
     * @return
     */
    public int getScore() {
        return score.get();
    }

    //    livesProperty ------------------------------------------------------------------------------------------------
    private IntegerProperty lives = new SimpleIntegerProperty(Integer.parseInt(SpaceInvaders.getSettings("player.lives")));

    /**
     * livesProperty
     * @return
     */
    public IntegerProperty livesProperty() {
        return lives;
    }

    /**
     * setlives
     * @param value
     */
    public void setlives(int value) {
        lives.set(value);
    }

    /**
     * getlives
     * @return
     */
    public int getlives() {
        return lives.get();
    }
}
