package org.blueberry.spaceinvaders.controller;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import org.blueberry.spaceinvaders.SpaceInvaders;

/**
 * ScreenController
 */
public class ScreenController extends StackPane {

    private Node screen;

    public ScreenController() {
        super();
    }

    /**
     * Lädt die View
     * @param resource Name der FXML Datei (ohne Endung)
     * @return hat geklappt?
     */
    public boolean loadScreen(String resource) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            this.screen = fxmlLoader.load();
            this.screen.setOnKeyPressed(evt -> {
                switch (evt.getCode()) {
                    case ESCAPE:
                        SpaceInvaders.setScreen("WelcomeView");
                        break;
                }
            });
            int paddingTop = (int) (Screen.getPrimary().getBounds().getHeight() - 600) / 2;
            int paddingRight = (int) (Screen.getPrimary().getBounds().getWidth() - 1024) / 2;
            screen.setStyle("-fx-padding:" + paddingTop + "px " + paddingRight +"px");

            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Setzt die View
     * @param resource Name der FXML Datei (ohne Endung)
     */
    public boolean setScreen(final String resource) {
        if (!loadScreen(resource)) {
            System.out.println("ScreenController: view konnte nicht geladen werden - " + resource);
        }
        if (!getChildren().isEmpty()) {
            getChildren().remove(0);
            getChildren().add(0, screen);
        } else {
            getChildren().add(screen);
        }
        return true;
    }

}