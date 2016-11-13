package sample;

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


        System.out.println("Starte Spiel...");

        Spiel spiel = Spiel.getInstance();

        for(int i = 0; i < spiel.getMaxSpieler(); i++){
            Spieler spieler = new Spieler(this, i);
            spieler.getPferd().positionProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> o, Object oldVal, Object newVal) {
                    pferdeListe.get(spieler.getSpielerId()).setLayoutX(750 - (spieler.getPferd().getPosition() * 20));
//                    labelPunkteListe.get(spieler.getSpielerId()).setText(Integer.toString(spieler.getPferd().getPosition()));
                }
            });

            spiel.addSpieler(spieler);
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



        spiel.startSpiel();
        spiel.clear();

//        System.out.println("Position ImageView 1: " + imageView1.getLayoutX());
//        imageViewPferd1.setLayoutX(700);
//        System.out.println("Position ImageView 1: " + imageView1.getLayoutX());
    }



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
