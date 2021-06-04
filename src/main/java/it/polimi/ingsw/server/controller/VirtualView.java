package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.MessageToClient.ConvertedMarbles;
import it.polimi.ingsw.server.MessageToClient.Updates.*;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.market.Marble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is an observer of the model: when a model change occurs, Virtual view is notified and it generates a specific
 * a specific update message containing the changes to the clients.
 */
public class VirtualView {
    private Controller controller;

    public VirtualView(Controller controller) {
        if(controller == null) return;
        this.controller = controller;

        controller.getGame().subscribe(this);
        controller.getGame().getDevelopmentCardSet().subscribe(this);
        controller.getGame().getFaithPath().subscribe(this);
        controller.getGame().getMarket().subscribe(this);
        for(Player p: controller.getGame().getPlayers()){
            p.subscribe(this);
            p.getDashboard().getWarehouseDepot().subscribe(this);
            p.getDashboard().getStrongbox().subscribe(this);
        }
    }

    /**
     * This method is used to notify the virtualView after a column changes in the market grid.
     * @param newColumn array of Marbles that are in the selected column after the change.
     * @param outerMarble the outer marble obtained after the transformation of the grid.
     * @param colPosition index of the column changed
     */
    public void updateMarketColumn(Marble[] newColumn, Marble outerMarble, int colPosition){
        ArrayList<String> col = new ArrayList<>();
        for(Marble m:newColumn)
            col.add(m.toString());
        String outer = outerMarble.toString();
        controller.sendBroadcast(new UpdateMarketColumn(col, outer, colPosition));
    }

    /**
     * This method is used to notify the virtualView after a row changes in the market grid.
     * @param newRow array of Marbles that are in the selected row after the change.
     * @param outerMarble the outer marble obtained after the transformation of the grid.
     * @param rowPosition index of the row changed
     */
    public void updateMarketRow(Marble[] newRow, Marble outerMarble, int rowPosition){
        ArrayList<String> row = new ArrayList<>();
        for(Marble m:newRow)
            row.add(m.toString());
        String outer = outerMarble.toString();
        controller.sendBroadcast(new UpdateMarketRow(row, outer, rowPosition));
    }

    /**
     * This method is used to notify the virtual view about available resources in the strongbox:
     * resources just produced will not be considered here.
     * @param resources a map containing all the resources that changed with the last quantity registered.
     */
    public void updateStrongbox(Map<Resource, Integer> resources){
        controller.sendBroadcast(new UpdateStrongbox(controller.getCurrentPlayer().getNickName(), resources));
    }

    /**
     * This method is used to notify the virtual view about a production success.
     * @param resources Map of resources just produced, with the last quantity registered.
     */
    public void updateLastProduced(Map<Resource, Integer> resources){
        controller.sendBroadcast(new UpdateLastProduced(controller.getCurrentPlayer().getNickName(), resources));
    }

    /**
     * This method is used to notify the virtual view about a change in the warehouse depot
     * @param shelf specific shelf involved in the change.
     * @param resource new resource placed in the shelf
     * @param quantity new quantity contained in the shelf.
     */
    public void updateWarehouseDepot(int shelf, Resource resource, int quantity){
        controller.sendBroadcast(new UpdateWarehouseDepot(controller.getCurrentPlayer().getNickName(), shelf, resource, quantity));
    }

    /**
     * This method is used to notify the virtual view when a pope meeting occurs.
     * @param deltaVictoryPoints victory points earned by each player after the meeting pope.
     */
    public void updateMeetingPope(Map<Player, Integer> deltaVictoryPoints){
        Map<String, Integer> delta = new HashMap<>();
        for(Player p: deltaVictoryPoints.keySet())
            delta.put(p.getNickName(), deltaVictoryPoints.get(p));
        controller.sendBroadcast(new UpdateMeetingPope(delta));
    }

    /**
     * This method is used to notify the virtual view about a change of player positions in the faith path.
     * @param newPositions map of players that changed their place, with the updated positions.
     */
    public void updateFaithPath(Map<Player, Integer> newPositions){
        Map<String, Integer> newPos = new HashMap<>();
        for(Player p: newPositions.keySet())
            newPos.put(p.getNickName(), newPositions.get(p));
        controller.sendBroadcast(new UpdateFaithpath(newPos));
    }

    public void updateSingleFaithPath(Map<String, Integer> newPositions) {
        controller.sendMessage(new UpdateFaithpath(new HashMap<>(newPositions)));
    }

    /**
     * This method is used to notify the virtual view when an extra slot content changes.
     * @param resource resource of the extraSlot.
     * @param id id of the leader card that offers the extraSlot
     * @param quantity new quantity of the resources contained by the extra slot.
     */
    public void updateExtraSlot(Resource resource, int id, int quantity){
        controller.sendBroadcast(new UpdateExtraSlot(controller.getCurrentPlayer().getNickName(), resource, id, quantity));
    }

    /**
     * This method is used to notify the virtual view that a leader card is discarded.
     * @param id ID of the specific leader card discarded.
     */
    public void updateDiscardLeaderCard(int id){
        controller.sendBroadcast(new UpdateDiscardLeaderCard(controller.getCurrentPlayer().getNickName(), id));
    }

    /**
     * This method is used to notify the virtual view that a new development card is placed in the dashboard of the current player.
     * @param position specific deck that now contains the development card purchased.
     * @param id id of the new development card.
     */
    public void updateDevDeck(int position, int id){
        controller.sendBroadcast(new UpdateDevDeck(controller.getCurrentPlayer().getNickName(), position, id));
    }

    /**
     * This method is used to notify that a development card has been taken from the decks containing development cards to be bought.
     * @param color color of the card purchased.
     * @param level level of the card purchased.
     * @param idNewCard id of the development card purchased.
     */
    public void updateDevCardDrawn(Color color, int level, int idNewCard){
        controller.sendBroadcast(new UpdateDevCardDrawn(color, level, idNewCard));
    }

    /**
     * This method is used to notify the virtual view when a leader card is activated.
     * @param id ID of the leader card activated by the current player.
     */
    public void updateActiveLeaderCard(int id){
        controller.sendBroadcast(new UpdateActiveLeaderCard(controller.getCurrentPlayer().getNickName(), id));
    }

    /**
     * this method is used to notify the virtual view when an action token is drawn
     * @param id id of the token drawn.
     */
    public void updateDrawToken(int id){
        controller.sendBroadcast(new UpdateDrawToken(id));
    }


    /**
     * This method is used to notify the virtual view about the number of white marbles that need to be converted.
     * @param remaining remaining white marbles.
     * @param id id of the leader card selected.
     */
    public void updateStrategies(int remaining, int id){ controller.sendMessage(new UpdateStrategyBuffer(remaining, id));}

    /**
     * notify the virtual view about the remaining resources to take from the market.
     * @param resources remaining resources.
     */
    public void updateConvertedMarbles(ArrayList<Benefit> resources){
        ArrayList<Resource> converted = new ArrayList<>();
        for(Benefit res : resources){
            converted.add((Resource) res);
        }
        controller.sendMessage(new ConvertedMarbles(converted));
    }
}
