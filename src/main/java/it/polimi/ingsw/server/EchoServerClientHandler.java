package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import it.polimi.ingsw.server.MessageFromClient.*;
import it.polimi.ingsw.server.MessageToClient.*;
import it.polimi.ingsw.server.MessageToClient.Error;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Handler of the client.
 * It converts all the messages from client in actions on the controller.
 */
public class EchoServerClientHandler implements Runnable {
    private String nickname;
    private Socket socket;
    private Controller controller;
    private boolean isMyTurn;
    private boolean isInGame;
    private RuntimeTypeAdapterFactory<MessageFromClient> shapeAdapterFactory;
    private BufferedReader in;
    private PrintWriter out;
    private Gson convert;

    public EchoServerClientHandler(Socket socket) {
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

    public void sendError(ErrorMessage error) {
        String outMessage = convert.toJson(new Error(error));
        out.println(outMessage);
        out.flush();
    }

    private void closeSocket(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                    if(MultiEchoServer.addNickname(nick, this)){
                        this.nickname = nick;
                        send(new NicknameAccepted(nick));
                        if(!MultiEchoServer.addToWaitingRoom(nick)) {
                            int mode = requireMode();
                            MultiEchoServer.newWaitingRoom(nick, mode);
                        }
                        break;
                    }else{
                        //when disconnected player tries to rejoin the game
                        if(isInGame){
                            this.nickname = nick;
                            isMyTurn = false;
                            send(new NicknameAccepted(nick));
                            controller.rejoinClient(this, nickname);
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
                MultiEchoServer.handleCrash(this);
                closeSocket();
                return false;
            } catch (CrashException e) {
                return false;
            }
        }
        return true;
    }

    private int requireMode() throws CrashException {
        try{
            send(new ModeRequest());
            while(true) {
                try{
                    String line = in.readLine();
                    if(line == null){
                        MultiEchoServer.removeNickname(nickname);
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
            MultiEchoServer.removeNickname(nickname);
            System.out.println("Player disconnected in login phase: " + nickname);
            //handle crash closing the socket
            closeSocket();
            throw new CrashException();
        }
    }

    public void run() {
        //if the client is crashed during login phase
        if (!login()) return;
//            is this part still needed, since we include the first turn in the state pattern
//            try {
//                String line = in.readLine();
//                if (isInGame) {
//
//                }
//
//            } catch (NoSuchElementException | IOException e) {
//                MultiEchoServer.handleCrash(this);
//                closeSocket();
//                return;
//            }
        while (true) {
            String line = null;
            try {
                line = in.readLine();
                if (line == null) {
                    //client crashed
                    MultiEchoServer.handleCrash(this);
                    //store the current state somewhere?
                    controller.getCurrentState().completeTurn();
                    if (isMyTurn)
                        controller.nextTurn();
                    break;
                }
            } catch (SocketTimeoutException e) {
                send(new Ping());
                continue;
            } catch (IOException e) {
                //client crashed
                MultiEchoServer.handleCrash(this);
                //store the current state somewhere?
                controller.getCurrentState().completeTurn();
                if (isMyTurn)
                    controller.nextTurn();
                break;
            }
            synchronized (controller) {
                MessageFromClient message = convert.fromJson(line, MessageFromClient.class);
                if (isMyTurn) {
                    System.out.println("[" + nickname + "]:" + message);
                    message.activate(controller);
                } else {
                    sendError(ErrorMessage.notYouTurn);
                    System.out.println("[" + nickname + "]:" + "tried to play in another turn");
                }
            }
            }
    }

    public void close(){
        closeSocket();
    }

    public boolean waitForPong(){
        try {
            String line = in.readLine();
            if(line == null) return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        isInGame = true;
    }

    public Controller getController() {
        return controller;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public String getNickname() {
        return nickname;
    }

    public void setSocketTimeOut(int time) throws SocketException {
        if(time < 0) socket.setSoTimeout(0);
        else socket.setSoTimeout(time);
    }
}

class CrashException extends Exception {
}
