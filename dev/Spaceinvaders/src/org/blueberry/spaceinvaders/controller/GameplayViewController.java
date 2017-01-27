/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.blueberry.spaceinvaders.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.blueberry.spaceinvaders.gameengine.Direction;
import org.blueberry.spaceinvaders.gameengine.Game;
import org.blueberry.spaceinvaders.SpaceInvaders;

import static org.blueberry.spaceinvaders.gameengine.Direction.*;
import static org.blueberry.spaceinvaders.gameengine.GameStatus.*;

/**
 * GameplayViewController-Klasse
 */
public class GameplayViewController implements Initializable {

    @FXML
    private AnchorPane display;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label lifesLabel;

    @FXML
    private Label levelLabel;


    @FXML
   private HBox infoBar;


    private boolean shipSelfMove = Boolean.parseBoolean(SpaceInvaders.getSettings("ship.move.self"));

    /**
     * Inizialisiert die Controller-Klasse.
     * listener fuer Key-Interaktionen des Spielers
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        //infoBar.setHgrow(lifesLabel, Priority.ALWAYS);
        //infoBar.setHgrow(scoreLabel, Priority.ALWAYS);
        //infoBar.setHgrow(levelLabel, Priority.ALWAYS);

       // infoBar.setSpacing(40);

        Game game = Game.getInstance();
        game.constructGame(display);

        scoreLabel.textProperty().bind(game.getPlayer().scoreProperty().asString());
        lifesLabel.textProperty().bind(game.getPlayer().livesProperty().asString());
        levelLabel.textProperty().bind(game.levelProperty().asString());

        display.setFocusTraversable(true);

        display.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    game.getShip().setMoveDirection(LEFT);
                    break;
                case RIGHT:
                    game.getShip().setMoveDirection(RIGHT);
                    break;
                case X:
                    game.tryShipShoot();
                    break;
                case SPACE:
                    game.tryShipShoot();
                    break;
                case ESCAPE:
                    game.stop();
//                    game.reset();
                    break;
                case P:
                    game.setGameStatus(game.getGameStatus() == PLAY ? PAUSE : PLAY);
                    break;
            }
        });

        display.setOnKeyReleased(event -> {
            if (shipSelfMove) {
                return;
            }
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
                game.getShip().setMoveDirection(Direction.NONE);
            }
        });


        game.play();
    }
}
