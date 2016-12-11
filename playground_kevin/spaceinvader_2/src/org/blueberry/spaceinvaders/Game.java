package org.blueberry.spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KK on 09.12.2016.
 */
public class Game {

    private static Game ourInstance = new Game();

    public static Game getInstance() {
        return ourInstance;
    }

    private Image invader1a_img;
    private Image invader1b_img;
    private Image invader2a_img;
    private Image invader2b_img;
    private Image invader3a_img;
    private Image invader3b_img;

    private Image ship_bullet_img;
    private Image ship_img;

    private AudioClip shipShootSound;
    private AudioClip shipExplosionSound;
    private AudioClip invaderKilledSound;

    private Map<String, Image> imageAssets = new HashMap<String, Image>();
    private InvaderGroup invaderGroup;



    public void loadAssets(String theme){

        imageAssets.put("invader1a", new Image(theme + "/graphics/invader1a.png"));
        imageAssets.put("invader1b", new Image(theme + "/graphics/invader1b.png"));
        imageAssets.put("invader2a", new Image(theme + "/graphics/invader2a.png"));
        imageAssets.put("invader2b", new Image(theme + "/graphics/invader2b.png"));
        imageAssets.put("invader3a", new Image(theme + "/graphics/invader3a.png"));
        imageAssets.put("invader3b", new Image(theme + "/graphics/invader3b.png"));

        imageAssets.put("ship_bullet", new Image(theme + "/graphics/ship_bullet.png"));
        imageAssets.put("ship", new Image(theme + "/graphics/invader3b.png"));



        shipShootSound = new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_shoot.wav").toExternalForm());
        shipExplosionSound = new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_explosion.wav").toExternalForm());
        invaderKilledSound = new AudioClip(getClass().getResource("/" + theme + "/sounds/invader_killed.wav").toExternalForm());

    }


    public Image getImageAsset(String key){
        return imageAssets.get(key);
    }

    private Game(){
        loadAssets(SpaceInvaders.getSettings("game.standardtheme"));

//        Invader invader = new Invader(invader1a_img);

    }

    public void createInvaderGroup(){
        invaderGroup = InvaderGroup.getInstance();
        invaderGroup.createGroup(Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.x")), Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.y")));
    }


    public InvaderGroup getInvaderGroup(){
        return invaderGroup;
    }

    public void setTheme(String theme){
        loadAssets(theme);
        invaderGroup.createGroup(Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.x")), Integer.parseInt(SpaceInvaders.getSettings("invadergroup.position.y")));
    }
}
