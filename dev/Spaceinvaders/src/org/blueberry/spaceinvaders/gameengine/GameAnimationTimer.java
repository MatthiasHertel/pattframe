package org.blueberry.spaceinvaders.gameengine;

import javafx.animation.AnimationTimer;
import org.blueberry.spaceinvaders.SpaceInvaders;
import java.util.Random;

import static org.blueberry.spaceinvaders.gameengine.GameStatus.*;

/**
 * GameAnimationTimer
 */
public class GameAnimationTimer extends AnimationTimer {

    private long invaderShootDelayMin = Long.parseLong(SpaceInvaders.getSettings("invader.shoots.delay.random.min"));
    private long invaderShootDelayMax = Long.parseLong(SpaceInvaders.getSettings("invader.shoots.delay.random.max"));

    private long mysteryShipDelayMin = Long.parseLong(SpaceInvaders.getSettings("mysteryship.delay.random.min"));
    private long mysteryShipDelayMax = Long.parseLong(SpaceInvaders.getSettings("mysteryship.delay.random.max"));

    private Game game = Game.getInstance();
    private Ship ship = game.getShip();
    private InvaderGroup invaderGroup = game.getInvaderGroup();

    private long invaderMoveLastTime = System.nanoTime();
    private long invaderShootLastTime = System.nanoTime();
    private long mysteryShipLastTime = System.nanoTime();

    private Random random = new Random();

    /**
     * handle - wird ca. 60 mal pro Sekunde aufgerufen, behandelt die Spielereignisse
     * @param now
     */
    @Override
    public void handle(long now) {

        if (game.getGameStatus() == PLAY) {

            ship.move(ship.getMoveDirection());

            game.checkAndHandleShipBulletMysteryShipCollision();
            game.checkAndHandleShipBulletInvaderCollision();
            game.checkAndHandleShipBulletShelterCollision();
            game.checkAndHandleInvaderBulletShelterCollision();
            game.checkAndHandleInvaderBulletShipCollision();

            //InvaderGroup bewegen (zeitabhängig)
            if (now > invaderMoveLastTime + game.getInvaderMoveDuration() * 1000000) {
                invaderMoveLastTime = now;
                invaderGroup.move();
            }

            //Invaderschuss absetzen (zeitabhängig)
            if (now > invaderShootLastTime + ((long) (random.nextDouble() * invaderShootDelayMax) + invaderShootDelayMin) * 1000000L) {
                invaderShootLastTime = now;
                game.tryInvaderShoot();
            }

            // MysteryShip losschicken (zeitabhängig)
            if (now > mysteryShipLastTime + ((long) (random.nextDouble() * mysteryShipDelayMax) + mysteryShipDelayMin) * 1000000L) {
                mysteryShipLastTime = now;
                game.tryCreateMysteryShip();
            }
        }
    }
}