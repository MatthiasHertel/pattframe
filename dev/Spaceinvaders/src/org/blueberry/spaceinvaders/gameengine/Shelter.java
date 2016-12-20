package org.blueberry.spaceinvaders.gameengine;

import javafx.scene.image.Image;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KK on 20.12.2016.
 */
public class Shelter {

    private int positionX;
    private int positionY;
    private int shelterPartWidth = Integer.parseInt(SpaceInvaders.getSettings("shelter.part.width"));
    private List<ShelterPart> shelterParts = new ArrayList<>();

    Shelter(int x, int y){
        this.positionX = x;
        this.positionY = y;

        constructShelter();
    }

    public void constructShelter(){

        // first line
        addNewShelterPart(positionX + 0*shelterPartWidth, positionY, 2, 0);
        addNewShelterPart(positionX + 1*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 2*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 3*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 4*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 5*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 6*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 7*shelterPartWidth, positionY, 2, 90);

        // second line
        positionY += shelterPartWidth;
        addNewShelterPart(positionX + -1*shelterPartWidth, positionY, 2, 0);
        addNewShelterPart(positionX + 0*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 1*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 2*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 3*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 4*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 5*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 6*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 7*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 8*shelterPartWidth, positionY, 2, 90);

        // third line
        positionY += shelterPartWidth;
        addNewShelterPart(positionX + -1*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 0*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 1*shelterPartWidth, positionY, 2, 180);

        addNewShelterPart(positionX + 6*shelterPartWidth, positionY, 2, 270);
        addNewShelterPart(positionX + 7*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 8*shelterPartWidth, positionY, 1, 0);

        // fourth line
        positionY += shelterPartWidth;
        addNewShelterPart(positionX + -1*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 0*shelterPartWidth, positionY, 1, 0);

        addNewShelterPart(positionX + 7*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 8*shelterPartWidth, positionY, 1, 0);

        // fifth line
        positionY += shelterPartWidth;
        addNewShelterPart(positionX + -1*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 0*shelterPartWidth, positionY, 1, 0);

        addNewShelterPart(positionX + 7*shelterPartWidth, positionY, 1, 0);
        addNewShelterPart(positionX + 8*shelterPartWidth, positionY, 1, 0);
    }


    public void addNewShelterPart(int x, int y, int typ, int rotation){

        Game game = Game.getInstance();
        List<Image> shelterPartsImageList = new ArrayList<>();

        if (typ == 1) {
            shelterPartsImageList.add(game.getImageAsset("shelter1a"));
            shelterPartsImageList.add(game.getImageAsset("shelter1b"));
            shelterPartsImageList.add(game.getImageAsset("shelter1c"));
            shelterPartsImageList.add(game.getImageAsset("shelter1d"));
            shelterPartsImageList.add(game.getImageAsset("shelter1e"));
            shelterPartsImageList.add(game.getImageAsset("shelter1f"));
        }
        else if (typ == 2) {
            shelterPartsImageList.add(game.getImageAsset("shelter2a"));
            shelterPartsImageList.add(game.getImageAsset("shelter2b"));
            shelterPartsImageList.add(game.getImageAsset("shelter2c"));
        }
        shelterParts.add(new ShelterPart(shelterPartsImageList, x, y, typ, rotation));
    }



    public List<ShelterPart> getShelterParts(){
        return shelterParts;
    }
}
