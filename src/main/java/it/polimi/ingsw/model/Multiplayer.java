package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Multiplayer extends Game {
    public Multiplayer(ArrayList<Player> players){
        super(players);
    }


    @Override
    public boolean isGameEnded() {
        return false;
    }
}
