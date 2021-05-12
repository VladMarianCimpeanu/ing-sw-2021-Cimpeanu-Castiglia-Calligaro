package it.polimi.ingsw.server.model.stubs;

import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.DevelopmentCardSet;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;

import java.io.IOException;

public class DevelopmentCardSetStub1 extends DevelopmentCardSet {
    private Color color;
    private int level;
    private int quantity;
    public DevelopmentCardSetStub1() throws IOException, InvalidReadException {
        super();
        color = null;
        level = -1;
        quantity = 4;
    }
    public DevelopmentCardSetStub1(int quantity) throws IOException, InvalidReadException {
        super();
        color = null;
        level = -1;
        this.quantity = quantity;
    }

    @Override
    public DevelopmentCard drawCard(Color color, int level){
        this.color = color;
        this.level = level;
        return null;
    }

    @Override
    public DevelopmentCard peekCard(Color color, int level) {
        return new DevelopmentCardStub1();
    }

    @Override
    public int getAvailableQuantity(Color color, int level) {
        return quantity;
    }
    public Color getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }
}
