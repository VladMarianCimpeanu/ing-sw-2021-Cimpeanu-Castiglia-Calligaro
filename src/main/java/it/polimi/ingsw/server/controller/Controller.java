package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.EchoServerClientHandler;
import it.polimi.ingsw.server.MessageToClient.*;
import it.polimi.ingsw.server.MessageToClient.Rejoin.*;
import it.polimi.ingsw.server.MessageToClient.Updates.UpdateFaithpath;
import it.polimi.ingsw.server.MultiEchoServer;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.FirstTurn;
import it.polimi.ingsw.server.controller.states.SelectionState;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.exceptions.*;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.shuffle;

/**
 * Controller perform actions requested by the client, on the model.
 * To manage the turn phases, it uses a State Pattern (currentState).
 */
public class Controller {
    private Game game;
    private String currentUser;
    private boolean isFirstTurn;

    private Map<String, EchoServerClientHandler> nicknames;
    private Map<String, Player> players;
    //nicknames in order of turns
    private ArrayList<String> turns;
    private TurnState currentState;

    public Controller(ArrayList<Identity> users){
        nicknames = new HashMap<>();
        players = new HashMap<>();
        turns = new ArrayList<>();
        currentUser = null;
        ArrayList<Identity> identities = new ArrayList<>(users);
        System.out.print("A new Game has started. Players: ");
        for(Identity i: identities) System.out.print(i.getNickname() + " ");
        shuffle(identities);
        try{
            if(identities.size() == 1) game = new Singleplayer(identities);
            else game = new Multiplayer(identities);
        }catch(InvalidReadException | IOException | NoSuchPlayerException | InvalidStepsException e){
            e.printStackTrace();
        }
        for(Identity i: identities){
            String nick = i.getNickname();
            EchoServerClientHandler client = MultiEchoServer.getClient(nick);
            client.setController(this);
            nicknames.put(nick, client);
            turns.add(nick);
        }
        System.out.println("Shuffling players");
        System.out.println("Player order: " + turns);
        currentState = new FirstTurn(this);
        for(Player p: game.getPlayers())
            players.put(p.getNickName(),p);
        new VirtualView(this);
        setUp();
    }


    /**
     * sends to all the players the initial conditions of the game: players in game, market information, faithPath information,
     * cards available and for each player, which leader cards have.
     */
    private void setUp(){
        sendBroadcast(new PlayersOrder(turns));
        sendBroadcast(setUpMarketGrid());
        sendBroadcast(setUpDevelopmentGrid());
        sendBroadcast(setUpFaithPath());
        //Keep LeaderCard
        for(String nick: turns){
            Player p = players.get(nick);

            nicknames.get(nick).send(new KeepLeadercards(p.getLeaderCards()
                    .stream()
                    .map(LeaderCard::getID)
                    .collect(Collectors.toCollection(ArrayList::new))
                    ));
        }
        sendBroadcast(new GameStarted());
    }

    /**
     * sends all the information of the current game to the specified player: players in game, current market information,
     * current faith path information, current cards available, dashboard of each player, the player's leader cards and all
     * the leader cards activated by the opponents.
     * @param nickname specified player to send all the information.
     */
    private synchronized void reSetUp(String nickname){
        sendMessage(nickname, new PlayersOrder(turns));
        sendMessage(nickname, setUpMarketGrid());
        sendMessage(nickname, setUpFaithPath());
        sendMessage(nickname, setUpDevelopmentGrid());
        //LeaderCards
        Map<Integer, Boolean> activated = new HashMap<>();
        for(LeaderCard card: players.get(nickname).getLeaderCards())
            activated.put(card.getID(), false);
        for(LeaderCard active: players.get(nickname).getActivatedLeader())
            activated.put(active.getID(), true);
        sendMessage(nickname, new RejoinLeaderCards(nickname, activated));
        //Opponents Activated LeaderCards
        for(Player p: players.values()){
            if(!p.getNickName().equals(nickname)){
                activated = new HashMap<>();
                for(LeaderCard active: p.getActivatedLeader())
                    activated.put(active.getID(), true);
                sendMessage(nickname, new RejoinLeaderCards(p.getNickName(), activated));
            }
        }
        recoveryDashboards(nickname);
        sendBroadcast(new RejoinPlayer(nickname));
    }

