package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.MessageToClient.Updates.UpdateFaithpath;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.market.Marble;

import java.util.HashMap;
import java.util.Map;

public class VirtualViewStub extends VirtualView {
    public VirtualViewStub() {
        super(null);
    }

    public VirtualViewStub(Game game){
        super(null);
        game.getDevelopmentCardSet().subscribe(this);
        game.getFaithPath().subscribe(this);
        game.getMarket().subscribe(this);
        for(Player p: game.getPlayers()){
            p.subscribe(this);
            p.getDashboard().getWarehouseDepot().subscribe(this);
            p.getDashboard().getStrongbox().subscribe(this);
        }
    }

    @Override
    public void updateActiveLeaderCard(int id) {
        System.out.println("update: activated Leader Card " + id);
    }

    @Override
    public void updateDevCardDrawn(Color color, int level, int idNewCard) {
        System.out.println("update devBoard " + color + " " + level + " " + idNewCard);
    }

    @Override
    public void updateDevDeck(int position, int id) {
        System.out.println("update devDeck " + position + " " + id);
    }

    @Override
    public void updateDiscardLeaderCard(int id) {
        System.out.println("update discarded Leader Card " + id);
    }

    @Override
    public void updateMarketColumn(Marble[] newColumn, Marble outerMarble, int colPosition) {
        System.out.println("update market:");
        System.out.println("Column " + colPosition + ": " + newColumn);
        System.out.println("Outer marble: " + outerMarble);
    }

    @Override
    public void updateMarketRow(Marble[] newRow, Marble outerMarble, int rowPosition) {
        System.out.println("update market:");
        System.out.println("Row " + rowPosition + ": " + newRow);
        System.out.println("Outer marble: " + outerMarble);
    }

    @Override
    public void updateStrongbox(Map<Resource, Integer> resources) {
        System.out.println("Strongbox: " + resources);
    }

    @Override
    public void updateLastProduced(Map<Resource, Integer> resources) {
        System.out.println("You have produced: " + resources + ", they will be added in your Strongbox in the next turn.");
    }

    @Override
    public void updateWarehouseDepot(int shelf, Resource resource, int quantity) {
        System.out.println("Update Depot: " + shelf + "has changed:");
        System.out.println("now contains " + quantity + " " + resource);
    }

    @Override
    public void updateMeetingPope(Map<Player, Integer> deltaVictoryPoints) {
        System.out.println("update MeetingPope: ");
        System.out.println(deltaVictoryPoints);
    }

    @Override
    public void updateFaithPath(Map<Player, Integer> newPositions) {
        System.out.println("update FaithPath:");
        System.out.println(newPositions);
    }

    @Override
    public void updateSingleFaithPath(Map<String, Integer> newPositions) {
        System.out.println("update SingleFaithPath:");
        System.out.println(newPositions);
    }

    @Override
    public void updateExtraSlot(Resource resource, int id, int quantity) {
        System.out.println("update Extra Slot:" + id + ", now contains " + quantity + " " + resource );
    }
}
