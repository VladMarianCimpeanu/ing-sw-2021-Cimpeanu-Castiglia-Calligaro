package it.polimi.ingsw.client.modelLight;

import it.polimi.ingsw.client.Resource;

/**
 * this class contains all the information needed to represent the depot of a specific player.
 */
public abstract class DepotView {
    protected int[] quantity;
    protected Resource[] resources;

    public DepotView(){
        quantity = new int[3];
        resources = new Resource[3];
        for(int i = 0; i<3; i++){
            quantity[i] = 0;
            resources[i] = null;
        }
    }

    public abstract void show();
    public abstract void showUpdate();

    public void update(int shelf, Resource resource, int many) {
        //exceptions?
        resources[shelf-1] = resource;
        quantity[shelf-1] = many;
    }

    public int howMany(Resource resource){
        for(int i = 0; i<3; i++)
            if(resources[i] == resource && quantity[i] != 0) return quantity[i];
        return 0;
    }
}
