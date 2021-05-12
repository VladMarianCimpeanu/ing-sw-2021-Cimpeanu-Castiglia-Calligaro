package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.Player;

public class PlayerStub2 extends Player {
    private int victoryPointsTest;
    public PlayerStub2(Identity identity, Game game) {
        super(identity, game, null, null);
        victoryPointsTest = 0;
    }

    @Override
    public void addVictoryPoints(int quantity) {
        victoryPointsTest += quantity;
    }


    @Override
    public int getVictoryPoints() {
        return victoryPointsTest;
    }
}
