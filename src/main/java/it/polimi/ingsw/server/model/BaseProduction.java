package it.polimi.ingsw.server.model;


/**
 * This class represents the action of base production.
 * At runtime can exists only an instance of this object.
 */
public class BaseProduction implements Production{
    private static BaseProduction instance = null;
    private BaseProduction() {
    }

    /**
     *
     * @return the unique instance of BaseProduction.
     */
    public static BaseProduction getBaseProduction(){
        if (instance == null) instance = new BaseProduction();
        return instance;
    }
}
