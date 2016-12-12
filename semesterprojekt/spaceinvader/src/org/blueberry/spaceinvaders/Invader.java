package org.blueberry.spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static org.blueberry.spaceinvaders.InvaderGroup.MoveDirection;
import static org.blueberry.spaceinvaders.InvaderGroup.MoveDirection.*;


/**
 * Created by KK on 09.12.2016.
 */
public class Invader extends ImageView {

    private int value;
    private int width;
    private int height;
    private Image image1;
    private Image image2;

    private int moveXPixels = Integer.parseInt(SpaceInvaders.getSettings("invader.move.xpixel"));
    private int moveYPixels = Integer.parseInt(SpaceInvaders.getSettings("invader.move.ypixel"));

    public Invader(Image image1, Image image2, int positionX, int positionY, int value){

        this.value = value;

        this.image1 = image1;
        this.image2 = image2;

        this.setX(positionX);
        this.setY(positionY);
        this.setImage(this.image1);
        this.setCache(true);
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



    private void changeView(){
        this.setImage(this.getImage() == image1 ? image2 : image1);
    }

    public void move(MoveDirection direction) {

        switch (direction){
            case DOWN: this.setY(this.getY() + moveYPixels);
                break;
            case RIGHT: this.setX(this.getX() + moveXPixels);
                break;
            case LEFT: this.setX(this.getX() + moveXPixels * -1);
        }

        changeView();
    }

    public int getValue(){
        return value;
    }


    public int getWidth(){
        return width;
    }


    public int getHeight(){
        return height;
    }
}
