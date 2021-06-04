package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.server.MessageFromClient.*;
import it.polimi.ingsw.server.MessageToClient.*;
import it.polimi.ingsw.server.MessageToClient.Error;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.model.exceptions.GameEndedException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Handler of the client.
 * It converts all the messages from client in actions on the controller.
 */
public class ClientHandler implements Runnable {
    private String nickname;
    private Socket socket;
    private Controller controller;
    private boolean isMyTurn;
    private boolean isInGame;
    private RuntimeTypeAdapterFactory<MessageFromClient> shapeAdapterFactory;
    private BufferedReader in;
    private PrintWriter out;
    private Gson convert;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        isMyTurn = true;
        isInGame = false;
        nickname = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        shapeAdapterFactory = RuntimeTypeAdapterFactory.of(MessageFromClient.class, "type");
        shapeAdapterFactory.registerSubtype(ActivateBaseProduction.class, "ActivateBaseProduction");
        shapeAdapterFactory.registerSubtype(ActivateCardProduction.class, "ActivateCardProduction");
        shapeAdapterFactory.registerSubtype(ActivateExtraProduction.class, "ActivateExtraProduction");
        shapeAdapterFactory.registerSubtype(ActivateLeaderCard.class, "ActivateLeaderCard");
        shapeAdapterFactory.registerSubtype(ActivateProduction.class, "ActivateProduction");
        shapeAdapterFactory.registerSubtype(BuyDevCard.class, "BuyDevCard");
        shapeAdapterFactory.registerSubtype(ChooseFirstResources.class, "ChooseFirstResources");
        shapeAdapterFactory.registerSubtype(DiscardLeaderCard.class, "DiscardLeaderCard");
        shapeAdapterFactory.registerSubtype(DiscardResource.class, "DiscardResource");
        shapeAdapterFactory.registerSubtype(EndTurn.class, "EndTurn");
        shapeAdapterFactory.registerSubtype(KeepLeaderCard.class, "KeepLeaderCard");
        shapeAdapterFactory.registerSubtype(Login.class, "Login");
        shapeAdapterFactory.registerSubtype(Market.class, "Market");
        shapeAdapterFactory.registerSubtype(Mode.class, "Mode");
        shapeAdapterFactory.registerSubtype(MoveResource.class, "MoveResource");
        shapeAdapterFactory.registerSubtype(PlaceCard.class, "PlaceCard");
        shapeAdapterFactory.registerSubtype(PutResPos.class, "PutResPos");
        shapeAdapterFactory.registerSubtype(SelResIn.class, "SelResIn");
        shapeAdapterFactory.registerSubtype(SelResOut.class, "SelResOut");
        shapeAdapterFactory.registerSubtype(Strategy.class, "Strategy");
        shapeAdapterFactory.registerSubtype(TakeResPos.class, "TakeResPos");
        shapeAdapterFactory.registerSubtype(MoveWarehouseToExtra.class, "MoveWarehouseToExtra");
        shapeAdapterFactory.registerSubtype(MoveExtraToWarehouse.class, "MoveExtraToWarehouse");
        shapeAdapterFactory.registerSubtype(CheatResource.class, "CheatResource");
        shapeAdapterFactory.registerSubtype(CheatFaith.class, "CheatFaith");
        shapeAdapterFactory.registerSubtype(PingFromClient.class, "PingFromClient");

