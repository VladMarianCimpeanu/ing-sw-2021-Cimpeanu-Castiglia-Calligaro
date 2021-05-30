package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.InvalidStepsException;
import it.polimi.ingsw.server.model.exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is used to represent the multiplayer game mode.
 */
public class Multiplayer extends Game {
    public Multiplayer(ArrayList<Identity> identities) throws InvalidStepsException, NoSuchPlayerException, IOException, InvalidReadException {
        super(identities);
        if(getPlayers() == null) return;
        faithPath = new MultiFaithPath(getPlayers());
    }

    @Override
    public boolean isGameEnded() {
        return false;
    }

    /**
     * it calculates points earned by each player.
     * @return a map containing points of each player.
     */
    @Override
    public Map<String, Integer> calculatePoints() {
        Map<String, Integer> points = new TreeMap<>();
        for(int player = 0; player < getPlayers().size(); player ++){
            points.put(getPlayers().get(player).getNickName(), calculatePointsOf(player));
        }
        return points;
    }
}
