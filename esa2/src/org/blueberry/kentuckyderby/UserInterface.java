package org.blueberry.kentuckyderby;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserInterface {

    private AnchorPane root;
    private Image icon;
    private String title;
    private double width, height;

    public void construct(Configuration config) {
        try {
            root = FXMLLoader.load(getClass().getResource("kentucky-derby.fxml"));
            title = config.getItem("application.title");
            icon = new Image(config.getItem("window.icon"));
            width = Double.parseDouble(config.getItem("window.width"));
            height = Double.parseDouble(config.getItem("window.height"));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void assemble(Stage stage) {
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add("stylesheets/main.css");
        stage.setScene(scene);
        stage.setTitle(title); // TODO: Möglichkeit title ins fxml?
        stage.getIcons().add(icon);
        stage.show();
    }
}