        convert = new GsonBuilder().registerTypeAdapterFactory(shapeAdapterFactory).create();
    }

    /**
     * Sends the object to the client
     * @param message specific message MessageToClient.
     */
    public void send(MessageToClient message){
        String outMessage = convert.toJson(message);
        out.println(outMessage);
        out.flush();
    }

    /**
     * Sends an error message to the client.
     * @param error specified error message to be sent
     */
    public void sendError(ErrorMessage error) {
        String outMessage = convert.toJson(new Error(error));
        out.println(outMessage);
        out.flush();
    }

    /**
     * close the connection with the client.
     */
    private void closeSocket(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    /**
     * login process.
     * @return true if the login process ends with success.
     */
    private boolean login(){
        while(true){
            try{
                String line = in.readLine();
                if(line == null){
                    System.out.println("Player disconnected in login phase");
                    closeSocket();
                    return false;
                }
                MessageFromClient message = convert.fromJson(line, MessageFromClient.class);
                System.out.println("Received: " + message);
                if(message instanceof Login) {
                    String nick = ((Login) message).getNickname();
                    if(nick == null || nick.equals("") || nick.equals("blackCross")){
                        sendError(ErrorMessage.invalidNickname);
                        System.out.println(nick + " is not permitted");
                        continue;
                    }
                    if(Server.addNickname(nick, this)){
                        this.nickname = nick;
                        send(new NicknameAccepted(nick));
                        if(!Server.addToWaitingRoom(nick)) {
                            int mode = requireMode();
                            Server.newWaitingRoom(nick, mode);
                        }
                        break;
                    }else{
                        //when disconnected player tries to rejoin the game
                        if(isInGame){
                            this.nickname = nick;
                            if(controller.isFirstTurn())isMyTurn = true;
                            else isMyTurn = false;
                            send(new NicknameAccepted(nick));
                            controller.rejoinClient(this, nickname);
                            awakeController();
                            break;
                        }
                        sendError(ErrorMessage.usedNickname);
                        System.out.println("Error: requested nickname already used");
                    }
                }else {
                    sendError(ErrorMessage.expectedLogin);
                    System.out.println("Error: unexpected command");
                }
            }catch(JsonParseException e) {
                sendError(ErrorMessage.invalidJson);
                System.out.println("Error: wrong json format");
            }catch(IOException e){
                System.out.println("Player disconnected in login phase");
                Server.handleCrash(this);
                closeSocket();
                return false;
            } catch (CrashException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * requires the game mode to the client
     * @return integer representing the number of the players that will play at the game creation.
     * @throws CrashException if a player crashes during the mode request.
     */
    private int requireMode() throws CrashException {
        try{
            send(new ModeRequest());
            while(true) {
                try{
                    String line = in.readLine();
                    if(line == null){
                        Server.removeNickname(nickname);
                        System.out.println("Player disconnected in login phase: " + nickname);
                        //handle crash closing the socket
                        closeSocket();
                        throw new CrashException();
                    }
                    MessageFromClient message = convert.fromJson(line, MessageFromClient.class);
                    System.out.println("Received: " + message);
                    if(message instanceof Mode){
                        int mode = (((Mode) message).getMode());
                        if(mode >= 1 && mode <= 4) return mode;
                    }
                    sendError(ErrorMessage.invalidMode);
                } catch(JsonParseException e){
                    sendError(ErrorMessage.invalidJson);
                    System.out.println("Error: wrong json format");

            }
        }
        } catch (IOException e) {
            Server.removeNickname(nickname);
            System.out.println("Player disconnected in login phase: " + nickname);
            //handle crash closing the socket
            closeSocket();
            throw new CrashException();
        }
    }

    /**
     * reads and parses the messages from the client and communicates with the controller when needed.
     */
    public void run() {
        if (!login()) return;
        while (true) {
            String line = null;
            try {
                line = in.readLine();
                if (line == null) {
                    handleCrash();
                    break;
                }
            } catch (SocketTimeoutException e) {
                send(new Ping());
                continue;
            } catch (IOException e) {
                System.out.println("closing the socket");
                if(!socket.isClosed()) handleCrash();
                break;
            }
            synchronized (controller) {
                MessageFromClient message = convert.fromJson(line, MessageFromClient.class);
                if (isMyTurn || message.isPing()) {
                    System.out.println("[" + nickname + "]:" + message);
                    message.activate(controller);
                } else {
                    sendError(ErrorMessage.notYouTurn);
                    System.out.println("[" + nickname + "]:" + "tried to play in another turn");
                }
            }
        }
    }

    /**
     * @param myTurn true if it's the turn of the client, otherwise false.
     */
    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    /**
     * links the client to the current game
     * @param controller specified controller which points to the game played by this client.
     */
    public void setController(Controller controller) {
        this.controller = controller;
        isInGame = true;
    }

    /**
     * returns the controller linked with this client.
     * @return the controller linked with this client.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * @return true if the player is online, otherwise false.
     */
    public boolean isInGame() {
        return isInGame;
    }

    /**
     * gives the nickname associated with this client.
     * @return the nickname associated with this client.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * sets the timer to this socket
     * @param time specified time after which the socket will rise an exception if there is no message from the client.
     * @throws SocketException if there is no such socket.
     */
    public void setSocketTimeOut(int time) throws SocketException {
        if(time < 0) socket.setSoTimeout(0);
        else socket.setSoTimeout(time);
    }

    /**
     * notify the controller that at least one player is online.
     */
    private void awakeController(){
        synchronized (controller) {
            try {
                controller.notifyAll();
            } catch(IllegalMonitorStateException e){ }
        }
    }

    /**
     * sends the results of the game to the client.
     * closes the socket and removes the nickname from the server's nickname list: when a player will retry to join with
     * the nickname used by this client, he will join a new game.
     * @param points specified message with the information about the end of the game.
     */
    public void endConnection(GameEnded points){
        send(points);
        closeSocket();
        Server.removeNickname(nickname);
    }

    /**
     * closes the socket of this player.
     * If the player has crashed during his turn, it autocompletes his turn.
     */
    private void handleCrash(){
        Server.handleCrash(this);
        if (isMyTurn) {
            try {
                controller.getCurrentState().completeTurn();
            } catch (GameEndedException gameEndedException) {
                controller.setLastTurns();
            }
        }
        closeSocket();
    }
}
class CrashException extends Exception {
}
