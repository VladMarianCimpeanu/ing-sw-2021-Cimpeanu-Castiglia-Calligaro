package it.polimi.ingsw.client.modelLight.ActionToken;

import it.polimi.ingsw.client.Color;

enum ActionToken {
    DISCARDYELLOW(Color.YELLOW.getIndex(), "A Yellow token has been drawn", "images/punchboard/cerchio4.png"),
    DISCARDBLUE(Color.BLUE.getIndex(), "A Blue token has been drawn", "images/punchboard/cerchio1.png"),
    DISCARDGREEN(Color.GREEN.getIndex(), "A Green token has been drawn", "images/punchboard/cerchio2.png"),
    DISCARDPURPLE(Color.PURPLE.getIndex(), "A Purple token has been drawn", "images/punchboard/cerchio3.png"),
    SHUFFLE(4, "A Shuffle token has been drawn\nThe ActionTokenDeck has been shuffled", "images/punchboard/cerchio7.png"),
    FAITHFORWARD(5, "A Faith forward token has been drawn", "images/punchboard/cerchio5.png");

    private final int id;
    private final String description;
    private final String url;

    ActionToken(int id, String description, String url){
        this.id = id;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}

