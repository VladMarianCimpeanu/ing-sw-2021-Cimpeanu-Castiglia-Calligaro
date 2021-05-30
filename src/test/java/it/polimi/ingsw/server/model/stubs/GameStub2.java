package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.actionToken.ActionToken;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GameStub2 extends Game {
    private ArrayList<ActionToken> availableActionTokens;

    public GameStub2(ArrayList<Identity> identities) throws IOException, InvalidReadException, NoSuchPlayerException {
        super(identities);
    }

    @Override
    public boolean isGameEnded() {
        return false;
    }

    @Override
    public Map<String, Integer> calculatePoints() {
        return null;
    }
}
