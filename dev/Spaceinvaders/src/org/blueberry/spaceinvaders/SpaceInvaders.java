package org.blueberry.spaceinvaders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import org.blueberry.spaceinvaders.controller.ScreenController;

import java.util.Properties;

/**
 * SpaceInvaders ist verantwortlich für den Start der Spiele-Applikation
 */

public class SpaceInvaders extends Application {
    private static final Properties settings = new Properties();
    private static ScreenController screenController = new ScreenController();

    /**
     * Start-Methode
     * @param primaryStage Hauptbühne
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        settings.load(getClass().getResourceAsStream("/config/application.properties"));

        setScreen("WelcomeView");
        //setScreen("GameplayView");

        Group root = new Group();
        root.getChildren().addAll(screenController);
        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * main
     * @param args
     */
    public static void main(String args[]) {
        SpaceInvaders.launch(args);
    }

    /**
     * Ruft die Settings ab.
     * @param property Name der Property-Datei
     * @return
     */
    public static String getSettings(String property) {
        return settings.getProperty(property);
    }

    /**
     * Legt den Screen-Namen fest.
     * @param viewName Name der View
     */
    public static void setScreen(String viewName) {
        screenController.setScreen("/views/" + viewName + ".fxml");
    }
}

