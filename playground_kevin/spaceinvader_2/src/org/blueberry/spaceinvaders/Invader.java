package org.blueberry.spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by KK on 09.12.2016.
 */
public class Invader extends ImageView {

    private int value;
    private int width;
    private int height;

    public Invader(Image image, int positionX, int positionY, int value){
        this.setX(positionX);
        this.setY(positionY);
        this.setImage(image);
        this.setCache(true);
        this.value = value;

        this.setPreserveRatio(true);

        int invaderHeigthSuggestion = Integer.parseInt(SpaceInvaders.getSettings("invader.height"));
        int invaderWidthSuggestion = Integer.parseInt(SpaceInvaders.getSettings("invader.widht"));
        if (invaderHeigthSuggestion > 0){
            this.setFitHeight(invaderHeigthSuggestion);
            this.height = invaderHeigthSuggestion;
            this.width = (int) this.getLayoutBounds().getWidth();
        }
        else if (invaderWidthSuggestion > 0){
            this.setFitWidth(invaderWidthSuggestion);
            this.width = invaderWidthSuggestion;
            this.height = (int) this.getLayoutBounds().getHeight();
        }


    }

    public int getWidth(){
        return width;
    }


    public int getHeight(){
        return height;
    }
}
