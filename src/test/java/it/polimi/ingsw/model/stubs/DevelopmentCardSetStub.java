package it.polimi.ingsw.model.stubs;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.DevelopmentCardSet;

import java.nio.file.NoSuchFileException;

public class DevelopmentCardSetStub extends DevelopmentCardSet {
    public int blueLevelOne;
    public int blueLevelTwo;
    public int blueLevelThree;
    public int yellowLevelOne;
    public int yellowLevelTwo;
    public int yellowLevelThree;
    DevelopmentCard devCard;

    public DevelopmentCardSetStub() throws NoSuchFileException {
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
    public DevelopmentCard drawCard(Color color, int level) {
        if (color.equals(Color.BLUE) && level == 1) blueLevelOne --;
        if (color.equals(Color.BLUE) && level == 2) blueLevelTwo --;
        if (color.equals(Color.BLUE) && level == 3) blueLevelThree --;

        if (color.equals(Color.YELLOW) && level == 1) yellowLevelOne --;
        if (color.equals(Color.YELLOW) && level == 2) yellowLevelTwo --;
        if (color.equals(Color.YELLOW) && level == 3) yellowLevelThree --;
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
