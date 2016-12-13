package org.blueberry.spaceinvaders;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by KK on 05.12.2016.
 */
public class Player {

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
}
