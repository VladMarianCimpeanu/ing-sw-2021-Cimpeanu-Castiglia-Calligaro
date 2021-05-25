package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.modelLight.PlayerView;

public class PlayerGUI extends PlayerView {

    public PlayerGUI(String nickname) {
        this.nickname = nickname;
        leaderCards = new LeaderCardSetGUI();
        decks = new DevelopmentCardDecksGUI();
        strongbox = new StrongboxGUI();
        depot = new DepotGUI();
    }

    @Override
    public void dumpPlayer(String player, String objectUpdated) {

    }
}
