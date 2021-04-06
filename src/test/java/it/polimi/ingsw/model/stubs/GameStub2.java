package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Identity;
import it.polimi.ingsw.model.actionToken.ActionToken;

import java.util.ArrayList;

public class GameStub2 extends Game {
    private ArrayList<ActionToken> availableActionTokens;

    public GameStub2(ArrayList<Identity> identities) {
        super(identities);
    }

    @Override
    public boolean isGameEnded() {
        return false;
    }
}