    /**
     * rejoin a client that disconnected, sending him all the information of the current game.
     * @param client EchoServerClientHandler of the specified player that has rejoined.
     * @param nickname specified nickname of the player that rejoins the game.
     */
    public void rejoinClient(EchoServerClientHandler client, String nickname) {
        nicknames.remove(nickname);
        nicknames.put(nickname, client);
        players.get(nickname).getIdentity().setOnline(true);
        client.setController(this);
        reSetUp(nickname);
    }


    /**
     * creates a new message containing all the information about the development cards available to buy.
     * @return a DevCardMessage containing all the information of the development cards currently available.
     */
    private DevCardSet setUpDevelopmentGrid(){
        ArrayList<ArrayList<Integer>> set = new ArrayList<>();
        for(int level = 1; level<=3; level++){
            set.add(new ArrayList<>());
            try {
                set.get(level - 1).add(game.getDevelopmentCardSet().peekCard(Color.GREEN, level).getID());
                set.get(level - 1).add(game.getDevelopmentCardSet().peekCard(Color.BLUE, level).getID());
                set.get(level - 1).add(game.getDevelopmentCardSet().peekCard(Color.YELLOW, level).getID());
                set.get(level - 1).add(game.getDevelopmentCardSet().peekCard(Color.PURPLE, level).getID());
            } catch (WrongLevelException | NoCardException e) {
                e.printStackTrace();
            }
        }
        return new DevCardSet(set);
    }

    /**
     * gives all the information about the current faithPath.
     * @return UpdateFaithPath message containing all the information of the current faith path.
     */
    private UpdateFaithpath setUpFaithPath(){
        Map<String, Integer> faithPositions = new HashMap<>();
        if(players.size() == 1){
            SingleFaithPath path = (SingleFaithPath)game.getFaithPath();
            faithPositions.put("blackCross", path.getBlackCrossPosition());
        }
        for(Player player: players.values()){
            try {
                faithPositions.put(player.getNickName(), game.getFaithPath().getPlayerPosition(player));
            } catch (NoSuchPlayerException e) {
                e.printStackTrace();
            }
        }
        return new UpdateFaithpath(faithPositions);
    }

