package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Multiplayer extends Game {
    public Multiplayer(ArrayList<Identity> identities){
        super(identities);
    }


    @Override
    public boolean isGameEnded() {
        return false;
    }
}
