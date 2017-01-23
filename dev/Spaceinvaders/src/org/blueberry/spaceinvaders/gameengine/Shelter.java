package org.blueberry.spaceinvaders.gameengine;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.controller.AssetController;

import java.util.ArrayList;
import java.util.List;

/**
 * Bunker / Schutz für den Spieler
 */
public class Shelter {

    private AssetController assetController = AssetController.getInstance();
    private int positionX;
    private int positionY;
    private int shelterPartWidth = Integer.parseInt(SpaceInvaders.getSettings("shelter.part.width"));
    private List<ShelterPart> shelterParts = new ArrayList<>();

    Shelter(int x, int y) {
        this.positionX = x;
        this.positionY = y;

        constructShelter();
    }

    /**
     * Erzeugt einen Bunker aus vielen Teilen
     */
    public void constructShelter() {

        int positionX = this.positionX;
        int positionY = this.positionY;

        // first line
        addNewShelterPart(positionX + 0 * shelterPartWidth, positionY, 2, 0);
        addNewShelterPart(positionX + 1 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 2 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 3 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 4 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 5 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 6 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 7 * shelterPartWidth, positionY, 2, 90);

        // second line
        positionY += shelterPartWidth;
        addNewShelterPart(positionX + -1 * shelterPartWidth, positionY, 2, 0);
        addNewShelterPart(positionX + 0 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 1 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 2 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 3 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 4 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 5 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 6 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 7 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 8 * shelterPartWidth, positionY, 2, 90);

        // third line
        positionY += shelterPartWidth;
        addNewShelterPart(positionX + -1 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 0 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 1 * shelterPartWidth, positionY, 2, 180);

        addNewShelterPart(positionX + 6 * shelterPartWidth, positionY, 2, 270);
        addNewShelterPart(positionX + 7 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 8 * shelterPartWidth, positionY, 1, 0);

        // fourth line
        positionY += shelterPartWidth;
        addNewShelterPart(positionX + -1 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 0 * shelterPartWidth, positionY, 1, 0);

        addNewShelterPart(positionX + 7 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 8 * shelterPartWidth, positionY, 1, 0);

        // fifth line
        positionY += shelterPartWidth;
        addNewShelterPart(positionX + -1 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 0 * shelterPartWidth, positionY, 1, 0);

        addNewShelterPart(positionX + 7 * shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 8 * shelterPartWidth, positionY, 1, 0);
    }

    /**
     * Fügt dem Bunker ein Teil hin zu
     * @param x X-Position
     * @param y Y-Position
     * @param typ Teilen-Typ (Ecke oder Quadrat)
     * @param rotation Rotation
     */
    private void addNewShelterPart(int x, int y, int typ, int rotation) {

        List<Image> shelterPartsImageList = new ArrayList<>();

        if (typ == 1) {
            shelterPartsImageList.add(assetController.getImageAsset("shelter1a"));
            shelterPartsImageList.add(assetController.getImageAsset("shelter1b"));
            shelterPartsImageList.add(assetController.getImageAsset("shelter1c"));
            shelterPartsImageList.add(assetController.getImageAsset("shelter1d"));
            shelterPartsImageList.add(assetController.getImageAsset("shelter1e"));
            shelterPartsImageList.add(assetController.getImageAsset("shelter1f"));
        } else if (typ == 2) {
            shelterPartsImageList.add(assetController.getImageAsset("shelter2a"));
            shelterPartsImageList.add(assetController.getImageAsset("shelter2b"));
            shelterPartsImageList.add(assetController.getImageAsset("shelter2c"));
        }
        shelterParts.add(new ShelterPart(shelterPartsImageList, x, y, typ, rotation));
    }

    /**
     * Gibt die Ausmaße des Bunker zurück
     * @return Rechteck
     */
    public Rectangle2D getLayoutBounds(){
        int width = 10 * shelterPartWidth;
        int height = 5 * shelterPartWidth;
        return new Rectangle2D(positionX - shelterPartWidth, positionY, width, height);
    }

    /**
     * Gibt die Bunker-Teile zurück
     * @return Liste der Bunker-Teile
     */
    public List<ShelterPart> getShelterParts() {
        return shelterParts;
    }
}
