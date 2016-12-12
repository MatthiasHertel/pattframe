package org.blueberry.spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by KK on 12.12.2016.
 */
public class Ship extends ImageView{

    private int borderXSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xstart"));
    private int borderXEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xend"));
    private int borderYEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"));

    private Bullet bullet;

    public Ship(Image image){

        this.setImage(image);
        this.setPreserveRatio(true);
        this.setCache(true);

        this.setFitWidth(Integer.parseInt(SpaceInvaders.getSettings("ship.width")));

        int positionX = borderXSstart + (borderXEend - borderXSstart) / 2 - (int)this.getFitWidth()/2;
        int positionY = borderYEend + 50;

        this.setX(positionX);
        this.setY(positionY);
    }



    public void move(int x, int y) {
        int positionX = (int)getX();

        if(positionX + x + getFitWidth() < borderXEend && positionX + x > 0){
            positionX += x;
            this.setX(positionX);
        }
    }

    public void shoot() {


        Game.getInstance().getAudioAsset("shipShoot").play();

//        bullet.move(0, 0);
        System.out.println("Schuss");

    }


    public Bullet getBullet(){
        return bullet;
    }

    public void newBullet(){
        int bulletPositionX = (int) (getX() + Integer.parseInt(SpaceInvaders.getSettings("ship.width")) / 2);
        int bulletPositionY = (int)getY()-30;
        bullet = new Bullet(Game.getInstance().getImageAsset("shipBullet"), bulletPositionX, bulletPositionY);
    }

    public void removeBullet(){
        bullet = null;
    }


}
