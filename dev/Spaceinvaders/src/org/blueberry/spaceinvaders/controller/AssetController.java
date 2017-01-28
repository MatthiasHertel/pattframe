package org.blueberry.spaceinvaders.controller;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import org.blueberry.spaceinvaders.SpaceInvaders;

import java.util.HashMap;
import java.util.Map;

/**
 * AssetController lädt Medien für das Spiel und stellt sie zur Verfügung
 */
public class AssetController {
    private static AssetController ourInstance = new AssetController();

    private Map<String, Image> imageAssets;
    private Map<String, AudioClip> audioAssets;

    public static AssetController getInstance() {
        return ourInstance;
    }

    /**
     * Konstruktor (privat), für Singleton
     */
    private AssetController() {
        loadAssets(SpaceInvaders.getSettings("game.standardtheme"));
    }


    /**
     * lädt die Medien für das Spiel
     * @param theme (dient dem einfachen Auswechseln des Themes ueber einen prefix path)
     */
    public void loadAssets(String theme) {

        imageAssets = new HashMap<>();
        audioAssets = new HashMap<>();

        imageAssets.put("invader1a", new Image(theme + "/graphics/invader1a.png"));
        imageAssets.put("invader1b", new Image(theme + "/graphics/invader1b.png"));
        imageAssets.put("invader2a", new Image(theme + "/graphics/invader2a.png"));
        imageAssets.put("invader2b", new Image(theme + "/graphics/invader2b.png"));
        imageAssets.put("invader3a", new Image(theme + "/graphics/invader3a.png"));
        imageAssets.put("invader3b", new Image(theme + "/graphics/invader3b.png"));

        imageAssets.put("shelter1a", new Image(theme + "/graphics/shelter1a.png"));
        imageAssets.put("shelter1b", new Image(theme + "/graphics/shelter1b.png"));
        imageAssets.put("shelter1c", new Image(theme + "/graphics/shelter1c.png"));
        imageAssets.put("shelter1d", new Image(theme + "/graphics/shelter1d.png"));
        imageAssets.put("shelter1e", new Image(theme + "/graphics/shelter1e.png"));
        imageAssets.put("shelter1f", new Image(theme + "/graphics/shelter1f.png"));
        imageAssets.put("shelter2a", new Image(theme + "/graphics/shelter2a.png"));
        imageAssets.put("shelter2b", new Image(theme + "/graphics/shelter2b.png"));
        imageAssets.put("shelter2c", new Image(theme + "/graphics/shelter2c.png"));

        imageAssets.put("invaderBullet", new Image(theme + "/graphics/invader_bullet.png"));

        imageAssets.put("shipBullet", new Image(theme + "/graphics/ship_bullet.png"));
        imageAssets.put("ship", new Image(theme + "/graphics/ship.png"));

        imageAssets.put("mysteryShip", new Image(theme + "/graphics/mystery_ship.png"));

        audioAssets.put("shipShoot", new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_shoot.wav").toExternalForm()));
        audioAssets.put("shipExplosion", new AudioClip(getClass().getResource("/" + theme + "/sounds/ship_explosion.wav").toExternalForm()));
        audioAssets.put("invaderKilled", new AudioClip(getClass().getResource("/" + theme + "/sounds/invader_killed.wav").toExternalForm()));
        audioAssets.put("mystery", new AudioClip(getClass().getResource("/" + theme + "/sounds/mystery.wav").toExternalForm()));
        audioAssets.put("mysteryKilled", new AudioClip(getClass().getResource("/" + theme + "/sounds/mystery_killed.wav").toExternalForm()));

    }

    /**
     * Getter-Methode für Imageasset aus der Map
     * @param key
     * @return Image
     */
    public Image getImageAsset(String key) {
        return imageAssets.get(key);
    }

    /**
     * Getter-Methode für Audioasset aus der Map
     * @param key
     * @return AudioClip
     */
    public AudioClip getAudioAsset(String key) {
        return audioAssets.get(key);
    }

    //    /**
//     * TODO: theme handle method
//     * @param theme
//     */
//    public void setTheme(String theme) {
//        loadAssets(theme);
//    }
}
