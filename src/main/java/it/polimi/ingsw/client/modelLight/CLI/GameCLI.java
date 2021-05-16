package it.polimi.ingsw.client.modelLight.CLI;


import it.polimi.ingsw.client.modelLight.GameView;

import java.util.ArrayList;
import java.util.HashMap;

public class GameCLI extends GameView {
    public GameCLI(ArrayList<String> nicknames) {
        players = new HashMap<>();
        for(String nickname : nicknames) players.put(nickname, new PlayerCLI(nickname));
        market = new MarketCLI();
        faithPath = new FaithPathCLI();
        cards = new DevelopmentCardsSetCLI();
    }
}
