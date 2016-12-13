package org.blueberry.spaceinvaders;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by KK on 05.12.2016.
 */
public class Player {

//    scoreProperty ------------------------------------------------------------------------------------------------
    private IntegerProperty score = new SimpleIntegerProperty(0);
    
    public IntegerProperty scoreProperty(){
        return score;
    }

    public void setScore(int value){
        score.set(value);
    }


    public int getScore(){
        return score.get();
    }


//    livesProperty ------------------------------------------------------------------------------------------------
    private IntegerProperty lives = new SimpleIntegerProperty(Integer.parseInt(SpaceInvaders.getSettings("player.lives")));

    public IntegerProperty livesProperty(){
        return lives;
    }

    public void setlives(int value){
        lives.set(value);
    }


    public int getlives(){
        return lives.get();
    }
}
