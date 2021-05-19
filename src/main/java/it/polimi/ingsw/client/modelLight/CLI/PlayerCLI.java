package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.modelLight.LeaderCardSetView;
import it.polimi.ingsw.client.modelLight.PlayerView;

import java.util.ArrayList;

public class PlayerCLI extends PlayerView {

    public PlayerCLI(String nickname) {
       this.nickname = nickname;
       decks = new DevelopmentCardsDecksCLI();
       strongbox = new StrongboxCLI();
       depot = new DepotCLI();
       leaderCards = new LeaderCardSetCLI();
    }

    @Override
    public void dumpPlayer(String player, String objectUpdated) {
        if(player.equals(nickname))
            System.out.print("Your");
        else
            System.out.print(player);
        System.out.println("'s "+ objectUpdated +" has changed into:");
    }
}
