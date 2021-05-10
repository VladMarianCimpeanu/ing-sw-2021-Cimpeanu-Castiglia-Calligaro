package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.controller.VirtualView;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.market.Marble;

import java.util.Map;

public class VirtualViewStub extends VirtualView {
    public VirtualViewStub() {
        super(null);
    }

    @Override
    public void updateActiveLeaderCard(int id) {
    }

    @Override
    public void updateDevCardDrawn(Color color, int level, int idNewCard) {
    }

    @Override
    public void updateDevDeck(int position, int id) {
    }

    @Override
    public void updateDiscardLeaderCard(int id) {
    }

    @Override
    public void updateMarketColumn(Marble[] newColumn, Marble outerMarble, int colPosition) {

    }

    @Override
    public void updateMarketRow(Marble[] newRow, Marble outerMarble, int rowPosition) {

    }

    @Override
    public void updateStrongbox(Map<Resource, Integer> resources) {

    }

    @Override
    public void updateLastProduced(Map<Resource, Integer> resources) {

    }

    @Override
    public void updateWarehouseDepot(int shelf, Resource resource, int quantity) {

    }

    @Override
    public void updateMeetingPope(Map<Player, Integer> deltaVictoryPoints) {

    }

    @Override
    public void updateFaithPath(Map<Player, Integer> newPositions) {

    }

    @Override
    public void updateExtraSlot(Resource resource, int id, int quantity) {
    }
}
