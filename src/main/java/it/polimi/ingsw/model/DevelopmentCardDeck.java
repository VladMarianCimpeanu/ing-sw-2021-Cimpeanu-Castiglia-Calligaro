package it.polimi.ingsw.model;

import it.polimi.ingsw.JsonToDevCard;
import it.polimi.ingsw.model.exceptions.InvalidReadException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to distribute a new set of development cards.
 */
public abstract class DevelopmentCardDeck {
    private static ArrayList<DevelopmentCard> cards = null;

    /**
     * This method will generate a new set of development cards. At the first call it will read the data from the default file:
     * "src/jsonSources/developmentCard.json".
     * @return non randomized ArrayList of development cards.
     * @throws IOException if an error occurred during opening/reading/closing the default file
     * @throws InvalidReadException if the read file is damaged: read an non-excepted data or the file misses some data.
     */
    public static ArrayList<DevelopmentCard> getDevelopmentCardDeck() throws IOException, InvalidReadException {
        if (cards == null) {
            JsonToDevCard myJsonReader = new JsonToDevCard();
            String path = "src/jsonSources/developmentCard.json";
            cards = (ArrayList<DevelopmentCard>) myJsonReader.readJsonDevelopmentCard(new FileInputStream(path));
        }
        return new ArrayList<>(cards);
    }
}
