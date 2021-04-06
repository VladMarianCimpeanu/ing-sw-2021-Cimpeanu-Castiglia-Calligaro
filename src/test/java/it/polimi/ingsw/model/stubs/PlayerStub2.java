package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Identity;
import it.polimi.ingsw.model.Player;

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
