package it.polimi.ingsw.server;

import it.polimi.ingsw.server.MessageToClient.CrashedPlayer;
import it.polimi.ingsw.server.MessageToClient.JoinPlayer;
import it.polimi.ingsw.server.MessageToClient.OkRoom;
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
public class Server {
    private static String ip;
    private static int port;
    //private Controller controller;
    private static Map<String, ClientHandler> nicknames = new HashMap<>();
    private static ArrayList<WaitingRoom> waitingRooms = new ArrayList<>();

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.nicknames = new HashMap<>();
        this.waitingRooms = new ArrayList<>();
    }

    public static ClientHandler getClient(String nickname){
        if(nickname != null && nicknames.containsKey(nickname)){
            return nicknames.get(nickname);
        }
        return null;
    }

    /**
     * Tries to join the specified player to a waiting room.
     * @param nickname nickname of the new client.
     * @return false if there are no waiting room available, else true.
     */
    public static synchronized boolean addToWaitingRoom(String nickname) {
        if(waitingRooms.isEmpty()) return false;
        ArrayList<Identity> idS = waitingRooms.get(0).getWaitingUsers();
        ArrayList<String> players = new ArrayList<>();
        for(Identity i: idS){
            players.add(i.getNickname());
            ClientHandler client = nicknames.get(i.getNickname());
            client.send(new JoinPlayer(nickname));
        }
        players.add(nickname);
        nicknames.get(nickname).send(new OkRoom("joined", waitingRooms.get(0).getGameMode(), players));
        System.out.println(nickname + " joined a waiting room");
        waitingRooms.get(0).addUser(nickname);
        return true;
    }

    /**
     * creates a new waiting room of the specified size and adds the specified client as a host.
     * @param nickname nickname of the client that creates the waiting room.
     * @param mode size of the waiting room.
     */
    public static void newWaitingRoom(String nickname, int mode){
        WaitingRoom newWaiting = new WaitingRoom(mode);
        waitingRooms.add(newWaiting);
        ArrayList<String> players = new ArrayList<>();
        players.add(nickname);
        nicknames.get(nickname).send(new OkRoom("created", mode, players));
        System.out.println(nickname + " created a new waiting room: size " + mode);
        newWaiting.addUser(nickname);
    }

    /**
     * deletes a waiting room.
     * @param waitingRoom specified waiting room to delete.
     */
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
    public static synchronized boolean addNickname(String nickname, ClientHandler echoServerClientHandler) {
        if(nicknames.containsKey(nickname)){
            //find out whether the already existing client is still connected
            ClientHandler oppositeClient = nicknames.get(nickname);
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
     * @param client references to ClientHandler
     */
    public static void handleCrash(ClientHandler client){
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
                    ClientHandler waitingClient = nicknames.get(i.getNickname());
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
                        ClientHandler waitingClient = nicknames.get(i.getNickname());
                        waitingClient.send(new CrashedPlayer(client.getNickname()));
                    }
                }
                System.out.println(client.getNickname() + " has left the game");
            }
        }
    }

    /**
     * make the server able to accept new connections from the clients.
     */
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
                ClientHandler currentClientHandler = new ClientHandler(socket);
                executor.submit(currentClientHandler);
//                controller.addClient(currentClientHandler);
            } catch(IOException e) {
                break; //here if the server socket is closed
            }
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        Server echoServer = new Server(args[0], Integer.parseInt(args[1]));
        echoServer.startServer();
    }
}