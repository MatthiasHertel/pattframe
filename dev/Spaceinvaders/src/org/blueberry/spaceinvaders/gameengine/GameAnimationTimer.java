package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.AnimationTimer;
import org.blueberry.spaceinvaders.SpaceInvaders;
import org.blueberry.spaceinvaders.controller.AssetController;

import java.util.Random;

import static org.blueberry.spaceinvaders.gameengine.Direction.LEFT;
import static org.blueberry.spaceinvaders.gameengine.Direction.RIGHT;
import static org.blueberry.spaceinvaders.gameengine.GameStatus.PLAY;
import static org.blueberry.spaceinvaders.gameengine.GameStatus.WON;

/**
 * GameAnimationTimer
 */
public class GameAnimationTimer extends AnimationTimer {

    private int invaderSpeed1 = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.1"));
    private int invaderSpeed2 = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.2"));
    private int invaderSpeed3 = Integer.parseInt(SpaceInvaders.getSettings("invader.move.speed.3"));
    private int invaderMoveDuration = invaderSpeed1;

    private long invaderShootDelayMin = Long.parseLong(SpaceInvaders.getSettings("invader.shoots.delay.random.min"));
    private long invaderShootDelayMax = Long.parseLong(SpaceInvaders.getSettings("invader.shoots.delay.random.max"));

    private long mysteryShipDelayMin = Long.parseLong(SpaceInvaders.getSettings("mysteryship.delay.random.min"));
    private long mysteryShipDelayMax = Long.parseLong(SpaceInvaders.getSettings("mysteryship.delay.random.max"));

    private AssetController assetController = AssetController.getInstance();

    private Game game = Game.getInstance();
    private Ship ship = game.getShip();
    private InvaderGroup invaderGroup = game.getInvaderGroup();
    private Player player = game.getPlayer();

    private int invaderMaxCount = invaderGroup.getInvaderList().size();

    private MysteryShip mysteryShip;

    private long invaderMoveLastTime = System.nanoTime();
    private long invaderShootLastTime = System.nanoTime();
    private long mysteryShipLastTime = System.nanoTime();
    private Random random = new Random();

    /**
     * handle
     * @param now
     */
    @Override
    public void handle(long now) {

        if (game.getGameStatus() == PLAY) {

            // ship bewegen
            ship.move(ship.getMoveDirection());

            //InvaderGroup bewegen (Zeitinterval application.properties: invader.move.speed.1)
            if (now > invaderMoveLastTime + invaderMoveDuration * 1000000) {
                invaderMoveLastTime = now;
                invaderGroup.move();
            }


            //Invaderschuss absetzen
            if (now > invaderShootLastTime + ((long) (random.nextDouble() * invaderShootDelayMax) + invaderShootDelayMin) * 1000000L) {
                invaderShootLastTime = now;
                game.tryInvaderShoot();
            }

            // hat die schiffskanone einen invader getroffen
            if (ship.getBullet() != null) {
                Invader collisionedInvader = game.detectCollisionedInvader(ship.getBullet(), invaderGroup.getInvaderList());
                if (collisionedInvader != null) {
//                    System.out.println("Invader getroffen");
                    assetController.getAudioAsset("invaderKilled").play();
                    game.removeBullet(ship);

                    player.setScore(player.getScore() + collisionedInvader.getValue());
                    game.removeInvader(collisionedInvader);

                    //Geschwindigkeit in Abhängigkeit von der Invaderanzahl setzen
                    if(invaderGroup.getInvaderList().size() < invaderMaxCount / 3 ){
                        invaderMoveDuration = invaderSpeed3;
                    }
                    else if(invaderGroup.getInvaderList().size() < invaderMaxCount * 2/3 ){
                        invaderMoveDuration = invaderSpeed2;
                    }
                }
                if (invaderGroup.getInvaderList().size() == 0) {
                    game.setGameStatus(WON);
                }
            }

            // hat die schiffskanone das MysteryShip getroffen
            if (ship.getBullet() != null && mysteryShip != null) {
                if (ship.getBullet().intersects(mysteryShip.getLayoutBounds())) {
                    assetController.getAudioAsset("mysteryKilled").play();
                    player.setScore(player.getScore() + mysteryShip.getValue());
                    game.removeBullet(ship);
                    game.removeMysteryShip();
                }
            }

            // hat die schiffskanone den Bunker getroffen
            bunkerWurdeGetroffen:
            if (ship.getBullet() != null) {
                for (Shelter shelter : game.getShelterList()) {
                    if(ship.getBullet().intersects(shelter.getLayoutBounds().getMinX(), shelter.getLayoutBounds().getMinY(), shelter.getLayoutBounds().getWidth(), shelter.getLayoutBounds().getHeight())) {
                        for (ShelterPart shelterPart : shelter.getShelterParts()) {
                            if (ship.getBullet().intersects(shelterPart.getLayoutBounds())) {
                                game.removeBullet(ship);
                                shelterPart.damagedFromBottom();
                                if (shelterPart.getState() == 0) {
                                    game.removeSprite(shelterPart);
                                }
                                break bunkerWurdeGetroffen;
                            }
                        }
                    }
                }
            }

            // hat ein invader den Bunker getroffen

            bunkerWurdeGetroffen2:
            for (Invader invader : invaderGroup.getInvaderList()) {
                if (invader.getBullet() != null) {
                    for (Shelter shelter : game.getShelterList()) {
                        if(invader.getBullet().intersects(shelter.getLayoutBounds().getMinX(), shelter.getLayoutBounds().getMinY(), shelter.getLayoutBounds().getWidth(), shelter.getLayoutBounds().getHeight())) {

                            for (ShelterPart shelterPart : shelter.getShelterParts()) {
                                if (invader.getBullet().intersects(shelterPart.getLayoutBounds())) {
                                    game.removeBullet(invader);
                                    shelterPart.damagedFromTop();
                                    if (shelterPart.getState() == 0) {
                                        game.removeSprite(shelterPart);
                                    }
                                    break bunkerWurdeGetroffen2;
                                }

                            }
                        }
                    }
                }
            }


            // hat ein Invader das ship getroffen
            for (Invader invader : invaderGroup.getInvaderList()) {
                if (invader.getBullet() != null) {
                    if (ship.intersects(invader.getBullet().getLayoutBounds())) {
//                        System.out.println("Ship getroffen");
                        assetController.getAudioAsset("shipExplosion").play();
                        player.setlives(player.getlives() - 1);
                        game.removeBullet(invader);
                        break;
                    }
                }
            }


            // MysteryShip losschicken
            if (now > mysteryShipLastTime + ((long) (random.nextDouble() * mysteryShipDelayMax) + mysteryShipDelayMin) * 1000000L) {
                if (mysteryShip != null) {
                    return;
                }
                mysteryShipLastTime = now;

                Direction randomDirection = random.nextInt(2) == 0 ? RIGHT : LEFT;
                mysteryShip = new MysteryShip(assetController.getImageAsset("mysteryShip"), randomDirection);
                mysteryShip.getTimeLine().setOnFinished(event -> {
                    mysteryShip = null;
                    game.removeMysteryShip();
                });
//                display.getChildren().add(mysteryShip);
//                game.addMysteryShipToPane(mysteryShip);
                game.setMysteryShip(mysteryShip);
                mysteryShip.move(randomDirection);
            }
        }
    }
}