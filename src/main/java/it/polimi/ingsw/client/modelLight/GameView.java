package it.polimi.ingsw.client.modelLight;

import java.util.ArrayList;
import java.util.Map;

public abstract class GameView {
    protected Map<String, PlayerView> players;
    protected MarketView market;
    protected FaithPathView faithPath;
    protected DevelopmentCardSetView cards;

    public FaithPathView getFaithPathView(){
        return faithPath;
    }

    public abstract void setPlayers(ArrayList<String> nicknames);

    public abstract void dumpMessage(String content);

    public PlayerView getPlayer(String nickname){
        return players.getOrDefault(nickname, null);
    }

    public MarketView getMarket(){
        return market;
    }

    public DevelopmentCardSetView getCards(){
        return cards;
    }

}
