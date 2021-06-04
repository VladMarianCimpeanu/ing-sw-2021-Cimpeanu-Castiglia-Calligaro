package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.actionToken.ActionToken;
import it.polimi.ingsw.server.model.exceptions.GameEndedException;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import static java.util.Collections.shuffle;

/**
 * The Singleplayer class implements the singleplayer game mode. The difference between multiplayer and singleplayer is the latter
 * gives access to a deck of action tokens: this objects are used only in single player mode.
 */
public class Singleplayer extends Game{
    private final Stack<ActionToken> availableActionTokens;

    public Singleplayer(ArrayList<Identity> identities) throws IOException, InvalidReadException, NoSuchPlayerException {
        super(identities);
        ArrayList<ActionToken> tempTokens = ActionTokenDeck.getTokens();
        availableActionTokens = new Stack<>();
        for(ActionToken token : tempTokens) availableActionTokens.push(token);
        shuffle(availableActionTokens);
        faithPath = new SingleFaithPath(getPlayers().get(0));
    }

    public ArrayList<ActionToken> getAvailableActionTokens() {
        return new ArrayList<>(availableActionTokens);
    }

    @Override
    public boolean isGameEnded() {
        return true;
    }

    /**
     * it triggers the effect of the token on top of the deck of action tokens.
     * @throws NoSuchPlayerException
     * @throws GameEndedException if the games ends after an action token is drawn.
     */
    public void drawToken() throws NoSuchPlayerException, GameEndedException {
        ActionToken tempToken = availableActionTokens.pop();
        availableActionTokens.add(0, tempToken);
        System.out.println(tempToken);
        virtualView.updateDrawToken(tempToken.getID());
        tempToken.triggerEffect(this);
    }

    /**
     * shuffle the ActionToken Set (available and discarded tokens together)
     */
    public void shuffleToken(){
        shuffle(availableActionTokens);
    }

    /**
     * draws an action token
     * @throws NoSuchPlayerException
     * @throws GameEndedException if the games ends after an action token is drawn.
     */
    @Override
    public void endTurn() throws NoSuchPlayerException, GameEndedException {
        drawToken();
    }

    /**
     * if the player wins, blackCross has 0 points whereas the player gains the points calculated with the rules of the game.
     * if the player loose, blackCross has 1 point, whereas the player gains 0 points.
     * @return a map which key is a player's nickname and the value is the amount of points earned by the player.
     */
    @Override
    public Map<String, Integer> calculatePoints() {
        Map<String, Integer> points = new TreeMap<>();
        if(getDevelopmentCardSet().isAColorMissing() || ((SingleFaithPath)getFaithPath()).getBlackCrossPosition() == 24){
            points.put("blackCross", 1);
            points.put(getPlayers().get(0).getNickName(), 0);
        }
        else{
            points.put("blackCross", 0);
            points.put(getPlayers().get(0).getNickName(), calculatePointsOf(0));
        }
        return points;
    }

}
