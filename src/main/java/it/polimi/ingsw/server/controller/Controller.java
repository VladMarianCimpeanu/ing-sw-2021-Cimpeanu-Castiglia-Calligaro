package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.EchoServerClientHandler;
import it.polimi.ingsw.server.MessageToClient.*;
import it.polimi.ingsw.server.MessageToClient.Rejoin.*;
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
        System.out.println("");
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

    private void setUp(){
        //send entire market and devcardset broadcast
        //this can used also for rejoinClient
        //could be done responding to some client messages?

        sendBroadcast(new PlayersOrder(turns));

        //Market
        String[][] market = new String[3][4];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++)
                market[i][j] = game.getMarket().getMarket()[i][j].toString();
        }
        sendBroadcast(new MarketGrid(market, game.getMarket().getOuterMarble().toString()));

        //DevCardSet
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
        sendBroadcast(new DevCardSet(set));

        //Keep LeaderCard
        for(String nick: turns){
            Player p = players.get(nick);

            nicknames.get(nick).send(new KeepLeadercards(p.getLeaderCards()
                    .stream()
                    .map(LeaderCard::getID)
                    .collect(Collectors.toCollection(ArrayList::new))
                    ));
        }
    }

    private synchronized void reSetUp(String nickname){
        sendMessage(nickname, new PlayersOrder(turns));

        //Market
        String[][] market = new String[3][4];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++)
                market[i][j] = game.getMarket().getMarket()[i][j].toString();
        }

        sendMessage(nickname, new MarketGrid(market, game.getMarket().getOuterMarble().toString()));

        //DevCardSet
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
        sendMessage(nickname, new DevCardSet(set));

        //LeaderCards
        Map<Integer, Boolean> activated = new HashMap<>();
        for(LeaderCard card: players.get(nickname).getLeaderCards())
            activated.put(card.getID(), false);
        for(LeaderCard active: players.get(nickname).getActivatedLeader())
            activated.put(active.getID(), true);
        sendMessage(nickname, new RejoinLeaderCards(nickname, activated));

        //Opponents Activated LeaderCards
        for(Player p: players.values()){
            if(p.getNickName() != nickname){
                activated = new HashMap<>();
                for(LeaderCard active: p.getActivatedLeader())
                    activated.put(active.getID(), true);
            }
            sendMessage(p.getNickName(), new RejoinLeaderCards(p.getNickName(), activated));
        }

        for(Player p: players.values()){
            try {
                for(int i = 1; i <= 3; i++){
                    sendMessage(p.getNickName(), new RejoinDepot(p.getNickName(),
                            i,
                            p.getDashboard().getWarehouseDepot().getShelfResource(i),
                            p.getDashboard().getWarehouseDepot().getShelfQuantity(i)
                    ));
                }

                sendMessage(p.getNickName(), new RejoinStrongbox(p.getNickName(),
                        p.getDashboard().getStrongbox().getResources()
                ));

                for(int i = 1; i <= 3; i++){
                    sendMessage(p.getNickName(), new RejoinDecks(p.getNickName(),
                            i,
                            p.getDashboard().getCardOnTop(i).getID()
                    ));
                }

                ArrayList<ExtraSlot> extraSlotList = p.getDashboard().getWarehouseDepot().getExtraSlotList();
                for(ExtraSlot extraSlot: extraSlotList){
                    sendMessage(p.getNickName(), new RejoinExtraSlot(p.getNickName(),
                            extraSlot.getResource(),
                            extraSlot.getID(),
                            extraSlot.getQuantity()
                            ));
                }

                sendBroadcast(new RejoinPlayer(nickname));
            } catch (InvalidShelfPosition | NoCardException | InvalidDeckPositionException e) {
                e.printStackTrace();
            }

        }

    }

    public void rejoinClient(EchoServerClientHandler client, String nickname) {
        nicknames.remove(nickname);
        nicknames.put(nickname, client);
        players.get(nickname).getIdentity().setOnline(true);
        client.setController(this);
        //setUp();
    }


    public ArrayList<String> getTurns() {
        return turns;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer(String nickname){return players.get(nickname);}

    public Player getCurrentPlayer(){
        return players.get(currentUser);
    }

    public Identity getIdentity(String nickname){
        return players.get(nickname).getIdentity();
    }

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
     * @param e  specific error to send.
     */
    public void sendError(String e) {
        if(currentUser == null) return;
        nicknames.get(currentUser).sendError(e);
    }

    public void setCurrentState(TurnState currentState) {
        this.currentState = currentState;
        System.out.println("Game state: " + currentState.getClass().getSimpleName());
    }

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
            sendError(ErrorMessage.generic.toString());
        }
    }

    public void startGame(){
        currentUser = turns.get(0);
        sendBroadcast(new ItsYourTurn(currentUser));
        for(int i = 1; i < turns.size(); i++){
            nicknames.get(turns.get(i)).setMyTurn(false);
        }
        setTimerPing();
        nicknames.get(currentUser).setMyTurn(true);
    }

    private boolean isAnyoneOnline(){
        for(String p: turns)
            if(players.get(p).isOnline()) return true;
        return false;
    }

    public TurnState getCurrentState() {
        return currentState;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

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