    /**
     * gives all the information about the current market.
     * @return MarketGrid message containing all the information about the current market.
     */
    private MarketGrid setUpMarketGrid(){
        String[][] market = new String[3][4];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++)
                market[i][j] = game.getMarket().getMarket()[i][j].toString();
        }
        return new MarketGrid(market, game.getMarket().getOuterMarble().toString());
    }

    /**
     * Sends all the information about all the player's dashboards to the specified player.
     * It sends a RejoinDepotMessage for each shelf of each player, a RejoinStrongbox for each player and a RejoinDeck for
     * each deck of each player.
     * @param nickname specified player to send all the information.
     */
    private void recoveryDashboards(String nickname){
        for(Player p: players.values()){
            try {
                for(int i = 1; i <= 3; i++){
                    sendMessage(nickname, new RejoinDepot(p.getNickName(),
                            i,
                            p.getDashboard().getWarehouseDepot().getShelfResource(i),
                            p.getDashboard().getWarehouseDepot().getShelfQuantity(i)
                    ));
                }
                sendMessage(nickname, new RejoinStrongbox(p.getNickName(),
                        p.getDashboard().getStrongbox().getResources()
                ));
                for(int i = 1; i <= 3; i++){
                    try {
                        sendMessage(nickname, new RejoinDecks(p.getNickName(),
                                i,
                                p.getDashboard().getCardOnTop(i).getID()
                        ));
                    } catch (NoCardException e) {
                        sendMessage(nickname, new RejoinDecks(p.getNickName(),
                                i,
                                0
                        ));
                    }
                }
                ArrayList<ExtraSlot> extraSlotList = p.getDashboard().getWarehouseDepot().getExtraSlotList();
                for(ExtraSlot extraSlot: extraSlotList){
                    sendMessage(nickname, new RejoinExtraSlot(p.getNickName(),
                            extraSlot.getResource(),
                            extraSlot.getID(),
                            extraSlot.getQuantity()
                    ));
                }
            } catch (InvalidShelfPosition | InvalidDeckPositionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Give the order of the players.
     * @return an ArrayList containing players' nicknames ordered by their turn order.
     */
    public ArrayList<String> getTurns() {
        return turns;
    }

    /**
     * @return the game managed by the controller.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Given a nickname, it returns the player with the specified nickname.
     * @param nickname specified nickname of the player
     * @return Player object that is in controller's game and has the specified nickname.
     */
    public Player getPlayer(String nickname){return players.get(nickname);}

    /**
     * @return the reference to the player that is currently playing.
     */
    public Player getCurrentPlayer(){
        return players.get(currentUser);
    }

    /**
     * returns the identity of the player with the specified nickname.
     * @param nickname specified nickname of the player that has the identity needed.
     * @return the identity of the specified nickname.
     */
    public Identity getIdentity(String nickname){
        return players.get(nickname).getIdentity();
    }

    /**
     * @return the reference to all the players playing to the current game.
     */
    public ArrayList<Player> getPlayers() {
        return new ArrayList<> (players.values());
    }

    /**
     * Sends a message to all the players of the game.
     * @param message specific message to send.
     */
    public void sendBroadcast(MessageToClient message){
        for(EchoServerClientHandler client : nicknames.values()) {
            client.send(message);
        }
    }

    /**
     * sends a message to the current player.
     * @param message specific message to send.
     */
    public void sendMessage(MessageToClient message){
        nicknames.get(currentUser).send(message);
    }

    /**
     * sends a message to a specific player.
     * @param message specific message to send.
     */
    public void sendMessage(String nickname, MessageToClient message){
        nicknames.get(nickname).send(message);
    }

    /**
     * sends an error to the current player.
     * @param error  specific error to send.
     */
    public void sendError(ErrorMessage error) {
        if(currentUser == null) return;
        nicknames.get(currentUser).sendError(error);
    }

    /**
     * changes the state of the game.
     * @param currentState new phase of the game.
     */
    public void setCurrentState(TurnState currentState) {
        this.currentState = currentState;
        System.out.println("Game state: " + currentState.getClass().getSimpleName());
    }

    /**
     * sets the current player to the next player that is online and change the state of the game to SelectionState.
     * It sets the ping timer of the previous player to infinite, whereas sets the ping timer of the next player to 30
     * seconds.
     */
    public void nextTurn(){
        if(isAnyoneOnline()) {
            //endGame: nobody is online
        }
        int pos = turns.indexOf(currentUser);
        pos = (pos+1)%turns.size();
        currentUser = turns.get(pos);
        if(!getCurrentPlayer().isOnline()) {
            nextTurn();
            return;
        }
        sendBroadcast(new ItsYourTurn(currentUser));
        setTimerPing();
        nicknames.get(currentUser).setMyTurn(true);
        setCurrentState(new SelectionState(this));
        try {
            game.endTurn();
        } catch (NoSuchPlayerException e) {
            sendError(ErrorMessage.generic);
        }
    }

    /**
     * start the first turn of the game: now only the first player can change the model and the states of the game.
     */
    public void startGame(){
        currentUser = turns.get(0);
        sendBroadcast(new ItsYourTurn(currentUser));
        for(int i = 1; i < turns.size(); i++){
            nicknames.get(turns.get(i)).setMyTurn(false);
        }
        setTimerPing();
        nicknames.get(currentUser).setMyTurn(true);
    }

    /**
     * checks if there are no players of the current game online.
     * @return true if there is at least one player online, otherwise false.
     */
    private boolean isAnyoneOnline(){
        for(String p: turns)
            if(players.get(p).isOnline()) return true;
        return false;
    }

    /**
     * @return the current state of the game.
     */
    public TurnState getCurrentState() {
        return currentState;
    }

    /**
     * sets the current player to the specified one.
     * @param currentUser nickname of the player that will became the current player.
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * set the socket timer to 30s to the current player, whereas it sets it to infinite for all the other players.
     */
    private void setTimerPing() {
        try {
            nicknames.get(currentUser).setSocketTimeOut(30*1000);
        } catch (SocketException e) {
            MultiEchoServer.handleCrash(nicknames.get(currentUser));
        }
        for(String user: turns) {
            if (user.equals(currentUser)) continue;
            try {
                nicknames.get(user).setMyTurn(false);
                nicknames.get(user).setSocketTimeOut(0);
            } catch (SocketException e) {
                MultiEchoServer.handleCrash(nicknames.get(currentUser));
            }
        }
    }
}