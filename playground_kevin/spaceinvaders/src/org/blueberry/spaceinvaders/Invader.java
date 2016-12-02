package org.blueberry.spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by KK on 25.11.2016.
 */
public class Invader implements Sprite {

    private Image image1;
    private Image image2;
    private ImageView view;
    private int positionX;
    private int positionY;
    private int type;
    private int value;


    public Invader(int type, int positionX, int positionY){

        this.positionX = positionX;
        this.positionY = positionY;


        switch (type){

            case 1 :{
                this.value = 10;
//                image1 = new Image(String.valueOf(getClass().getResource("../../../images/invader1a.png")));
//                image2 = new Image(String.valueOf(getClass().getResource("../../../images/invader1b.png")));
                image1 = new Image("images/invader1a.png");
                image2 = new Image("images/invader1b.png");
                break;
            }
            case 2 :{
                this.value = 20;
//                image1 = new Image(String.valueOf(getClass().getResource("../../../images/invader2a.png")));
//                image2 = new Image(String.valueOf(getClass().getResource("../../../images/invader2b.png")));
                image1 = new Image("images/invader2a.png");
                image2 = new Image("images/invader2b.png");
                break;

            }
            case 3 :{
                this.value = 30;
//                image1 = new Image(String.valueOf(getClass().getResource("../../../images/invader3a.png")));
//                image2 = new Image(String.valueOf(getClass().getResource("../../../images/invader3b.png")));
                image1 = new Image("images/invader3a.png");
                image2 = new Image("images/invader3b.png");
                break;

            }
        }



        view = new ImageView(image1);
        view.setFitHeight(23);
//        view.setFitWidth(23);
        view.setPreserveRatio(true);
//        view.setImage(image1);

        view.setX(positionX);
        view.setY(positionY);

    }

    @Override
    public void move(int x, int y) {

        switch (InvaderGroup.getInstance().getMoveDirection()){
            case RIGHT: {
                positionX += x;
                view.setX(positionX);
                break;
            }

            case LEFT: {
                positionX -= x;
                view.setX(positionX);
                break;
            }
            case DOWN: {
                positionY += y;
                view.setY(positionY);
                break;
            }
        }


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


    @Override
    public ImageView getView(){
        return view;
    }

    @Override
    public int getPositionX(){
        return positionX;
    }

    @Override
    public int getPositionY(){
        return positionY;
    }
}

