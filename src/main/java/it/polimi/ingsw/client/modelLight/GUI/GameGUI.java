package it.polimi.ingsw.client.modelLight.GUI;

import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.ActionToken.ActionTokenGUI;
import it.polimi.ingsw.client.modelLight.CLI.DevelopmentCardsSetCLI;
import it.polimi.ingsw.client.modelLight.CLI.FaithPathCLI;
import it.polimi.ingsw.client.modelLight.CLI.MarketCLI;
import it.polimi.ingsw.client.modelLight.CLI.PlayerCLI;
import it.polimi.ingsw.client.modelLight.GameView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class GameGUI extends GameView {
    public GameGUI() {
        market = new MarketGUI();
        cards = new DevelopmentCardsSetGUI();
        faithPath = new FaithPathGUI();
    }

    @Override
    public void setPlayers(ArrayList<String> nicknames) {
        if(nicknames.size() == 1) actionTokenView = new ActionTokenGUI();
        players = new TreeMap<>();
        //with TreeMap we can save the player order
        for(String nickname : nicknames) players.put(nickname, new PlayerGUI(nickname));
    }

    @Override
    public void dumpMessage(String content) {

    }

    @Override
    public void displayResourcesToPay(Map<Resource, Integer> resources) {

    }
}
