package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

/**
 * Message from client: it contains the resources to place in the stocks. It specifies the location with the keywords:
 * extra, depot or strongbox. If the keyword is depot, it contains also an Integer representing the shelf.
 */
public class PutResPos extends MessageFromClient {
    private Resource res;
    private String pos;
    private int shelf;
    @Override
    public void activate(Controller controller) {
        if(pos == null || res == null) {
            controller.sendError(ErrorMessage.invalidJson);
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
                controller.sendError(ErrorMessage.invalidJson);
        }
    }
}
