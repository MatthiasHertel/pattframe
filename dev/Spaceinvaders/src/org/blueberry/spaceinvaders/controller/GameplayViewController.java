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

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import org.blueberry.spaceinvaders.gameengine.Game;
import org.blueberry.spaceinvaders.SpaceInvaders;

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
    private Pane pane;

    @FXML
   private HBox infoBar;

    /**
     * Inizialisiert die Controller-Klasse.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        pane.setMinWidth(20);
//        pane.setMinWidth(1000);
//        pane.setPrefWidth(1000);

//        Platform.runLater(() -> {
//            pane.setMinWidth(1600);
//                });

        //infoBar.setHgrow(lifesLabel, Priority.ALWAYS);
        //infoBar.setHgrow(scoreLabel, Priority.ALWAYS);
        //infoBar.setHgrow(levelLabel, Priority.ALWAYS);

       // infoBar.setSpacing(40);

        System.out.println("Gamestatus vor reset:  " + Game.getInstance().getGameStatus());
        Game.reset();

        Game game = Game.getInstance();
        System.out.println("Gamestatus nach reset:  " + Game.getInstance().getGameStatus());

        game.constructGame(display);



        scoreLabel.textProperty().bind(game.getPlayer().scoreProperty().asString());
        lifesLabel.textProperty().bind(game.getPlayer().livesProperty().asString());
        levelLabel.textProperty().bind(game.levelProperty().asString());

        game.play();
    }
}
