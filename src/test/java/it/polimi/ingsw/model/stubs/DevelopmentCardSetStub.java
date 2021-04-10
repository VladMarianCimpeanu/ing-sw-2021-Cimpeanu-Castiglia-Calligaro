package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.DevelopmentCardSet;
import it.polimi.ingsw.model.exceptions.InvalidReadException;
import it.polimi.ingsw.model.exceptions.NoCardException;
import it.polimi.ingsw.model.exceptions.WrongLevelException;

import java.io.IOException;

public class DevelopmentCardSetStub extends DevelopmentCardSet {
    public int blueLevelOne;
    public int blueLevelTwo;
    public int blueLevelThree;
    public int yellowLevelOne;
    public int yellowLevelTwo;
    public int yellowLevelThree;
    DevelopmentCard devCard;

    public DevelopmentCardSetStub() throws IOException, InvalidReadException {
        super();
        blueLevelOne = 0;
         blueLevelTwo = 0;
         blueLevelThree = 0;
         yellowLevelOne = 0;
         yellowLevelTwo = 0;
         yellowLevelThree = 0;
         devCard = new DevelopmentCardStub(5);
    }

    @Override
    public DevelopmentCard peekCard(Color color, int level) {
        return devCard;
    }

    @Override
    public DevelopmentCard drawCard(Color color, int level) throws WrongLevelException, NoCardException {
        if (level < 0 || level > 3) throw new WrongLevelException();
        if (color.equals(Color.BLUE) && level == 1) {
            if(blueLevelOne > 0) blueLevelOne -= 1;
            else throw new NoCardException();
        }
        if (color.equals(Color.BLUE) && level == 2) {
            if(blueLevelTwo > 0) blueLevelTwo -= 1;
            else throw new NoCardException();
        }
        if (color.equals(Color.BLUE) && level == 3) {
            if(blueLevelThree > 0) blueLevelThree -= 1;
            else throw new NoCardException();
        }

        if (color.equals(Color.YELLOW) && level == 1) {
            if(yellowLevelOne > 0) yellowLevelOne -= 1;
            else throw new NoCardException();
        }
        if (color.equals(Color.YELLOW) && level == 2) {
            if(yellowLevelOne > 0) yellowLevelTwo -= 1;
            else throw new NoCardException();
        }
        if (color.equals(Color.YELLOW) && level == 3) {
            if(yellowLevelThree > 0) yellowLevelThree -= 1;
            else throw new NoCardException();
        }
        return devCard;
    }

    @Override
    public int getAvailableQuantity(Color color, int level) {
        if (color.equals(Color.BLUE) && level == 1) return blueLevelOne;
        if (color.equals(Color.BLUE) && level == 2) return blueLevelTwo;
        if (color.equals(Color.BLUE) && level == 3) return blueLevelThree;

        if (color.equals(Color.YELLOW) && level == 1) return yellowLevelOne;
        if (color.equals(Color.YELLOW) && level == 2) return yellowLevelTwo;
        if (color.equals(Color.YELLOW) && level == 3) return yellowLevelThree;
        return 0;
    }
}
