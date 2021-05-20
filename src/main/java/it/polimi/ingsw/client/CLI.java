package it.polimi.ingsw.client;

import it.polimi.ingsw.client.MessageToServer.*;
import it.polimi.ingsw.client.modelLight.PlayerView;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class CLI implements Runnable {
    private Scanner in;
    private PrintWriter out;
    private Client client;
    private String nickname;

    public CLI(Client client) {
        this.client = client;
        this.in = new Scanner(System.in);
        this.out = new PrintWriter(System.out, true);
    }

    @Override
    public void run() {
        Welcome.dump();

        while(true){
            String line = in.nextLine();
            String[] command = line.split(" ");
            try {
                switch (command.length) {
                    case 1:
                        switch (command[0]) {
                            case "pass":
                                client.send(new EndTurn());
                                break;
                            case "activateProduction":
                                client.send(new ActivateProduction());
                                break;
                            case "produceBase":
                                client.send(new ActivateBaseProduction());
                                break;
                            case "cheat":
                                client.send(new CheatResource());
                                break;
                            case "help":
                                readHelp();
                                break;
                            default:
                                out.println("Unexpected command.");
                        }
                        break;
                    case 2:
                        switch (command[0]) {
                            case "login":
                                client.send(new Login(command[1]));
                                break;
                            case "mode":
                                client.send(new Mode(Integer.parseInt(command[1])));
                                break;
                            case "strategy":
                            int id = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[1]));
                            client.send(new Strategy(id));
                                break;
                            case "place":
                                client.send(new PlaceCard(Integer.parseInt(command[1])));
                                break;
                            case "activateLeader":
                                id = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[1]));
                                client.send(new ActivateLeaderCard(id));
                                break;
                            case "discardLeader":
                                id = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[1]));
                                client.send(new DiscardLeaderCard(id));
                                break;
                            case "cheat":
                                client.send(new CheatFaith(Integer.parseInt(command[1])));
                                break;
                            case "discard":
                                client.send(new DiscardResource(Resource.valueOf(command[1].toUpperCase())));
                                break;
                            case "produce":
                                client.send(new ActivateCardProduction(Integer.parseInt(command[1].toUpperCase())));
                                break;
                            case "produceExtra":
                            id = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[1]));
                            client.send(new ActivateExtraProduction(id));
                                break;
                            case "resOut":
                                client.send(new SelResOut(Resource.valueOf(command[1].toUpperCase())));
                                break;
                            case "show":
                                switch (command[1]){
                                    case "faith":
                                        client.getGameView().getFaithPathView().show();
                                        break;
                                    case "cards":
                                        client.getGameView().getCards().show();
                                        break;
                                    case "leadercards":
                                        client.getGameView().getPlayer(nickname).getLeaderCards().show();
                                        break;
                                    case "market":
                                        client.getGameView().getMarket().show();
                                        break;
                                    case "me":
                                        PlayerView player = client.getGameView().getPlayer(nickname);
                                        player.getDecks().show();
                                        player.getStrongbox().show();
                                        player.getDepot().show();
                                        player.getLeaderCards().show();
                                        break;
                                    case "resources":
                                        it.polimi.ingsw.client.Resource.showLegend();
                                        break;
                                    default:
                                        if(client.getGameView().getPlayer(command[1]) != null) {
                                            PlayerView opponent = client.getGameView().getPlayer(command[1]);
                                            opponent.getDecks().show();
                                            opponent.getStrongbox().show();
                                            opponent.getDepot().show();
                                            opponent.getLeaderCards().show();
                                        } else out.println("Unexpected command.");
                                        break;
                                }
                                break;
                            default:
                                out.println("Unexpected command.");
                        }
                        break;
                    case 3:
                        switch (command[0]) {
                            case "keep":
                                int id1 = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[1]));
                                int id2 = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[2]));
                                client.send(new KeepLeaderCard(id1, id2, nickname));
                                break;
                            case "market":
                                client.send(new Market(command[1], Integer.parseInt(command[2])));
                                break;
                            case "put":
                                client.send(new PutResPos(
                                        Resource.valueOf(command[1].toUpperCase()),
                                        command[2],
                                        0
                                ));
                                break;
                            case "buy":
                                client.send(new BuyDevCard(Integer.parseInt(command[1]), Color.valueOf((command[2].toUpperCase()))));
                                break;
                            case "take":
                                client.send(new TakeResPos(
                                        Resource.valueOf(command[1].toUpperCase()),
                                        command[2]
                                ));
                                break;
                            case "choose":
                                client.send(new ChooseFirstResources(
                                        Resource.valueOf(command[1].toUpperCase()),
                                        Integer.parseInt(command[2]),
                                        nickname
                                ));
                                break;
                            case "resIn":
                                client.send(new SelResIn(Resource.valueOf(command[1].toUpperCase()), Resource.valueOf(command[2].toUpperCase())));
                                break;
                            case "move":
                                //move from to
                                client.send(new MoveResource(Integer.parseInt(command[1]), Integer.parseInt(command[2])));
                                break;
                            default:
                                out.println("Unexpected command.");
                        }
                        break;
                    case 4:
                        switch (command[0]) {
                            case "put":
                                client.send(new PutResPos(
                                        Resource.valueOf(command[1].toUpperCase()),
                                        command[2],
                                        Integer.parseInt(command[3])
                                ));
                                break;
                            case "buy":
                                int id = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[3]));
                                client.send(new BuyDevCard(
                                        Integer.parseInt(command[1]),
                                        Color.valueOf((command[2].toUpperCase())),
                                        id
                                        ));
                                break;
                            case "moveFromExtra":
                                //move from(leaderId) to quantity
                                id = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[1]));
                                client.send(new MoveExtraToWarehouse(Integer.parseInt(command[2]), id, Integer.parseInt(command[3])));
                                break;
                            case "moveToExtra":
                                //move from to(leaderId) quantity
                                id = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[2]));
                                client.send(new MoveWarehouseToExtra(Integer.parseInt(command[1]), id, Integer.parseInt(command[3])));
                                break;
                            default:
                                out.println("Unexpected command.");
                        }
                        break;
                    case 5:
                        switch (command[0]) {
                            //change id with index (1... 4)
                            case "choose":
                                client.send(new ChooseFirstResources(
                                        Resource.valueOf(command[1].toUpperCase()),
                                        Resource.valueOf(command[3].toUpperCase()),
                                        Integer.parseInt(command[2]),
                                        Integer.parseInt(command[4]),
                                        nickname
                                ));
                                break;
                                //choose servant 2 coin 1
                            case "buy":
                                int id1 = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[3]));
                                int id2 = client.getGameView().getPlayer(nickname).getLeaderCards().getIDfromIndex(Integer.parseInt(command[4]));
                                client.send(new BuyDevCard(
                                        Integer.parseInt(command[1]),
                                        Color.valueOf((command[2].toUpperCase())),
                                        id1,
                                        id2
                                ));
                                break;
                            default:
                                out.println("Unexpected command.");
                        }
                        break;
                    default:
                        out.println("Unexpected command.");
                }
            }catch(Exception e){
                out.println("Unexpected command.");
            }
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private void readHelp(){
        Path path = Paths.get("src/resources/help.txt");
        try {
            for(String line: Files.readAllLines(path))
                System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
