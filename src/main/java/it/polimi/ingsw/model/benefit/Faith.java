package it.polimi.ingsw.model.benefit;


public class Faith implements Benefit {
    private static Faith instance = null;

    private Faith(){
    }
    public static Faith giveFaith(){
        if (instance == null) instance = new Faith();
        return instance;
    }
    @Override
    public String toString() {
        return "FAITH";
    }
}
