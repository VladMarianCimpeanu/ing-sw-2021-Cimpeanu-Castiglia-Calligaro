package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.MessageFromServer.*;
import it.polimi.ingsw.client.MessageFromServer.Error;
import it.polimi.ingsw.client.MessageFromServer.Rejoin.*;
import it.polimi.ingsw.client.MessageFromServer.Updates.*;
import it.polimi.ingsw.client.MessageToServer.MessageToServer;
import it.polimi.ingsw.client.modelLight.CLI.DepotCLI;
import it.polimi.ingsw.client.modelLight.CLI.GameCLI;
import it.polimi.ingsw.client.modelLight.CLI.MarketCLI;
import it.polimi.ingsw.client.modelLight.CLI.StrongboxCLI;
import it.polimi.ingsw.client.modelLight.GameView;
import it.polimi.ingsw.client.modelLight.PlayerView;
import it.polimi.ingsw.server.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.server.model.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.client.Resource.*;


public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private RuntimeTypeAdapterFactory<MessageFromServer> shapeAdapterFactory;
    private Gson convert;
    private CLI cli;
    private String nickname;
    private PlayerView me;
    private GameView gameView;
    //nicknames in order
    private ArrayList<String> nicknames;

    public Client(String ip, int port){
        try{
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        shapeAdapterFactory.registerSubtype(Ok.class, "Ok");
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

        convert = new GsonBuilder().registerTypeAdapterFactory(shapeAdapterFactory).create();
    }

    public void start(){
        cli = new CLI(this);
        gameView = new GameCLI();
        new Thread(cli).start();

        String line = null;
        while(true){
            try {
                line = in.readLine();
                MessageFromServer message = convert.fromJson(line, MessageFromServer.class);
                message.activateMessage(this);

                //TODO: implement inside activateMessage() of NicknameAccepted
                if(message instanceof NicknameAccepted){
                    nickname = ((NicknameAccepted) message).getNickname();
                    cli.setNickname(nickname);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void send(MessageToServer message){
        String outMessage = convert.toJson(message);
        out.println(outMessage);
    }

    public static void main(String[] args) {
//        DepotCLI depot = new DepotCLI();
//        depot.update(1, COIN, 1);
//        depot.update(2, SERVANT, 2);
//        depot.update(3, STONE, 3);
//        depot.show();
        Client client = new Client(args[0], Integer.parseInt(args[1]));
        client.start();
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setNicknames(ArrayList<String> nicknames) {
        this.nicknames = new ArrayList<>(nicknames);
        gameView.setPlayers(nicknames);
    }

    public String getNickname() {
        return nickname;
    }
}
