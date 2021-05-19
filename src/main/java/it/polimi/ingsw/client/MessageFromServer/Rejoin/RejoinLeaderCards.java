package it.polimi.ingsw.client.MessageFromServer.Rejoin;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.MessageFromServer.MessageFromServer;
import it.polimi.ingsw.client.modelLight.LeaderCardView;
import it.polimi.ingsw.server.model.leaderCards.LeaderCard;

import java.util.ArrayList;
import java.util.Map;

public class RejoinLeaderCards extends MessageFromServer {
    private String nickname;
    private Map<Integer, Boolean> active;


    @Override
    public void activateMessage(Client client) {
        ArrayList<Integer> ids = new ArrayList<>();
        for(int i: active.keySet())
            ids.add(i);
        client.getGameView().getPlayer(nickname).getLeaderCards().update(ids);
        for(int i: active.keySet())
            if(active.get(i))
                client.getGameView().getPlayer(nickname).getLeaderCards().activate(i);
    }
}
