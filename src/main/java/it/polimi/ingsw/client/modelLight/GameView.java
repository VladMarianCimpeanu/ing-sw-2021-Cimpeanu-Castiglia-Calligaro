package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.MessageFromServer.ErrorMessage;
import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.ActionToken.ActionTokenView;

import java.util.ArrayList;
import java.util.Collections;
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
     * @return the FaithPath
     */
    public FaithPathView getFaithPath() {
        return faithPath;
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

    public abstract void startBaseProd();

    public abstract void startExtraProd();

    /**
     * displays that something wrong happened with the connection.
     */
    public abstract void handleCrash();

    /**
     * restart the game from where it ended last time.
     */
    public void rejoinGame(){
        startGame();
    }

    /**
     * displays the end of the game and the scores.
     * @param ranks A map which keys are the nicknames of each player whereas the values are the scores of each player.
     * @param resources A map which keys are the nicknames of each player whereas the values are the resources owned of each player.
     */
    public abstract void endGame(Map<String, Integer> ranks, Map<String, Integer> resources);

    /**
     * Orders the players by their scores: the first element will be the player with the highest score. If more players has
     * the same score, the order will be given by the amount of resources owned by each player.
     * @param ranks A map which keys are the nicknames of each player whereas the values are the scores of each player.
     * @param resources A map which keys are the nicknames of each player whereas the values are the resources owned of each player.
     * @return a list of nicknames ordered by the scores of each player.
     */
    protected ArrayList<String> orderPlayers(Map<String, Integer> ranks, Map<String, Integer> resources){
        ArrayList<Integer> order = new ArrayList<>(ranks.values());
        Collections.sort(order, Collections.reverseOrder());
        ArrayList<String> result = new ArrayList<>();
        for(int i: order){
            //finds the remaining players with the highest score.
            ArrayList<String> sameValue = new ArrayList<>();
            for(String s: new ArrayList<>(ranks.keySet()))
                if (ranks.get(s) == i){
                    sameValue.add(s);
                    ranks.remove(s);
                }
            //orders the found player by the amount of resources owned.
            while(!sameValue.isEmpty()){
                int max = -1;
                String name = null;
                for (String s: sameValue){
                    if(!s.equals("blackCross") && resources.get(s) > max){
                        max = resources.get(s);
                        name = s;
                    }
                    if(s.equals("blackCross")) name = "blackCross";
                }
                result.add(name);
                sameValue.remove(name);
            }
        }
        return result;
    }
}
