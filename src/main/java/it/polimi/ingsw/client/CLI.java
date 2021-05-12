package it.polimi.ingsw.client;

import it.polimi.ingsw.client.MessageToServer.*;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.benefit.Resource;

import java.io.PrintWriter;
import java.util.ArrayList;
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
        while(true){
            String line = in.nextLine();
            String[] command = line.split(" ");
            try {
                switch (command.length) {
                    case 2:
                        switch (command[0]) {
                            case "login":
                                client.send(new Login(command[1]));
                                break;
                            case "mode":
                                client.send(new Mode(Integer.parseInt(command[1])));
                                break;
                            case "strategy":
                                client.send(new Strategy(Integer.parseInt(command[1])));
                                break;
                            case "place":
                                client.send(new PlaceCard(Integer.parseInt(command[1])));
                                break;
                            case "activateLeader":
                                client.send(new ActivateLeaderCard(Integer.parseInt(command[1])));
                                break;
                            case "discardLeader":
                                client.send(new DiscardLeaderCard(Integer.parseInt(command[1])));
                                break;
                            default:
                                out.println("Unexpected command.");
                        }
                        break;
                    case 3:
                        switch (command[0]) {
                            //change id with index (1... 4)
                            case "keep":
                                client.send(new KeepLeaderCard(Integer.parseInt(command[1]), Integer.parseInt(command[2]), nickname));
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
                                client.send(new BuyDevCard(Integer.parseInt(command[1]), Color.valueOf((command[2]))));
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
                                client.send(new BuyDevCard(
                                        Integer.parseInt(command[1]),
                                        Color.valueOf((command[2])),
                                        Integer.parseInt(command[3])
                                        ));
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
                                client.send(new BuyDevCard(
                                        Integer.parseInt(command[1]),
                                        Color.valueOf((command[2])),
                                        Integer.parseInt(command[3]),
                                        Integer.parseInt(command[4])
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
}