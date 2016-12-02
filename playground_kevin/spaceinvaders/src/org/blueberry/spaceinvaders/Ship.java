package org.blueberry.spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by KK on 28.11.2016.
 */
public class Ship implements Sprite{

    private int positionX;
    private int positionY;
    private ImageView view;

    public Ship(){
        positionX = Border.getInstance().getXend() / 2;
        positionY = Border.getInstance().getYend() + 50;

//        view = new ImageView(new Image(String.valueOf(getClass().getResource("../../../images/ship.png"))));
        view = new ImageView(new Image("images/ship.png"));
        view.setX(positionX);
        view.setY(positionY);

    }

    @Override
    public void move(int x, int y) {

        if(positionX + x + 40 < Border.getInstance().getXend() && positionX + x > 0){
            positionX += x;
            view.setX(positionX);
        }



    }

    @Override
    public void shoot() {

    }

    @Override
    public ImageView getView() {
        return view;
    }

    @Override
    public int getPositionX() {
        return 0;
    }

    @Override
    public int getPositionY() {
        return 0;
    }
}
