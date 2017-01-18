package org.blueberry.spaceinvaders;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
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

//        setScreen("WelcomeView");
        setScreen("GameplayView");

        Group root = new Group();
        root.getChildren().addAll(screenController);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.F11));

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

