package it.polimi.ingsw.server.model.benefit;

/**
 * This class represents the faith object.
 * Only one instance of Faith can exists at run time.
 */
public class Faith implements Benefit {
    private static Faith instance = null;

    private Faith(){
    }

    /**
     * return the unique instance of Faith.
     * @return a Faith object
     */
    public static Faith giveFaith(){
        if (instance == null) instance = new Faith();
        return instance;
    }
    @Override
    public String toString() {
        return "FAITH";
    }
}
