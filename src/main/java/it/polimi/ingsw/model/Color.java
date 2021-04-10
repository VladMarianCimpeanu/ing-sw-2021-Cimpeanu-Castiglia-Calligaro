package it.polimi.ingsw.model;

public enum Color {
    GREEN(0),
    BLUE(1),
    YELLOW(2),
    PURPLE(3);

    private final int index;
    Color(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
