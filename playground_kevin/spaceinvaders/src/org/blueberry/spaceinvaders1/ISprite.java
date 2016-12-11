package org.blueberry.spaceinvaders1;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

/**
 * Created by KK on 25.11.2016.
 */

public interface ISprite {
    void move(int x, int y);
    ImageView getView();
    int getPositionX();
    int getPositionY();

    Rectangle2D getBoundary();
}
