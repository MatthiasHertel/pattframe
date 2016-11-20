package sample;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class Controller {

    @FXML
    private ImageView imageViewPferd1;
    @FXML
    private ImageView imageViewPferd2;
    @FXML
    private ImageView imageViewPferd3;
    @FXML
    private ImageView imageViewPferd4;
    @FXML
    private ImageView imageViewPferd5;
    @FXML
    private ImageView imageViewPferd6;

    @FXML
    private ImageView imageViewPferd7;
    @FXML
    private ImageView imageViewPferd8;
    @FXML
    private ImageView imageViewPferd9;
    @FXML
    private ImageView imageViewPferd10;
    @FXML
    private ImageView imageViewPferd11;
    @FXML
    private ImageView imageViewPferd12;

    @FXML
    private Label labelWurf1;
    @FXML
    private Label labelWurf2;
    @FXML
    private Label labelWurf3;
    @FXML
    private Label labelWurf4;
    @FXML
    private Label labelWurf5;
    @FXML
    private Label labelWurf6;
    @FXML
    private Label labelWurf7;
    @FXML
    private Label labelWurf8;
    @FXML
    private Label labelWurf9;
    @FXML
    private Label labelWurf10;
    @FXML
    private Label labelWurf11;
    @FXML
    private Label labelWurf12;

    @FXML
    private Label labelPunkte1;
    @FXML
    private Label labelPunkte2;
    @FXML
    private Label labelPunkte3;
    @FXML
    private Label labelPunkte4;
    @FXML
    private Label labelPunkte5;
    @FXML
    private Label labelPunkte6;
    @FXML
    private Label labelPunkte7;
    @FXML
    private Label labelPunkte8;
    @FXML
    private Label labelPunkte9;
    @FXML
    private Label labelPunkte10;
    @FXML
    private Label labelPunkte11;
    @FXML
    private Label labelPunkte12;


    ArrayList<ImageView> pferdeListe = new ArrayList<ImageView>();
    ArrayList<Label> labelPunkteListe = new ArrayList<Label>();
    ArrayList<Label> labelWurfListe = new ArrayList<Label>();




    @FXML
    private void handleButtonAction(ActionEvent event){

        pferdeListe.add(imageViewPferd1);
        pferdeListe.add(imageViewPferd2);
        pferdeListe.add(imageViewPferd3);
        pferdeListe.add(imageViewPferd4);
        pferdeListe.add(imageViewPferd5);
        pferdeListe.add(imageViewPferd6);
        pferdeListe.add(imageViewPferd7);
        pferdeListe.add(imageViewPferd8);
        pferdeListe.add(imageViewPferd9);
        pferdeListe.add(imageViewPferd10);
        pferdeListe.add(imageViewPferd11);
        pferdeListe.add(imageViewPferd12);

        labelPunkteListe.add(labelPunkte1);
        labelPunkteListe.add(labelPunkte2);
        labelPunkteListe.add(labelPunkte3);
        labelPunkteListe.add(labelPunkte4);
        labelPunkteListe.add(labelPunkte5);
        labelPunkteListe.add(labelPunkte6);
        labelPunkteListe.add(labelPunkte7);
        labelPunkteListe.add(labelPunkte8);
        labelPunkteListe.add(labelPunkte9);
        labelPunkteListe.add(labelPunkte10);
        labelPunkteListe.add(labelPunkte11);
        labelPunkteListe.add(labelPunkte12);

        labelWurfListe.add(labelWurf1);
        labelWurfListe.add(labelWurf2);
        labelWurfListe.add(labelWurf3);
        labelWurfListe.add(labelWurf4);
        labelWurfListe.add(labelWurf5);
        labelWurfListe.add(labelWurf6);
        labelWurfListe.add(labelWurf7);
        labelWurfListe.add(labelWurf8);
        labelWurfListe.add(labelWurf9);
        labelWurfListe.add(labelWurf10);
        labelWurfListe.add(labelWurf11);
        labelWurfListe.add(labelWurf12);




        System.out.println("Starte Spiel...");

        Spiel spiel = Spiel.getInstance();

//        for(int i = 0; i < 5; i++){
        for(int i = 0; i < spiel.getMaxSpieler(); i++){
            Spieler spieler = new Spieler(this, i);
//            spieler.getPferd().positionProperty().addListener(new ChangeListener<Object>() {
//                @Override
//                public void changed(ObservableValue<?> o, Object oldVal, Object newVal) {
//                    pferdeListe.get(spieler.getSpielerId()).setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
////                    labelPunkte1.setText(new Integer(spieler.getPferd().getPosition()).toString());
////                    System.out.println("Pferdeposition geändert");
//
//                }
//            });



//            labelPunkte1.textProperty().bind(spieler.getPferd().positionProperty().asString());

            spiel.addSpieler(spieler);
//            labelPunkteListe.get(i).textProperty().bind(spieler.getPferd().positionProperty().asString());
            //pferdeListe.get(i).layoutXProperty().bind(spieler.getPferd().positionProperty());
        }

//        Spieler spieler = new Spieler(this, 0);
//        spieler.getPferd().positionProperty().addListener(new ChangeListener<Object>() {
//            @Override
//            public void changed(ObservableValue<?> o, Object oldVal, Object newVal) {
//                imageViewPferd1.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//            }
//        });
//
//        spiel.addSpieler(spieler);


//        labelPunkteListe.get(0).textProperty().bind(spiel.getMySpieler().get(0).getPferd().positionProperty().asString());
//        labelPunkte1.textProperty().bind(spiel.getMySpieler().get(0).getPferd().positionProperty().asString());
//        labelPunkte1.textProperty().bind(spiel.getMySpieler().get(0).getPferd().positionProperty().asString());

//        imageViewPferd1.layoutXProperty().bind(spiel.getMySpieler().get(0).getPferd().positionProperty());


//        NumberBinding bla = spiel.getMySpieler().get(4).getPferd().positionProperty().multiply(20).negate();
//        NumberBinding bla = spiel.getMySpieler().get(4).getPferd().positionProperty().add(1);
//        DoubleBinding bla = spiel.getMySpieler().get(4).getPferd().positionProperty().add(1.0);

//        imageViewPferd4.layoutXProperty().setValue(750);
        imageViewPferd1.xProperty().unbind();
        imageViewPferd1.xProperty().bind(spiel.getMySpieler().get(0).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd2.xProperty().bind(spiel.getMySpieler().get(1).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd3.xProperty().bind(spiel.getMySpieler().get(2).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd4.xProperty().bind(spiel.getMySpieler().get(3).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd5.xProperty().bind(spiel.getMySpieler().get(4).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd6.xProperty().bind(spiel.getMySpieler().get(5).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd7.xProperty().bind(spiel.getMySpieler().get(6).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd8.xProperty().bind(spiel.getMySpieler().get(7).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd9.xProperty().bind(spiel.getMySpieler().get(8).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd10.xProperty().bind(spiel.getMySpieler().get(9).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd11.xProperty().bind(spiel.getMySpieler().get(10).getPferd().positionProperty().multiply(20).negate());
        imageViewPferd12.xProperty().bind(spiel.getMySpieler().get(11).getPferd().positionProperty().multiply(20).negate());



        labelPunkte1.textProperty().bind(spiel.getMySpieler().get(0).getPferd().positionProperty().asString());
        labelPunkte2.textProperty().bind(spiel.getMySpieler().get(1).getPferd().positionProperty().asString());
        labelPunkte3.textProperty().bind(spiel.getMySpieler().get(2).getPferd().positionProperty().asString());
        labelPunkte4.textProperty().bind(spiel.getMySpieler().get(3).getPferd().positionProperty().asString());
        labelPunkte5.textProperty().bind(spiel.getMySpieler().get(4).getPferd().positionProperty().asString());
        labelPunkte6.textProperty().bind(spiel.getMySpieler().get(5).getPferd().positionProperty().asString());
        labelPunkte7.textProperty().bind(spiel.getMySpieler().get(6).getPferd().positionProperty().asString());
        labelPunkte8.textProperty().bind(spiel.getMySpieler().get(7).getPferd().positionProperty().asString());
        labelPunkte9.textProperty().bind(spiel.getMySpieler().get(8).getPferd().positionProperty().asString());
        labelPunkte10.textProperty().bind(spiel.getMySpieler().get(9).getPferd().positionProperty().asString());
        labelPunkte11.textProperty().bind(spiel.getMySpieler().get(10).getPferd().positionProperty().asString());
        labelPunkte12.textProperty().bind(spiel.getMySpieler().get(11).getPferd().positionProperty().asString());



        labelWurf1.textProperty().bind(spiel.getMySpieler().get(0).wurfAnzahlPropertyProperty().asString());
        labelWurf2.textProperty().bind(spiel.getMySpieler().get(1).wurfAnzahlPropertyProperty().asString());
        labelWurf3.textProperty().bind(spiel.getMySpieler().get(2).wurfAnzahlPropertyProperty().asString());
        labelWurf4.textProperty().bind(spiel.getMySpieler().get(3).wurfAnzahlPropertyProperty().asString());
        labelWurf5.textProperty().bind(spiel.getMySpieler().get(4).wurfAnzahlPropertyProperty().asString());
        labelWurf6.textProperty().bind(spiel.getMySpieler().get(5).wurfAnzahlPropertyProperty().asString());
        labelWurf7.textProperty().bind(spiel.getMySpieler().get(6).wurfAnzahlPropertyProperty().asString());
        labelWurf8.textProperty().bind(spiel.getMySpieler().get(7).wurfAnzahlPropertyProperty().asString());
        labelWurf9.textProperty().bind(spiel.getMySpieler().get(8).wurfAnzahlPropertyProperty().asString());
        labelWurf10.textProperty().bind(spiel.getMySpieler().get(9).wurfAnzahlPropertyProperty().asString());
        labelWurf11.textProperty().bind(spiel.getMySpieler().get(10).wurfAnzahlPropertyProperty().asString());
        labelWurf12.textProperty().bind(spiel.getMySpieler().get(11).wurfAnzahlPropertyProperty().asString());









        spiel.startSpiel();
        spiel.clear();

//        System.out.println("Position ImageView 1: " + imageView1.getLayoutX());
//        imageViewPferd1.setLayoutX(700);
//        System.out.println("Position ImageView 1: " + imageView1.getLayoutX());
    }




//        imageViewPferd4.layoutXProperty().bind(spiel.getMySpieler().get(4).getPferd().positionProperty());



        public void updateView(Spieler spieler){

//        switch(spieler.getSpielerId() +1 ){
//            case 29:
////                labelWurf1.setText(Integer.toString(spieler.getWurfAnzahl()));
////                labelPunkte1.setText(Integer.toString(spieler.getPferd().getPosition()));
////                imageViewPferd1.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 2:
//                labelWurf2.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte2.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd2.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 3:
//                labelWurf3.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte3.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd3.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 4:
//                labelWurf4.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte4.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd4.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 5:
//                labelWurf5.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte5.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd5.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 6:
//                labelWurf6.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte6.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd6.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 7:
//                labelWurf7.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte7.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd7.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 8:
//                labelWurf8.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte8.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd8.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 9:
//                labelWurf9.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte9.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd9.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 10:
//                labelWurf10.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte10.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd10.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 11:
//                labelWurf11.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte11.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd11.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//            case 12:
//                labelWurf12.setText(Integer.toString(spieler.getWurfAnzahl()));
//                labelPunkte12.setText(Integer.toString(spieler.getPferd().getPosition()));
//                imageViewPferd12.setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                break;
//        }
    }


}
