package com.oliverhuckfeldt.controllers;

import com.oliverhuckfeldt.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class EditorController {

    @FXML
    public void initialize() {
        System.out.println("Editor!");
    }

    @FXML
    public void toSettings() throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/settings.fxml"));
        Scene scene = new Scene(root, 400, 300);
        Main.stage.setScene(scene);
    }
}
