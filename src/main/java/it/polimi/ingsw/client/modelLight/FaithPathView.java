package it.polimi.ingsw.client.modelLight;

import java.util.HashMap;
import java.util.Map;

/**
 * This class gives access to all the information about the current faith path, in order to display it.
 */
public abstract class FaithPathView {
    protected Map<String, Integer> positions;

    public abstract void show();
    public abstract void showUpdate();

    /**
     * This method add a new player to the faith path.
     * @param player nickname of the specified player.
     */
    public abstract void addPlayer(String player);

    /**
     * It updates the faith path with the current positions of each player.
     * @param positions positions' key is the nickname of the player, whereas positions' values represents the position of the player.
     */
    public void update(Map<String, Integer> positions){
        if(this.positions != null){
            this.positions = new HashMap<>(positions);
            showUpdate();
        }else{
            this.positions = new HashMap<>(positions);
            show();
        }
    }
}
