package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.exceptions.InvalidIDExcpetion;
import it.polimi.ingsw.server.model.exceptions.InvalidStrategyException;
import static it.polimi.ingsw.server.controller.states.ErrorMessage.*;

/**
 * A player is in this state when he must chose which leader card to use to convert the white marbles obtained by the market.
 * The player will remains here till all the white marbles are covered by a leader card.
 */
public class MarketStrategyState extends TurnState {
    private int whiteMarbles;
    public MarketStrategyState(Controller controller, int whiteMarbles) {
        super(controller);
        this.whiteMarbles = whiteMarbles;
    }

    @Override
    public void addStrategy(int id) {
        try {
            MarketStrategy strategy = getController().getCurrentPlayer().getMarketStrategy(id);
            getController().getCurrentPlayer().addInMarketStrategyStack(strategy);
            whiteMarbles -= 1;
            if (whiteMarbles == 0) {
                getController().getCurrentPlayer().passStrategiesToMarket();
                if(getController().getCurrentPlayer().isMarketResourcesUnavailable()) getController().setCurrentState(new EndTurnState(getController()));
                else getController().setCurrentState(new MarketState(getController()));
            }
        } catch (InvalidIDExcpetion e) {
            getController().sendError(invalidLeaderCardID.toString());
        } catch (InvalidStrategyException e) {
            e.printStackTrace();
        }

    }

}
