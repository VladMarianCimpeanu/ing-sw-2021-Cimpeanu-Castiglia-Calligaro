package it.polimi.ingsw.client.modelLight.CLI;

import it.polimi.ingsw.client.modelLight.StrongboxView;
import it.polimi.ingsw.client.Resource;

import java.util.HashMap;
import java.util.Map;

public class StrongboxCLI extends StrongboxView {
    private final Map<Resource, Integer> content;

    public StrongboxCLI(){
        content = new HashMap<>();
    }

    @Override
    public void show() {

    }


}
