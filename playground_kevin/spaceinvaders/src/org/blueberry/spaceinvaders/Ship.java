package org.blueberry.spaceinvaders;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

/**
 * Created by KK on 28.11.2016.
 */
public class Ship implements IGunSprite {

    private int positionX;
    private int positionY;
    private ImageView view;
    private Bullet bullet;
    private AudioClip shootSound;


    public Ship(){
        positionX = Border.getInstance().getXend() / 2;
        positionY = Border.getInstance().getYend() + 50;

        view = new ImageView(new Image("images/ship.png"));
        view.setX(positionX);
        view.setY(positionY);

//        AudioClip shootSound = new AudioClip("audio/shoot.wav");
        shootSound = new AudioClip(getClass().getResource("/audio/shoot.wav").toExternalForm());

    }

    public Bullet getBullet(){
        return bullet;
    }

    public void newBullet(){
        int xMiddle = (int) (view.getX() + view.getImage().getWidth() / 2);
        int yMiddle = (int) (view.getY() + view.getImage().getHeight() / 2);
        bullet = new Bullet(xMiddle, yMiddle);
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

        shootSound.play();
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
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, view.getImage().getWidth(), view.getImage().getHeight());
    }


}
