package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.MessageToClient.ConvertedMarbles;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.MarketStrategy;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.WarehouseDepot;
import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidIDExcpetion;
import it.polimi.ingsw.server.model.exceptions.InvalidResourceException;
import it.polimi.ingsw.server.model.exceptions.InvalidShelfPosition;
import it.polimi.ingsw.server.model.exceptions.InvalidStrategyException;

import java.util.ArrayList;

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
                else {
                    ArrayList<Resource> resources = getController().getCurrentPlayer().getReceivedFromMarket();
                    getController().sendMessage(new ConvertedMarbles(resources));

                    getController().setCurrentState(new MarketState(getController()));
                }
            }
        } catch (InvalidIDExcpetion e) {
            getController().sendError(invalidLeaderCardID);
        } catch (InvalidStrategyException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void completeTurn() {
        Player player = getController().getCurrentPlayer();
        Dashboard dashboard = player.getDashboard();
        WarehouseDepot depot = dashboard.getWarehouseDepot();
        ArrayList<MarketStrategy> strategies = player.getMarketStrategies();

        if(strategies.size() == 1){
            try {
                for(int i = 0; i<whiteMarbles; i++)
                    player.addInMarketStrategyStack(strategies.get(0));
            } catch (InvalidStrategyException e) {
                e.printStackTrace();
            }
        }else if(strategies.size() == 2){
            //check for free places in extraSlots
            Resource avRes1 = strategies.get(0).getResource();
            Resource avRes2 = strategies.get(1).getResource();
            try {
                int places = 2-depot.getExtraQuantity(avRes1);
                for(int i = 0; i<places && whiteMarbles > 0; i++) {
                        player.addInMarketStrategyStack(strategies.get(0));
                        whiteMarbles--;
                    }
                if(whiteMarbles != 0) {
                    places = 2 - depot.getExtraQuantity(avRes2);
                    for(int i = 0; i<places && whiteMarbles > 0; i++) {
                        player.addInMarketStrategyStack(strategies.get(0));
                        whiteMarbles--;
                    }
                }
                int availablePlaces = 0;
                boolean placed1 = false;
                boolean placed2 = false;
                try {
                    int shelf = depot.getResourceShelf(avRes1);
                    availablePlaces = shelf-depot.getShelfQuantity(shelf);
                    for(int i = 0; i<availablePlaces && whiteMarbles>0; i++)
                        player.addInMarketStrategyStack(strategies.get(0));
                    placed1 = true;
                }catch (InvalidResourceException e) {
                } catch (InvalidShelfPosition invalidShelfPosition) {
                    invalidShelfPosition.printStackTrace();
                }
                if(whiteMarbles != 0) {
                    try {
                        int shelf = depot.getResourceShelf(avRes2);
                        availablePlaces = shelf - depot.getShelfQuantity(shelf);
                        for (int i = 0; i < availablePlaces && whiteMarbles > 0; i++)
                            player.addInMarketStrategyStack(strategies.get(1));
                        placed2 = true;
                    } catch (InvalidResourceException e) {
                    } catch (InvalidShelfPosition invalidShelfPosition) {
                        invalidShelfPosition.printStackTrace();
                    }
                }
                try {
                    int shelf1 = 0;
                    int quantity1 = 0;
                    int shelf2 = 0;
                    int quantity2 = 0;
                    while(whiteMarbles > 0 && !placed1){
                        if(quantity1 == 0)
                            shelf1 = depot.getFreeShelf(3);
                        whiteMarbles--;
                        quantity1++;
                        if(quantity1 == shelf1) break;
                    }

                    while(whiteMarbles > 0 && !placed2){
                        if(quantity2 == 0)
                            if(!placed1)
                                shelf2 = depot.getFreeShelf(shelf1-1);
                        whiteMarbles--;
                        quantity2++;
                        if(quantity2 == shelf2) break;
                    }
                }catch (InvalidShelfPosition invalidShelfPosition) {
                    for(int i = whiteMarbles; i>0; i--)
                        player.addInMarketStrategyStack(strategies.get(0));
                }
            } catch (InvalidStrategyException e) {
                e.printStackTrace();
            }
           }
        try {
            player.passStrategiesToMarket();
        } catch (InvalidStrategyException e) {
            e.printStackTrace();
        }
        MarketState marketState = new MarketState(getController());
        marketState.completeTurn();
        //getController().nextTurn();
    }

}
