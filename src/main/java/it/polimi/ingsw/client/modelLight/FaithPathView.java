package it.polimi.ingsw.client.modelLight;

import java.util.HashMap;
import java.util.Map;

public abstract class FaithPathView {
    protected Map<String, Integer> positions;

    public abstract void show();
    public abstract void showUpdate();
    public abstract void addPlayer(String player);
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
