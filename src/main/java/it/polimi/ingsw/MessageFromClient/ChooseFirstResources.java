package it.polimi.ingsw.MessageFromClient;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.states.TurnState;
import it.polimi.ingsw.model.benefit.Resource;

public class ChooseFirstResources extends MessageFromClient {
    private Resource res1, res2;
    private int shelf1, shelf2;
    private String nickname;
    @Override
    public void activate(Controller controller) {
        TurnState state = controller.getCurrentState();
        state.selectResources(nickname, res1, res2, shelf1, shelf2);
    }
}
