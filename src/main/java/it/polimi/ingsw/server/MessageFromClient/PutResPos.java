package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

public class PutResPos extends MessageFromClient {
    private Resource res;
    private String pos;
    private int shelf;
    @Override
    public void activate(Controller controller) {
        if(pos == null || res == null) {
            controller.sendError("invalidJson");
            return;
        }
        TurnState state = controller.getCurrentState();
        switch(pos){
            case "depot":
                state.putWarehouse(res, shelf);
                break;
            case "extra":
                state.putExtraSlot(res);
                break;
            default:
                controller.sendError("invalidCommand");
        }
    }
}
