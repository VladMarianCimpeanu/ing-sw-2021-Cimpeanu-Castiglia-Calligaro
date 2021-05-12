package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.Discount;
import it.polimi.ingsw.server.model.benefit.Resource;

public class DiscountStub extends Discount {
    private Resource resource;

    public DiscountStub(Resource resource){
        super(resource, 0);
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
