package it.polimi.ingsw.client.modelLight.ActionToken;

import it.polimi.ingsw.client.Color;

enum ActionToken {
    DISCARDYELLOW(Color.YELLOW.getIndex(), "A Yellow token has been drawn"),
    DISCARDBLUE(Color.BLUE.getIndex(), "A Blue token has been drawn"),
    DISCARDGREEN(Color.GREEN.getIndex(), "A Green token has been drawn"),
    DISCARDPURPLE(Color.PURPLE.getIndex(), "A Purple token has been drawn"),
    SHUFFLE(4, "A Shuffle token has been drawn\nThe ActionTokenDeck has been shuffled"),
    FAITHFORWARD(5, "A Faith forward token has been drawn");

    private final int id;
    private final String description;

    ActionToken(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}

