package org.blueberry.spaceinvaders;

import javafx.beans.property.IntegerProperty;
import javafx.scene.image.ImageView;

/**
 * Created by KK on 25.11.2016.
 */

public interface ISprite {
    void move(int x, int y);
    ImageView getView();
    int getPositionX();
    int getPositionY();

    int getXMiddle();
    void setXMiddle(int value);
    IntegerProperty xMiddle();
    int getYMiddle();
    void setYMiddle(int value);
    IntegerProperty yMiddle();
}
