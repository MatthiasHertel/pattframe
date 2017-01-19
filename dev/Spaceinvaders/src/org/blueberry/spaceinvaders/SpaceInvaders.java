package org.blueberry.spaceinvaders;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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


    public static void showDialog(final String message) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        VBox box = new VBox();
        box.getChildren().addAll(new Label(message));
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5));
        dialogStage.setScene(new Scene(box));
        dialogStage.show();
    }
}

