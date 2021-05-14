package it.polimi.ingsw.client.MessageFromServer;

import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.market.Marble;

import java.util.ArrayList;

public class ConvertedMarbles extends MessageFromServer{
    private ArrayList<Resource> resources;

    @Override
    public void activateMessage() {
        System.out.println("Converted:\n" + resources);
    }
}