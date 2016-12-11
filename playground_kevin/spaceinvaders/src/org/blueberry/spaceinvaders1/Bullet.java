package org.blueberry.spaceinvaders1;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
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

    public Bullet(int positionX, int positionY){

        view = new ImageView(new Image("images/bullet.png"));
        view.setX(positionX);
        view.setY(positionY);

        KeyValue keyValue = new KeyValue(view.yProperty(), Border.getInstance().getYstart());
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), keyValue);
        timeLine = new Timeline();
        timeLine.getKeyFrames().add(keyFrame);

    }




    private boolean intersects(IGunSprite invader){
        return invader.getBoundary().intersects(getBoundary());
    }

    public IGunSprite collisionedInvader(List<IGunSprite> invaders){

        for (IGunSprite invader: invaders){
//            System.out.println("Distance" + getDistance(invader.getPositionX(), invader.getPositionY(), (int)view.getX(), (int)view.getY()));
//            System.out.println("Distance" + getDistance(invader.getXMiddle(), invader.getYMiddle(), (int)view.getX(), (int)view.getY()));

              if(intersects(invader)){
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
    public Rectangle2D getBoundary() {
        return new Rectangle2D(view.getX(), view.getY(), view.getImage().getWidth(), view.getImage().getHeight());
    }
}
