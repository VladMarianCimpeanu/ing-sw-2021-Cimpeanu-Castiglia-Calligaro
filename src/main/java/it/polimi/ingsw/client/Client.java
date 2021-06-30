package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.MessageFromServer.*;
import it.polimi.ingsw.client.MessageFromServer.Error;
import it.polimi.ingsw.client.MessageFromServer.Rejoin.*;
import it.polimi.ingsw.client.MessageFromServer.Updates.*;
import it.polimi.ingsw.client.MessageToServer.MessageToServer;
import it.polimi.ingsw.client.MessageToServer.PingFromClient;
import it.polimi.ingsw.client.modelLight.CLI.DepotCLI;
import it.polimi.ingsw.client.modelLight.CLI.GameCLI;
import it.polimi.ingsw.client.modelLight.CLI.MarketCLI;
import it.polimi.ingsw.client.modelLight.CLI.StrongboxCLI;
import it.polimi.ingsw.client.modelLight.GUI.GameGUI;
import it.polimi.ingsw.client.modelLight.GameView;
import it.polimi.ingsw.client.modelLight.PlayerView;
import it.polimi.ingsw.server.RuntimeTypeAdapterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static it.polimi.ingsw.client.Resource.*;

/**
 * This class represents a Client, it starts the View in GUI or CLI.
 */
public class Client {
    private boolean gui;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private RuntimeTypeAdapterFactory<MessageFromServer> shapeAdapterFactory;
    private Gson convert;
    private CLI cli;
    private String nickname;
    private PlayerView me;
    private GameView gameView;
    private int timer;
    //nicknames in order
    private ArrayList<String> nicknames;
    private boolean run;
    private boolean isEnded;

    public Client(String ip, int port, String mode){
        if(mode.equals("cli"))
            gui = false;
        else if(mode.equals("gui"))
            gui = true;
        else
            System.out.println("Error: you need to select a mode between cli and gui.");
        try{
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            run = true;
        } catch (UnknownHostException e) {
            System.out.println("Ops... It was not possible to determine the specified IP");
            run = false;
        } catch (IOException e) {
            System.out.println("Ops... It was not possible to connect to the specified IP or port.");
            run = false;
        }
        shapeAdapterFactory = RuntimeTypeAdapterFactory.of(MessageFromServer.class, "type");
        shapeAdapterFactory.registerSubtype(AskStrategy.class, "AskStrategy");
        shapeAdapterFactory.registerSubtype(CrashedPlayer.class, "CrashedPlayer");
        shapeAdapterFactory.registerSubtype(DevCardSet.class, "DevCardSet");
        shapeAdapterFactory.registerSubtype(Error.class, "Error");
        shapeAdapterFactory.registerSubtype(TurnOrder.class, "TurnOrder");
        shapeAdapterFactory.registerSubtype(GameStarted.class, "GameStarted");
        shapeAdapterFactory.registerSubtype(ItsYourTurn.class, "ItsYourTurn");
        shapeAdapterFactory.registerSubtype(JoinPlayer.class, "JoinPlayer");
        shapeAdapterFactory.registerSubtype(KeepLeadercards.class, "KeepLeadercards");
        shapeAdapterFactory.registerSubtype(LastTurn.class, "LastTurn");
        shapeAdapterFactory.registerSubtype(MarketGrid.class, "MarketGrid");
        shapeAdapterFactory.registerSubtype(ModeRequest.class, "ModeRequest");
        shapeAdapterFactory.registerSubtype(OkRoom.class, "OkRoom");
        shapeAdapterFactory.registerSubtype(Ping.class, "Ping");
        shapeAdapterFactory.registerSubtype(ResourceToPay.class, "ResourceToPay");
        shapeAdapterFactory.registerSubtype(ResourceToPut.class, "ResourceToPut");
        shapeAdapterFactory.registerSubtype(SelectedLeadercards.class, "SelectedLeadercards");
        shapeAdapterFactory.registerSubtype(SelectPlaceCard.class, "SelectPlaceCard");
        shapeAdapterFactory.registerSubtype(SelectResourceIn.class, "SelectResourceIn");
        shapeAdapterFactory.registerSubtype(SelectResourceOut.class, "SelectResourceOut");
        shapeAdapterFactory.registerSubtype(UpdateActiveLeaderCard.class, "UpdateActiveLeaderCard");
        shapeAdapterFactory.registerSubtype(UpdateDevCardDrawn.class, "UpdateDevCardDrawn");
        shapeAdapterFactory.registerSubtype(UpdateDevDeck.class, "UpdateDevDeck");
        shapeAdapterFactory.registerSubtype(UpdateDiscardLeaderCard.class, "UpdateDiscardLeaderCard");
        shapeAdapterFactory.registerSubtype(UpdateExtraSlot.class, "UpdateExtraSlot");
        shapeAdapterFactory.registerSubtype(UpdateFaithpath.class, "UpdateFaithpath");
        shapeAdapterFactory.registerSubtype(UpdateLastProduced.class, "UpdateLastProduced");
        shapeAdapterFactory.registerSubtype(UpdateMarketColumn.class, "UpdateMarketColumn");
        shapeAdapterFactory.registerSubtype(UpdateMarketRow.class, "UpdateMarketRow");
        shapeAdapterFactory.registerSubtype(UpdateMeetingPope.class, "UpdateMeetingPope");
        shapeAdapterFactory.registerSubtype(UpdateStrongbox.class, "UpdateStrongbox");
        shapeAdapterFactory.registerSubtype(UpdateWarehouseDepot.class, "UpdateWarehouseDepot");
        shapeAdapterFactory.registerSubtype(NicknameAccepted.class, "NicknameAccepted");
        shapeAdapterFactory.registerSubtype(SelectedMarbles.class, "SelectedMarbles");
        shapeAdapterFactory.registerSubtype(ConvertedMarbles.class, "ConvertedMarbles");
        shapeAdapterFactory.registerSubtype(PlayersOrder.class, "PlayersOrder");
        shapeAdapterFactory.registerSubtype(UpdateDrawToken.class, "UpdateDrawToken");
        shapeAdapterFactory.registerSubtype(RejoinDecks.class, "RejoinDecks");
        shapeAdapterFactory.registerSubtype(RejoinDepot.class, "RejoinDepot");
        shapeAdapterFactory.registerSubtype(RejoinExtraSlot.class, "RejoinExtraSlot");
        shapeAdapterFactory.registerSubtype(RejoinLeaderCards.class, "RejoinLeaderCards");
        shapeAdapterFactory.registerSubtype(RejoinStrongbox.class, "RejoinStrongbox");
        shapeAdapterFactory.registerSubtype(RejoinPlayer.class, "RejoinPlayer");
        shapeAdapterFactory.registerSubtype(FirstTurnEnded.class, "FirstTurnEnded");
        shapeAdapterFactory.registerSubtype(UpdateStrategyBuffer.class, "UpdateStrategyBuffer");
        shapeAdapterFactory.registerSubtype(GameEnded.class, "GameEnded");

        convert = new GsonBuilder().registerTypeAdapterFactory(shapeAdapterFactory).create();
        timer = 0;
        isEnded = false;
    }

