package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

// Controller Klasse für die View
// instanziert die GUI Elemente

public class Controller {

    @FXML    private ImageView imageViewPferd1;
    @FXML    private ImageView imageViewPferd2;
    @FXML    private ImageView imageViewPferd3;
    @FXML    private ImageView imageViewPferd4;
    @FXML    private ImageView imageViewPferd5;
    @FXML    private ImageView imageViewPferd6;
    @FXML    private ImageView imageViewPferd7;
    @FXML    private ImageView imageViewPferd8;
    @FXML    private ImageView imageViewPferd9;
    @FXML    private ImageView imageViewPferd10;
    @FXML    private ImageView imageViewPferd11;
    @FXML    private ImageView imageViewPferd12;

    @FXML    private Label labelWurf1;
    @FXML    private Label labelWurf2;
    @FXML    private Label labelWurf3;
    @FXML    private Label labelWurf4;
    @FXML    private Label labelWurf5;
    @FXML    private Label labelWurf6;
    @FXML    private Label labelWurf7;
    @FXML    private Label labelWurf8;
    @FXML    private Label labelWurf9;
    @FXML    private Label labelWurf10;
    @FXML    private Label labelWurf11;
    @FXML    private Label labelWurf12;

    @FXML    private Label labelPunkte1;
    @FXML    private Label labelPunkte2;
    @FXML    private Label labelPunkte3;
    @FXML    private Label labelPunkte4;
    @FXML    private Label labelPunkte5;
    @FXML    private Label labelPunkte6;
    @FXML    private Label labelPunkte7;
    @FXML    private Label labelPunkte8;
    @FXML    private Label labelPunkte9;
    @FXML    private Label labelPunkte10;
    @FXML    private Label labelPunkte11;
    @FXML    private Label labelPunkte12;

    // ActionEvent für das Starten des SPieles an Button gebunden

    @FXML
    private void handleButtonAction(ActionEvent event){

        System.out.println("Starte Spiel...");

        ISpiel spiel = Spiel.getInstance();


        for(int i = 0; i < spiel.getSpielerAnzahl(); i++){

            spiel.addSpieler(new Spieler());
        }

        // Binding der Pferdposition

        imageViewPferd1.xProperty().bind(spiel.getAllSpieler().get(0).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd2.xProperty().bind(spiel.getAllSpieler().get(1).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd3.xProperty().bind(spiel.getAllSpieler().get(2).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd4.xProperty().bind(spiel.getAllSpieler().get(3).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd5.xProperty().bind(spiel.getAllSpieler().get(4).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd6.xProperty().bind(spiel.getAllSpieler().get(5).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd7.xProperty().bind(spiel.getAllSpieler().get(6).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd8.xProperty().bind(spiel.getAllSpieler().get(7).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd9.xProperty().bind(spiel.getAllSpieler().get(8).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd10.xProperty().bind(spiel.getAllSpieler().get(9).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd11.xProperty().bind(spiel.getAllSpieler().get(10).getSpielFigur().positionProperty().multiply(20).negate());
        imageViewPferd12.xProperty().bind(spiel.getAllSpieler().get(11).getSpielFigur().positionProperty().multiply(20).negate());

        // Bindings an die Labels zur Update der View

        labelPunkte1.textProperty().bind(spiel.getAllSpieler().get(0).getSpielFigur().positionProperty().asString());
        labelPunkte2.textProperty().bind(spiel.getAllSpieler().get(1).getSpielFigur().positionProperty().asString());
        labelPunkte3.textProperty().bind(spiel.getAllSpieler().get(2).getSpielFigur().positionProperty().asString());
        labelPunkte4.textProperty().bind(spiel.getAllSpieler().get(3).getSpielFigur().positionProperty().asString());
        labelPunkte5.textProperty().bind(spiel.getAllSpieler().get(4).getSpielFigur().positionProperty().asString());
        labelPunkte6.textProperty().bind(spiel.getAllSpieler().get(5).getSpielFigur().positionProperty().asString());
        labelPunkte7.textProperty().bind(spiel.getAllSpieler().get(6).getSpielFigur().positionProperty().asString());
        labelPunkte8.textProperty().bind(spiel.getAllSpieler().get(7).getSpielFigur().positionProperty().asString());
        labelPunkte9.textProperty().bind(spiel.getAllSpieler().get(8).getSpielFigur().positionProperty().asString());
        labelPunkte10.textProperty().bind(spiel.getAllSpieler().get(9).getSpielFigur().positionProperty().asString());
        labelPunkte11.textProperty().bind(spiel.getAllSpieler().get(10).getSpielFigur().positionProperty().asString());
        labelPunkte12.textProperty().bind(spiel.getAllSpieler().get(11).getSpielFigur().positionProperty().asString());

        labelWurf1.textProperty().bind(spiel.getAllSpieler().get(0).wurfAnzahlPProperty());
        labelWurf2.textProperty().bind(spiel.getAllSpieler().get(1).wurfAnzahlPProperty());
        labelWurf3.textProperty().bind(spiel.getAllSpieler().get(2).wurfAnzahlPProperty());
        labelWurf4.textProperty().bind(spiel.getAllSpieler().get(3).wurfAnzahlPProperty());
        labelWurf5.textProperty().bind(spiel.getAllSpieler().get(4).wurfAnzahlPProperty());
        labelWurf6.textProperty().bind(spiel.getAllSpieler().get(5).wurfAnzahlPProperty());
        labelWurf7.textProperty().bind(spiel.getAllSpieler().get(6).wurfAnzahlPProperty());
        labelWurf8.textProperty().bind(spiel.getAllSpieler().get(7).wurfAnzahlPProperty());
        labelWurf9.textProperty().bind(spiel.getAllSpieler().get(8).wurfAnzahlPProperty());
        labelWurf10.textProperty().bind(spiel.getAllSpieler().get(9).wurfAnzahlPProperty());
        labelWurf11.textProperty().bind(spiel.getAllSpieler().get(10).wurfAnzahlPProperty());
        labelWurf12.textProperty().bind(spiel.getAllSpieler().get(11).wurfAnzahlPProperty());

        spiel.startSpiel();
        spiel.clear();
    }

}
