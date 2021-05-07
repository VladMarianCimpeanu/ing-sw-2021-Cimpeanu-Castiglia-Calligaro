package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.WarehouseDepot;
import it.polimi.ingsw.model.benefit.Resource;

public class WarehouseDepotStub1 extends WarehouseDepot {
    private int countExtraSlot;
    public WarehouseDepotStub1(){
        super();
        countExtraSlot = 0;
    }

    @Override
    public void addExtraSlot(Resource resourceExtra, int ID){
        countExtraSlot++;
    }

    public int getCountExtraSlot() {
        return countExtraSlot;
    }
}
