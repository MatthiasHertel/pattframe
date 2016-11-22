package sample;

import java.util.Vector;

public interface ISpiel {

    void addSpieler(ISpieler spieler);
    void startSpiel();
    int getSpielerAnzahl();
    void clear();
    Vector<ISpieler> getAllSpieler();
}
