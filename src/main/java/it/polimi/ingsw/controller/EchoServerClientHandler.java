package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServerClientHandler implements Runnable {
    private String nickname;
    private Socket socket;
    private Controller controller;
    private boolean isMyTurn;
    private boolean isInGame;
    Scanner in;
    PrintWriter out;
    Gson convert;

    public EchoServerClientHandler(Socket socket) {
        this.socket = socket;
        isMyTurn = true;
        isInGame = false;
        nickname = null;
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        convert = new Gson();
    }

    private boolean login(){
        while(true){
            try{
                String line = in.nextLine();
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
            }
        }
        return true;
    }

    public void send(String command, ArrayList<String> params){
        Message mess = new Message(command, params);
        String outMess = convert.toJson(mess);
        out.println(outMess);
        out.flush();
    }

    public void sendSimple(String c, String p){
        ArrayList<String> params = new ArrayList<String>();
        params.add(p);
        send(c, params);
    }

    public void sendError(String e){
        ArrayList<String> params = new ArrayList<String>();
        params.add(e);
        send("error", params);
    }

    private void closeSocket(){
        in.close();
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
                    String line = in.nextLine();
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

    private void play(){

    }

    public void run() {
        //if the client is crashed during login phase
        if (!login()) return;
        while (true) {
            try {
                String line = in.nextLine();
                if (isInGame) {

                }
                //TODO: continua con il primo turno
            } catch (NoSuchElementException e) {
                MultiEchoServer.handleCrash(this);
                break;
            }

            String line = in.nextLine();
            Message message = convert.fromJson(line, Message.class);
            if (isMyTurn) {

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
}

class CrashException extends Exception {
}
