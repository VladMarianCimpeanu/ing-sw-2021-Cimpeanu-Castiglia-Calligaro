package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.ActionToken.ActionTokenView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class that gives access to all the information of the light model.
 */
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

    /**
     * stores the resources that have not been paid/taken yet.
     * @param resBuffer resources to be paid/taken.
     */
    public void setResBuffer(Map<Resource, Integer> resBuffer) {
        this.resBuffer = resBuffer;
    }

    /**
     * @return returns a FaithPathView object.
     */
    public FaithPathView getFaithPathView(){
        return faithPath;
    }

    /**
     * stores the resources that have not been paid/taken yet.
     * @param resources resources to be paid/taken.
     */
    public void setResBuffer(ArrayList<Resource> resources) {
        resBuffer.clear();
        for(Resource resource : resources){
            resBuffer.put(resource, resBuffer.getOrDefault(resource, 0) + 1);
        }
    }

    /**
     * add players to the current game.
     * @param nicknames specified nickname of each player that has to be set.
     */
    public abstract void setPlayers(ArrayList<String> nicknames);

    /**
     * print a message.
     * @param content content of the message.
     */
    public abstract void dumpMessage(String content);

    /**
     * get the player with the specified nickname
     * @param nickname nickname of the requested player.
     * @return PlayerView object that has the specified nickname.
     */
    public PlayerView getPlayer(String nickname){
        return players.getOrDefault(nickname, null);
    }

    /**
     * @return the MarketView object associated to this game.
     */
    public MarketView getMarket(){
        return market;
    }

    /**
     * @return DevelopmentCardSetView object which has all the information about development cards.
     */
    public DevelopmentCardSetView getCards(){
        return cards;
    }

    /**
     * @return an ActionTokenView object.
     */
    public ActionTokenView getActionTokenView() {
        return actionTokenView;
    }

    /**
     * renders resources that have not been paid yet.
     * @param resources resources to pay that should be displayed.
     */
    public abstract void displayResourcesToPay(Map<Resource, Integer> resources);
    //TODO: content from the server, why? discuss.
    public abstract void notifyJoin(String content, int size, ArrayList<String> players);

    /**
     * displays an error.
     * @param error error to be displayed
     */
    public abstract void displayError(ErrorMessage error);

    /**
     * update the waiting room with a new player.
     * @param player
     */
    public abstract void newWaitingPlayer(String player);

    /**
     * update the waiting room that a player has left the waiting room.
     * @param player nickname of the player that left the waiting room.
     */
    public abstract void crashedWaitingPlayer(String player);

    /**
     * starts the game.
     */
    public abstract void startGame();

    /**
     * notify the player that there are no waiting room to access.
     */
    public abstract void requireMode();

    /**
     * updates the resources that have not been redeemed yet from the market.
     * @param resources resources left.
     */
    public abstract void updateResourcesFromMarket(ArrayList<Resource> resources);

    /**
     * update the strategies that have been selected to convert marbles at the market
     * and how many white marbles need to be converted.
     * @param whiteMarbles white marbles left.
     * @param strategies ID of the new strategy selected.
     */
    public abstract void updatedUsedStrategies(int whiteMarbles, int strategies);

    /**
     * display to the player in which position he will start the game and how many resources has to place in the depot.
     * @param position specified position of the player.
     * @param number number of resources to redeem.
     */
    public abstract void chooseResources(int position, int number);

    /**
     * update the state of the game, now there will be normal turns.
     */
    public abstract void firstTurnEnded();

    /**
     * updates the resources produced by the player.
     * @param resources specified resources produced by the player.
     * @param player specified player who performed the production.
     */
    public abstract void lastProduced(Map<Resource, Integer> resources, String player);

    /**
     * updates the current player.
     * @param player player who has just started the turn.
     */
    public abstract void changeTurn(String player);
}
