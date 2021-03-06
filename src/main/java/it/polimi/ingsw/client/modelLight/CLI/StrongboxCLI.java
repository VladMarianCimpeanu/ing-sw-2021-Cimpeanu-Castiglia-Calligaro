package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.Resource;
import it.polimi.ingsw.client.modelLight.StrongboxView;

public class StrongboxCLI extends StrongboxView {


    public StrongboxCLI(){
        super();
    }

    @Override
    public void show() {
        for(Resource resource: content.keySet())
                System.out.print(content.get(resource) + "x" + resource.escape() + "  " + Resource.RESET);
        System.out.println();
    }

}
