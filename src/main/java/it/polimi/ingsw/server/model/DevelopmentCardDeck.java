package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.JsonToDevCard;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;
import it.polimi.ingsw.server.model.exceptions.NoCardException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
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
            String path = "/developmentCard.json";
            InputStreamReader reader = new InputStreamReader(DevelopmentCard.class.getResourceAsStream(path), StandardCharsets.UTF_8);
            JsonToDevCard myJsonReader = new JsonToDevCard();
            cards = (ArrayList<DevelopmentCard>) myJsonReader.readJsonDevelopmentCard(reader);
        }
        return new ArrayList<>(cards);
    }

    /**
     * Given an ID number, this method returns the development card with that ID
     * @param ID of the specified developmentCard
     * @return Development card having the specified ID
     * @throws IOException if an error occurred during opening/reading/closing the default file
     * @throws InvalidReadException if the read file is damaged: read an non-excepted data or the file misses some data.
     */
    public static DevelopmentCard getDevelopmentCard(int ID) throws IOException, InvalidReadException, NoCardException {
        if (ID < 1 || ID > 48) throw new NoCardException();
        if (cards == null) {
            String path = "/developmentCard.json";
            InputStreamReader reader = new InputStreamReader(DevelopmentCard.class.getResourceAsStream(path), StandardCharsets.UTF_8);
            JsonToDevCard myJsonReader = new JsonToDevCard();
            cards = (ArrayList<DevelopmentCard>) myJsonReader.readJsonDevelopmentCard(reader);
        }
        return cards.get(ID - 1);
    }
}
