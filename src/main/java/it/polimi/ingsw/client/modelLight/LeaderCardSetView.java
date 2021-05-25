package it.polimi.ingsw.client.modelLight;

import java.util.ArrayList;

public abstract class LeaderCardSetView {

    public abstract void show();
    public abstract void update(ArrayList<Integer> idS);
    public abstract void remove(int id);
    public abstract int getIDfromIndex(int index);
    public abstract void activate(int id);
    public abstract void dumpMessage(String message);

}
