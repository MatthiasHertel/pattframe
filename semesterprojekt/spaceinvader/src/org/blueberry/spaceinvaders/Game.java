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



    private Map<String, Image> imageAssets = new HashMap<String, Image>();
    private Map<String, AudioClip> audioAssets = new HashMap<String, AudioClip>();
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

        imageAssets.put("bunker1a", new Image(theme + "/graphics/bunker/8x8/1a.png"));
        imageAssets.put("bunker2a", new Image(theme + "/graphics/bunker/8x8/2a.png"));


        audioAssets.put("shipShoot", new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_shoot.wav").toExternalForm()));
        audioAssets.put("shipExplosion", new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_explosion.wav").toExternalForm()));
        audioAssets.put("invaderKilled", new AudioClip(getClass().getResource("/" + theme + "/sounds/invader_killed.wav").toExternalForm()));


    }


    public Image getImageAsset(String key){
        return imageAssets.get(key);
    }

    public AudioClip getAudioAsset(String key){
        return audioAssets.get(key);
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

    public void play(){
        invaderGroup.move();
    }
}
