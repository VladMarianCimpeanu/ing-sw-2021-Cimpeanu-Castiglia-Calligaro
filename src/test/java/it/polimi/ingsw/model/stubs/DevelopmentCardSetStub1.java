package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.DevelopmentCardSet;

import java.nio.file.NoSuchFileException;

public class DevelopmentCardSetStub1 extends DevelopmentCardSet {
    private Color color;
    private int level;
    private int quantity;
    public DevelopmentCardSetStub1() {
        super();
        color = null;
        level = -1;
        quantity = 4;
    }
    public DevelopmentCardSetStub1(int quantity) {
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
    public DevelopmentCard peekCard(Color color, int level) throws NoSuchFileException {
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
