package org.blueberry.spaceinvaders.gameengine;

import javafx.scene.image.Image;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.util.ArrayList;
import java.util.List;

import static org.blueberry.spaceinvaders.gameengine.InvaderGroup.MoveDirection.*;

/**
 * InvaderGroup-Klasse
 */
public class InvaderGroup {

    private static InvaderGroup ourInstance = new InvaderGroup();

    /**
     * getInstance
     * @return
     */
    public static InvaderGroup getInstance() {
        return ourInstance;
    }

    private List<List<Invader>> invaders;
    private List<Invader> invaderList;
    private int borderXSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xstart"));
    private int borderXEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xend"));
    private int borderYSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.ystart"));
    private int borderYEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"));

    private MoveDirection moveDirection = MoveDirection.RIGHT;
    private MoveDirection lastLeftRightDirection;

    /**
     * InvaderGroup
     */
    private InvaderGroup() {
        //createGroup();
    }

    /**
     * createGroup
     * @param positionX
     * @param positionY
     */
    public void createGroup(int positionX, int positionY) {
        positionX += borderXSstart;
        positionY += borderYSstart;

        invaders = new ArrayList<>();

        Invader invaderDummy = new Invader(Game.getInstance().getImageAsset("invader1a"), Game.getInstance().getImageAsset("invader1b"), 0, 0, 0); //to get height and width
        int invaderWidth = invaderDummy.getWidth();
        int invaderHeight = invaderDummy.getHeight();
        int invaderXGap = Integer.parseInt(SpaceInvaders.getSettings("invader.gap.x"));
        int invaderYGap = Integer.parseInt(SpaceInvaders.getSettings("invader.gap.y"));
        int invaderPerLine = Integer.parseInt(SpaceInvaders.getSettings("invader.line.count"));

        for (int invaderTyp = 3; invaderTyp > 0; invaderTyp--) {
            addNewInvadersToGroup(invaders, invaderTyp, positionX, positionY, invaderXGap, invaderYGap, invaderWidth, invaderHeight, invaderPerLine);
        }

        invaderList = invadersToList();
    }

    /**
     * addNewInvadersToGroup
     * @param invaderGroup
     * @param invaderType
     * @param positionX
     * @param positionY
     * @param invaderXGap
     * @param invaderYGap
     * @param invaderWidth
     * @param invaderHeight
     * @param invaderPerLine
     */
    //adds Invader, depends from typ(1,2,3) and values from application.properties - called from createGroup
    private void addNewInvadersToGroup(List<List<Invader>> invaderGroup, int invaderType, int positionX, int positionY, int invaderXGap, int invaderYGap, int invaderWidth, int invaderHeight, int invaderPerLine) {
        int invaderValue;
        Image image1;
        Image image2;

        invaderValue = Integer.parseInt(SpaceInvaders.getSettings("invader." + Integer.toString(invaderType) + ".value"));
        image1 = Game.getInstance().getImageAsset("invader" + Integer.toString(invaderType) + "a");
        image2 = Game.getInstance().getImageAsset("invader" + Integer.toString(invaderType) + "b");
        for (int j = 0; j < Integer.parseInt(SpaceInvaders.getSettings("invader." + Integer.toString(invaderType) + ".lines")); j++) {
            List<Invader> invaderList = new ArrayList<>();
            int posY = positionY + (invaderHeight + invaderYGap) * invaderGroup.size();
            for (int i = 0; i < invaderPerLine; i++) {
                invaderList.add(new Invader(image1, image2, positionX + (invaderWidth + invaderXGap) * i, posY, invaderValue));
            }
            invaderGroup.add(invaderList);
        }
    }

    /**
     * invadersToList
     * @return
     */
    private List<Invader> invadersToList() {
        List<Invader> invaderReturnList = new ArrayList<>();
        for (List<Invader> invaderList : invaders) {
            for (Invader invader : invaderList) {
                invaderReturnList.add(invader);
            }
        }
        return invaderReturnList;
    }

    /**
     * getInvaderList
     * @return
     */
    public List<Invader> getInvaderList() {
        return invaderList;
    }

    /**
     * getInvaders
     * @return
     */
    public List<List<Invader>> getInvaders() {
        return invaders;
    }

    /**
     * move
     */
    public void move() {

        setNextGroupMoveDirection();

        if (moveDirection != NONE) {
            for (Invader invader : invaderList) {
                invader.move(moveDirection);
            }
        } else {
            gameOver();
        }
    }

    /**
     * gameOver
     */
    private void gameOver() {
        ourInstance = new InvaderGroup();
        Game.getInstance().setGameStatus(Game.GameStatus.GAMEOVER);
    }

    /**
     * testNextGroupMove
     * @param direction
     * @return
     */
    private boolean testNextGroupMove(MoveDirection direction) {

        switch (direction) {
            case RIGHT: {
                for (List<Invader> invaderLine : invaders) {
                    if (invaderLine.size() > 0) {
                        Invader rightInvader = invaderLine.get(invaderLine.size() - 1);
                        if (rightInvader.getX() + 2 * rightInvader.getWidth() > borderXEend) {
                            return false;
                        }
                    }
                }
                return true;
            }
            case LEFT: {
                for (List<Invader> invaderLine : invaders) {
                    if (invaderLine.size() > 0) {
                        Invader leftInvader = invaderLine.get(0);
                        if (leftInvader.getX() - leftInvader.getWidth() < borderXSstart) {
                            return false;
                        }
                    }
                }
                return true;
            }
            case DOWN: {
                if (invaderList.get(invaderList.size() - 1).getY() > borderYEend) {
                    return false;
                }
                return true;
            }
        }

        return false;
    }

    /**
     * setNextGroupMoveDirection
     */
    private void setNextGroupMoveDirection() {

        if (moveDirection == RIGHT || moveDirection == LEFT) {
            if (testNextGroupMove(moveDirection)) {
                lastLeftRightDirection = moveDirection;
            } else {
                if (testNextGroupMove(DOWN))
                    moveDirection = DOWN;
                else
                    moveDirection = NONE;
            }
        } else if (moveDirection == DOWN) {
            moveDirection = lastLeftRightDirection == LEFT ? RIGHT : LEFT;
            setNextGroupMoveDirection();
        }
    }

    /**
     * removeInvader
     * @param invader
     */
    public void removeInvader(Invader invader) {
        invaderList.remove(invader);
        for (List<Invader> iList : invaders) {
            iList.remove(invader);
        }
    }

    /**
     * MoveDirection
     */
    public enum MoveDirection {
        RIGHT,
        LEFT,
        DOWN,
        UP,
        NONE;
    }
}
