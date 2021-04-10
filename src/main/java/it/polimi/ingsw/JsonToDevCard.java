package it.polimi.ingsw;

import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.benefit.Benefit;
import it.polimi.ingsw.model.benefit.Faith;
import it.polimi.ingsw.model.benefit.Resource;
import it.polimi.ingsw.model.exceptions.InvalidReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import static it.polimi.ingsw.model.Color.*;



public class JsonToDevCard {
    /**
     *
     * @param in is a specified FileInputStream used to open Json file with development cards information.
     * @return a list of DevelopmentCard with all the cards read from the file.
     * @throws IOException if a general error occurred opening/reading/closing the specified file.
     * @throws NoSuchElementException if an element read is anomalous
     * @throws InvalidReadException if a card misses some data
     */
    public List<DevelopmentCard> readJsonDevelopmentCard(InputStream in) throws IOException, NoSuchElementException, InvalidReadException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return readDevelopmentCardArray(reader);
        }
    }

    private List<DevelopmentCard> readDevelopmentCardArray(JsonReader reader) throws IOException, NoSuchElementException, InvalidReadException {
        List<DevelopmentCard> developmentCards = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            developmentCards.add(readDevelopmentCard(reader));
        }
        reader.endArray();
        return developmentCards;
    }

    private DevelopmentCard readDevelopmentCard(JsonReader reader) throws IOException, NoSuchElementException, InvalidReadException {
        int victoryPoints = -1;
        int level = -1;
        Color color = null;
        Map<Resource, Integer> ResourceCost = null;
        Map<Resource, Integer> ResourceIn = null;
        Map<Benefit, Integer> ResourceOut = null;
        String bufferColor;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
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
        if(victoryPoints ==  -1) throw new InvalidReadException("This card misses VictoryPoints");
        if(level ==  -1) throw new InvalidReadException("This card misses level");
        if(color ==  null) throw new InvalidReadException("This card misses color");
        if(ResourceCost ==  null) throw new InvalidReadException("This card misses ResourceCost");
        if(ResourceIn ==  null) throw new InvalidReadException("This card misses ResourceIn");
        if(ResourceOut ==  null) throw new InvalidReadException("This card misses ResourceOut");

        return new DevelopmentCard(victoryPoints, level, color, ResourceCost, ResourceIn, ResourceOut);
    }



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
