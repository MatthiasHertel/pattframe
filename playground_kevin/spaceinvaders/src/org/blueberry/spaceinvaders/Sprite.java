package org.blueberry.spaceinvaders;

import javafx.scene.image.ImageView;

/**
 * Created by KK on 25.11.2016.
 */

public interface Sprite {
    void move(int x, int y);
    void shoot();
    ImageView getView();
    int getPositionX();
    int getPositionY();
}
