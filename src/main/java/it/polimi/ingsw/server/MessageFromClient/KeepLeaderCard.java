package it.polimi.ingsw.server.MessageFromClient;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.ErrorMessage;
import it.polimi.ingsw.server.controller.states.TurnState;

/**
 * Message from client: it contains the ids of the leader cards selected by the specified player at the beginning of the game.
 */
public class KeepLeaderCard extends MessageFromClient {
    private int id1, id2;
    private String nickname;
    @Override
    public void activate(Controller controller) {
        if(id1 == 0 || id2 == 0 || nickname == null) {
            controller.sendError(ErrorMessage.invalidJson);
            return;
        }
        TurnState state = controller.getCurrentState();
        state.keepLeaderCards(nickname, id1, id2);
    }
}
