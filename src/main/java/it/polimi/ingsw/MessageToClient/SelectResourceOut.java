package it.polimi.ingsw.MessageToClient;

import it.polimi.ingsw.model.benefit.Resource;

public class SelectResourceOut implements MessageToClient{
    private String type;

    public SelectResourceOut() {
        type = "SelectResourceOut";
    }
}
