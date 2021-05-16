package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.modelLight.PlayerView;

import java.util.ArrayList;

public class PlayerCLI extends PlayerView {
    ArrayList<LeaderCardCLI> leaderCards;
    public PlayerCLI(String nickname) {
       this.nickname = nickname;
       decks = new DevelopmentCardsDecksCLI();
       strongbox = new StrongboxCLI();
       depot = new DepotCLI();
       leaderCards = new ArrayList<LeaderCardCLI>();
    }
}
