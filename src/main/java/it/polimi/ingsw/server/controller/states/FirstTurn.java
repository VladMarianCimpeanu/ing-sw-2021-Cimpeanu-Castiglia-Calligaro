package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.client.MessageFromServer.Ok;
import it.polimi.ingsw.server.JsonToLeaderCard;
import it.polimi.ingsw.server.MessageToClient.Error;
import it.polimi.ingsw.server.MessageToClient.SelectedLeadercards;
import it.polimi.ingsw.server.MessageToClient.TurnOrder;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.server.controller.states.ErrorMessage.*;

public class FirstTurn extends TurnState{
    private ArrayList<String> waitingForLeaderCards;
    private Map<String, Integer> firstTurnResources;

    public FirstTurn(Controller controller) {
        super(controller);
        firstTurnResources = new HashMap<>();
        waitingForLeaderCards = new ArrayList<>(controller.getTurns());
        for(int i = 2; i <= controller.getTurns().size(); i++)
            firstTurnResources.put(controller.getTurns().get(i-1), i/2);
    }

    @Override
    public void keepLeaderCards(String nickname, int id1, int id2){
        if(!waitingForLeaderCards.contains(nickname)) {
            getController().sendMessage(nickname, new Error(invalidCommand));
            return;
        }
        try {
            getController().getPlayer(nickname).keepLeaderCards(JsonToLeaderCard.getLeaderCard(id1), JsonToLeaderCard.getLeaderCard(id2));
            getController().sendMessage(nickname, new SelectedLeadercards(id1, id2));
        } catch (NoCardException e) {
            getController().sendMessage(nickname, new Error(invalidLeaderCardID));
            return;
        }
        waitingForLeaderCards.remove(nickname);
        int position = getController().getTurns().indexOf(nickname);
        int resNum = firstTurnResources.getOrDefault(nickname, 0);
        getController().sendMessage(nickname, new TurnOrder(position, resNum));
        endPhase();
    }

    @Override
    public synchronized void selectResources(String nickname, Resource res1, Resource res2, int shelf1, int shelf2) {
        if (!firstTurnResources.containsKey(nickname)) {
            getController().sendMessage(nickname, new Error(invalidCommand));
            return;
        }
        if (res1 == null){
            getController().sendMessage(nickname, new Error(invalidCommand));
            return;
        }
        try {
            if (firstTurnResources.get(nickname) == 1) {
                if (res2 != null) {
                    getController().sendMessage(nickname, new Error(invalidCommand));
                    return;
                }
                getController().setCurrentUser(nickname);
                getController().getPlayer(nickname).getDashboard().getWarehouseDepot().addResource(shelf1, 1, res1);
            } else if (firstTurnResources.get(nickname) == 2) {
                if(res2 == null){
                    getController().sendMessage(nickname, new Error(invalidCommand));
                    return;
                }
                getController().setCurrentUser(nickname);
                getController().getPlayer(nickname).getDashboard().getWarehouseDepot().addResource(shelf1, 1, res1);
                getController().getPlayer(nickname).getDashboard().getWarehouseDepot().addResource(shelf2, 1, res2);
            }
            firstTurnResources.remove(nickname);
            getController().setCurrentUser(null);
            endPhase();
        } catch(InvalidShelfPosition invalidShelfPosition){
            getController().sendMessage(nickname, new Error(invalidShelf));
        } catch(ExistingResourceException e){
            getController().sendMessage(nickname, new Error(existingResource));
        } catch(InvalidResourceException e){
            getController().sendMessage(nickname, new Error(invalidResource));
        }
    }

    @Override
    public void completeTurn() {
        //The First Turn has to be completed by each player in order to start the game
    }


    private void endPhase(){
        if(waitingForLeaderCards.isEmpty() && firstTurnResources.isEmpty()){
            for (int i = 2; i < getController().getPlayers().size() ; i++) {
                try {
                    getController().getGame().getFaithPath().movePlayer(getController().getPlayers().get(i), 1);
                } catch (NoSuchPlayerException | InvalidStepsException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("First turn ended. Game starts now!");
            getController().startGame();
            getController().setCurrentState(new SelectionState(getController()));
        }
    }

    @Override
    public void moveWarehouse(int fromShelf, int toShelf) {
        getController().sendError(invalidCommand);
    }

    @Override
    public void moveExtraToWarehouse(int shelf, int leaderId, int quantityToMove) {
        getController().sendError(invalidCommand);
    }

    @Override
    public void moveWarehouseToExtra(int shelf, int leaderId, int quantityToMove) {
        getController().sendError(invalidCommand);
    }
}

