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
public class Shelter implements ISprite{

    private AssetController assetController = AssetController.getInstance();
    private int positionX;
    private int positionY;
    private int shelterPartWidth = Integer.parseInt(SpaceInvaders.getSettings("shelter.part.width"));
    private int shelterPartHeight = Integer.parseInt(SpaceInvaders.getSettings("shelter.part.height"));
    private List<ShelterPart> shelterParts = new ArrayList<>();

    Shelter(int x, int y) {
        this.positionX = x;
        this.positionY = y;

        constructShelter();
    }

    /**
     * Erzeugt einen Bunker aus vielen Teilen
     */
    private void constructShelter() {

        int x = this.positionX;
        int y = this.positionY;

        // first line
        addNewShelterPart(x                         , y, 2, 0);
        addNewShelterPart(x +       shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   2 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   3 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   4 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   5 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   6 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   7 * shelterPartWidth, y, 2, 90);

        // second line
        y += shelterPartHeight;
        addNewShelterPart(x +  -1 * shelterPartWidth, y, 2, 0);
        addNewShelterPart(x                         , y, 1, 0);
        addNewShelterPart(x +       shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   2 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   3 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   4 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   5 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   6 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   7 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   8 * shelterPartWidth, y, 2, 90);

        // third line
        y += shelterPartHeight;
        addNewShelterPart(x +  -1 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x                         , y, 1, 0);
        addNewShelterPart(x +       shelterPartWidth, y, 2, 180);

        addNewShelterPart(x +   6 * shelterPartWidth, y, 2, 270);
        addNewShelterPart(x +   7 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   8 * shelterPartWidth, y, 1, 0);

        // fourth line
        y += shelterPartHeight;
        addNewShelterPart(x +  -1 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x                         , y, 1, 0);

        addNewShelterPart(x +   7 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   8 * shelterPartWidth, y, 1, 0);

        // fifth line
        y += shelterPartHeight;
        addNewShelterPart(x +  -1 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x                         , y, 1, 0);

        addNewShelterPart(x +   7 * shelterPartWidth, y, 1, 0);
        addNewShelterPart(x +   8 * shelterPartWidth, y, 1, 0);
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
        int height = 5 * shelterPartHeight;
        return new Rectangle2D(positionX - shelterPartWidth, positionY, width, height);
    }

    /**
     * Gibt die Bunker-Teile zurück
     * @return Liste der Bunker-Teile
     */
    List<ShelterPart> getShelterParts() {
        return shelterParts;
    }

    /**
     * Implementiert die Bewegung des eines Bunker-Teils (z.Z. nicht implementiert, für zuknftige Spielvariante vorgesehen)
     * @param direction die Bewegungsrichtung
     */
    @Override
    public void move(Direction direction) {

    }
}
