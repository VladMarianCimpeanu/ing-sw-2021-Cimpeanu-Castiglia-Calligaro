package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Discount;
import it.polimi.ingsw.model.benefit.Resource;

public class DiscountStub extends Discount {
    private Resource resource;

    public DiscountStub(Resource resource){
        super(resource);
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