    /**
     * sets the phase of the game.
     * @param ended true if the game is going to end, else false.
     */
    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    /**
     * Starts on a new thread, the CLI or the GUI
     * and remains listening from server messages
     */
    public void start(){
        if(run){
            if(gui){
                GUI.setClient(this);
                GUI.start();
                gameView = new GameGUI();
            }else {
                cli = new CLI(this);
                gameView = new GameCLI();
                new Thread(cli).start();
            }
            try {
                clientSocket.setSoTimeout(30 *  1000);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        String line = null;
        while(run){
            try {
                line = in.readLine();
                if(line == null){
                    closeSocket();
                    if(!isEnded)gameView.handleCrash();
                    break;
                }
                MessageFromServer message = convert.fromJson(line, MessageFromServer.class);
                message.activateMessage(this);
                if (message instanceof NicknameAccepted) {
                    nickname = ((NicknameAccepted) message).getNickname();
                    if (!gui)
                        cli.setNickname(nickname);
                }
            } catch (SocketTimeoutException e) {
                updateTimer();
            } catch (IOException e) {
                closeSocket();
                if(!isEnded)gameView.handleCrash();
                break;
            }
        }
    }

    /**
     * sends the specified message to the server.
     * @param message specified MessageToServer to send to the server.
     */
    public void send(MessageToServer message){
        String outMessage = convert.toJson(message);
        if(clientSocket != null) out.println(outMessage);
        else gameView.dumpMessage("There is no connection with the server.");
    }

    /**
     * Starts the Client
     * @param args 3 argument needed:
     *             + Server Ip Address
     *             + Server Port
     *             + Mode: cli/gui
     */
    public static void main(String[] args) {
        Client client = new Client(args[0], Integer.parseInt(args[1]), args[2]);
        client.start();
    }


    /**
     * gives the gameView class of the current game.
     * @return the gameView class of the current game.
     */
    public GameView getGameView() {
        return gameView;
    }

    /**
     * saves the nicknames of the opponents and initializes the player's views
     * @param nicknames array list of strings containing the nicknames of the players that are playing the current game.
     */
    public void setNicknames(ArrayList<String> nicknames) {
        this.nicknames = new ArrayList<>(nicknames);
        gameView.setPlayers(nicknames);
    }

    /**
     * gives the nickname chosen by this client.
     * @return a string containing the nickname chosen by this client.
     */
    public String getNickname() {
        return nickname;
    }


    /**
     * gives all the players of the game
     * @return arrayList of nicknames ordered by the turns order.
     */
    public ArrayList<String> getTurns(){
        return new ArrayList<>(nicknames);
    }


    /**
     * close the connection with the client.
     */
    private void closeSocket(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
        clientSocket = null;
    }

    /**
     * stops the game and closes the connection.
     */
    public void stopTheGame(){
        run = false;
        if (clientSocket != null )closeSocket();
    }

    /**
     * checks if the client is still running
     * @return true if the client is still running, else false.
     */
    public boolean isRunning(){
        return run;
    }

    /**
     * checks if 30 seconds are passed, if so, then it sends a ping to server.
     */
    private void updateTimer(){
        timer += 1;
        if(timer == 10) {
            send(new PingFromClient());
            timer = 0;
        }
    }
}
