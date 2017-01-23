package org.blueberry.spaceinvaders.gameengine;

import javafx.scene.image.Image;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.util.ArrayList;
import java.util.List;

import static org.blueberry.spaceinvaders.gameengine.Direction.*;

/**
 * Invader-Gruppe
 */
public class InvaderGroup {

    private static InvaderGroup ourInstance = new InvaderGroup();

    /**
     * Gibt die Objektistanz der Invader-Gruppe zurück (Singleton)
     * @return Invader-Gruppe
     */
    public static InvaderGroup getInstance() {
        return ourInstance;
    }

    private List<List<Invader>> invaders;
    private List<Invader> invaderList;
    private int borderXSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xstart"));
    private int borderXEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.xend"));
//    private int borderYSstart = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.ystart"));
    private int borderYEend = Integer.parseInt(SpaceInvaders.getSettings("invadergroup.border.yend"));
    private int yGap = Integer.parseInt(SpaceInvaders.getSettings("invader.move.ypixel"));

    private Direction moveDirection = RIGHT;
    private Direction lastLeftRightDirection = moveDirection;

    /**
     * Konstruktor für die Invader-Gruppe
     */
    private InvaderGroup() {
    }

    /**
     * Erzeugt die Invader-Gruppe
     * @param positionX X-Position
     * @param positionY Y-Position
     */
    public void createGroup(int positionX, int positionY) {
        ourInstance = new InvaderGroup();

        Invader invaderDummy = new Invader(Game.getInstance().getImageAsset("invader1a"), Game.getInstance().getImageAsset("invader1b"), 0, 0, 0); //to get height and width
        int invaderWidth = invaderDummy.getWidth();
        int invaderHeight = invaderDummy.getHeight();
        int invaderXGap = Integer.parseInt(SpaceInvaders.getSettings("invader.gap.x"));
        int invaderYGap = Integer.parseInt(SpaceInvaders.getSettings("invader.gap.y"));
        int invaderPerLine = Integer.parseInt(SpaceInvaders.getSettings("invader.line.count"));


        int invaderLines = Integer.parseInt(SpaceInvaders.getSettings("invader.1.lines"));
        invaderLines += Integer.parseInt(SpaceInvaders.getSettings("invader.2.lines"));
        invaderLines += Integer.parseInt(SpaceInvaders.getSettings("invader.3.lines"));

        int borderHigh = (11 - Game.getInstance().levelProperty().get()) * Integer.parseInt(SpaceInvaders.getSettings("invader.move.ypixel"))  +  (invaderLines - 1) *  Integer.parseInt(SpaceInvaders.getSettings("invader.move.ypixel")) + invaderLines * invaderHeight  ;
        positionX += borderXSstart;
        positionY -= borderHigh;


        invaders = new ArrayList<>();
        for (int invaderTyp = 3; invaderTyp > 0; invaderTyp--) {
            addNewInvadersToGroup(invaders, invaderTyp, positionX, positionY, invaderXGap, invaderYGap, invaderWidth, invaderHeight, invaderPerLine);
        }

        invaderList = invadersToList();
    }

    /**
     * Fügt die Invader zu der Gruppe hinzu, in Abhängigkeit der Application-Properties
     * @param invaderGroup Invader-Gruppe
     * @param invaderType Invader-Typ (1-3)
     * @param positionX X-Position
     * @param positionY Y-Position
     * @param invaderXGap Invader-X-Abstand
     * @param invaderYGap Invader-Y-Abstand
     * @param invaderWidth Invader-Breite
     * @param invaderHeight Invader-Höhe
     * @param invaderPerLine Invader pro Linie
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
     * Erstell aus dem zweidimensionalen Invader-Array eine handhabbare Invader-Liste
     * @return Invader-Liste
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
     * Getter-Methode für die eindimensionale Invader-Liste
     * @return Invader-Liste
     */
    public List<Invader> getInvaderList() {
        return invaderList;
    }

    /**
     * Getter-Methode für die zweidimensionale Invader-Liste
     * @return Invader-Liste
     */
    public List<List<Invader>> getInvaders() {
        return invaders;
    }

    /**
     * Invader-Gruppen-Bewegung
     */
    public void move() {

        setNextGroupDirection();

        if (moveDirection != NONE) {
            for (Invader invader : invaderList) {
                invader.move(moveDirection);
            }
        } else {
            gameOver();
        }
    }

    /**
     * Game-Over - die Invader haben gewonnen
     */
    private void gameOver() {
        ourInstance = new InvaderGroup();
        Game.getInstance().setGameStatus(GameStatus.GAMEOVER);
    }

    /**
     * Testet die Bewegungsrichtung
     * @param direction die Bewegungsrichtung
     * @return boolean
     */
    private boolean testNextGroupMove(Direction direction) {

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
                Invader invader = invaderList.get(invaderList.size() - 1);
                int invaderBottomY = (int) invader.getY() + 1*invader.getHeight() ;
                if (invaderBottomY + yGap > borderYEend) {
                    return false;
                }
                return true;
            }
        }

        return false;
    }

    /**
     * Setzt die Bewegungsrichtungsänderung der Invader-Gruppe
     */
    private void setNextGroupDirection() {

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
//            setNextGroupDirection();
        }
    }

    /**
     * Entfernt einen Invader aus der Gruppe
     * @param invader
     */
    public void removeInvader(Invader invader) {
        invaderList.remove(invader);
        for (List<Invader> iList : invaders) {
            iList.remove(invader);
        }
    }

}
