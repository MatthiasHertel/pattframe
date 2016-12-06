package org.blueberry.spaceinvaders;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by KK on 28.11.2016.
 */
public class Ship implements IGunSprite {

    private int positionX;
    private int positionY;
    private ImageView view;
    private Bullet bullet;

    private IntegerProperty xMiddle = new SimpleIntegerProperty(0);
    private IntegerProperty yMiddle = new SimpleIntegerProperty(0);

    public Ship(){
        positionX = Border.getInstance().getXend() / 2;
        positionY = Border.getInstance().getYend() + 50;

//        view = new ImageView(new Image(String.valueOf(getClass().getResource("../../../images/ship.png"))));
        view = new ImageView(new Image("images/ship.png"));
        view.setX(positionX);
        view.setY(positionY);

        xMiddle.bind(view.xProperty().add(view.getImage().widthProperty().divide(2)));
        yMiddle.bind(view.yProperty().add(view.getImage().heightProperty().divide(2)));

    }

    public Bullet getBullet(){
        return bullet;
    }

    public void newBullet(){
        System.out.println("xmiddle/ymiddle: " + getXMiddle() + " " + getYMiddle());
        System.out.println("view x: " + view.getX() );
        System.out.println("view.getImage().widthProperty() x: " + view.getImage().getWidth() );
        bullet = new Bullet(getXMiddle(), getYMiddle());
    }

    public void removeBullet(){
        bullet = null;
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

        bullet.move(0, 0);
        System.out.println("Schuss");

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
