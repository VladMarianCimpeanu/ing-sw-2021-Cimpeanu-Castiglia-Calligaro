package it.polimi.ingsw.server;

import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.DevelopmentCard;
import it.polimi.ingsw.server.model.benefit.Benefit;
import it.polimi.ingsw.server.model.benefit.Faith;
import it.polimi.ingsw.server.model.benefit.Resource;
import it.polimi.ingsw.server.model.exceptions.InvalidReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import static it.polimi.ingsw.server.model.Color.*;


/**
 * This class is used to read from a Json file all the information about development cards and generates a new set of DevelopmentCards.
 */
public class JsonToDevCard {
    /**
     * This method will convert all the data inside the specified file in a List of development Cards. The Json file to be read has to respect the following standards:
     * 0- in order to make this class behave correctly, cards should be ordered by ID.
     * 1- all the cards must be in the same Json file.
     * 2- all the cards must contain the following case sensitive keys: "VictoryPoints", "Level", "ResourceCost", "ResourceIn", "BenefitOut" (keys order has no importance).
     * 3- VictoryPoints has to be a non negative integer value.
     * 4- Level has to be an integer between 1 and 3.
     * 5- ResourceCost and ResourceIn are Maps which can not contain keys different from the following case sensitive keys: "STONE", "COIN", "SHIELD", "SERVANT"(order does not matter).
     *    Non negative values are the only values accepted for these keys. If a key misses it is assumed that its value is 0.
     * 6- BenefitOut is a Map which can not contain keys different from the following case sensitive keys: "STONE", "COIN", "SHIELD", "SERVANT", "FAITH"(order does not matter).
     *    Non negative values are the only values accepted for these keys. If a key misses it is assumed that its value is 0.
     * @param in is a specified FileInputStream used to open Json file with development cards information.
     * @return a list of DevelopmentCard with all the cards read from the file. The list contains the development cards ordered by ID.
     * @throws IOException if a general error occurred opening/reading/closing the specified file.
     * @throws NoSuchElementException if an element read is anomalous.
     * @throws InvalidReadException if a card misses some data.
     */
    public List<DevelopmentCard> readJsonDevelopmentCard(InputStreamReader in) throws IOException, NoSuchElementException, InvalidReadException {
        JsonReader reader = new JsonReader(in);
        return readDevelopmentCardArray(reader);
    }

