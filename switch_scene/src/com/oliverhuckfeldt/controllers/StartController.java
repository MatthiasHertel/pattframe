package com.oliverhuckfeldt.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import com.oliverhuckfeldt.Main;

public class StartController {

    @FXML
    public void initialize() {
        System.out.println("Start!");
    }

    @FXML
    public void toEditor() throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("/fxml/editor.fxml"));
        Scene scene = new Scene(root, 600, 300);
        Main.stage.setScene(scene);
    }
}
