package it.polimi.ingsw.model;

/**
 * This enum is used to represent the color attribute of development cards
 */
public enum Color {
    GREEN(0),
    BLUE(1),
    YELLOW(2),
    PURPLE(3);

    private final int index;
    Color(int index){
        this.index = index;
    }

    /**
     * @return a numeric value associated to the specified color.
     */
    public int getIndex() {
        return index;
    }
}
