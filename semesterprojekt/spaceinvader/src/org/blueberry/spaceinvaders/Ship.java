package org.blueberry.spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static org.blueberry.spaceinvaders.InvaderGroup.MoveDirection;


/**
 * Created by KK on 12.12.2016.
 */
public class Ship extends ImageView implements IGunSprite{

    private int borderXSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xstart"));
    private int borderXEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xend"));
    private int borderYEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"));

    private Bullet bullet;
    private MoveDirection moveDirection = MoveDirection.NONE;

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



    @Override
    public void move(MoveDirection direction) {
        if (Game.getInstance().getGameStatus() != Game.GameStatus.PLAY || direction == MoveDirection.NONE){
            return;
        }
        int positionX = (int)getX();
        int x = 0;

        if (direction == MoveDirection.RIGHT){
            x = Integer.parseInt(SpaceInvaders.getSettings("ship.move.step"));
        }
        else  if (direction == MoveDirection.LEFT){
            x = Integer.parseInt(SpaceInvaders.getSettings("ship.move.step")) * -1;
        }

        if(positionX + x + getFitWidth() < borderXEend && positionX + x > 0){
            positionX += x;
            this.setX(positionX);
        }
    }

    public void shoot() {


        Game.getInstance().getAudioAsset("shipShoot").play();
        bullet.move(InvaderGroup.MoveDirection.UP);

        System.out.println("Schuss");

    }


    public void setMoveDirection(MoveDirection direction){
        this.moveDirection = direction;
    }

    public MoveDirection getMoveDirection(){
        return this.moveDirection;
    }

    @Override
    public void newBullet(){
        int bulletPositionX = (int) (getX() + Integer.parseInt(SpaceInvaders.getSettings("ship.width")) / 2);
        int bulletPositionY = (int)getY()-30;
        bullet = new Bullet(Game.getInstance().getImageAsset("shipBullet"), bulletPositionX, bulletPositionY);
    }

    @Override
    public void removeBullet(){
        bullet = null;
    }

    @Override
    public Bullet getBullet(){
        return bullet;
    }


}
