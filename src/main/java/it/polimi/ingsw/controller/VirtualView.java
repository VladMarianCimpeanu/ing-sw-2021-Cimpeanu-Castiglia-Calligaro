package it.polimi.ingsw.controller;

import it.polimi.ingsw.MessageToClient.Updates.*;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.market.Marble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VirtualView {
    private Controller controller;

    public VirtualView(Controller controller) {
        if(controller == null) return;
        this.controller = controller;
        //TODO: subscribe model classes
        controller.getGame().getDevelopmentCardSet().subscribe(this);
        controller.getGame().getFaithPath().subscribe(this);
        controller.getGame().getMarket().subscribe(this);
        for(Player p: controller.getGame().getPlayers()){
            p.subscribe(this);
            p.getDashboard().getWarehouseDepot().subscribe(this);
            p.getDashboard().getStrongbox().subscribe(this);
        }
    }

    public void updateMarketColumn(Marble[] newColumn, Marble outerMarble, int colPosition){
        ArrayList<String> col = new ArrayList<>();
        for(Marble m:newColumn)
            col.add(m.toString());
        String outer = outerMarble.toString();
        controller.sendBroadcast(new UpdateMarketColumn(col, outer, colPosition));
    }

    public void updateMarketRow(Marble[] newRow, Marble outerMarble, int rowPosition){
        ArrayList<String> row = new ArrayList<>();
        for(Marble m:newRow)
            row.add(m.toString());
        String outer = outerMarble.toString();
        controller.sendBroadcast(new UpdateMarketRow(row, outer, rowPosition));
    }

    public void updateStrongbox(Map<Resource, Integer> resources){
        controller.sendBroadcast(new UpdateStrongbox(controller.getCurrentPlayer().getNickName(), resources));
    }

    public void updateLastProduced(Map<Resource, Integer> resources){
        controller.sendBroadcast(new UpdateLastProduced(controller.getCurrentPlayer().getNickName(), resources));
    }
    //TODO
    public void updateWarehouseDepot(int shelf, Resource resource, int quantity){
        controller.sendBroadcast(new UpdateWarehouseDepot(controller.getCurrentPlayer().getNickName(), shelf, resource, quantity));
    }

    public void updateMeetingPope(Map<Player, Integer> deltaVictoryPoints){
        Map<String, Integer> delta = new HashMap<>();
        for(Player p: deltaVictoryPoints.keySet())
            delta.put(p.getNickName(), deltaVictoryPoints.get(p));
        controller.sendBroadcast(new UpdateMeetingPope(delta));
    }

    public void updateFaithPath(Map<Player, Integer> newPositions){
        Map<String, Integer> newPos = new HashMap<>();
        for(Player p: newPositions.keySet())
            newPos.put(p.getNickName(), newPositions.get(p));
        controller.sendBroadcast(new UpdateFaithpath(newPos));
    }

    public void updateExtraSlot(Resource resource, int id, int quantity){
        controller.sendBroadcast(new UpdateExtraSlot(controller.getCurrentPlayer().getNickName(), resource, id, quantity));
    }

    public void updateDiscardLeaderCard(int id){
        controller.sendBroadcast(new UpdateDiscardLeaderCard(controller.getCurrentPlayer().getNickName(), id));
    }

    public void updateDevDeck(int position, int id){
        controller.sendBroadcast(new UpdateDevDeck(controller.getCurrentPlayer().getNickName(), position, id));
    }

    public void updateDevCardDrawn(Color color, int level, int idNewCard){
        controller.sendBroadcast(new UpdateDevCardDrawn(color, level, idNewCard));
    }

    public void updateActiveLeaderCard(int id){
        controller.sendBroadcast(new UpdateActiveLeaderCard(controller.getCurrentPlayer().getNickName(), id));
    }
}
