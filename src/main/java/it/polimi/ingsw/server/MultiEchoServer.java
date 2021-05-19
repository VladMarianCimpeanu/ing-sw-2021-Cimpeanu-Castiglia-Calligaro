package it.polimi.ingsw.server;

import it.polimi.ingsw.server.MessageToClient.CrashedPlayer;
import it.polimi.ingsw.server.MessageToClient.JoinPlayer;
import it.polimi.ingsw.server.MessageToClient.Ok;
import it.polimi.ingsw.server.MessageToClient.Ping;
import it.polimi.ingsw.server.model.Identity;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.WaitingRoom;
import it.polimi.ingsw.server.model.exceptions.EmptyException;
import it.polimi.ingsw.server.model.exceptions.NoSuchUserException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server. It requires 2 params to be executed: the ip address and the port of the server.
 */
public class MultiEchoServer {
    private static String ip;
    private static int port;
    //private Controller controller;
    private static Map<String, EchoServerClientHandler> nicknames = new HashMap<>();
    private static ArrayList<WaitingRoom> waitingRooms = new ArrayList<>();

    public MultiEchoServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.nicknames = new HashMap<>();
        this.waitingRooms = new ArrayList<>();
    }

    public static EchoServerClientHandler getClient(String nickname){
        if(nickname != null && nicknames.containsKey(nickname)){
            return nicknames.get(nickname);
        }
        return null;
    }

    public static synchronized boolean addToWaitingRoom(String nickname) {
        if(waitingRooms.isEmpty()) return false;
        ArrayList<Identity> idS = waitingRooms.get(0).getWaitingUsers();
        nicknames.get(nickname).send(new Ok("joined"));
        for(Identity i: idS){
            EchoServerClientHandler client = nicknames.get(i.getNickname());
            client.send(new JoinPlayer(nickname));
        }
        System.out.println(nickname + " joined a waiting room");
        waitingRooms.get(0).addUser(nickname);
        return true;
    }

    public static void newWaitingRoom(String nickname, int mode){
        WaitingRoom newWaiting = new WaitingRoom(mode);
        waitingRooms.add(newWaiting);
        nicknames.get(nickname).send(new Ok("created"));
        System.out.println(nickname + " created a new waiting room: size " + mode);
        newWaiting.addUser(nickname);
    }

    public static void removeWaitingRoom(WaitingRoom waitingRoom){
        if(waitingRoom != null && waitingRooms.contains(waitingRoom)){
            waitingRooms.remove(waitingRoom);
            System.out.println("Removed a waiting room");
        }
    }

    /**
     * add a nickName and a handler
     * @return false if the nickname is already used
     */
    public static synchronized boolean addNickname(String nickname, EchoServerClientHandler echoServerClientHandler) {
        if(nicknames.containsKey(nickname)){
            //find out whether the already existing client is still connected
            EchoServerClientHandler oppositeClient = nicknames.get(nickname);
            if(oppositeClient.isInGame()) {
                if (oppositeClient.getController().getPlayer(nickname).isOnline()) {
                    oppositeClient.send(new Ping());
                } else
                    echoServerClientHandler.setController(oppositeClient.getController());
            }else{
                oppositeClient.send(new Ping());
            }
            return false;
        }
        nicknames.put(nickname, echoServerClientHandler);
        System.out.println(nickname + " is ready to play");
        return true;
    }

    /**
     * remove a nickname from the list
     */
    public static void removeNickname(String nickname){
        if(nicknames.containsKey(nickname)){
            nicknames.remove(nickname);
        }
    }

    /**
     * called by clientHandler when crashes
     * @param client references to EchoServerClientHandler
     */
    public static void handleCrash(EchoServerClientHandler client){
        if(client != null){
            if(!client.isInGame()){
                nicknames.remove(client.getNickname());
                WaitingRoom containingClient = null;
                for(WaitingRoom w: waitingRooms){
                    if(w.contains(client.getNickname())){
                        containingClient = w;
                        try {
                            w.removeUser(client.getNickname());
                        } catch (NoSuchUserException | EmptyException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //Send an update to all other waiting clients
                ArrayList<Identity> idS = containingClient.getWaitingUsers();
                for(Identity i: idS){
                    EchoServerClientHandler waitingClient = nicknames.get(i.getNickname());
                    waitingClient.send(new CrashedPlayer(client.getNickname()));
                }
                System.out.println(client.getNickname() + " crashed");
            }else{
                //set online flag in client related Identity to false
                client.getController().getIdentity(client.getNickname()).setOnline(false);
                ArrayList<Player> players = client.getController().getPlayers();
                for(Player p: players){
                    Identity i = p.getIdentity();
                    if(i.isOnline()){
                        EchoServerClientHandler waitingClient = nicknames.get(i.getNickname());
                        waitingClient.send(new CrashedPlayer(client.getNickname()));
                    }
                }
                System.out.println(client.getNickname() + " has left the game");
            }
        }
    }

    public static void startServer() {
        //to set an address != localhost
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port,50, addr);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // ip address or port already used
            return;
        }
        System.out.println("Server ready");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New connection accepted");
//                if(controller == null || controller.isFull()) controller = new Controller();
                EchoServerClientHandler currentClientHandler = new EchoServerClientHandler(socket);
                executor.submit(currentClientHandler);
//                controller.addClient(currentClientHandler);
            } catch(IOException e) {
                break; // Entrerei qui se serverSocket venisse chiuso
            }
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        MultiEchoServer echoServer = new MultiEchoServer(args[0], Integer.parseInt(args[1]));
        echoServer.startServer();
    }
}