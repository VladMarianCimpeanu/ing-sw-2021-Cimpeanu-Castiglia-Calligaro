package it.polimi.ingsw.client.modelLight;

import java.util.ArrayList;

public abstract class PlayerView {
    protected DevelopmentCardDecksView decks;
    protected DepotView depot;
    protected StrongboxView strongbox;
    protected String nickname;

    public String getNickname() {
        return nickname;
    }

    public abstract void dumpPlayer(String player, String objectUpdated);

    public DepotView getDepot() {
        return depot;
    }

    public DevelopmentCardDecksView getDecks() {
        return decks;
    }

    public StrongboxView getStrongbox() {
        return strongbox;
    }
}
