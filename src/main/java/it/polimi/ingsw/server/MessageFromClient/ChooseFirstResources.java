package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.TurnState;
import it.polimi.ingsw.server.model.benefit.Resource;

public class ChooseFirstResources extends MessageFromClient {
    private Resource res1, res2;
    private int shelf1, shelf2;
    private String nickname;
    @Override

    public void activate(Controller controller) {
        if(nickname == null || res1 == null || shelf1 == 0) {
            controller.sendError("invalidJson");
            return;
        }
        TurnState state = controller.getCurrentState();
        state.selectResources(nickname, res1, res2, shelf1, shelf2);
    }
}