    /**
     * This method is used to read the entire file pointed by the specified JsonReader.
     * @param reader specified pointer to a JsonFile from which development cards information will be taken.
     * @return ArrayList of Development cards read by 'reader'.
     * @throws IOException if a general error occurred reading the specified file.
     * @throws NoSuchElementException if an element read is anomalous.
     * @throws InvalidReadException if a card misses some data.
     */
    private List<DevelopmentCard> readDevelopmentCardArray(JsonReader reader) throws IOException, NoSuchElementException, InvalidReadException {
        List<DevelopmentCard> developmentCards = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            developmentCards.add(readDevelopmentCard(reader));
        }
        reader.endArray();
        return developmentCards;
    }

    /**
     * This method reads the first development card following the reader passed as parameter.
     * @param reader Json reader which points to a specific development card inside a Json file.
     * @return a development card with all the features described in the file read by reader.
     * @throws IOException if a general error occurred reading the specified file.
     * @throws NoSuchElementException if an element read is anomalous.
     * @throws InvalidReadException if a card misses some data.
     */
    private DevelopmentCard readDevelopmentCard(JsonReader reader) throws IOException, NoSuchElementException, InvalidReadException {
        int victoryPoints = -1;
        int level = -1;
        int ID = -1;
        Color color = null;
        Map<Resource, Integer> ResourceCost = null;
        Map<Resource, Integer> ResourceIn = null;
        Map<Benefit, Integer> ResourceOut = null;
        String bufferColor;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "ID":
                    ID = reader.nextInt();
                    break;
                case "VictoryPoints":
                    victoryPoints = reader.nextInt();
                    if(victoryPoints < 0) throw new NoSuchElementException("Negative victory points are not allowed. Founded VICTORY POINTS == " + victoryPoints );
                    break;
                case "Level":
                    level = reader.nextInt();
                    if(level > 3 || level < 1) throw new NoSuchElementException(level + " is out of level's range");
                    break;
                case "Color":
                    bufferColor = reader.nextString();
                    switch (bufferColor) {
                        case "YELLOW":
                            color = YELLOW;
                            break;
                        case "BLUE":
                            color = BLUE;
                            break;
                        case "PURPLE":
                            color = PURPLE;
                            break;
                        case "GREEN":
                            color = GREEN;
                            break;
                        default:
                            throw new NoSuchElementException(bufferColor + " is not an expected color");
                    }
                    break;
                case "ResourceCost":
                    ResourceCost = readResources(reader);
                    break;
                case "ResourceIn":
                    ResourceIn = readResources(reader);
                    break;
                case "BenefitOut":
                    ResourceOut = readBenefits(reader);
                    break;
                default:
                    throw new NoSuchElementException(name + " is an undefined field");
            }
        }
        reader.endObject();
        if(ID == -1) throw new IOException("This card misses the ID");
        if(victoryPoints ==  -1) throw new InvalidReadException("This card misses VictoryPoints");
        if(level ==  -1) throw new InvalidReadException("This card misses level");
        if(color ==  null) throw new InvalidReadException("This card misses color");
        if(ResourceCost ==  null) throw new InvalidReadException("This card misses ResourceCost");
        if(ResourceIn ==  null) throw new InvalidReadException("This card misses ResourceIn");
        if(ResourceOut ==  null) throw new InvalidReadException("This card misses ResourceOut");

        return new DevelopmentCard(ID, victoryPoints, level, color, ResourceCost, ResourceIn, ResourceOut);
    }


    /**
     * This method is used to read a Map which keys are Resources and values are integers.
     * @param reader Json reader which points to a Map<Resource, Integer>.
     * @return the Map read by 'reader'.
     * @throws IOException if a general error occurred reading the specified file.
     */
    private Map<Resource, Integer> readResources(JsonReader reader) throws IOException{
        int integerBuffer ;
        Map <Resource, Integer> resources = new HashMap<>();
        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "STONE":
                    integerBuffer = reader.nextInt();
                    if (integerBuffer > 0) resources.put(Resource.STONE, integerBuffer);
                    else if(integerBuffer < 0) throw new NoSuchElementException(integerBuffer + " is out of Resources range");
                    break;
                case "SHIELD":
                    integerBuffer = reader.nextInt();
                    if (integerBuffer > 0) resources.put(Resource.SHIELD, integerBuffer);
                    else if(integerBuffer < 0) throw new NoSuchElementException(integerBuffer + " is out of Resources range");
                    break;
                case "COIN":
                    integerBuffer = reader.nextInt();
                    if (integerBuffer > 0) resources.put(Resource.COIN, integerBuffer);
                    else if(integerBuffer < 0) throw new NoSuchElementException(integerBuffer + " is out of Resources range");
                    break;
                case "SERVANT":
                    integerBuffer = reader.nextInt();
                    if (integerBuffer > 0) resources.put(Resource.SERVANT, integerBuffer);
                    else if(integerBuffer < 0) throw new NoSuchElementException(integerBuffer + " is out of Resources range");
                    break;
                default:
                    throw new NoSuchElementException(name + " is not a resource ");
            }
        }
        reader.endObject();
        return resources;
    }

    /**
     * This method is used to read a Map which keys are Benefits and values are integers.
     * @param reader Json reader which points to a Map<Benefit, Integer>.
     * @return the Map read by 'reader'.
     * @throws IOException if a general error occurred reading the specified file.
     */
    private Map<Benefit, Integer> readBenefits(JsonReader reader) throws IOException{
        int integerBuffer;
        Map <Benefit, Integer> benefits = new HashMap<>();
        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "STONE":
                    integerBuffer = reader.nextInt();
                    if (integerBuffer > 0) benefits.put(Resource.STONE, integerBuffer);
                    else if(integerBuffer < 0) throw new NoSuchElementException(integerBuffer + " is out of Benefits range");
                    break;
                case "SHIELD":
                    integerBuffer = reader.nextInt();
                    if (integerBuffer > 0) benefits.put(Resource.SHIELD, integerBuffer);
                    else if(integerBuffer < 0) throw new NoSuchElementException(integerBuffer + " is out of Benefits range");
                    break;
                case "COIN":
                    integerBuffer = reader.nextInt();
                    if (integerBuffer > 0) benefits.put(Resource.COIN, integerBuffer);
                    else if(integerBuffer < 0) throw new NoSuchElementException(integerBuffer + " is out of Benefits range");
                    break;
                case "SERVANT":
                    integerBuffer = reader.nextInt();
                    if (integerBuffer > 0) benefits.put(Resource.SERVANT, integerBuffer);
                    else if(integerBuffer < 0) throw new NoSuchElementException(integerBuffer + " is out of Benefits range");
                    break;
                case "FAITH":
                    integerBuffer = reader.nextInt();
                    if (integerBuffer > 0) benefits.put(Faith.giveFaith(), integerBuffer);
                    else if(integerBuffer < 0) throw new NoSuchElementException(integerBuffer + " is out of Benefits range");
                    break;
                default:
                    throw new NoSuchElementException(name + " is not a Benefit ");
            }
        }
        reader.endObject();
        return benefits;
    }
}
