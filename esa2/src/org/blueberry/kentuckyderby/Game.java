package org.blueberry.kentuckyderby;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private int numberOfPlayers;
    private List<Player> players;
    private static Game instance;

    private Game() {}

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void run() {
        players = new ArrayList<Player>();

        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player());
        }

        //System.out.println("Spiel gestartet...");
        //System.out.println(numberOfPlayers);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
