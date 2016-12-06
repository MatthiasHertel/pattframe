package org.blueberry.spaceinvaders;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by KK on 25.11.2016.
 */
public class Invader implements IGunSprite {

    private Image image1;
    private Image image2;
    private ImageView view;
    private IntegerProperty positionX;
    private IntegerProperty positionY;

    private int id;
    private int value;


    public Invader(int id, int type, int positionX, int positionY){

        this.positionY = new SimpleIntegerProperty(positionY);
        this.positionX = new SimpleIntegerProperty(positionX);
        this.id = id;


        switch (type){

            case 1 :{
                this.value = 10;
                image1 = new Image("images/invader1a.png");
                image2 = new Image("images/invader1b.png");
                break;
            }
            case 2 :{
                this.value = 20;
                image1 = new Image("images/invader2a.png");
                image2 = new Image("images/invader2b.png");
                break;

            }
            case 3 :{
                this.value = 30;
                image1 = new Image("images/invader3a.png");
                image2 = new Image("images/invader3b.png");
                break;

            }
        }


        view = new ImageView(image1);
        view.setFitHeight(23);
//        view.setFitWidth(23);
        view.setPreserveRatio(true);

        view.xProperty().bind(positionXProperty());
        view.yProperty().bind(positionYProperty());

    }




    @Override
    public void move(int x, int y) {

        positionX.set(getPositionX() + x);
        positionY.set(getPositionY() + y);
        changeView();

    }

    @Override
    public void shoot() {

    }



    private void changeView(){
        if (view.getImage() == image1){
            view.setImage(image2);
        }
        else {
            view.setImage(image1);
        }
    }

    public int getValue(){
        return value;
    }



    @Override
    public ImageView getView(){
        return view;
    }



//    Methoden für Properties positionX/Y

    public IntegerProperty positionXProperty(){
        return positionX;
    }

    public IntegerProperty positionYProperty(){
        return positionY;
    }

    public void setPositionX(int value){
        positionX.set(value);
    }
    public void setPositionY(int value){
            positionY.set(value);
        }

    @Override
    public int getPositionX(){
        return positionX.get();
    }

    @Override
    public int getPositionY(){
        return positionY.get();
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(getPositionX(), getPositionY(), 33, 23);
    }

}

