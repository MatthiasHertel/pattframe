package org.blueberry.spaceinvaders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KK on 09.12.2016.
 */
public class InvaderGroup {

    private static InvaderGroup ourInstance = new InvaderGroup();

    public static InvaderGroup getInstance() {
        return ourInstance;
    }


    private List<List<Invader>> invaders;

    private InvaderGroup() {
//        createGroup();

    }

    public void createGroup(int positionX, int positionY){
        invaders = new ArrayList<>();

        Invader invaderDummy = new Invader(Game.getInstance().getImageAsset("invader1a"), 0, 0, 0); //to get height and width
        int invaderWidth = invaderDummy.getWidth();
        int invaderHeight = invaderDummy.getHeight();
        int invaderXGap = Integer.parseInt(SpaceInvaders.getSettings("invader.gap.x"));
        int invaderYGap = Integer.parseInt(SpaceInvaders.getSettings("invader.gap.y"));
        int invaderPerLine = Integer.parseInt(SpaceInvaders.getSettings("invader.line.count"));
        int invaderValue;


        //Invader vom Typ 3 erzeugen und zu "invaders" hinzufügen
        invaderValue = Integer.parseInt(SpaceInvaders.getSettings("invader.3.value"));
        for (int j = 0; j < Integer.parseInt(SpaceInvaders.getSettings("invader.3.lines")); j++){
            List<Invader> invaderList = new ArrayList<>();
            positionY += (invaderHeight + invaderYGap);
            for (int i = 0; i < invaderPerLine; i++){
                invaderList.add(new Invader(Game.getInstance().getImageAsset("invader3a"), positionX + (invaderWidth + invaderXGap) * i, positionY, invaderValue));
            }
            invaders.add(invaderList);
        }


        //Invader vom Typ 2 erzeugen und zu "invaders" hinzufügen
        invaderValue = Integer.parseInt(SpaceInvaders.getSettings("invader.2.value"));
        for (int j = 0; j < Integer.parseInt(SpaceInvaders.getSettings("invader.2.lines")); j++){
            List<Invader> invaderList = new ArrayList<>();
            positionY += (invaderHeight + invaderYGap);
            for (int i = 0; i < invaderPerLine; i++){
                invaderList.add(new Invader(Game.getInstance().getImageAsset("invader2a"), positionX + (invaderWidth + invaderXGap) * i, positionY, invaderValue));
            }
            invaders.add(invaderList);
        }

        //Invader vom Typ 1 erzeugen und zu "invaders" hinzufügen
        invaderValue = Integer.parseInt(SpaceInvaders.getSettings("invader.1.value"));
        for (int j = 0; j < Integer.parseInt(SpaceInvaders.getSettings("invader.1.lines")); j++){
            List<Invader> invaderList = new ArrayList<>();
            positionY += (invaderHeight + invaderYGap) ;
            for (int i = 0; i < invaderPerLine; i++){
                invaderList.add(new Invader(Game.getInstance().getImageAsset("invader1a"), positionX + (invaderWidth + invaderXGap) * i, positionY, invaderValue));
            }
            invaders.add(invaderList);
        }






    }

    public List<List<Invader>> getInvaders(){
        return invaders;
    }

}
