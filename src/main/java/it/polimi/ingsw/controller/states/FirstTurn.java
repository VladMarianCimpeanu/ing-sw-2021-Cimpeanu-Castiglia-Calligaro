package it.polimi.ingsw.controller.states;

import it.polimi.ingsw.JsonToLeaderCard;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.controller.states.ErrorMessage.invalidCommand;

public class FirstTurn extends TurnState{
    private ArrayList<String> waitingForLeaderCards;
    private Map<String, Integer> firstTurnResources;

    public FirstTurn(Controller controller) {
        super(controller);
        waitingForLeaderCards = new ArrayList<>();
        firstTurnResources = new HashMap<>();
        waitingForLeaderCards = new ArrayList<>(controller.getTurns());
        for(int i = 2; i <= controller.getTurns().size(); i++)
            firstTurnResources.put(controller.getTurns().get(i-1), i/2);

        //TODO: Distribute LeaderCards
    }

    @Override
    public void keepLeaderCards(String nickname, int id1, int id2){
        try {
            getController().getPlayer(nickname).keepLeaderCards(JsonToLeaderCard.getLeaderCard(id1), JsonToLeaderCard.getLeaderCard(id2));
            //TODO update Unicast (OK)
        } catch (NoCardException e) {
            //TODO
        }
        waitingForLeaderCards.remove(nickname);
        endPhase();
    }

    //TODO error management
    @Override
    public void selectResources(String nickname, Resource res1, Resource res2, int shelf1, int shelf2) {
        if (!firstTurnResources.containsKey(nickname)) ;//TODO
        if (res1 == null) ;
        try {
            if (firstTurnResources.get(nickname) == 1) {
                if (res2 != null) ;
                getController().getPlayer(nickname).getDashboard().getWarehouseDepot().addResource(shelf1, 1, res1);
            } else if (firstTurnResources.get(nickname) == 2) {
                if(res2 == null);
                getController().getPlayer(nickname).getDashboard().getWarehouseDepot().addResource(shelf1, 1, res1);
                getController().getPlayer(nickname).getDashboard().getWarehouseDepot().addResource(shelf2, 1, res2);
            }
        } catch(InvalidShelfPosition invalidShelfPosition){
            invalidShelfPosition.printStackTrace();
        } catch(ExistingResourceException e){
            e.printStackTrace();
        } catch(InvalidResourceException e){
            e.printStackTrace();
        }
        firstTurnResources.remove(nickname);
        endPhase();
    }


    private void endPhase(){
        if(waitingForLeaderCards.isEmpty() && firstTurnResources.isEmpty()){
            for (int i = 0; i < getController().getPlayers().size() ; i++) {
                try {
                    getController().getGame().getFaithPath().movePlayer(getController().getPlayers().get(i), i / 2);
                } catch (NoSuchPlayerException | InvalidStepsException e) {
                    e.printStackTrace();
                }
            }
            getController().setCurrentState(new SelectionState(getController()));
            getController().startGame();
        }
    }

    @Override
    public void moveWarehouse(int fromShelf, int toShelf) {
        getController().sendError(invalidCommand.toString());
    }

    @Override
    public void moveExtraToWarehouse(int shelf, int leaderId, int quantityToMove) {
        getController().sendError(invalidCommand.toString());
    }

    @Override
    public void moveWarehouseToExtra(int shelf, int leaderId, int quantityToMove) {
        getController().sendError(invalidCommand.toString());
    }
}

