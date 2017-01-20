package org.blueberry.spaceinvaders.gameengine;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.util.List;

/**
 * Bunker-Teil
 */
public class ShelterPart extends ImageView implements ISprite {

    private int state;
    private int shelterType;
    private int width;
    private int height;
    private List<Image> imageList;

    /**
     * Konstruktor für ein Bunker-Teil
     * @param imageList Bunker-Stautusbild
     * @param positionX X-Position
     * @param positionY Y-Position
     * @param type Teilen-Typ (Ecke oder Quadrat)
     * @param rotation Rotation der Eck-Teile
     */
    ShelterPart(List<Image> imageList, int positionX, int positionY, int type, int rotation) {

        this.shelterType = type;
        this.state = type == 1 ? 3 : 2;

        this.imageList = imageList;

        this.setX(positionX);
        this.setY(positionY);
        this.setImage(this.imageList.get(0));
        this.setCache(true);
        this.setPreserveRatio(true);

        this.setFitWidth(Integer.parseInt(SpaceInvaders.getSettings("shelter.part.width")));
        this.width = (int) getFitWidth();
        this.height = (int) this.getLayoutBounds().getHeight();

        if (rotation != 0) {
            this.setRotate(rotation);
        }
    }

    /**
     * Getter-Methode für den Zerstörungsstatus eines Bunker-Teiles
     * @return
     */
    public int getState() {
        return this.state;
    }

    /**
     * Implementiert die Bewegung des eines Bunker-Teils (z.Z. nicht implementiert, für zuknftige Spielvariante vorgesehen)
     * @param direction die Bewegungsrichtung
     */
    @Override
    public void move(InvaderGroup.MoveDirection direction) {

    }

    /**
     * Visuelle Darstellung der Zerstörung von oben (Typ 1: a-b; Typ 2: a)
     */
    public void damagedFromTop() {
        if (shelterType == 1) {
            switch (state) {
                case 3:
                    this.setImage(imageList.get(3));
                    break;
                case 2:
                    this.setImage(getImage() == imageList.get(3) ? imageList.get(4) : imageList.get(5));
                    break;
            }
        } else if (shelterType == 2 && state == 2) {
            this.setImage(imageList.get(1));
        }
        state--;
    }

    /**
     * Visuelle Darstellung der Zerstörung von unten (Typ 1: e-f; Typ 2: c)
     */
    public void damagedFromBottom() {
        if (shelterType == 1) {
            switch (state) {
                case 3:
                    this.setImage(imageList.get(1));
                    break;
                case 2:
                    this.setImage(getImage() == imageList.get(1) ? imageList.get(2) : imageList.get(5));
                    break;
            }
        } else if (shelterType == 2 && state == 2) {
            this.setImage(imageList.get(2));
        }
        state--;
    }
}
