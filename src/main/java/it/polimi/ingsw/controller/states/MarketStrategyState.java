package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.MarketStrategy;
import it.polimi.ingsw.model.exceptions.InvalidIDExcpetion;
import it.polimi.ingsw.model.exceptions.InvalidStrategyException;
import static it.polimi.ingsw.controller.states.ErrorMessage.*;

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
