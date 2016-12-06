package org.blueberry.spaceinvaders;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;

/**
 * Created by KK on 05.12.2016.
 */


public class Bullet implements ISprite {

    private ImageView view;
    private Timeline timeLine;

    private IntegerProperty xMiddle = new SimpleIntegerProperty(0);
    private IntegerProperty yMiddle = new SimpleIntegerProperty(0);



    public Bullet(int positionX, int positionY){

        view = new ImageView(new Image("images/bullet.png"));
        view.setX(positionX);
        view.setY(positionY);

        KeyValue keyValue = new KeyValue(view.yProperty(), Border.getInstance().getYstart());
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), keyValue);
        timeLine = new Timeline();
        timeLine.getKeyFrames().add(keyFrame);

        xMiddle.bind(view.xProperty().add(view.getImage().widthProperty().divide(2)));
        yMiddle.bind(view.yProperty().add(view.getImage().heightProperty().divide(2)));
    }


    private int getDistance(int x1, int y1, int x2, int y2){
        if(x1 > x2){
            int x = x2;
            x2 = x1;
            x1 = x;
        }
        if(y1 > y2){
            int y = y2;
            y2 = y1;
            y1 = y;
        }
        return (int) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
    }

    private boolean bulletInInvader(IGunSprite invader){
//        if()
        return false;
    }

    public IGunSprite collisionedInvader(List<IGunSprite> invaders){

        for (IGunSprite invader: invaders){
//            System.out.println("Distance" + getDistance(invader.getPositionX(), invader.getPositionY(), (int)view.getX(), (int)view.getY()));
//            System.out.println("Distance" + getDistance(invader.getXMiddle(), invader.getYMiddle(), (int)view.getX(), (int)view.getY()));
//            if (getDistance(invader.getPositionX(), invader.getPositionY(), (int)view.getX(), (int)view.getY()) < 10){
            if (getDistance(invader.getXMiddle(), invader.getYMiddle(), getXMiddle(), getYMiddle()) < 20){
                timeLine.stop();
                return invader;
            }
        }
        return null;
    }

    @Override
    public void move(int x, int y) {

        timeLine.play();

    }

    public Timeline getTimeLine(){
        return timeLine;
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

    @Override
    public int getXMiddle() {
        return xMiddle.get();
    }

    @Override
    public void setXMiddle(int value) {
        xMiddle.set(value);
    }



    @Override
    public IntegerProperty xMiddle() {
        return xMiddle;
    }

    @Override
    public int getYMiddle() {
        return yMiddle.get();
    }

    @Override
    public void setYMiddle(int value) {
        yMiddle.set(value);
    }



    @Override
    public IntegerProperty yMiddle() {
        return yMiddle;
    }


}
