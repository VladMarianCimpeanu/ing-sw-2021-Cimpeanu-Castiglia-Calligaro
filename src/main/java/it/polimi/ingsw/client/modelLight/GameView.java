package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.ActionToken.ActionTokenView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class GameView {
    protected Map<String, PlayerView> players;
    protected MarketView market;
    protected FaithPathView faithPath;
    protected DevelopmentCardSetView cards;
    protected ActionTokenView actionTokenView;
    private Map<Resource, Integer> resBuffer;

    public GameView(){
        resBuffer = new HashMap<>();
    }

    public Map<Resource, Integer> getResBuffer() {
        return resBuffer;
    }

    public void setResBuffer(Map<Resource, Integer> resBuffer) {
        this.resBuffer = resBuffer;
    }

    public FaithPathView getFaithPathView(){
        return faithPath;
    }

    public void setResBuffer(ArrayList<Resource> resources) {
        resBuffer.clear();
        for(Resource resource : resources){
            resBuffer.put(resource, resBuffer.getOrDefault(resource, 0) + 1);
        }
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

    public ActionTokenView getActionTokenView() {
        return actionTokenView;
    }

    public abstract void displayResourcesToPay(Map<Resource, Integer> resources);

    public abstract void notifyJoin(String content, int size, ArrayList<String> players);

    public abstract void displayError(ErrorMessage error);

    public abstract void newWaitingPlayer(String player);

    public abstract void crashedWaitingPlayer(String player);

    public abstract void startGame();

    public abstract void requireMode();

    public abstract void updateResourcesFromMarket(ArrayList<Resource> resources);

    public abstract void updatedUsedStrategies(int whiteMarbles, int strategies);

    public abstract void chooseResources(int position, int number);

    public abstract void firstTurnEnded();

    public abstract void lastProduced(Map<Resource, Integer> resources, String player);

    public abstract void changeTurn(String player);
}
