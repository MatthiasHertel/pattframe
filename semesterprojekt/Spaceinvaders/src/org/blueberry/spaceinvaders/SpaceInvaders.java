package org.blueberry.spaceinvaders;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.scene.Group;
import org.blueberry.spaceinvaders.controller.ScreenController;

import java.util.Properties;

/**
 * SpaceInvaders ist verantwortlich für den Start der Spiele-Applikation
 */

public class SpaceInvaders extends Application {

    private static final Properties settings = new Properties();
    private static ScreenController screenController = new ScreenController();
    private static HostServices hostServices ;

    private static Stage myPrimaryStage;


    /**
     * Start-Methode lädt als Standard-Screen die WelcomeView und lädt die Application-Properties
     * @param primaryStage Hauptbühne
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        myPrimaryStage = primaryStage;

        hostServices = getHostServices();
        settings.load(getClass().getResourceAsStream("/config/application.properties"));

        setScreen("WelcomeView");
//        setScreen("HighscoreView");

        Group root = new Group();
        root.getChildren().addAll(screenController);

//        Stage stage = new Stage();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Space Invaders");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/invader.png").toExternalForm()));

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
     * Globale Methode zum Abrufen der Schlüssel-Wert-Paare aus den Application-Properties
     * @param property Name der Property-Datei
     * @return
     */
    public static String getSettings(String property) {
        return settings.getProperty(property);
    }

    /**
     * Setzt den Screen
     * @param viewName Name der View (fxml-Dateiname)
     */
    public static void setScreen(String viewName) {
        screenController.setScreen("/views/" + viewName + ".fxml");
    }

    /**
     * Globale Hilsmethode zur Ausgabe in einem modalen Dialogs insbesodere zur Fehlerausgabe
     * @param message Meldung
     */
    public static void showDialog(final String message) {
//        Stage dialogStage = new Stage();
//        dialogStage.initModality(Modality.WINDOW_MODAL);
//        VBox box = new VBox();
//        box.getChildren().addAll(new Label(message));
//        box.setAlignment(Pos.CENTER);
//        box.setPadding(new Insets(5));
//        dialogStage.setScene(new Scene(box));
//        dialogStage.show();

        final Popup popup = new Popup();
        popup.setX(300);
        popup.setY(200);

        TextArea textArea = new TextArea();
        textArea.setText(message);
        textArea.setEditable(false);

        Button button = new Button("OK");
        button.setOnAction(event -> popup.hide());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(textArea, button);

        vBox.setStyle(
                "-fx-font-size: 12;\n" +
                "-fx-text-fill: #000;\n" +
                "-fx-background-color: #e8e8e8;\n" +
                "-fx-background-radius: 5;\n" +
                "-fx-background-insets: 0;\n" +
                "-fx-border-radius: 5;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-pref-width: 500px;\n" +
                "-fx-pref-height: 150px;\n" +
                "-fx-padding: 5px 15px;\n" +
                "-fx-alignment: center;\n" +
                "-fx-border-color: #e8e8e8;"
        );

        popup.getContent().add(vBox);
        popup.show(myPrimaryStage);
    }

    /**
     * Getter-Methode für den Application.HostServices
     * wird benutzt, um beim Link-Click-Ereignis eine URL im Default-Browser zu üffnen
     * @return HostServices
     */
    public static HostServices getMyHostServices() {
        return hostServices ;
    }
}

