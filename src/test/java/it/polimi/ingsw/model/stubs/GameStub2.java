package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Identity;
import it.polimi.ingsw.model.actionToken.ActionToken;
import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.util.ArrayList;

public class GameStub2 extends Game {
    private ArrayList<ActionToken> availableActionTokens;

    public GameStub2(ArrayList<Identity> identities) throws IOException, InvalidReadException, NoSuchPlayerException {
        super(identities);
    }

    @Override
    public boolean isGameEnded() {
        return false;
    }
}
