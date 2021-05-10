package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.model.Multiplayer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServerClientHandler implements Runnable {
    private String nickname;
    private Socket socket;
    private Controller controller;
    private boolean isMyTurn;
    private boolean isInGame;
    BufferedReader in;
    PrintWriter out;
    Gson convert;

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
        convert = new Gson();
    }

    private boolean login(){
        while(true){
            try{
                String line = in.readLine();
                Message message = convert.fromJson(line, Message.class);
                System.out.println("Received: " + message);
                if(message.getCommand().equals("login")) {
                    String nick = message.getParams().get(0);
                    if(nick == null || nick.equals("")){
                        sendError("emptyNickname");
                        System.out.println(nick + " is not permitted");
                        continue;
                    }
                    if(MultiEchoServer.addNickname(nick, this)){
                        this.nickname = nick;
                        if(MultiEchoServer.addToWaitingRoom(nick)){
                            sendSimple("ok", "joined");
                        }else{
                            int mode = requireMode();
                            MultiEchoServer.newWaitingRoom(nick, mode);
                            sendSimple("ok", "created");
                        }
                        break;
                    }else{
                        if(true){

                            break;
                        }
                        sendError("usedNickname");
                        System.out.println("Error: requested nickname already used");
                    }
                }else {
                    sendError("expectedLogin");
                    System.out.println("Error: unexpected command");
                }
            }catch(JsonSyntaxException e){
                sendError("invalidJson");
                System.out.println("Error: wrong json format");
            }catch(NoSuchElementException e){
                System.out.println("Player disconnected in login phase");
                //handle crash closing the socket
                closeSocket();
                return false;
            } catch (CrashException e) {
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    @Deprecated
    public void send(String command, ArrayList<String> params){
        Message mess = new Message(command, params);
        String outMess = convert.toJson(mess);
        out.println(outMess);
        out.flush();
    }
    @Deprecated
    public void sendSimple(String c, String p){
        ArrayList<String> params = new ArrayList<String>();
        params.add(p);
        send(c, params);
    }
    @Deprecated
    public void sendError(String e){
        ArrayList<String> params = new ArrayList<String>();
        params.add(e);
        send("error", params);
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

    private int requireMode() throws CrashException {
        try{
            sendSimple("request","mode");
            while(true) {
                try{
                    String line = in.readLine();
                    Message message = convert.fromJson(line, Message.class);
                    if(message.getCommand().equals("mode")){
                        String modeString = (message.getParams().get(0));
                        try{
                            int mode = Integer.parseInt(modeString);
                            if(mode >= 1 && mode <= 4) return mode;
                        }catch(NumberFormatException e){
                            e.printStackTrace();
                        }
                    }
                    sendError("invalidMode");
                } catch(JsonSyntaxException e){
                    sendError("invalidJson");
                    System.out.println("Error: wrong json format");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(NoSuchElementException e){
            MultiEchoServer.removeNickname(nickname);
            System.out.println("Player disconnected in login phase: " + nickname);
            //handle crash closing the socket
            closeSocket();
            throw new CrashException();
        }
    }

    private void play(Message message){
    }

    public void run() {
        //if the client is crashed during login phase
        if (!login()) return;
            try {
                String line = in.readLine();
                if (isInGame) {

                }
                //TODO: continua con il primo turno
            } catch (NoSuchElementException | IOException e) {
                MultiEchoServer.handleCrash(this);
                closeSocket();
                return;
            }
        while (true) {
            String line = null;
            try {
                line = in.readLine();
                if(line == null){
                    //client crashed
                    MultiEchoServer.handleCrash(this);
                    //store the current state somewhere?
                    controller.nextTurn();
                }
            }catch(SocketTimeoutException e){
                sendSimple("Ping", "");
            }catch (IOException e){
                //client crashed
                MultiEchoServer.handleCrash(this);
                //store the current state somewhere?
                controller.nextTurn();
            }

            Message message = convert.fromJson(line, Message.class);
            if(message.getCommand().equals("EndGame")) break;
            if (isMyTurn) {
                play(message);
            } else {
                out.println("It isn't your turn!");
            }
        }
        closeSocket();
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
