package org.blueberry.spaceinvaders;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static org.blueberry.spaceinvaders.InvaderGroup.MoveDirection.*;

/**
 * Created by KK on 27.11.2016.
 */
public class InvaderGroup {
    private static InvaderGroup ourInstance = new InvaderGroup();

    public static InvaderGroup getInstance() {
        return ourInstance;
    }

    private InvaderGroup() {
        createGroup();
    }

    private List<Sprite> invaders = new ArrayList<>();
    private int stepDuration = 500;
    private MoveDirection moveDirection = MoveDirection.RIGHT;
    private MoveDirection lastLeftRightDirection;

    private int moveGapX = 10; //TODO vielleicht config ala Olli
    private int moveGapY = 10;


    private int invaderWidth = 30; //TODO anders ermitteln




    private void createGroup(){
        int positionY = 300;
        int invaderType = 1;

        for (int y = 0; y < 5; y++){

            if (y == 2 || y == 3) invaderType = 2;
            if (y == 4) invaderType = 3;

            for (int x = 0; x < 11; x++){
                invaders.add(new Invader(invaderType, x *50, positionY));
            }

            positionY -= 50;
        }
    }

    public void move(){
        MoveService moveService = new MoveService();
        moveService.setDelay(Duration.seconds(3));
        moveService.start();
    }

    public List<Sprite> getInvaders(){
        return invaders;
    }

    public void setStepDuration(int millis){
        stepDuration -= millis;
    }

    public void setMoveDirection(MoveDirection direction){
        this.moveDirection = direction;
    }

    public MoveDirection getMoveDirection(){
        return moveDirection;
    }


    private boolean testNextGroupMove(MoveDirection direction){

        switch (direction){
            case RIGHT: {
                for(Sprite invader: invaders){
//                    if(invader.getPositionX() + invader.getView().getFitWidth() > Border.getInstance().getXend()){
                    if(invader.getPositionX() + 2*invaderWidth > Border.getInstance().getXend()){
                        return false;
                    }
                }
                return true;
            }
            case LEFT: {
                for(Sprite invader: invaders){
                    if(invader.getPositionX() - invaderWidth < Border.getInstance().getXstart()){
                        return false;
                    }
                }
                return true;
            }
            case DOWN: {
                for(Sprite invader: invaders){
                    if(invader.getPositionY()  >  Border.getInstance().getYend()){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


    private void setNextGroupMoveDirection(){

        if (moveDirection == RIGHT || moveDirection == LEFT){
            if (testNextGroupMove(moveDirection)){
                lastLeftRightDirection = moveDirection;
            }
            else {
                if(testNextGroupMove(MoveDirection.DOWN))
                    moveDirection = DOWN;
                else
                    moveDirection = NONE;
            }
        }
        else if (moveDirection == DOWN){
            moveDirection = lastLeftRightDirection == LEFT ? RIGHT : LEFT;
            setNextGroupMoveDirection();
        }
    }


    public class MoveService extends ScheduledService {

        @Override
        public void start(){
            this.setPeriod(Duration.millis(stepDuration));

            setNextGroupMoveDirection();

            if (moveDirection != NONE){
                super.start();
            }
            else {
                this.cancel();
            }
        }

        @Override
        protected Task createTask() {
            return new Task() {

                @Override
                protected Void call() {
                    for(Sprite invader: invaders){
                        invader.move(moveGapX, moveGapY);
                    }
                    return null;
                }
            };
        }
    }





    public enum MoveDirection{
        RIGHT,
        LEFT,
        DOWN,
        NONE;
    }

}
