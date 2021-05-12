package it.polimi.ingsw.client.MessageToServer;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

public class PutResPos extends MessageFromClient {
    private Resource res;
    private String pos;
    private int shelf;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        switch(pos){
            case "warehouseDepot":
                state.putWarehouse(res, shelf);
                break;
            case "extraSlot":
                state.putExtraSlot(res);
                break;
        }
    }
}
