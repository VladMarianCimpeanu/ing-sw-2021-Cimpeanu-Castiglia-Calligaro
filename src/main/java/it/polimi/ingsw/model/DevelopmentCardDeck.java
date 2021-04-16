package it.polimi.ingsw.model;

import it.polimi.ingsw.JsonToDevCard;
import it.polimi.ingsw.model.exceptions.InvalidReadException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class DevelopmentCardDeck {
    private static ArrayList<DevelopmentCard> cards = null;

    public static ArrayList<DevelopmentCard> getDevelopmentCardDeck() throws IOException, InvalidReadException {
        if (cards == null) {
            JsonToDevCard myJsonReader = new JsonToDevCard();
            String path = "src/jsonSources/developmentCard.json";
            cards = (ArrayList<DevelopmentCard>) myJsonReader.readJsonDevelopmentCard(new FileInputStream(path));
        }

        return new ArrayList<>(cards);
    }
}
